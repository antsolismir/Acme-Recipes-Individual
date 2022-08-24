package acme.features.chef.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.Recipe;
import acme.entities.systemConfiguration.SystemConfiguration;
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
		//SpamDetector spamDetector;
		final String strongSpamTerms;
		final String weakSpamTerms;
		final double strongSpamThreshold;
		final double weakSpamThreshold;

		//spamDetector = new SpamDetector();
		final SystemConfiguration configuration = this.repository.findConfig();
//		strongSpamTerms = configuration.getStrongSpamTerm();
//		weakSpamTerms = configuration.getWeakSpamTerm();
//		strongSpamThreshold = configuration.getStrongSpamThreshold();
//		weakSpamThreshold = configuration.getWeakSpamThreshold();

		
		if(!errors.hasErrors("code")) {
			Recipe existing;
			
			existing = this.repository.findRecipeByCode(entity.getCode());
			errors.state(request, existing == null|| existing.getId() == entity.getId(), "code", "chef.recipe.form.error.duplicated");
		}
//		
//		if(!errors.hasErrors("title")) {
//			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getHeading())
//				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getHeading()),
//				"title", "chef.recipe.form.error.spam");
//		}
//
//		if(!errors.hasErrors("description")) {
//			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getDescription())
//				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getDescription()),
//				"description", "chef.recipe.form.error.spam");
//		}
//
//		if(!errors.hasErrors("assemblyNotes")) {
//			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getPreparationNotes())
//				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getPreparationNotes()),
//				"assemblyNotes", "chef.recipe.form.error.spam");
//		}
		
	}

	@Override
	public void update(final Request<Recipe> request, final Recipe entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
