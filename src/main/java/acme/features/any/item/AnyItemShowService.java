package acme.features.any.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;

@Service
public class AnyItemShowService implements AbstractShowService<Any, Item>{
	

	@Autowired
	protected AnyItemRepository repository;

	@Autowired
	protected AnyItemMoneyExchange anyItemMoneyExchange;

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		boolean result;
		int itemId;
		Item item;
		
		itemId = request.getModel().getInteger("id");
		item = this.repository.findOneAnyItemById(itemId);
		result = item.getPublished(); 

		return result;
	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;

		Item result;
		int id;
		id=request.getModel().getInteger("id");
		
		result = this.repository.findOneAnyItemById(id);
		
		return result;
	}
	
	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String systemCurrency= this.repository.getDefaultCurrency();
		final Money priceExchanged=this.anyItemMoneyExchange.computeMoneyExchange(entity.getRetailPrice(), systemCurrency).getTarget();
		model.setAttribute("money", priceExchanged);

		request.unbind(entity, model, "name", "code", "description", "retailPrice", "itemType", "published", "link");
	
		
		
	}
}
