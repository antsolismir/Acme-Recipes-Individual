package acme.features.any.itemQuantity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.framework.repositories.AbstractRepository;


@Repository
public interface AnyItemQuantityRepository extends AbstractRepository{
	@Query("select a from Item a where a.published = true")
	Collection<Item> findAllItems();

	@Query("select a from Item a where a.id =:id")
	Item findItemById(int id);

	@Query("select a.item from ItemQuantity a where a.recipe.id = :id")
	Collection<Item> findManyItemsByMasterId(int id);

	@Query("select t from Recipe t where t.id=:id")
	Recipe findRecipeById(int id);

	@Query("select at from ItemQuantity at where at.id=:id")
	ItemQuantity findItemQuantityById(int id);

	@Query("select at from ItemQuantity at where at.recipe.id=:id")
	Collection<ItemQuantity> findManyItemQuantityByRecipeId(int id);

	@Query("select a from Item a where a.code =:code")
	Item findItemByCode(String code);
	
	
}
