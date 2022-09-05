package acme.features.chef.dish;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.dish.Dish;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ChefDishRepository extends AbstractRepository{
	
	@Query("select dish from Dish dish where dish.chef.id = :id and dish.published = true")
	Collection<Dish> findDishByChefId(int id);

	@Query("select dish from Dish dish where dish.id = :id")
	Dish findDishById(int id);
	
	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();
}
