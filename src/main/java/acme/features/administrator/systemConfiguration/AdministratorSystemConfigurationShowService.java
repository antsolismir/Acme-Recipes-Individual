/*
 * AuthenticatedConsumerUpdateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorSystemConfigurationShowService implements AbstractShowService<Administrator, SystemConfiguration>{

	@Autowired
	AdministratorSystemConfigurationRepository repository;

	@Override
	public boolean authorise(final Request<SystemConfiguration> request) {
		assert request != null;

		return true;
	}

	@Override
	public SystemConfiguration findOne(final Request<SystemConfiguration> request) {
		assert request != null;

		return this.repository.getConfiguration();
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

}
