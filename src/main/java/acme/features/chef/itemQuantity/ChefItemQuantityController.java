package acme.features.chef.itemQuantity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.recipe.ItemQuantity;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;

@Controller
public class ChefItemQuantityController extends AbstractController<Chef, ItemQuantity>{
	
	@Autowired
    protected ChefItemQuantityListService listService;

    @Autowired
    protected ChefItemQuantityShowService showService;
    // Constructors -----------------------------------------------------------

    @PostConstruct
    protected void initialise() {
        super.addCommand("list", this.listService);
        super.addCommand("show", this.showService);
    }

}
