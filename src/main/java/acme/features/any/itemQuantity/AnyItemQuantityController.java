package acme.features.any.itemQuantity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.recipe.ItemQuantity;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;

@Controller
public class AnyItemQuantityController extends AbstractController<Any, ItemQuantity>{
	
	@Autowired
    protected AnyItemQuantityListService listService;

    @Autowired
    protected AnyItemQuantityShowService showService;
    // Constructors -----------------------------------------------------------

    @PostConstruct
    protected void initialise() {
        super.addCommand("list", this.listService);
        super.addCommand("show", this.showService);
    }

}
