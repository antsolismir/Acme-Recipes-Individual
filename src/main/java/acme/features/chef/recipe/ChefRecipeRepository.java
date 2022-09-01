package acme.features.chef.recipe;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Chef;


@Repository
public interface ChefRecipeRepository extends AbstractRepository{
	
	@Query("SELECT t FROM Recipe t WHERE t.chef.id = :id")
	Collection<Recipe> findAllChefRecipes(int id);

	@Query("SELECT t FROM Recipe t WHERE t.id =:id")
	Recipe findRecipeById(int id);

	@Query("SELECT t FROM Recipe t WHERE t.code =:code")
	Recipe findRecipeByCode(String code);
	
	@Query("SELECT i FROM Chef i WHERE i.id =:id")
	Chef findChefById(int id);

	@Query("SELECT at FROM ItemQuantity at WHERE at.recipe.id=:id")
	Collection<ItemQuantity> findManyItemQuantityByRecipeId(int id);

	@Query("SELECT at.item FROM ItemQuantity at WHERE at.recipe.id=:id")
	Collection<Item> findManyItemByRecipeId(int id);
	
	@Query("SELECT c FROM SystemConfiguration c")
	SystemConfiguration getSystemConfiguration();

	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();
}
