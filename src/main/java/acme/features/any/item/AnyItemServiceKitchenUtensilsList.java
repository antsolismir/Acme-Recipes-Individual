/*
 * AuthenticatedConsumerCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

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
public class AnyItemServiceKitchenUtensilsList implements AbstractListService<Any, Item> {

	@Autowired
	protected AnyItemRepository anyKitchenUtensilsRepository;
	
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
			result = this.anyKitchenUtensilsRepository.findAllKitchenUtensilsByAny();
			return result;
		
	}
	
	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String systemCurrency= this.anyKitchenUtensilsRepository.getDefaultCurrency();
		final Money priceExchanged=this.anyItemMoneyExchange.computeMoneyExchange(entity.getRetailPrice(), systemCurrency).getTarget();
		model.setAttribute("money", priceExchanged);

		request.unbind(entity, model, "name", "retailPrice");
	}

}
