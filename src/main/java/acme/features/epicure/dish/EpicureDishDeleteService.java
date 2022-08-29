package acme.features.epicure.dish;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.entities.memorandum.Memorandum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Epicure;

@Service
public class EpicureDishDeleteService implements AbstractDeleteService<Epicure, Dish> {

	@Autowired
	protected EpicureDishRepository repository;

	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;

		boolean result;
		int dishId;
		Dish dish;

		dishId = request.getModel().getInteger("id");
		dish = this.repository.findDishById(dishId);
		result = (dish != null && !dish.getPublished() && request.isPrincipal(dish.getEpicure()));

		return result;
	}

	@Override
	public Dish findOne(final Request<Dish> request) {
		assert request != null;

		Dish result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findDishById(id);

		return result;
	}

	@Override
	public void bind(final Request<Dish> request, final Dish entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "request", "budget", "initialPeriodDate", "finalPeriodDate","link");
	}

	@Override
	public void unbind(final Request<Dish> request, final Dish entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "request", "budget", "initialPeriodDate", "finalPeriodDate","link","published");
	}

	@Override
    public void validate(final Request<Dish> request, final Dish entity, final Errors errors) {
        assert request != null;
        assert entity != null;
        assert errors != null;
       
    }

	@Override
	public void delete(final Request<Dish> request, final Dish entity) {
		assert request != null;
		assert entity != null;
		final Collection<Memorandum> memorandums = this.repository.findMemorandumsByDishId(entity.getId());
        if (memorandums.isEmpty()) {
        	this.repository.delete(entity);        
        }
	}
	
}