package acme.features.chef.memorandum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.memorandum.Memorandum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;

@Service
public class ChefMemorandumListService implements AbstractListService<Chef, Memorandum>{
	
	@Autowired
	protected ChefMemorandumRepository repository;


	@Override
	public boolean authorise(final Request<Memorandum> request) {
		assert request != null;
        
        return true;
	}

	@Override
	public void unbind(final Request<Memorandum> request, final Memorandum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "sequenceNumber", "instantiationMoment");
	}

	@Override
	public Collection<Memorandum> findMany(final Request<Memorandum> request) {
		assert request != null;
		
		Collection<Memorandum> result;
		
		final int idUsuario = request.getPrincipal().getAccountId();

		result = this.repository.findMemorandumById(idUsuario);
		
		return result;
	}

}
