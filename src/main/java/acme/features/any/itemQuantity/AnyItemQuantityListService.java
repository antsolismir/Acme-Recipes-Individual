package acme.features.any.itemQuantity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.ItemQuantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyItemQuantityListService implements AbstractListService<Any,ItemQuantity> {
	
	
	@Autowired
	protected AnyItemQuantityRepository repo;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;
		return true;
	}
	
	
	@Override
    public Collection<ItemQuantity> findMany(final Request<ItemQuantity> request) {
		int masterId;
		masterId = request.getModel().getInteger("masterId");
		return this.repo.findManyItemQuantityByRecipeId(masterId);
    }

	
    @Override
    public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
        assert request != null;
        assert entity != null;
        assert model != null;
        
        request.unbind(entity, model, "amount","item.name");
    }
}
