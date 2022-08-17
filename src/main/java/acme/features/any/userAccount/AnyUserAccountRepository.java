package acme.features.any.userAccount;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

public interface AnyUserAccountRepository extends AbstractRepository{

	@Query("select user from UserAccount user " + "join user.roles rol where user.enabled = true " + "and (type(rol) = Chef or type(rol) = Epicure) " + "and Administrator not in (select type(rol) from UserAccount user2 " + "join user2.roles rol where user2.id = user.id)")
	Collection<UserAccount> findAllUserAccounts();

	@Query("select user from UserAccount user where user.id = :id")
	UserAccount findUserAccountById(@Param("id") int id);

}
