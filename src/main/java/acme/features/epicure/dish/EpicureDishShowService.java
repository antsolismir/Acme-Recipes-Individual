package acme.features.epicure.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Epicure;

@Service
public class EpicureDishShowService implements AbstractShowService<Epicure,Dish> {
	
	@Autowired
	protected EpicureDishRepository repository;
	
	@Autowired
	protected EpicureDishMoneyExchange epicureDishMoneyExchange;
	
	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;
		
		boolean result;
		int dishId;
		Dish dish;

		dishId = request.getModel().getInteger("id");
		dish = this.repository.findDishById(dishId);
		result = (dish.getEpicure().getId() == request.getPrincipal().getActiveRoleId() || dish.getChef().getId() == request.getPrincipal().getActiveRoleId());

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
	public void unbind(final Request<Dish> request, final Dish entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		 final String systemCurrency= this.repository.getDefaultCurrency();
		 MoneyExchange priceExchanged = null;
	     Integer i=0;
	        while (priceExchanged == null && i<=50) {
	        	priceExchanged=this.epicureDishMoneyExchange.computeMoneyExchange(entity.getBudget(), systemCurrency);
				i++;
			}
	        try {
				model.setAttribute("money", priceExchanged.getTarget());
			} catch (final Exception e) {
				model.setAttribute("money", "API unavailable at the moment");
			}
		
		model.setAttribute("chefs", this.repository.findAllChefs());
		model.setAttribute("chefId", entity.getChef().getId());
		model.setAttribute("chefOrganisation", entity.getChef().getOrganisation());
		model.setAttribute("chefAssertion", entity.getChef().getAssertion());
		model.setAttribute("chefUserAccount",entity.getChef().getUserAccount().getUsername());
		model.setAttribute("status", entity.getStatus().toString());
		model.setAttribute("published", entity.getPublished());
		
		request.unbind(entity, model, "code", "request", "budget", "creationDate", "initialPeriodDate","finalPeriodDate","link");
		request.unbind(entity, model, "chef.organisation", "chef.assertion");
		request.unbind(entity, model, "chef.userAccount.username");

	
	}
}