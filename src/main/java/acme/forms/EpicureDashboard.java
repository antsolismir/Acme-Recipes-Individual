package acme.forms;

import java.io.Serializable;
import java.util.Map;

public class EpicureDashboard implements Serializable{

	// Serialisation identifier --------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Integer totalNumberOfProposedFineDishes;
	Integer totalNumberOfAcceptedFineDishes;
	Integer totalNumberOfDeniedFineDishes;
	
	Map<String,Double> averageBudgetOfProposedFineDishesGroupedByCurrrency;
	Map<String,Double> averageBudgetOfAcceptedFineDishesGroupedByCurrrency;
	Map<String,Double> averageBudgetOfDeniedFineDishesGroupedByCurrrency;
	
	Map<String,Double> deviationBudgetOfProposedFineDishesGroupedByCurrrency;
	Map<String,Double> deviationBudgetOfAcceptedFineDishesGroupedByCurrrency;
	Map<String,Double> deviationBudgetOfDeniedFineDishesGroupedByCurrrency;

	Map<String,Double> minimumBudgetOfProposedFineDishesGroupedByCurrrency;
	Map<String,Double> minimumBudgetOfAcceptedFineDishesGroupedByCurrrency;
	Map<String,Double> minimumBudgetOfDeniedFineDishesGroupedByCurrrency;
	
	Map<String,Double> maximumBudgetOfProposedFineDishesGroupedByCurrrency;
	Map<String,Double> maximumBudgetOfAcceptedFineDishesGroupedByCurrrency;
	Map<String,Double> maximumBudgetOfDeniedFineDishesGroupedByCurrrency;
}
