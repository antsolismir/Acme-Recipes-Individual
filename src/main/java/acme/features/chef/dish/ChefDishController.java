package acme.features.chef.dish;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.dish.Dish;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;

@Controller
@RequestMapping("/chef/dish/")
public class ChefDishController extends AbstractController<Chef, Dish> {
	
	@Autowired
	protected ChefDishListService	listService;

	@Autowired
	protected ChefDishShowService	showService;
	
	@Autowired
	protected ChefDishAcceptService	acceptService;
	
	@Autowired
	protected ChefDishDenieService	denieService;

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		
		super.addCommand("accept", "update", this.acceptService);
		super.addCommand("denie", "update", this.denieService);
	}

}
