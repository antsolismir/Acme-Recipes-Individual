package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peep.Peep;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;

@Service
public class AnyPeepCreateService implements AbstractCreateService<Any, Peep>{

	
		@Autowired
		protected AnyPeepRepository repository;


		@Override
		public boolean authorise(final Request<Peep> request) {
			assert request != null;

			return true;
		}

		@Override
		public Peep instantiate(final Request<Peep> request) {
			assert request != null;

			Peep result;
			Date moment;
			moment = new Date(System.currentTimeMillis() - 1);

			result = new Peep();
			result.setMoment(moment);
			result.setHeading("");
			result.setWriter("");
			result.setText("");
			result.setEmail("");

			return result;
		}

		@Override
		public void bind(final Request<Peep> request, final Peep entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			request.bind(entity, errors, "heading", "writer", "text", "email");
		}


		@Override
		public void validate(final Request<Peep> request, final Peep entity, final Errors errors) {
			assert request != null;
			assert entity != null;
			assert errors != null;

			boolean confirmation;

			if(!errors.hasErrors("heading")) {
				final boolean isHeadingSpam = SpamDetector.isSpam(entity.getHeading(), this.repository.getSystemConfiguration());
				errors.state(request, !isHeadingSpam, "heading", "any.peep.form.error.heading-spam");
			}

			if(!errors.hasErrors("writer")) {
				final boolean isWriterSpam = SpamDetector.isSpam(entity.getWriter(), this.repository.getSystemConfiguration());
				errors.state(request, !isWriterSpam, "writer", "any.peep.form.error.writer-spam");
			}

			if(!errors.hasErrors("text")) {
				final boolean isTextSpam = SpamDetector.isSpam(entity.getText(), this.repository.getSystemConfiguration());
				errors.state(request, !isTextSpam, "text", "any.peep.form.error.text-spam");
			}
			confirmation = request.getModel().getBoolean("confirmation");
			errors.state(request, confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
		}

		@Override
		public void unbind(final Request<Peep> request, final Peep entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;

			request.unbind(entity, model, "heading", "writer", "text", "email");
			model.setAttribute("confirmation", false);
			model.setAttribute("moment", entity.getMoment());
		}

		@Override
		public void create(final Request<Peep> request, final Peep entity) {
			assert request != null;
			assert entity != null;

			this.repository.save(entity);
		}
}
