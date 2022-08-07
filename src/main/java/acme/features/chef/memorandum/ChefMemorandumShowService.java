package acme.features.chef.memorandum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.memorandum.Memorandum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;

@Service
public class ChefMemorandumShowService implements AbstractShowService<Chef, Memorandum>{
	
	@Autowired
	protected ChefMemorandumRepository repository;


	@Override
	public boolean authorise(final Request<Memorandum> request) {
		assert request != null;
		
		Memorandum memorandum;
		int id;
		int userId;
		boolean result;
		
		id = request.getModel().getInteger("id");
		memorandum = this.repository.findOneMemorandumById(id);
		
		userId = request.getPrincipal().getAccountId();
		result = userId == memorandum.getDish().getChef().getUserAccount().getId(); 

		return result;
	}

	@Override
	public void unbind(final Request<Memorandum> request, final Memorandum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "sequenceNumber", "instantiationMoment", "report", "link", "dish.code");
	}

	@Override
	public Memorandum findOne(final Request<Memorandum> request) {
		assert request != null;

		Memorandum result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneMemorandumById(id);

		return result;
	}


}
