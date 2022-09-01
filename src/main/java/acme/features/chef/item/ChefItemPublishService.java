package acme.features.chef.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.features.authenticated.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Chef;


@Service
public class ChefItemPublishService implements AbstractUpdateService<Chef, Item>{
	
	@Autowired
	protected ChefItemRepository repository;
	
	@Autowired
	AdministratorSystemConfigurationRepository confgRepository;

	@Override
	public boolean authorise(final Request<Item> request) {

		final boolean result;
		int id;
		Item item;
		Chef chef;

		id = request.getModel().getInteger("id");
		item = this.repository.findItemById(id);
		chef = item.getChef();
		result = !item.getPublished() && request.isPrincipal(chef);
		return result;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "published");
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "published");

	}

	@Override
	public Item findOne(final Request<Item> request) {
		assert request != null;

		Item result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findItemById(id);

		return result;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
//		if(!errors.hasErrors("link")) {
//			boolean isLink;
//			if(!entity.getLink().isEmpty()) {
//				isLink = (entity.getLink().startsWith("http") || entity.getLink().startsWith("www")) && entity.getLink().contains(".");
//				errors.state(request, isLink, "link", "chef.item.form.error.link");
//			}
//		}
//		
//		if(!errors.hasErrors("retailPrice")) {
//			final Double amount = entity.getRetailPrice().getAmount();
//			
//			final String[] acceptedCurrencies = this.repository.findAcceptedCurrencies().split(",");
//			final Set<String> setCurrencies = new HashSet<String>();
//			Collections.addAll(setCurrencies, acceptedCurrencies);
//			final Boolean validCurrency = setCurrencies.contains(entity.getRetailPrice().getCurrency());
//			
//			errors.state(request, amount > 0., "retailPrice", "chef.item.form.error.retail-price-amount-negative-or-zero");
//			errors.state(request, validCurrency, "retailPrice", "chef.item.form.error.retail-price-currency-invalid");
//		}
		
//		final Boolean resName = SpamDetector.checkText(entity.getName(),
//			this.confgRepository.getConfiguration().getStrongSpamTerms(),
//			this.confgRepository.getConfiguration().getStrongSpamThreshold(),
//			this.confgRepository.getConfiguration().getWeakSpamTerms(),
//			this.confgRepository.getConfiguration().getWeakSpamThreshold());
//			errors.state(request, resName, "name", "inventor.item.form.error.spam");
//			
//		final Boolean resTechnology = SpamDetector.checkText(entity.getTechnology(),
//			this.confgRepository.getConfiguration().getStrongSpamTerms(),
//			this.confgRepository.getConfiguration().getStrongSpamThreshold(),
//			this.confgRepository.getConfiguration().getWeakSpamTerms(),
//			this.confgRepository.getConfiguration().getWeakSpamThreshold());
//			errors.state(request, resTechnology, "technology", "inventor.item.form.error.spam");
//		
//		final Boolean resDescription = SpamDetector.checkText(entity.getDescription(),
//			this.confgRepository.getConfiguration().getStrongSpamTerms(),
//			this.confgRepository.getConfiguration().getStrongSpamThreshold(),
//			this.confgRepository.getConfiguration().getWeakSpamTerms(),
//			this.confgRepository.getConfiguration().getWeakSpamThreshold());
//			errors.state(request, resDescription, "description", "inventor.item.form.error.spam");
	}

	@Override
	public void update(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		entity.setPublished(true);
		this.repository.save(entity);
	}


}