package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletin.Bulletin;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorBulletinCreateService implements AbstractCreateService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

		@Autowired
		protected AdministratorBulletinRepository repository;

		// AbstractCreateService<Administrator, Bulletin> interface --------------


		@Override
		public boolean authorise(final Request<Bulletin> request) {
			assert request != null;

			return true;
		}

		@Override
		public void bind(final Request<Bulletin> request, final Bulletin entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			request.bind(entity, errors,"heading", "text", "critical", "info");
		}

		@Override
		public void unbind(final Request<Bulletin> request, final Bulletin entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model,"heading", "text", "critical", "info");
			model.setAttribute("confirmation", false);
			model.setAttribute("readonly", false);
		}

		@Override
		public Bulletin instantiate(final Request<Bulletin> request) {
			assert request != null;

			Bulletin result;
			Date moment;

			moment = new Date(System.currentTimeMillis() - 1);

			result = new Bulletin();
			result.setHeading("");
			result.setInstantiationMoment(moment);
			result.setText("");
			result.setCritical(false);

			return result;
		}

		@Override
		public void validate(final Request<Bulletin> request, final Bulletin entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;
			/*SpamDetector spamDetector;
			String strongSpamTerms;
			String weakSpamTerms;
			double strongSpamThreshold;
			double weakSpamThreshold;

			/*spamDetector = new SpamDetector();
			final SystemConfiguration configuration = this.repository.findConfig();
			strongSpamTerms = configuration.getStrongSpamTerm();
			weakSpamTerms = configuration.getWeakSpamTerm();
			strongSpamThreshold = configuration.getStrongSpamThreshold();
			weakSpamThreshold = configuration.getWeakSpamThreshold();

			if(!errors.hasErrors("heading")) {
				errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getTitle())
					&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getTitle()),
					"heading", "administrator.bulletin.form.error.spam");
			}

			if(!errors.hasErrors("text")) {
				errors.state(request, !spamDetector.containsSpam(weakSpamTerms.split(","), weakSpamThreshold, entity.getBody())
					&& !spamDetector.containsSpam(strongSpamTerms.split(","), strongSpamThreshold, entity.getBody()),
					"text", "administrator.bulletin.form.error.spam");
			}
*/
			boolean confirmation;

			confirmation = request.getModel().getBoolean("confirmation");
			errors.state(request, confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
		}

		@Override
		public void create(final Request<Bulletin> request, final Bulletin entity) {
			assert request != null;
			assert entity != null;

			Date moment;

			moment = new Date(System.currentTimeMillis() - 1);
			entity.setInstantiationMoment(moment);

			this.repository.save(entity);
		}

}