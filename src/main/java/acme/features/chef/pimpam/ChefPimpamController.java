package acme.features.chef.pimpam;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.pimpam.Pimpam;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;
//examen
@Controller
@RequestMapping("/chef/pimpam/")
public class ChefPimpamController extends AbstractController<Chef, Pimpam>{
	
	@Autowired
	protected ChefPimpamListService	listService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		
	}

}
