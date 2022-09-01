package acme.features.chef.item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamDetector;
import acme.entities.item.Item;
import acme.features.authenticated.systemConfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Chef;



@Service
public class ChefItemCreateService implements AbstractCreateService<Chef, Item> {

	@Autowired
	protected ChefItemRepository repository;
	
	@Autowired
	AdministratorSystemConfigurationRepository confgRepository;

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		request.bind(entity, errors, "name", "code", "description", "published", "retailPrice", "itemType", "link");
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		request.unbind(entity, model, "name", "code", "description", "published", "retailPrice","itemType", "link");
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		assert request != null;

		final Item result;
		final Chef chef;

		chef = this.repository.findChefById(request.getPrincipal().getActiveRoleId());
		result = new Item();
		result.setName("");
		result.setCode("");
		result.setDescription("");
		result.setChef(chef);
		result.setLink("");

		return result;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("code")) {
			Item existing;

			existing = this.repository.findItemByCode(entity.getCode());
			errors.state(request, existing == null, "code", "chef.item.form.error.duplicated");
		}
		
		if(!errors.hasErrors("link")) {
			boolean isLink;
			if(!entity.getLink().isEmpty()) {
				isLink = (entity.getLink().startsWith("http") || entity.getLink().startsWith("www")) && entity.getLink().contains(".");
				errors.state(request, isLink, "link", "chef.item.form.error.link");
			}
		}
		
		if(!errors.hasErrors("retailPrice")) {
			final Double amount = entity.getRetailPrice().getAmount();
			
			final String[] acceptedCurrencies = this.repository.findAcceptedCurrencies().split(",");
			final Set<String> setCurrencies = new HashSet<String>();
			Collections.addAll(setCurrencies, acceptedCurrencies);
			final Boolean validCurrency = setCurrencies.contains(entity.getRetailPrice().getCurrency());
			
			errors.state(request, amount > 0., "retailPrice", "chef.item.form.error.retail-price-amount-negative-or-zero");
			errors.state(request, validCurrency, "retailPrice", "chef.item.form.error.retail-price-currency-invalid");
		}
		
		if(!errors.hasErrors("name")) {
			final boolean isNameSpam = SpamDetector.isSpam(entity.getName(), this.repository.getSystemConfiguration());
			errors.state(request, !isNameSpam, "name", "Name contains spam");
		}
		
		if(!errors.hasErrors("description")) {
			final boolean isDescriptionSpam = SpamDetector.isSpam(entity.getDescription(), this.repository.getSystemConfiguration());
			errors.state(request, !isDescriptionSpam, "description", "Description contains spam");
		}
		
	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}


}