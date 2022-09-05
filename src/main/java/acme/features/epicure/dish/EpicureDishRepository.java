package acme.features.epicure.dish;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.dish.Dish;
import acme.entities.memorandum.Memorandum;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Chef;
import acme.roles.Epicure;

@Repository
public interface EpicureDishRepository extends AbstractRepository{
	
	@Query("select dish from Dish dish where dish.epicure.id = :id")
	Collection<Dish> findDishByEpicureId(int id);

	@Query("select dish from Dish dish where dish.id = :id")
	Dish findDishById(int id);
	

	@Query("select c.defaultCurrency from SystemConfiguration c")
	String getDefaultCurrency();

	@Query("SELECT epicure from Epicure epicure WHERE epicure.id=:id")
	Optional<Epicure> findEpicureById(int id);
	
	@Query("SELECT i from Chef i WHERE i.id=:id")
	Optional<Chef> findChefById(int id);
	
	@Query("SELECT p FROM Dish p WHERE p.code LIKE :code")
	Dish findDishByCode(String code);
	
	@Query("SELECT sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
	
	@Query("SELECT i from Chef i")
	Collection<Chef> findAllChefs();
	
	@Query("SELECT pr from Memorandum pr where pr.dish.id = :dishId")
    Collection<Memorandum> findMemorandumsByDishId(int dishId);


}