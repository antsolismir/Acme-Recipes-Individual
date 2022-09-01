package acme.features.epicure.memorandum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.memorandum.Memorandum;
import acme.framework.controllers.AbstractController;
import acme.roles.Epicure;

@Controller
public class EpicureMemorandumController extends AbstractController<Epicure, Memorandum>{

	@Autowired
	protected EpicureMemorandumListService	listService;
	
	@Autowired
	protected EpicureGroupMemorandumListService	listGroupService;
	
	@Autowired
	protected EpicureMemorandumShowService	showService;
	
	@Autowired
	protected EpicureMemorandumCreateService createService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-all", "list", this.listService);
		super.addCommand("list-group", "list", this.listGroupService);
		
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
	}
}