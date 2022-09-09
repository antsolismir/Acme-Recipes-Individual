package acme.features.chef.quotelas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.quotelas.Quotelas;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Chef;
//examen
@Service
public class ChefQuotelasDeleteService implements AbstractDeleteService<Chef, Quotelas> {
	
	@Autowired
	protected ChefQuotelasRepository repository;
	
	@Override
	public boolean authorise(final Request<Quotelas> request) {
		assert request != null;

		boolean result;
		int quotelasId;
		Quotelas quotelas;

		quotelasId = request.getModel().getInteger("id");
		quotelas = this.repository.findQuotelasById(quotelasId);
		result = (quotelas != null && request.isPrincipal(quotelas.getItem().getChef()));

		return result;
	}
	
	@Override
	public Quotelas findOne(final Request<Quotelas> request) {
		assert request != null;
		
		Quotelas result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findQuotelasById(id);

		return result;
	}
	
	@Override
	public void bind(final Request<Quotelas> request, final Quotelas entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
				
		request.bind(entity, errors, "name", "explanation", "initialInterval", "finalInterval", "share", "additionalInfo");
	}
	
	@Override
	public void unbind(final Request<Quotelas> request, final Quotelas entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "name", "explanation", "initialInterval", "finalInterval", "share", "additionalInfo");
	}

	@Override
	public void validate(final Request<Quotelas> request, final Quotelas entity, final Errors errors) {
		assert request != null;
        assert entity != null;
        assert errors != null;
	}

	@Override
	public void delete(final Request<Quotelas> request, final Quotelas entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
	}

}
