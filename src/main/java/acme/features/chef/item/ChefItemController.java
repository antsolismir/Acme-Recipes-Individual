package acme.features.chef.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.item.Item;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;


@Controller
public class ChefItemController extends AbstractController<Chef,Item> {
	
	@Autowired
	protected ChefKitchenUtensilsListService listKitchenUtensilsService;
	
	@Autowired
	protected ChefItemIgredientListService listIgredientService;
	
	@Autowired
	protected ChefItemShowService showService;
	

	@PostConstruct
	protected void initialise() {
		super.addCommand("list-kitchen_utensils","list", this.listKitchenUtensilsService);
		super.addCommand("list-ingredients","list", this.listIgredientService); 
		super.addCommand("show", this.showService);

	}

}
