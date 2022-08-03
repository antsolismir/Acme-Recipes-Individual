package acme.features.any.userAccount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.UserAccount;
import acme.framework.entities.UserAccountStatus;
import acme.framework.roles.Administrator;
import acme.framework.roles.Anonymous;
import acme.framework.roles.Any;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractListService;

@Service
public class AnyUserAccountListService implements AbstractListService<Any, UserAccount> {

	@Autowired
	protected AnyUserAccountRepository repository;


	@Override
	public boolean authorise(final Request<UserAccount> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<UserAccount> findMany(final Request<UserAccount> request) {

		Collection<UserAccount> result;

		result = this.repository.findAllUserAccounts();
		final List<UserAccount> userAccounts = new ArrayList<>();
		
		for(final UserAccount ua: result) {
			if(!ua.isEnabled() || ua.hasRole(Anonymous.class) || ua.hasRole(Administrator.class)) {
				userAccounts.add(ua);
			}
		}
		
		result.removeAll(userAccounts);
		
		for (final UserAccount ua : result) {
			ua.getRoles().forEach(r -> {
			});
		}

		return result;
	}
	
	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		StringBuilder buffer;
		Collection<UserRole> roles;

		request.unbind(entity, model, "username", "identity.name", "identity.surname", "identity.email");

		roles = entity.getRoles();
		buffer = new StringBuilder();
		
		
		
		for (final UserRole role : roles) {
			buffer.append(role.getAuthorityName());
			buffer.append(" ");
		}
		model.setAttribute("roleList", buffer.toString());

		

		if (entity.isEnabled()) {
			model.setAttribute("status", UserAccountStatus.ENABLED);
		} else {
			model.setAttribute("status", UserAccountStatus.DISABLED);
		}
			
	}

}
