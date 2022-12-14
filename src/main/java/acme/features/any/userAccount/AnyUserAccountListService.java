package acme.features.any.userAccount;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Any;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractListService;

@Service
public class AnyUserAccountListService implements AbstractListService<Any, UserAccount> {

	@Autowired
	protected AnyUserAccountRepository userAccountRepository;


	@Override
	public boolean authorise(final Request<UserAccount> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<UserAccount> findMany(final Request<UserAccount> request) {
		assert request != null;
		Collection<UserAccount> userAcc;
		userAcc = this.userAccountRepository.findAllUserAccounts();
		for (final UserAccount userAccount : userAcc) {
			userAccount.getRoles().forEach(r -> { ; });
		}
		
		return userAcc;
	}

	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		StringBuilder buffer;
		Collection<UserRole> roles;

		model.setAttribute("roles", entity.getAuthorityString());

		request.unbind(entity, model, "username", "identity.name", "identity.surname");

		roles = entity.getRoles();
		buffer = new StringBuilder();
		for (final UserRole role : roles) {
			buffer.append(role.getAuthorityName());
			buffer.append(" ");
		}
		model.setAttribute("roleList", buffer.toString());
	}
}
