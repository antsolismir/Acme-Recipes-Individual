package acme.features.epicure.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dish.Dish;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Epicure;

@Service
public class EpicureDishPublishService implements AbstractUpdateService<Epicure, Dish>{

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
		entity.setChef(this.repository.findChefById(Integer.valueOf(request.getModel().getAttribute("chefId").toString())).orElse(null));


		request.bind(entity, errors, "published");
	}

	@Override
	public void validate(final Request<Dish> request, final Dish entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void unbind(final Request<Dish> request, final Dish entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "published");
		model.setAttribute("chefs", this.repository.findAllChefs());
		model.setAttribute("chefId", entity.getChef().getId());
		model.setAttribute("status", entity.getStatus().toString());
		
	}

	@Override
	public void update(final Request<Dish> request, final Dish entity) {
		assert request != null;
		assert entity != null;
		entity.setPublished(true);

		this.repository.save(entity);
	}

	
}