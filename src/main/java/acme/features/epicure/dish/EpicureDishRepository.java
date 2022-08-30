package acme.features.epicure.dish;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.dish.Dish;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EpicureDishRepository extends AbstractRepository{
	
	@Query("select dish from Dish dish where dish.epicure.id = :id")
	Collection<Dish> findDishByEpicureId(int id);

	@Query("select dish from Dish dish where dish.id = :id")
	Dish findDishById(int id);
	
	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();
}