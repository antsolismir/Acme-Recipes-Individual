package acme.features.chef.dish;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;

@Service
public class ChefDishListService implements AbstractListService<Chef,Dish>{
	
	@Autowired
	protected ChefDishRepository repository;
	
	@Autowired
	protected ChefDishMoneyExchange chefDishMoneyExchange;
	
	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<Dish> findMany(final Request<Dish> request) {
		assert request != null;
		
		Principal principal;
		int chefId;
		Collection<Dish> result;
		
		principal = request.getPrincipal();
		chefId = principal.getActiveRoleId();
		result = this.repository.findDishByChefId(chefId);

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
	        	priceExchanged=this.chefDishMoneyExchange.computeMoneyExchange(entity.getBudget(), systemCurrency);
				i++;
			}
	        try {
				model.setAttribute("money", priceExchanged.getTarget());
			} catch (final Exception e) {
				model.setAttribute("money", "API unavailable at the moment");
			}
		
		request.unbind(entity, model, "status", "code", "budget", "creationDate");
	}

}
