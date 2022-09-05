
package acme.features.any.recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyRecipeListService implements AbstractListService<Any, Recipe>{
	
	// Internal state -------------------------------------------------------------------
	
	@Autowired
	protected AnyRecipeRepository repository;
		
	@Autowired
	protected AnyRecipeMoneyExchange anyRecipeMoneyExchange;
	
	// AbstractListService<Authenticated, Recipe> interface ----------------------------
	
	@Override
	public boolean authorise(final Request<Recipe> request) {
		assert request != null;
		
		return true;
	}
	

	

	@Override
	public Collection<Recipe> findMany(final Request<Recipe> request) {
		assert request != null;
		
		Collection<Recipe> result;
		
		result = this.repository.findAllRecipes();
		final Collection<Recipe> finalResult = new ArrayList<Recipe>();
		finalResult.addAll(result);
		final Iterator<Recipe> resultIter = result.iterator();
		
		while(resultIter.hasNext()) {
			
			final Recipe recipe = resultIter.next();
			final Collection<ItemQuantity> hola = this.repository.findItemQuantityByRecipe(recipe.getId());

			if(hola.isEmpty()) {
				finalResult.remove(recipe);
			}
		}
		
		return finalResult;
	}

	@Override
	public void unbind( final Request<Recipe> request,  final Recipe entity,  final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String systemCurrency= this.repository.getDefaultCurrency();
		double price=0.;
		Money moneyInternational;
		final Collection<ItemQuantity> itemQuantitys = this.repository.findItemQuantityByRecipe(entity.getId());
        try {		
			for(final ItemQuantity a :itemQuantitys) {
				final Money itemPrice=a.getItem().getRetailPrice();
				
				MoneyExchange priceExchanged = null;
			    Integer i=0;
		        while (priceExchanged == null && i<=50) {
		        	priceExchanged=this.anyRecipeMoneyExchange.computeMoneyExchange(itemPrice, systemCurrency);
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
	
		request.unbind(entity, model,"code", "heading");
	}
	

}