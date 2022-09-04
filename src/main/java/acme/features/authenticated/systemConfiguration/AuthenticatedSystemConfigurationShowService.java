package acme.features.authenticated.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedSystemConfigurationShowService implements AbstractShowService<Authenticated, SystemConfiguration>{

	// Internal state -------------------------------------------------------
	@Autowired
	protected AuthenticatedSystemConfigurationRepository repository;
	
	// AbstractShowService<Authenticated, SystemConfiguration> interface ----
	@Override
	public boolean authorise(final Request<SystemConfiguration> request) {
		assert request != null;
		return true;
	}

	@Override
	public SystemConfiguration findOne(final Request<SystemConfiguration> request) {
		assert request != null;
		return this.repository.findInitialConfiguration();
		
	}

	@Override
	public void unbind(final Request<SystemConfiguration> request, final SystemConfiguration entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String information = "We have used the services provided from exchangerate.host to perform all the money exchanges in our system."
			+ "To do so, we are making use of the api they offered. This is the call we are currently using: \"https://api.exchangerate.host/latest?base={0}&symbols={1}\", being {0}"
			+ " the source currency and {1} the target currency, so the default currency is always the target currency. ";
		
		model.setAttribute("moneyExchangeInformation", information);
		
		request.unbind(entity, model, "defaultCurrency", "acceptedCurrencies");
		
	}

}
