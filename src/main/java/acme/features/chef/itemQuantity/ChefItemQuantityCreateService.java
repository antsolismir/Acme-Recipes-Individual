package acme.features.chef.itemQuantity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.ItemType;
import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class ChefItemQuantityCreateService implements AbstractCreateService<Chef, ItemQuantity> {

	// Internal state ---------------------------------------------------------

		@Autowired
		protected ChefItemQuantityRepository repository;

		// AbstractCreateService<Administrator, Announcement> interface --------------


		@Override
		public boolean authorise(final Request<ItemQuantity> request) {
			
			boolean result;
			int id;
			id = request.getModel().getInteger("masterId");

			Recipe recipe;
			recipe = this.repository.findRecipeById(id);

			result = request.isPrincipal(recipe.getChef()) && !recipe.isPublished();

			return result;
		}

		@Override
		public void bind(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			request.bind(entity, errors, "amount");
			int itemId;
			itemId = request.getModel().getInteger("items");
	        
	        Item item;
	        item = this.repository.findItemById(itemId);
	        entity.setItem(item);
		}

		@Override
		public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;
			
			int masterId;
			masterId =  request.getModel().getInteger("masterId");
			List<Item> items; 
			
			Collection<Item> itemsInRecipe;
			itemsInRecipe = this.repository.findManyItemsByMasterId(masterId);
			items=this.repository.findAllItems().stream().filter(x->!itemsInRecipe.contains(x)).collect(Collectors.toList());
			request.unbind(entity, model,"amount","recipe");
			model.setAttribute("items",items );
			model.setAttribute("masterId",masterId);
			model.setAttribute("itemSize", items.size());
		}

		@Override
		public ItemQuantity instantiate(final Request<ItemQuantity> request) {
			assert request != null;

			ItemQuantity result;
			Recipe recipe;
			
			
			recipe = this.repository.findRecipeById(request.getModel().getInteger("masterId"));
			result = new ItemQuantity();
			result.setRecipe(recipe);
			return result;
		}

		@Override
		public void validate(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			if(!errors.hasErrors("amount")) {
				Item item;
				item = this.repository.findItemById(request.getModel().getInteger("items"));
				errors.state(request, !(entity.getAmount()!=1&&item.getItemType().equals(ItemType.KITCHEN_UTENSIL)), "amount", "chef.itemQuantity.form.error.invalidAmount");
			}
		}

		@Override
		public void create(final Request<ItemQuantity> request, final ItemQuantity entity) {
			assert request != null;
			assert entity != null;
			this.repository.save(entity);
		}

}