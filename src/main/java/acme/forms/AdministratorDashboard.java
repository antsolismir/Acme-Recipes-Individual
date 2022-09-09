package acme.forms;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

//examen
@Getter
@Setter
public class AdministratorDashboard implements Serializable{

	// Serialisation identifier --------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//Ingredients
	@Min(0)
	Integer	totalNumberOfIngredients;
	Map<String,Double> averageRetailPriceOfIngredientsGroupedByCurrency;
	Map<String,Double> deviationRetailPriceOfIngredientsGroupedByCurrency;
	Map<String,Double> minimunRetailPriceOfIngredientsGroupedByCurrency;
	Map<String,Double> maximunRetailPriceOfIngredientsGroupedByCurrency;
	
	//KitchenUtensils
	@Min(0)
	Integer totalNumberOfKitchenUtensils;
	Map<String,Double> averageRetailPriceOfKitchenUtensilsGroupedByCurrency;
	Map<String,Double> deviationRetailPriceOfKitchenUtensilsGroupedByCurrency;
	Map<String,Double> minimunRetailPriceOfKitchenUtensilsGroupedByCurrency;
	Map<String,Double> maximunRetailPriceOfKitchenUtensilsGroupedByCurrency;
	
	//examen
	@Min(0)
	Integer totalNumberOfQuotelas;
	Map<String,Double> averageRetailPriceOfQuotelasGroupedByCurrency;
	Map<String,Double> deviationRetailPriceOfQuotelasGroupedByCurrency;
	Map<String,Double> minimunRetailPriceOfQuotelasGroupedByCurrency;
	Map<String,Double> maximunRetailPriceOfQuotelasGroupedByCurrency;

	// FineDishes
	@Min(0)
	Integer	totalNumberOfProposedFineDishes;
	Integer	totalNumberOfAcceptedFineDishes;
	Integer	totalNumberOfDeniedFineDishes;
	
	Double averageBudgetOfFineDishesProposed;
	Double averageBudgetOfFineDishesAccepted;
	Double averageBudgetOfFineDishesDenied;
	
	Double deviationBudgetOfFineDishesProposed;
	Double deviationBudgetOfFineDishesAccepted;
	Double deviationBudgetOfFineDishesDenied;
	
	Double minimunBudgetOfFineDishesProposed;
	Double minimunBudgetOfFineDishesAccepted;
	Double minimunBudgetOfFineDishesDenied;
	
	Double maximunBudgetOfFineDishesProposed;
	Double maximunBudgetOfFineDishesAccepted;
	Double maximunBudgetOfFineDishesDenied;
}
