package acme.forms;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.Min;

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
	Map<String,Double> averageBudgetOfKitchenUtensilsGroupedByCurrency;
	Map<String,Double> deviationBudgetOfKitchenUtensilsGroupedByCurrency;
	Map<String,Double> minimunBudgetOfKitchenUtensilsGroupedByCurrency;
	Map<String,Double> maximunBudgetOfKitchenUtensilsGroupedByCurrency;

	// FineDishes
	@Min(0)
	
	int	totalNumberOfProposedFineDishes;
	int	totalNumberOfAcceptedFineDishes;
	int	totalNumberOfDeniedFineDishes;
	
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
