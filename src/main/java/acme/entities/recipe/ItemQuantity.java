package acme.entities.recipe;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.entities.item.Item;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ItemQuantity extends AbstractEntity{
	
	// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
		
		@Min(1)
		protected int 			amount;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
		@Valid
		@NotNull
		@ManyToOne(optional=false)
		protected Item item;
		
		@Valid
		@NotNull
		@ManyToOne(optional=false)
		protected Recipe recipe;
		

}
