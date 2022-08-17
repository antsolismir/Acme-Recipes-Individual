package acme.entities.item;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Chef;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Item extends AbstractEntity{
	
	
	protected static final long serialVersionUID = 1L;
	
	
	@NotBlank
	@Pattern(regexp="^([A-Z]{2}:)?[A-Z]{3}-[0-9]{3}$", message="Incorrect format, follow the example JE:DBV-201")
	@Column(unique=true)
	protected String code;
	
	
	@NotBlank
	@Size(max=100)
	protected String name;
	
	
	@NotBlank
	@Size(max=255)
	protected String description;
	
	
	@NotNull
	@Valid
	protected Money retailPrice;
	

	@URL
	protected String link;
	
	
	@NotNull
	protected ItemType itemType;

	@NotNull
	protected Boolean published;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	protected Chef chef;

}
