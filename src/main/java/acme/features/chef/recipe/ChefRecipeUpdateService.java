package acme.features.chef.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.entities.recipe.Recipe;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;

@Service
public class ChefRecipeUpdateService implements AbstractUpdateService<Chef, Recipe>{

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
		
		if(!errors.hasErrors("code")) {
			Recipe existing;
			
			existing = this.repository.findRecipeByCode(entity.getCode());
			errors.state(request, existing == null|| existing.getId() == entity.getId(), "code", "chef.recipe.form.error.duplicated");
		}
		
		if(!errors.hasErrors("info")) {
			final boolean isInfo;
			if(!entity.getInfo().isEmpty()) {
				isInfo = (entity.getInfo().startsWith("http") || entity.getInfo().startsWith("www")) && entity.getInfo().contains(".");
				errors.state(request, isInfo, "info", "chef.recipe.form.error.info");
			}
		}

		if(!errors.hasErrors("description")) {
			final boolean isDescriptionSpam = SpamDetector.isSpam(entity.getDescription(), this.repository.getSystemConfiguration());
			errors.state(request, !isDescriptionSpam, "description", "Description contains spam");
		}

		if(!errors.hasErrors("preparationNotes")) {
			final boolean ispreparationNotesSpam = SpamDetector.isSpam(entity.getPreparationNotes(), this.repository.getSystemConfiguration());
			errors.state(request, !ispreparationNotesSpam, "preparationNotes", "Preparation notes contains spam");
		}
		
		if(!errors.hasErrors("heading")) {
			final boolean isheadingSpam = SpamDetector.isSpam(entity.getHeading(), this.repository.getSystemConfiguration());
			errors.state(request, !isheadingSpam, "heading", "Heading contains spam");
		}
	}

	@Override
	public void update(final Request<Recipe> request, final Recipe entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
