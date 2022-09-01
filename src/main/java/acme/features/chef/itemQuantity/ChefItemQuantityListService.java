package acme.features.chef.itemQuantity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;

@Service
public class ChefItemQuantityListService implements AbstractListService<Chef,ItemQuantity> {
	
	
	@Autowired
	protected ChefItemQuantityRepository repo;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request!=null;
		boolean result;
		int id;
		id = request.getModel().getInteger("masterId");

		Recipe recipe;
		recipe = this.repo.findRecipeById(id);

		result = request.isPrincipal(recipe.getChef());

		return result;
	}
	
	
	@Override
    public Collection<ItemQuantity> findMany(final Request<ItemQuantity> request) {
		int masterId;
		masterId = request.getModel().getInteger("masterId");
		return this.repo.findManyItemQuantityByRecipeId(masterId);
    }

	@Override
	public void unbind(final Request<ItemQuantity> request, final Collection<ItemQuantity> entities, final Model model) {
		assert request != null;
		assert !CollectionHelper.someNull(entities);
		assert model != null;

		int masterId;
		final Recipe recipe;
		boolean showCreate;

		masterId = request.getModel().getInteger("masterId");
		recipe = this.repo.findRecipeById(masterId);
		showCreate = (!recipe.isPublished() && request.isPrincipal(recipe.getChef()));

		model.setAttribute("masterId", masterId);
		model.setAttribute("showCreate", showCreate);
	}
	
    @Override
    public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
        assert request != null;
        assert entity != null;
        assert model != null;
        
        request.unbind(entity, model, "amount","item.name");
    }
}
