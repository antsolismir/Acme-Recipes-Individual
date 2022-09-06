package acme.features.chef.recipe;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefRecipeShowService implements AbstractShowService<Chef, Recipe> {
	
	@Autowired
	protected ChefRecipeRepository repo;
	
	@Autowired
	protected ChefRecipeMoneyExchange chefRecipeMoneyExchange;
	
	@Override
	public boolean authorise(final Request<Recipe> request) {
		 assert request != null;

	        boolean result;
	        int id;
	        id = request.getModel().getInteger("id");

	        Recipe recipe;
	        recipe = this.repo.findRecipeById(id);

	        result = request.isPrincipal(recipe.getChef());

	        return result;
	}

	@Override
	public Recipe findOne(final Request<Recipe> request) {
		assert request != null;
		
		int id;
		Recipe recipe;
		
		id = request.getModel().getInteger("id");
		recipe = this.repo.findRecipeById(id);
		
		return recipe;
	}

	@Override
	public void unbind(final Request<Recipe> request, final Recipe entity, final Model model) {
		assert request != null;
        assert entity != null;
        assert model != null;

		final String systemCurrency= this.repo.getDefaultCurrency();
		double price=0.;
		Money moneyInternational;
		final Collection<ItemQuantity> itemQuantitys = this.repo.findManyItemQuantityByRecipeId(entity.getId());
        try {		
			for(final ItemQuantity a :itemQuantitys) {
				final Money itemPrice=a.getItem().getRetailPrice();
				
				MoneyExchange priceExchanged = null;
			    Integer i=0;
		        while (priceExchanged == null && i<=50) {
		        	priceExchanged=this.chefRecipeMoneyExchange.computeMoneyExchange(itemPrice, systemCurrency);
					i++;
				}
				price += a.getAmount()*priceExchanged.getTarget().getAmount();
			}
	    	moneyInternational=new Money();
	    	moneyInternational.setAmount(price);
	    	moneyInternational.setCurrency(systemCurrency);
	    	model.setAttribute("money", moneyInternational);			
	    } catch (final Exception e) {
			model.setAttribute("money", "API unavailable at the moment");
		}

        request.unbind(entity, model, "code", "heading", "description", "preparationNotes", "info", "id", "published");
		
	}

}
