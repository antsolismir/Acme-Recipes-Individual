package acme.features.chef.recipe;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.recipe.Recipe;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefRecipePublishService  implements AbstractUpdateService<Chef, Recipe>  {

	@Autowired
	protected ChefRecipeRepository repository;
	
	
	@Override
	public boolean authorise(final Request<Recipe> request) {
		assert request != null;

		boolean result;
		int recipeId;
		Recipe recipe;
		Chef chef;

		recipeId = request.getModel().getInteger("id");
		recipe = this.repository.findRecipeById(recipeId);
		chef = recipe.getChef();
		result = !recipe.isPublished() && request.isPrincipal(chef);

		return result;
	}

	@Override
	public void bind(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "code", "heading", "description", "preparationNotes", "info");
	}

	@Override
	public void unbind(final Request<Recipe> request, final Recipe entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "heading", "description", "preparationNotes", "info", "published");
		
	}

	@Override
	public Recipe findOne(final Request<Recipe> request) {
		assert request != null;

		Recipe result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findRecipeById(id);

		return result;
	}

	@Override
	public void validate(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		Collection<Item> items;
		items = this.repository.findManyItemByRecipeId(entity.getId());
		if(!errors.hasErrors("code")) {
			Recipe existing;
			
			existing = this.repository.findRecipeByCode(entity.getCode());
			errors.state(request, existing == null|| existing.getId() == entity.getId(), "code", "chef.recipe.form.error.duplicated");
		}
			errors.state(request, !items.isEmpty(), "*", "chef.recipe.form.error.noItem");
	}

	@Override
	public void update(final Request<Recipe> request, final Recipe entity) {
		assert request != null;
		assert entity != null;

		entity.setPublished(true);
		this.repository.save(entity);
	}		
}
