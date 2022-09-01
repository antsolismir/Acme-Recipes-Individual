package acme.features.chef.memorandum;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.memorandum.Memorandum;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ChefMemorandumRepository extends AbstractRepository {
	
	@Query("select a from Memorandum a where a.dish.chef.userAccount.id = :id")
	Collection<Memorandum> findMemorandumById(int id);
	
	@Query("select a from Memorandum a where a.id = :id")
	Memorandum findOneMemorandumById(int id);
	
	@Query("select a from Memorandum a where a.dish.id = :masterId")
	Collection<Memorandum> findOneMemorandumByDish(int masterId);

	@Query("select r from Memorandum r where r.dish.id = :id order by r.sequenceNumber")
	List<Memorandum> findMemorandumByDishId(int id);

	@Query("SELECT pr from Memorandum pr where pr.dish.id = :dishId")
    Collection<Memorandum> findMemorandumsByDishId(int dishId);

	@Query("SELECT c FROM SystemConfiguration c")
	SystemConfiguration getSystemConfiguration();
}
