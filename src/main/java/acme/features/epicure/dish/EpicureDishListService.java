package acme.features.epicure.dish;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Epicure;

@Service
public class EpicureDishListService implements AbstractListService<Epicure,Dish>{
	
	@Autowired
	protected EpicureDishRepository repository;
	
	@Autowired
	protected EpicureDishMoneyExchange epicureDishMoneyExchange;
	
	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<Dish> findMany(final Request<Dish> request) {
		assert request != null;
		
		Principal principal;
		int epicureId;
		Collection<Dish> result;
		
		principal = request.getPrincipal();
		epicureId = principal.getActiveRoleId();
		result = this.repository.findDishByEpicureId(epicureId);

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
	        	System.out.println(i);
			}
	        try {
				model.setAttribute("money", priceExchanged.getTarget());
			} catch (final Exception e) {
				model.setAttribute("money", "La API ha fallado");
			}

		request.unbind(entity, model, "status", "code", "budget", "creationDate", "published");

	}

}