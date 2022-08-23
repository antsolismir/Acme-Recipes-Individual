package acme.features.any.itemQuantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;

@Service
public class AnyItemQuantityShowService implements AbstractShowService<Any, ItemQuantity> {
	
	@Autowired
	protected AnyItemQuantityRepository repo;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;
		return true;
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
	}

}
