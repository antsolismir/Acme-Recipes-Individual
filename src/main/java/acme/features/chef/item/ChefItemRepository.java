package acme.features.chef.item;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;


@Repository
public interface ChefItemRepository extends AbstractRepository {
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	 
	@Query("select distinct i from Item i where i.chef.userAccount.id = :chefId and i.itemType=0")
	Collection<Item> findKitchenUtensilsByChef(Integer chefId);
	
	@Query("select distinct i from Item i where i.chef.userAccount.id = :chefId and i.itemType=1")
	Collection<Item> findIngredientByChef(Integer chefId);
	

}

