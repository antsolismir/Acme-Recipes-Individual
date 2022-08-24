package acme.features.chef.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.recipe.Recipe;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;

@Service
public class ChefRecipeCreateService implements AbstractCreateService<Chef, Recipe>{

	@Autowired
	protected ChefRecipeRepository repo;
	
	@Override
	public boolean authorise(final Request<Recipe> request) {
		assert request != null;
		
		return true;

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
	public Recipe instantiate(final Request<Recipe> request) {
		assert request != null;

		Recipe result;
		Chef chef;
		
		chef = this.repo.findChefById(request.getPrincipal().getActiveRoleId());
		result = new Recipe();
		result.setPublished(false);
		result.setChef(chef);

		return result;

	
	}

	@Override
	public void validate(final Request<Recipe> request, final Recipe entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
//		SpamDetector spamDetector;
//		final String strongSpamTerms;
//		final String weakSpamTerms;
//		final double strongSpamThreshold;
//		final double weakSpamThreshold;

//		spamDetector = new SpamDetector();
//		final SystemConfiguration configuration = this.repo.findConfig();
//		strongSpamTerms = configuration.getStrongSpamTerm();
//		weakSpamTerms = configuration.getWeakSpamTerm();
//		strongSpamThreshold = configuration.getStrongSpamThreshold();
//		weakSpamThreshold = configuration.getWeakSpamThreshold();

//		if(!errors.hasErrors("title")) {
//			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getTitle())
//				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getTitle()),
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
//			errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getAssemblyNotes())
//				&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getAssemblyNotes()),
//				"assemblyNotes", "chef.recipe.form.error.spam");
//		}
		
		if(!errors.hasErrors("code")) {
			Recipe existing;
			
			existing = this.repo.findRecipeByCode(entity.getCode());
			errors.state(request, existing == null, "code", "chef.recipe.form.error.duplicated");
		}
	}

	@Override
	public void create(final Request<Recipe> request, final Recipe entity) {
		assert request != null;
		assert entity != null;

		this.repo.save(entity);
	}

}
