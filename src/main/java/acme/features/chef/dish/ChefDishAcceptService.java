package acme.features.chef.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Status;
import acme.entities.dish.Dish;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefDishAcceptService implements AbstractUpdateService<Chef,Dish> {
	
	//private static final Logger logeador = Logger.getLogger("acme.features.inventor.patronage.InventorPatronageUpdateService");
	
	@Autowired
	protected ChefDishRepository repository;

	

	@Override
	public void bind(final Request<Dish> request, final Dish entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "status","code","request","budget","creationDate","initialPeriodDate","finalPeriodDate","link");
		request.bind(entity, errors, "epicure.organisation", "epicure.assertion", "epicure.userAccount.username");
	}



	@Override
	public boolean authorise(final Request<Dish> request) {
		assert request != null;
		
		return true;
	}



	@Override
	public void unbind(final Request<Dish> request, final Dish entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "status", "code", "request", "budget","creationDate", "initialPeriodDate","finalPeriodDate","link");
		request.unbind(entity, model, "epicure.organisation", "epicure.assertion");
		request.unbind(entity, model, "epicure.userAccount.username");
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
	public void validate(final Request<Dish> request, final Dish entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}



	@Override
	public void update(final Request<Dish> request, final Dish entity) {
		assert request != null;
		assert entity != null;

		entity.setStatus(Status.ACCEPTED);
		this.repository.save(entity);
	}
	
}
