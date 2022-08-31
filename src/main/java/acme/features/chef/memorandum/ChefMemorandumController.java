package acme.features.chef.memorandum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.memorandum.Memorandum;
import acme.framework.controllers.AbstractController;
import acme.roles.Chef;

@Controller
public class ChefMemorandumController extends AbstractController<Chef, Memorandum>{

	@Autowired
	protected ChefMemorandumListService	listService;
	
	@Autowired
	protected ChefGroupMemorandumListService	listGroupService;
	
	@Autowired
	protected ChefMemorandumShowService	showService;
	
	@Autowired
	protected ChefMemorandumCreateService createService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-all", "list", this.listService);
		super.addCommand("list-group","list", this.listGroupService);
		
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
	}
}
