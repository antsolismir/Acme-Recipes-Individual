package acme.features.chef.quotelas;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.quotelas.Quotelas;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;
//examen
@Service
public class ChefQuotelasListService implements AbstractListService<Chef,Quotelas>{
	
	@Autowired
	protected ChefQuotelasRepository repository;
	
	@Override
	public boolean authorise(final Request<Quotelas> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<Quotelas> findMany(final Request<Quotelas> request) {
		assert request != null;
		
		Principal principal;
		int chefId;
		Collection<Quotelas> result;
		
		principal = request.getPrincipal();
		chefId = principal.getActiveRoleId();
		result = this.repository.findQuotelasByChefId(chefId);

		return result;
		
	}
	
	@Override
	public void unbind(final Request<Quotelas> request, final Quotelas entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("money", "API unavailable at the moment");
		request.unbind(entity, model, "code", "instantiationMoment", "name", "share");
	}

}
