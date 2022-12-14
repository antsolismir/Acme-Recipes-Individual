
package acme.features.any.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;


@Repository
public interface AnyItemRepository extends AbstractRepository {
	
	@Query("select i from Item i")
	Collection<Item> findAllItems();

	@Query("select i from Item i where i.id=:id")
	Item findOneAnyItemById(int id);
	
	@Query("select i from Item i where i.itemType=0 and i.published = true")
	Collection<Item> findAllKitchenUtensilsByAny();
	
	@Query("select i from Item i where i.itemType=1 and i.published = true")
	Collection<Item> findAllIngredientsByAny();
	
	@Query("select c.defaultCurrency  from SystemConfiguration c")
	String getDefaultCurrency();
}
