package acme.features.chef.pimpam;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.pimpam.Pimpam;
import acme.framework.repositories.AbstractRepository;
//examen
@Repository
public interface ChefPimpamRepository extends AbstractRepository {
	
	@Query("select p from Pimpam p where p.item.chef.id = :id and p.item.published = true")
	Collection<Pimpam> findPimpamByChefId(int id);

	@Query("select p from Pimpam p where p.id = :id")
	Pimpam findPimpamById(int id);
	
	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();

}
