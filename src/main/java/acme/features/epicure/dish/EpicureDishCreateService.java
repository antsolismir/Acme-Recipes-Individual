package acme.features.epicure.dish;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.components.Status;
import acme.entities.dish.Dish;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Epicure;

@Service
public class EpicureDishCreateService implements AbstractCreateService<Epicure, Dish>{

	@Autowired
	protected EpicureDishRepository repository;

	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;

		return true;
	}

	@Override
	public Dish instantiate(final Request<Dish> request) {
			assert request != null;

			final Dish result;
			Date initialD;
			Date finalD;

			initialD = DateUtils.addMonths( new Date(System.currentTimeMillis() + 300000),1);
			finalD = DateUtils.addMonths( initialD,1);
			finalD = DateUtils.addMinutes(finalD, 1);


			result = new Dish();
			result.setRequest("");
			result.setInitialPeriodDate(initialD);
			result.setFinalPeriodDate(finalD);
			result.setEpicure(this.repository.findEpicureById(request.getPrincipal().getActiveRoleId()).orElse(null));


			return result;
		}

		@Override
		public void bind(final Request<Dish> request, final Dish entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;
			
			Date moment;
			moment = new Date(System.currentTimeMillis() - 1);
			entity.setCreationDate(moment);
			entity.setStatus(Status.PROPOSED);
			entity.setPublished(false);
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
				errors.state(request, existing == null, "code", "epicure.dish.form.error.duplicated");
			}
			
			
			if(!errors.hasErrors("initialPeriodDate")) {
				final Date minInitialDate=DateUtils.addMonths(entity.getCreationDate(), 1);

				errors.state(request,entity.getInitialPeriodDate().after(minInitialDate), "initialPeriodDate", "epicure.dish.form.error.too-close-start-date");
				
			}
			if(!errors.hasErrors("finalPeriodDate") && !errors.hasErrors("initialPeriodDate")) {
				final Date minFinishDate=DateUtils.addMonths(entity.getInitialPeriodDate(), 1);
				
				errors.state(request,entity.getFinalPeriodDate().after(minFinishDate), "finalPeriodDate", "epicure.dish.form.error.one-month");
				
			}
			
			
			if (!errors.hasErrors("budget")) {
				final Boolean acceptedCurrency= this.repository.findSystemConfiguration().getAcceptedCurrencies()
					.matches("(.*)"+entity.getBudget().getCurrency()+"(.*)");
				
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

			model.setAttribute("chefs", this.repository.findAllChefs());

			request.unbind(entity, model, "code", "request", "budget", "initialPeriodDate", "finalPeriodDate","link","published");
			

		}

		@Override
		public void create(final Request<Dish> request, final Dish entity) {
			assert request != null;
			assert entity != null;
			
			this.repository.save(entity);
		}
		
}