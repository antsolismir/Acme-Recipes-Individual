package acme.features.chef.quotelas;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.entities.quotelas.Quotelas;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
//examen
@Repository
public interface ChefQuotelasRepository extends AbstractRepository {
	
	@Query("select p from Quotelas p where p.item.chef.id = :id")
	Collection<Quotelas> findQuotelasByChefId(int id);

	@Query("select p from Quotelas p where p.id = :id")
	Quotelas findQuotelasById(int id);
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	
	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();
	
	@Query("select p from Quotelas p where p.code = :code")
	Quotelas findQuotelasByCode(String code);
	
	@Query("SELECT sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
	
	@Query("SELECT i from Item i where i.chef.id = :id and i.id NOT IN (SELECT p.item.id from Quotelas p) and i.published = false and i.itemType = 1")
	Collection<Item> findItems(int id);
	//examen
	//Cuidado aqui, tienes que filtrar tambien por Ingrdient o Kitchen utensil, dependiendo de lo que venga en el examen
	//No puedo crear un pimpam con un item ya publicado (YA ESTA)
	//No puedo crear un pimpam con un item que ya tenga pimpam (YA ESTA)
}
