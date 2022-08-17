
package acme.features.any.recipe;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
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
		
		return result;
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
		
		for(final ItemQuantity a :itemQuantitys) {
		final Money itemPrice=a.getItem().getRetailPrice();
			price = price + a.getAmount()*itemPrice.getAmount();
		}
		
		moneyInternational=new Money();
		moneyInternational.setAmount(price);
		moneyInternational.setCurrency(systemCurrency);
		
		model.setAttribute("money", moneyInternational);
		
		request.unbind(entity, model,"code", "heading");
	}
	

}