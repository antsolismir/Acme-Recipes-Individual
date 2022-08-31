package acme.features.epicure.memorandum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.entities.memorandum.Memorandum;
import acme.features.epicure.dish.EpicureDishRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Epicure;

@Service
public class EpicureGroupMemorandumListService implements AbstractListService<Epicure, Memorandum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected EpicureMemorandumRepository repository;
	
	@Autowired
	protected EpicureDishRepository dishRepository;

	@Override
	public boolean authorise(final Request<Memorandum> request) {
		assert request != null;

		boolean result;
		int masterId;
		Dish dish;

		masterId = request.getModel().getInteger("masterId");
		dish = this.dishRepository.findDishById(masterId);
		
		result = dish != null;
		
		if(result) {
			result = request.getPrincipal().getActiveRoleId() == dish.getEpicure().getId();
		}

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
		showCreate = request.isPrincipal(dish.getEpicure());

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
