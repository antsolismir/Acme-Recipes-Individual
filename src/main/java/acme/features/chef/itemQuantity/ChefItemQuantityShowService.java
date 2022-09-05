package acme.features.chef.itemQuantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefItemQuantityShowService implements AbstractShowService<Chef, ItemQuantity> {
	
	@Autowired
	protected ChefItemQuantityRepository repo;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;

		boolean result;
		int id;
		id = request.getModel().getInteger("id");

		final ItemQuantity itemQuantity;
		itemQuantity = this.repo.findItemQuantityById(id);

		result = request.isPrincipal(itemQuantity.getRecipe().getChef());

		return result;
	}

	@Override
	public ItemQuantity findOne(final Request<ItemQuantity> request) {
		assert request != null;
		
		int id;
		ItemQuantity result;
		
		id = request.getModel().getInteger("id");
		result = this.repo.findItemQuantityById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
		assert request != null;
        assert entity != null;
        assert model != null;

        request.unbind(entity, model, "amount","item.name","item.itemType","item.code");
        model.setAttribute("published", entity.getRecipe().isPublished());

	}

}
