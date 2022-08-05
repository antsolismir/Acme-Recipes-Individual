package acme.features.chef.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefItemShowService implements AbstractShowService<Chef, Item> {
	
	@Autowired
	protected ChefItemRepository repository;
	

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		
		Item item;
		int id;
		int userId;
		boolean result;
		
		id = request.getModel().getInteger("id");
		item = this.repository.findItemById(id);
		userId = request.getPrincipal().getAccountId();

		result = userId == item.getChef().getUserAccount().getId(); 
		return result;
		
	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;
		Item result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findItemById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		
		request.unbind(entity, model, "name", "code", "description", "retailPrice", "published" ,"link", "itemType");
		
	}

}
