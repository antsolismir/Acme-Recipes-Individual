package acme.features.chef.pimpam;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.entities.pimpam.Pimpam;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;
//examen
@Service
public class ChefPimpamCreateService  implements AbstractCreateService<Chef, Pimpam> {

	@Autowired
	protected ChefPimpamRepository repository;

	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Pimpam instantiate(final Request<Pimpam> request) {
			assert request != null;

			Pimpam result;
			Date initialD;
			Date finalD;

			initialD = DateUtils.addMonths( new Date(System.currentTimeMillis() + 300000),1);
			finalD = DateUtils.addMonths( initialD,1);
			finalD = DateUtils.addMinutes(finalD, 1);

			result = new Pimpam();
			result.setDescription("");
			result.setInitialPeriodDate(initialD);
			result.setFinalPeriodDate(finalD);

			return result;
	}

	@Override
	public void bind(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setInstantiationMoment(moment);
		entity.setItem(this.repository.findItemById(Integer.valueOf(request.getModel().getAttribute("itemId").toString())));
		
		request.bind(entity, errors, "code", "title", "description", "initialPeriodDate", "finalPeriodDate", "budget", "link");
	}
	
	@Override
	public void validate(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("code")) {
			Pimpam existing;

			existing = this.repository.findPimpamByCode(entity.getCode());
			errors.state(request, existing == null, "code", "chef.pimpam.form.error.duplicated");
		}
		
		
		if(!errors.hasErrors("initialPeriodDate")) {
			final Date minInitialDate=DateUtils.addMonths(entity.getInstantiationMoment(), 1);

			errors.state(request,entity.getInitialPeriodDate().after(minInitialDate), "initialPeriodDate", "chef.pimpam.form.error.too-close-start-date");
			
		}
		if(!errors.hasErrors("finalPeriodDate") && !errors.hasErrors("initialPeriodDate")) {
			final Date minFinishDate=DateUtils.addMonths(entity.getInitialPeriodDate(), 1);
			
			errors.state(request,entity.getFinalPeriodDate().after(minFinishDate), "finalPeriodDate", "chef.pimpam.form.error.one-month");
		}
		
		
		if (!errors.hasErrors("budget")) {
			final Boolean acceptedCurrency= this.repository.findSystemConfiguration().getAcceptedCurrencies()
				.matches("(.*)"+entity.getBudget().getCurrency()+"(.*)");
			
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "chef.pimpam.form.error.negative-budget");
			errors.state(request, acceptedCurrency, "budget", "chef.pimpam.form.error.non-accepted-currency");
		}
		
		if(!errors.hasErrors("description")) {
			final boolean isRequestSpam = SpamDetector.isSpam(entity.getDescription(), this.repository.findSystemConfiguration());
			errors.state(request, !isRequestSpam, "request", "chef.pimpam.form.error.spam");
		}
		
		if(!errors.hasErrors("title")) {
			final boolean isRequestSpam = SpamDetector.isSpam(entity.getTitle(), this.repository.findSystemConfiguration());
			errors.state(request, !isRequestSpam, "title", "chef.pimpam.form.error.spam");
		}
	}
	
	@Override
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("items", this.repository.findItems(request.getPrincipal().getActiveRoleId()));

		request.unbind(entity, model, "code", "title", "description", "initialPeriodDate", "finalPeriodDate","link","budget");
	}
	
	@Override
	public void create(final Request<Pimpam> request, final Pimpam entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
	
}
