package acme.features.epicure.dish;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.entities.dish.Dish;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Epicure;

@Service
public class EpicureDishUpdateService implements AbstractUpdateService<Epicure, Dish>{

	@Autowired
	protected EpicureDishRepository repository;
	

	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;

		boolean result;
		int dishId;
		Dish dish;

		dishId = request.getModel().getInteger("id");
		dish = this.repository.findDishById(dishId);
		result = (dish != null && !dish.getPublished() && request.isPrincipal(dish.getEpicure()));

		return result;
	}

	@Override
	public Dish findOne(final Request<Dish> request) {
		assert request != null;
		
		Dish result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findDishById(id);

		return result;
	}

	@Override
	public void bind(final Request<Dish> request, final Dish entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		entity.setChef(this.repository.findChefById(Integer.valueOf(request.getModel().getAttribute("chefId").toString())).orElse(null));
		
		request.bind(entity, errors, "code", "request", "budget", "initialPeriodDate", "finalPeriodDate","link");
	}

	@Override
	public void validate(final Request<Dish> request, final Dish entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("code")) {
			Dish existing;

			existing = this.repository.findDishByCode(entity.getCode());
			if(existing!=null) {
			errors.state(request, existing.getId()==entity.getId() , "code", "epicure.dish.form.error.duplicated");
			}
		}
		
		if(entity.getInitialPeriodDate()==null || entity.getFinalPeriodDate() == null) {
			errors.state(request, entity.getInitialPeriodDate()!=null, "initialPeriodDate", "message.error.null.date");
			errors.state(request, entity.getFinalPeriodDate()!=null, "finalPeriodDate", "message.error.null.date");
		} else {
		
			if(!errors.hasErrors("initialPeriodDate")) {
				final Date minStartDate=DateUtils.addMonths(entity.getCreationDate(), 1);
				errors.state(request, entity.getInitialPeriodDate().after(minStartDate), "initialPeriodDate", "epicure.dish.form.error.too-close-start-date");
			}
			if(!errors.hasErrors("finalPeriodDate") && !errors.hasErrors("initialPeriodDate")) {
				final Date minFinishDate=DateUtils.addMonths(entity.getInitialPeriodDate(), 1);
				errors.state(request, entity.getFinalPeriodDate().after(minFinishDate), "finalPeriodDate", "epicure.dish.form.error.complete");
			}
			if(!errors.hasErrors("finalPeriodDate")) {
				final Date minFinishDate=DateUtils.addMonths(entity.getInitialPeriodDate(), 1);
				errors.state(request, entity.getFinalPeriodDate().after(minFinishDate), "finalPeriodDate", "epicure.dish.form.error.one-month");
			}
			
		}
		
		
		if (!errors.hasErrors("budget")) {
			final Boolean acceptedCurrency=this.repository.findSystemConfiguration().getAcceptedCurrencies().matches("(.*)"+entity.getBudget().getCurrency()+"(.*)");
			
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "epicure.dish.form.error.negative-budget");
			errors.state(request, acceptedCurrency, "budget", "epicure.dish.form.error.non-accepted-currency");
		}
		
		if(!errors.hasErrors("request")) {
			final boolean isRequestSpam = SpamDetector.isSpam(entity.getRequest(), this.repository.findSystemConfiguration());
			errors.state(request, !isRequestSpam, "request", "epicure.dish.form.error.spam");
		}

	}

	@Override
	public void unbind(final Request<Dish> request, final Dish entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "request", "budget", "initialPeriodDate", "finalPeriodDate","link");
		model.setAttribute("chefs", this.repository.findAllChefs());
		model.setAttribute("chefId", entity.getChef().getId());
		model.setAttribute("status", entity.getStatus().toString());
		model.setAttribute("published", entity.getPublished());
		
	}

	@Override
	public void update(final Request<Dish> request, final Dish entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

	
}

