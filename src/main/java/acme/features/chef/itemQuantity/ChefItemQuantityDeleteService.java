package acme.features.chef.itemQuantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Chef;
@Service
public class ChefItemQuantityDeleteService implements AbstractDeleteService<Chef, ItemQuantity>{

	@Autowired
	protected ChefItemQuantityRepository repository;
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;

        boolean result;
        int id;
        id = request.getModel().getInteger("id");

        ItemQuantity itemQuantity;
        itemQuantity = this.repository.findItemQuantityById(id);

        result = request.isPrincipal(itemQuantity.getRecipe().getChef());


        return result;

	}

	@Override
	public void bind(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors,"amount","item","recipe");		
	}

	@Override
	public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "amount","item","recipe");		
	}

	@Override
	public ItemQuantity findOne(final Request<ItemQuantity> request) {
		assert request != null;

		ItemQuantity result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findItemQuantityById(id);

		return result;
	}

	@Override
	public void validate(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;		
	}

	@Override
	public void delete(final Request<ItemQuantity> request, final ItemQuantity entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
		
	}
}
