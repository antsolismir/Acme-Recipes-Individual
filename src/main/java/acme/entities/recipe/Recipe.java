package acme.entities.recipe;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	
	@Pattern(regexp = "^([A-Z]{2}:)?[A-Z]{3}-[0-9]{3}$")
	@Column(unique=true)
	@NotBlank
	protected String			code;

	@NotBlank
	@Length(max = 101)
	protected String			heading;

	@NotBlank
	@Length(max = 256)
	protected String			description;
	
	@NotBlank
	@Length(max = 256)
	protected String			preparationNotes;

	@URL
	protected String			info;
	
	// Derived attributes -----------------------------------------------------
	
	// Relationships ----------------------------------------------------------


}

