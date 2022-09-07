package acme.features.chef.pimpam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pimpam.Pimpam;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Chef;
//examen
@Service
public class ChefPimpamDeleteService implements AbstractDeleteService<Chef, Pimpam> {
	
	@Autowired
	protected ChefPimpamRepository repository;
	
	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request != null;

		boolean result;
		int pimpamId;
		Pimpam pimpam;

		pimpamId = request.getModel().getInteger("id");
		pimpam = this.repository.findPimpamById(pimpamId);
		result = (pimpam != null && request.isPrincipal(pimpam.getItem().getChef()));

		return result;
	}
	
	@Override
	public Pimpam findOne(final Request<Pimpam> request) {
		assert request != null;
		
		Pimpam result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findPimpamById(id);

		return result;
	}
	
	@Override
	public void bind(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
				
		request.bind(entity, errors, "title", "description", "initialPeriodDate", "finalPeriodDate", "budget", "link");
	}
	
	@Override
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "description", "initialPeriodDate", "finalPeriodDate", "budget", "link");
	}

	@Override
	public void validate(final Request<Pimpam> request, final Pimpam entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
	}

	@Override
	public void delete(final Request<Pimpam> request, final Pimpam entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
	}

}
