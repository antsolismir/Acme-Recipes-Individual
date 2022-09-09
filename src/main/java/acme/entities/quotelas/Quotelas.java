package acme.entities.quotelas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.item.Item;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quotelas extends AbstractEntity{ //examen
	
	protected static final long serialVersionUID = 1L;
	
	@NotBlank
	@Pattern(regexp="^[a-zA-Z_0-9]{2}[0-9]{2}-[0-1][0-9][0-3][0-9][0-9][0-9]$", message="Incorrect format, WWDD-mmddyy, where “yy”, “mm”, and “dd” refer to the year, month, and day when is created AND W is an uppercase, lowercase or digit AND D is a digit.")
	@Column(unique=true)
	protected String code;
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date instantiationMoment;
	
	@NotBlank
	@Size(max=100)
	protected String name;
	
	@NotBlank
	@Length(max=255)
	protected String explanation;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date initialInterval;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date finalInterval;
	
	@NotNull
	@Valid
	protected Money share;
	
	@URL
	protected String additionalInfo;
	
	@NotNull
	@Valid
	@OneToOne(optional=false)
	protected Item item;
}
