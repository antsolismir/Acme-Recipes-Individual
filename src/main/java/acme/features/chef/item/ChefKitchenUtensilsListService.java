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

package acme.features.chef.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;

@Service
public class ChefKitchenUtensilsListService implements AbstractListService<Chef, Item> {
	
	@Autowired
	protected ChefItemRepository repository;

	@Autowired
	protected ChefItemMoneyExchange chefItemMoneyExchange;
	
	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		return true;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		assert request != null;
		
		final Collection<Item> result;
		
		result = this.repository.findKitchenUtensilsByChef(request.getPrincipal().getAccountId());

		return result;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		final String systemCurrency= this.repository.getDefaultCurrency();
		 MoneyExchange priceExchanged = null;
	     Integer i=0;
	        while (priceExchanged == null && i<=50) {
	        	priceExchanged=this.chefItemMoneyExchange.computeMoneyExchange(entity.getRetailPrice(), systemCurrency);
				i++;
			}
	        try {
				model.setAttribute("money", priceExchanged.getTarget());
			} catch (final Exception e) {
				model.setAttribute("money", "API unavailable at the moment");
			}
		
		request.unbind(entity, model, "name", "retailPrice");

	}

}
