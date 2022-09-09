package acme.features.chef.quotelas;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.quotelas.Quotelas;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;
//examen
@Controller
@RequestMapping("/chef/quotelas/")
public class ChefQuotelasController extends AbstractController<Chef, Quotelas>{
	
	@Autowired
	protected ChefQuotelasListService	listService;
	
	@Autowired
	protected ChefQuotelasShowService	showService;
	
	@Autowired
	protected ChefQuotelasCreateService	createService;
	
	@Autowired
	protected ChefQuotelasUpdateService	updateService;
	
	@Autowired
	protected ChefQuotelasDeleteService	deleteService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
	}

}
