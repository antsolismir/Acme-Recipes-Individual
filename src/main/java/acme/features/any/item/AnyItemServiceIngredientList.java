package acme.features.any.item;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyItemServiceIngredientList implements AbstractListService<Any,Item>{
	

		@Autowired
		protected AnyItemRepository anyIngredientsRepository;

		@Autowired
		protected AnyItemMoneyExchange anyItemMoneyExchange;
		
		@Override
		public boolean authorise(final Request<Item> request) {
			assert request != null;
			
			return true;
		}

		@Override
		public Collection<Item> findMany(final Request<Item> request) {
			assert request != null;

			Collection<Item> result;

			result = this.anyIngredientsRepository.findAllIngredientsByAny();
			

			return result;
		}
		
		@Override
		public void unbind(final Request<Item> request, final Item entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			final String systemCurrency= this.anyIngredientsRepository.getDefaultCurrency();
			final Money priceExchanged=this.anyItemMoneyExchange.computeMoneyExchange(entity.getRetailPrice(), systemCurrency).getTarget();
			model.setAttribute("money", priceExchanged);
			
			request.unbind(entity, model, "name", "code","retailPrice");
		}
}
