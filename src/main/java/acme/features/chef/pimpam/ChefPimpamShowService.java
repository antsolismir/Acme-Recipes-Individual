package acme.features.chef.pimpam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pimpam.Pimpam;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;
//examen
@Service
public class ChefPimpamShowService implements AbstractShowService<Chef,Pimpam>{

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
		result = (request.getPrincipal().getActiveRoleId() == pimpam.getItem().getChef().getId());

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
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "instantiationMoment", "title", "description", "initialPeriodDate", "finalPeriodDate","budget","link");
		request.unbind(entity, model, "item.code", "item.name");
		model.setAttribute("itemId", entity.getItem().getId());
		model.setAttribute("published", entity.getItem().getPublished());
	}
}
