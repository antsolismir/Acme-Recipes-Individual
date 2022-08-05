package acme.features.any.recipe;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.recipe.ItemQuantity;
import acme.entities.recipe.Recipe;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyRecipeRepository extends AbstractRepository {
	
	@Query("select t from Recipe t where t.draftMode = false")
	Collection<Recipe> findAllRecipes();
	
	@Query("select t from Recipe t where t.id = :id and t.draftMode = false")
	Recipe findOneRecipeById(int id);
	
	@Query("select a from ItemQuantity a where a.recipe.id=:id")
	Collection<ItemQuantity> findItemQuantityByToolKit(int id);
	
	@Query("select c.defaultCurrency  from Configuration c")
	String getDefaultCurrency();
	
	

}