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
	
	@Autowired
	protected ChefItemCreateService createService;
	
	@Autowired
	protected ChefItemDeleteService deleteService;
	
	@Autowired
	protected ChefItemUpdateService updateService;
	
	@Autowired
	protected ChefItemPublishService publishService;
	

	@PostConstruct
	protected void initialise() {
		super.addCommand("list-kitchen_utensils","list", this.listKitchenUtensilsService);
		super.addCommand("list-ingredients","list", this.listIgredientService); 
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
		super.addCommand("delete", this.deleteService);
		super.addCommand("update", this.updateService);
		super.addCommand("publish", "update", this.publishService);

	}

}
