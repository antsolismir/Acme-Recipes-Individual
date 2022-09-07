package acme.features.chef.pimpam;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.entities.pimpam.Pimpam;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
//examen
@Repository
public interface ChefPimpamRepository extends AbstractRepository {
	
	@Query("select p from Pimpam p where p.item.chef.id = :id")
	Collection<Pimpam> findPimpamByChefId(int id);

	@Query("select p from Pimpam p where p.id = :id")
	Pimpam findPimpamById(int id);
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	
	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();
	
	@Query("select p from Pimpam p where p.code = :code")
	Pimpam findPimpamByCode(String code);
	
	@Query("SELECT sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
	
	@Query("SELECT i from Item i where i.chef.id = :id and i.id NOT IN (SELECT p.item.id from Pimpam p) and i.published = false")
	Collection<Item> findItems(int id);
	//examen
	//Cuidado aqui, tienes que filtrar tambien por Ingrdient o Kitchen utensil, dependiendo de lo que venga en el examen
	//No puedo crear un pimpam con un item ya publicado (YA ESTA)
	//No puedo crear un pimpam con un item que ya tenga pimpam (YA ESTA)
}
