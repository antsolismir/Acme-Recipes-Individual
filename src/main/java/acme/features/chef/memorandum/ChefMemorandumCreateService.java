/*
 * AdministratorPatronageReportCreateService.java
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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.entities.memorandum.Memorandum;
import acme.features.chef.dish.ChefDishRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class ChefMemorandumCreateService implements AbstractCreateService<Chef, Memorandum> {

	
	@Autowired 
	protected ChefDishRepository repository;
	
	@Autowired
	protected ChefMemorandumRepository memorandumRepository;


	@Override
	public boolean authorise(final Request<Memorandum> request) {
		assert request != null;

		Dish dish;
		int id;
		int userId;
		boolean result;
		
		id = request.getModel().getInteger("masterId");
		dish = this.repository.findDishById(id);
		
		userId = request.getPrincipal().getAccountId();
		result = userId == dish.getChef().getUserAccount().getId(); 

		return result;
	}
	
	@Override
	public void bind(final Request<Memorandum> request, final Memorandum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors,"sequenceNumber","instantiationMoment", "report", "link", "dish.code");
		
	}

	@Override
	public void unbind(final Request<Memorandum> request, final Memorandum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model,"sequenceNumber","instantiationMoment", "report", "link", "dish.code");
		model.setAttribute("masterId", request.getModel().getAttribute("masterId"));
		model.setAttribute("confirmation", false);
		model.setAttribute("readonly", false);
	}

	private String generateSequence(final Dish dish){
		String pcode;
		String seq;
		final List<Memorandum> preports = this.memorandumRepository.findMemorandumByDishId(dish.getId());
		if (preports.isEmpty()) {
			pcode = dish.getCode();
			seq = "0001";
		}else {
			pcode = dish.getCode();
			final String[] actual = preports.get(preports.size()-1).getSequenceNumber().split(":");
			seq = String.format("%04d", Integer.valueOf(actual[1]) + 1);
		}
		
		return pcode + ":" + seq;
	}
	
	@Override
	public Memorandum instantiate(final Request<Memorandum> request) {
		assert request != null;
		int masterId;
		final Dish dish;
		Memorandum result;
		Date moment;
		masterId = request.getModel().getInteger("masterId");
		dish = this.repository.findDishById(masterId);
		moment = new Date(System.currentTimeMillis() - 1);
		final String sequenceNumber = this.generateSequence(dish);

		result = new Memorandum();
		result.setSequenceNumber(sequenceNumber);
		result.setInstantiationMoment(moment);
		result.setReport("");
		result.setLink("");
		result.setDish(dish);
		
		return result;
		
		
	}

	@Override
	public void validate(final Request<Memorandum> request, final Memorandum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		boolean confirmation;

		confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
		
	}

	@Override
	public void create(final Request<Memorandum> request, final Memorandum entity) {
		assert request != null;
		assert entity != null;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setInstantiationMoment(moment);
		this.repository.save(entity);		
	}
}


	