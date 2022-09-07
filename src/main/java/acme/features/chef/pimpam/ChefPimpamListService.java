package acme.features.chef.pimpam;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.pimpam.Pimpam;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Chef;
//examen
@Service
public class ChefPimpamListService implements AbstractListService<Chef,Pimpam>{
	
	@Autowired
	protected ChefPimpamRepository repository;
	
	@Override
	public boolean authorise(final Request<Pimpam> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<Pimpam> findMany(final Request<Pimpam> request) {
		assert request != null;
		
		Principal principal;
		int chefId;
		Collection<Pimpam> result;
		
		principal = request.getPrincipal();
		chefId = principal.getActiveRoleId();
		result = this.repository.findPimpamByChefId(chefId);

		return result;
		
	}
	
	@Override
	public void unbind(final Request<Pimpam> request, final Pimpam entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

//		final String systemCurrency= this.repository.getDefaultCurrency();
//		 MoneyExchange priceExchanged = null;
//	     Integer i=0;
//	        while (priceExchanged == null && i<=50) {
//	        	priceExchanged=this.chefDishMoneyExchange.computeMoneyExchange(entity.getBudget(), systemCurrency);
//				i++;
//			}
//	        try {
//				model.setAttribute("money", priceExchanged.getTarget());
//			} catch (final Exception e) {
//				model.setAttribute("money", "API unavailable at the moment");
//			}
		model.setAttribute("money", "API unavailable at the moment");
		request.unbind(entity, model, "code", "instantiationMoment", "title", "budget");
	}

}
