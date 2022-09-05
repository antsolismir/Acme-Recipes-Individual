package acme.features.chef.itemQuantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.ItemType;
import acme.entities.recipe.ItemQuantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefItemQuantityUpdateService implements AbstractUpdateService<Chef,ItemQuantity>{

    @Autowired
    protected ChefItemQuantityRepository repository;

    @Override
    public boolean authorise(final Request<ItemQuantity> request) {
    	assert request != null;

		boolean result;
		int id;
		id = request.getModel().getInteger("id");

		final ItemQuantity itemQuantity;
		itemQuantity = this.repository.findItemQuantityById(id);

		result = request.isPrincipal(itemQuantity.getRecipe().getChef());

		return result;
    }

    @Override
    public void bind(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
        assert request != null;
        assert entity != null;
        assert errors != null;

        request.bind(entity, errors,"amount");

    }

    @Override
    public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
        assert request != null;
        assert entity != null;
        assert model!=null;
        request.unbind(entity, model,"amount","item.name","item.itemType","item.code");
        model.setAttribute("published", entity.getRecipe().isPublished());
    }

    @Override
    public ItemQuantity findOne(final Request<ItemQuantity> request) {
    	ItemQuantity result;
        final int id =request.getModel().getInteger("id");

        result=this.repository.findItemQuantityById(id);
        return result;
    }

    @Override
    public void validate(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
        assert request != null;
        assert entity != null;
        assert errors != null;
        if(!errors.hasErrors("amount")) {
			Item item;
			item = this.repository.findItemByCode(request.getModel().getString("item.code"));
			errors.state(request, !(entity.getAmount()!=1&&item.getItemType().equals(ItemType.KITCHEN_UTENSIL)), "amount", "chef.itemQuantity.form.error.invalidAmount");
		}
    }

    @Override
    public void update(final Request<ItemQuantity> request, final ItemQuantity entity) {
        assert request != null;
        assert entity != null;
        
        this.repository.save(entity);

    }

}