package acme.features.chef.quotelas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.quotelas.Quotelas;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Chef;
//examen
@Service
public class ChefQuotelasShowService implements AbstractShowService<Chef,Quotelas>{

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
		result = (request.getPrincipal().getActiveRoleId() == quotelas.getItem().getChef().getId());

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
	public void unbind(final Request<Quotelas> request, final Quotelas entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "instantiationMoment", "name", "explanation", "initialInterval", "finalInterval","share","additionalInfo");
		request.unbind(entity, model, "item.code", "item.name");
		model.setAttribute("itemId", entity.getItem().getId());
		model.setAttribute("published", entity.getItem().getPublished());
	}
}
