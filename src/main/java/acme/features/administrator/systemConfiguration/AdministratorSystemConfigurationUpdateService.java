package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorSystemConfigurationUpdateService implements AbstractUpdateService<Administrator, SystemConfiguration>{

	@Autowired
	AdministratorSystemConfigurationRepository repository;

	@Override
	public boolean authorise(final Request<SystemConfiguration> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<SystemConfiguration> request, final SystemConfiguration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "defaultCurrency", "acceptedCurrencies","spamTermsEn",
			"spamTermsEs","spamThreshold");	
		
	}
	
	@Override
	public SystemConfiguration findOne(final Request<SystemConfiguration> request) {
		assert request != null;

		SystemConfiguration result;
		
		result = this.repository.getConfiguration();

		return result;
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
		
		request.unbind(entity, model, "defaultCurrency", "acceptedCurrencies","spamTermsEn",
			"spamTermsEs","spamThreshold");
	}

	@Override
	public void validate(final Request<SystemConfiguration> request, final SystemConfiguration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("systemCurrency")) {
			final String acceptedCurrencies=entity.getAcceptedCurrencies();
			final String defaultCurrency="(.*)"+entity.getDefaultCurrency()+"(.*)";
            final boolean dc=acceptedCurrencies.matches(defaultCurrency);            
			
			errors.state(request, dc, "defaultCurrency", "administrator.configuration.form.error.non-accepted-currency");
		}
		
	}

	@Override
	public void update(final Request<SystemConfiguration> request, final SystemConfiguration entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}
	
}
