package acme.entities.dish;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import acme.components.Status;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Chef;
import acme.roles.Epicure;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dish extends AbstractEntity{
protected static final long	serialVersionUID	= 1L;
	
	@NotNull
	protected Status status;
	
	@NotBlank
	@Column(unique=true)
	@Pattern(regexp = "^([A-Z]{2}:)?[A-Z]{3}-[0-9]{3}$")
	protected String code;
	
	@NotBlank
	@Length(max=255)
	protected String request;
	
	@NotNull
	@Valid
	protected Money budget;
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date initialPeriodDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date finalPeriodDate;
	
	@URL
	protected String link;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Epicure epicure;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Chef chef;
	
	protected boolean published;

}
