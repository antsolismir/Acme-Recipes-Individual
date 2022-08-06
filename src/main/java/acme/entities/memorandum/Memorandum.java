package acme.entities.memorandum;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.dish.Dish;
import acme.framework.entities.AbstractEntity;

public class Memorandum extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Pattern(regexp = "^([A-Z]{2}:)?[A-Z]{3}-[0-9]{3}:[0-9]{4}$", message="Formato incorrecto, debe coincidir con por ejemplo PW:NAS-175:9358")
	private String sequenceNumber;
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date instantiationMoment;
	
	@NotBlank
	@Length(max=255, message = "Debe contener menos de 255 carácteres")
	private String report;
	
	@URL
	private String link;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Dish dish;
}
