package acme.features.chef.recipe;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.recipe.Recipe;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;

@Controller
public class ChefRecipeController extends AbstractController<Chef, Recipe>{
	
	@Autowired
    protected ChefRecipeListService listService;

    @Autowired
    protected ChefRecipeShowService showService;
    
    @Autowired
    protected ChefRecipeCreateService createService;
    
    @Autowired
    protected ChefRecipeUpdateService updateService;

    @Autowired
    protected ChefRecipePublishService publishService;
    
    @Autowired
    protected ChefRecipeDeleteService deleteService;
    // Constructors -----------------------------------------------------------

    @PostConstruct
    protected void initialise() {
        super.addCommand("list", this.listService);
        super.addCommand("show", this.showService);
        super.addCommand("create", this.createService);
        super.addCommand("update", this.updateService);
        super.addCommand("publish","update",this.publishService);
        super.addCommand("delete", this.deleteService);
    }

}
