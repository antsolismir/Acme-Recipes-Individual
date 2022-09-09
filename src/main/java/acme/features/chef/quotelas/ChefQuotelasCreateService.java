package acme.features.chef.quotelas;

import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.entities.quotelas.Quotelas;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;
//examen
@Service
public class ChefQuotelasCreateService  implements AbstractCreateService<Chef, Quotelas> {

	@Autowired
	protected ChefQuotelasRepository repository;

	@Override
	public boolean authorise(final Request<Quotelas> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Quotelas instantiate(final Request<Quotelas> request) {
			assert request != null;

			Quotelas result;
			Date initialD;
			Date finalD;

			initialD = DateUtils.addMonths( new Date(System.currentTimeMillis() + 300000),1);
			finalD = DateUtils.addMonths( initialD,1);
			finalD = DateUtils.addMinutes(finalD, 1);

			result = new Quotelas();
			result.setExplanation("");
			result.setInitialInterval(initialD);
			result.setFinalInterval(finalD);

			return result;
	}

	@Override
	public void bind(final Request<Quotelas> request, final Quotelas entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setInstantiationMoment(moment);
		
		entity.setItem(this.repository.findItemById(Integer.valueOf(request.getModel().getAttribute("itemId").toString())));
		request.bind(entity, errors, "code", "name", "explanation", "initialInterval", "finalInterval", "share", "additionalInfo");
	}
	
	@Override
	public void validate(final Request<Quotelas> request, final Quotelas entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		//examen
		if (!errors.hasErrors("code")) {
			Quotelas existing;

			existing = this.repository.findQuotelasByCode(entity.getCode());
			errors.state(request, existing == null, "code", "chef.quotela.form.error.duplicated");
			
			final String codeP = entity.getCode();
			final Integer year = Integer.parseInt("20"+codeP.charAt(9)+""+codeP.charAt(10));
			final Integer month = Integer.parseInt(codeP.charAt(7)+""+codeP.charAt(8));
			final Integer day =  Integer.parseInt(codeP.charAt(5)+""+codeP.charAt(6));
			
			final LocalDate dateCode = LocalDate.of(year,month,day);
			
			final LocalDate dateCorrect = LocalDate.now();
			
			errors.state(request,dateCode.equals(dateCorrect), "code", "chef.quotela.form.error.bad-code");
			
		}
		
		
		if(entity.getInitialInterval()==null || entity.getFinalInterval() == null) {
			errors.state(request, entity.getInitialInterval()!=null, "initialInterval", "message.error.null.date");
			errors.state(request, entity.getFinalInterval()!=null, "finalInterval", "message.error.null.date");
		} else {
		
			if(!errors.hasErrors("initialInterval")) {
				final Date minStartDate=DateUtils.addMonths(entity.getInstantiationMoment(), 1);
				errors.state(request, entity.getInitialInterval().after(minStartDate), "initialInterval", "chef.quotela.form.error.too-close-start-date");
			}
			if(!errors.hasErrors("finalInterval") && !errors.hasErrors("initialPeriodDate")) {
				final Date minFinishDate=DateUtils.addWeeks(entity.getInitialInterval(), 1);
				errors.state(request, entity.getFinalInterval().after(minFinishDate), "finalInterval", "chef.quotela.form.error.complete");
			}
			if(!errors.hasErrors("finalInterval")) {
				final Date minFinishDate=DateUtils.addWeeks(entity.getInitialInterval(), 1);
				errors.state(request, entity.getFinalInterval().after(minFinishDate), "finalInterval", "chef.quotela.form.error.one-month");
			}
			
		}
		
		
		if (!errors.hasErrors("share")) {
			final Boolean acceptedCurrency= this.repository.findSystemConfiguration().getAcceptedCurrencies()
				.matches("(.*)"+entity.getShare().getCurrency()+"(.*)");
			
			errors.state(request, entity.getShare().getAmount() > 0, "share", "chef.quotela.form.error.negative-budget");
			errors.state(request, acceptedCurrency, "share", "chef.quotela.form.error.non-accepted-currency");
		}
		
		if(!errors.hasErrors("explanation")) {
			final boolean isRequestSpam = SpamDetector.isSpam(entity.getExplanation(), this.repository.findSystemConfiguration());
			errors.state(request, !isRequestSpam, "explanation", "chef.quotela.form.error.spam");
		}
		
		if(!errors.hasErrors("name")) {
			final boolean isRequestSpam = SpamDetector.isSpam(entity.getName(), this.repository.findSystemConfiguration());
			errors.state(request, !isRequestSpam, "name", "chef.quotela.form.error.spam");
		}
	}
	
	@Override
	public void unbind(final Request<Quotelas> request, final Quotelas entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("items", this.repository.findItems(request.getPrincipal().getActiveRoleId()));
		
		request.unbind(entity, model, "code", "name", "explanation", "initialInterval", "finalInterval","additionalInfo","share");
	}
	
	@Override
	public void create(final Request<Quotelas> request, final Quotelas entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
	
}
