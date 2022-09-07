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
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;
//examen
@Service
public class ChefPimpamUpdateService implements AbstractUpdateService<Chef, Pimpam>{

	@Autowired
	protected ChefPimpamRepository repository;
	
	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request != null;

		boolean result;
		int pimpamId;
		Pimpam pimpam;

		pimpamId = request.getModel().getInteger("id");
		pimpam = this.repository.findPimpamById(pimpamId);
		result = (pimpam != null && request.isPrincipal(pimpam.getItem().getChef()));

		return result;
	}
	
	@Override
	public Pimpam findOne(final Request<Pimpam> request) {
		assert request != null;
		
		Pimpam result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findPimpamById(id);

		return result;
	}
	
	@Override
	public void bind(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		entity.setItem(this.repository.findItemById(Integer.valueOf(request.getModel().getAttribute("itemId").toString())));
		
		request.bind(entity, errors, "title", "description", "initialPeriodDate", "finalPeriodDate", "budget", "link");
	}
	
	@Override
	public void validate(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("code")) {
			Pimpam existing;

			existing = this.repository.findPimpamByCode(entity.getCode());
			if(existing!=null) {
			errors.state(request, existing.getId()==entity.getId() , "code", "chef.pimpam.form.error.duplicated");
			}
		}
		
		
		if(entity.getInitialPeriodDate()==null || entity.getFinalPeriodDate() == null) {
			errors.state(request, entity.getInitialPeriodDate()!=null, "initialPeriodDate", "message.error.null.date");
			errors.state(request, entity.getFinalPeriodDate()!=null, "finalPeriodDate", "message.error.null.date");
		} else {
		
			if(!errors.hasErrors("initialPeriodDate")) {
				final Date minStartDate=DateUtils.addMonths(entity.getInstantiationMoment(), 1);
				errors.state(request, entity.getInitialPeriodDate().after(minStartDate), "initialPeriodDate", "chef.pimpam.form.error.too-close-start-date");
			}
			if(!errors.hasErrors("finalPeriodDate") && !errors.hasErrors("initialPeriodDate")) {
				final Date minFinishDate=DateUtils.addWeeks(entity.getInitialPeriodDate(), 1);
				errors.state(request, entity.getFinalPeriodDate().after(minFinishDate), "finalPeriodDate", "chef.pimpam.form.error.complete");
			}
			if(!errors.hasErrors("finalPeriodDate")) {
				final Date minFinishDate=DateUtils.addWeeks(entity.getInitialPeriodDate(), 1);
				errors.state(request, entity.getFinalPeriodDate().after(minFinishDate), "finalPeriodDate", "chef.pimpam.form.error.one-month");
			}
			
		}
		
		if (!errors.hasErrors("budget")) {
			final Boolean acceptedCurrency=this.repository.findSystemConfiguration().getAcceptedCurrencies().matches("(.*)"+entity.getBudget().getCurrency()+"(.*)");
			
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "chef.pimpam.form.error.negative-budget");
			errors.state(request, acceptedCurrency, "budget", "chef.pimpam.form.error.non-accepted-currency");
		}
		
		if(!errors.hasErrors("description")) {
			final boolean isRequestSpam = SpamDetector.isSpam(entity.getDescription(), this.repository.findSystemConfiguration());
			errors.state(request, !isRequestSpam, "description", "chef.pimpam.form.error.spam");
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
		model.setAttribute("itemId", entity.getItem().getId());
		model.setAttribute("published", entity.getItem().getPublished());
		request.unbind(entity, model, "title", "description", "initialPeriodDate", "finalPeriodDate", "budget", "link");
		request.unbind(entity, model, "item.code", "item.name");
	}
	
	@Override
	public void update(final Request<Pimpam> request, final Pimpam entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
}
