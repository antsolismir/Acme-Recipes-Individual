package acme.features.chef.quotelas;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.entities.quotelas.Quotelas;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;
//examen
@Service
public class ChefQuotelasUpdateService implements AbstractUpdateService<Chef, Quotelas>{

	@Autowired
	protected ChefQuotelasRepository repository;
	
	@Override
	public boolean authorise(final Request<Quotelas> request) {
		assert request != null;

		boolean result;
		int quotelasId;
		Quotelas quotelas;

		quotelasId = request.getModel().getInteger("id");
		quotelas = this.repository.findQuotelasById(quotelasId);
		result = (quotelas != null && request.isPrincipal(quotelas.getItem().getChef()));

		return result;
	}
	
	@Override
	public Quotelas findOne(final Request<Quotelas> request) {
		assert request != null;
		
		Quotelas result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findQuotelasById(id);

		return result;
	}
	
	@Override
	public void bind(final Request<Quotelas> request, final Quotelas entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		entity.setItem(this.repository.findItemById(Integer.valueOf(request.getModel().getAttribute("itemId").toString())));
		
		request.bind(entity, errors, "name", "explanation", "initialInterval", "finalInterval", "share", "additionalInfo");
	}
	
	@Override
	public void validate(final Request<Quotelas> request, final Quotelas entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("code")) {
			Quotelas existing;

			existing = this.repository.findQuotelasByCode(entity.getCode());
			if(existing!=null) {
			errors.state(request, existing.getId()==entity.getId() , "code", "chef.quotela.form.error.duplicated");
			}
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
			final Boolean acceptedCurrency=this.repository.findSystemConfiguration().getAcceptedCurrencies().matches("(.*)"+entity.getShare().getCurrency()+"(.*)");
			
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
		model.setAttribute("itemId", entity.getItem().getId());
		model.setAttribute("published", entity.getItem().getPublished());
		request.unbind(entity, model, "name", "explanation", "initialInterval", "finalInterval", "share", "additionalInfo");
		request.unbind(entity, model, "item.code", "item.name");
	}
	
	@Override
	public void update(final Request<Quotelas> request, final Quotelas entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
