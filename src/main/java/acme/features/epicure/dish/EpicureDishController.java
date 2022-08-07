package acme.features.epicure.dish;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.dish.Dish;
import acme.framework.controllers.AbstractController;
import acme.roles.Epicure;

@Controller
@RequestMapping("/epicure/dish/")
public class EpicureDishController extends AbstractController<Epicure, Dish> {
	
	@Autowired
	protected EpicureDishListService	listService;

	@Autowired
	protected EpicureDishShowService	showService;

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
	}

}