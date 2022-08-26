/*
 * EmployertPatronageReportListService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.chef.memorandum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.entities.memorandum.Memorandum;
import acme.features.chef.dish.ChefDishRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;

@Service
public class ChefGroupMemorandumListService implements AbstractListService<Chef, Memorandum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ChefMemorandumRepository repository;
	
	@Autowired
	protected ChefDishRepository dishRepository;

	@Override
	public boolean authorise(final Request<Memorandum> request) {
		assert request != null;

		boolean result;
		int masterId;
		Dish dish;

		masterId = request.getModel().getInteger("masterId");
		dish = this.dishRepository.findDishById(masterId);
		result = dish != null;

		return result;
	}

	@Override
	public Collection<Memorandum> findMany(final Request<Memorandum> request) {
		assert request != null;

		Collection<Memorandum> result;
		int masterId;

		masterId = request.getModel().getInteger("masterId");
		result = this.repository.findOneMemorandumByDish(masterId);

		return result;
	}

	@Override
	public void unbind(final Request<Memorandum> request, final Collection<Memorandum> entities, final Model model) {
		assert request != null;
		assert !CollectionHelper.someNull(entities);
		assert model != null;

		int masterId;
		Dish dish;
		final boolean showCreate;

		masterId = request.getModel().getInteger("masterId");
		dish = this.dishRepository.findDishById(masterId);
		showCreate = request.isPrincipal(dish.getChef());

		model.setAttribute("masterId", masterId);
		model.setAttribute("showCreate", showCreate);
	}
	
	@Override
	public void unbind(final Request<Memorandum> request, final Memorandum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "sequenceNumber", "instantiationMoment");

	}


}
