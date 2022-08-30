package acme.features.chef.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefItemShowService implements AbstractShowService<Chef, Item> {
	
	@Autowired
	protected ChefItemRepository repository;
	
	@Autowired
	protected ChefItemMoneyExchange chefItemMoneyExchange;

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
		
        final String systemCurrency= this.repository.getDefaultCurrency();
		final Money priceExchanged=this.chefItemMoneyExchange.computeMoneyExchange(entity.getRetailPrice(), systemCurrency).getTarget();
		model.setAttribute("money", priceExchanged);
		
		request.unbind(entity, model, "name", "code", "description", "retailPrice", "published" ,"link", "itemType");
		
	}

}
