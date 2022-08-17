package acme.features.any.recipe;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyRecipeRepository extends AbstractRepository {
	
	@Query("select t from Recipe t where t.published = true")
	Collection<Recipe> findAllRecipes();
	
	@Query("select t from Recipe t where t.id = :id and t.published = true")
	Recipe findOneRecipeById(int id);
	
	@Query("select a from ItemQuantity a where a.recipe.id=:id")
	Collection<ItemQuantity> findItemQuantityByRecipe(int id);
	
	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();
	
	

}