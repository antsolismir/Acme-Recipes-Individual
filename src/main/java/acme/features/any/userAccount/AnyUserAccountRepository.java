package acme.features.any.userAccount;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

public interface AnyUserAccountRepository extends AbstractRepository{

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);

	@Query("select ua from UserAccount ua where ua.enabled = true")
	Collection<UserAccount> findEnabledUserAccounts();
}
