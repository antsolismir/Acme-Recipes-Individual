package acme.features.authenticated.chef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.HttpMethod;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class AuthenticatedChefCreateService implements AbstractCreateService<Authenticated, Chef> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedChefRepository repository;

	// AbstractCreateService<Authenticated, chef> ---------------------------


	@Override
	public boolean authorise(final Request<Chef> request) {
		assert request != null;
		
		boolean result;
		
		result = !request.getPrincipal().hasRole(Chef.class); 

		return result;
	}

	@Override
	public void validate(final Request<Chef> request, final Chef entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	
		if(!errors.hasErrors("assertion")) {
			final boolean isAssertionSpam = SpamDetector.isSpam(entity.getAssertion(), this.repository.getSystemConfiguration());
			errors.state(request, !isAssertionSpam, "assertion", "authenticated.chef.form.error.assertion-spam");
		}

		if(!errors.hasErrors("organisation")) {
			final boolean isOrganisationSpam = SpamDetector.isSpam(entity.getOrganisation(), this.repository.getSystemConfiguration());
			errors.state(request, !isOrganisationSpam, "organisation", "authenticated.chef.form.error.organisation-spam");
		}
	
	
	}

	@Override
	public void bind(final Request<Chef> request, final Chef entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "organisation", "assertion", "link");
	}

	@Override
	public void unbind(final Request<Chef> request, final Chef entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "organisation", "assertion", "link");
	}

	@Override
	public Chef instantiate(final Request<Chef> request) {
		assert request != null;

		Chef result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Chef();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void create(final Request<Chef> request, final Chef entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<Chef> request, final Response<Chef> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
