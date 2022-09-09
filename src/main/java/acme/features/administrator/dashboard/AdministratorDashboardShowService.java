package acme.features.administrator.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AdministratorDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;
//examen
@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, AdministratorDashboard> {

	@Autowired
	protected AdministratorDashboardRepository repository;

	@Override
	public boolean authorise(final Request<AdministratorDashboard> request) {
		assert request != null;
		boolean result;

		result = request.getPrincipal().hasRole(Administrator.class);

		return result;
	}

	@Override
	public AdministratorDashboard findOne(final Request<AdministratorDashboard> request) {
		assert request != null;

		AdministratorDashboard result;
		Integer totalNumberOfUtensil;
		totalNumberOfUtensil = this.repository.totalNumberOfUtensil();

		final Map<String, Double> averageRetailPriceOfUtensilByCurrency = new HashMap<>();
		final Map<String, Double> deviationRetailPriceOfUtensilByCurrency = new HashMap<>();
		final Map<String, Double> minimumRetailPriceOfUtensilByCurrency = new HashMap<>();
		final Map<String, Double> maximumRetailPriceOfUtensilByCurrency = new HashMap<>();

		final List<Object[]> metricsUtensilByCurrency = this.repository.findMetricsUtensilByCurrency();
		for (final Object[] fila : metricsUtensilByCurrency) {
			final String currency = (String) fila[0];
			final Double avg = (Double) fila[1];
			final Double stdev = (Double) fila[2];
			final Double min = (Double) fila[3];
			final Double max = (Double) fila[4];

			averageRetailPriceOfUtensilByCurrency.put(currency, avg);
			deviationRetailPriceOfUtensilByCurrency.put(currency, stdev);
			minimumRetailPriceOfUtensilByCurrency.put(currency, min);
			maximumRetailPriceOfUtensilByCurrency.put(currency, max);
		}

		Integer totalNumberOfIngredients;
		totalNumberOfIngredients = this.repository.totalNumberOfIngredients();

		final Map<String, Double> averageRetailPriceOfIngredientsByCurrency = new HashMap<>();
		final Map<String, Double> deviationRetailPriceOfIngredientsByCurrency = new HashMap<>();
		final Map<String, Double> minimumRetailPriceOfIngredientsByCurrency = new HashMap<>();
		final Map<String, Double> maximumRetailPriceOfIngredientsByCurrency = new HashMap<>();
		
		final List<Object[]> metricsIngredientsByCurrency = this.repository.findMetricsIngredientsByCurrency();

		for (final Object[] fila : metricsIngredientsByCurrency) {
			final String currency = (String) fila[0];
			final Double avg = (Double) fila[1];
			final Double stdev = (Double) fila[2];
			final Double min = (Double) fila[3];
			final Double max = (Double) fila[4];

			averageRetailPriceOfIngredientsByCurrency.put(currency, avg);
			deviationRetailPriceOfIngredientsByCurrency.put(currency, stdev);
			minimumRetailPriceOfIngredientsByCurrency.put(currency, min);
			maximumRetailPriceOfIngredientsByCurrency.put(currency, max);
		}
		
		Integer totalNumberOfQuotelas;
		totalNumberOfQuotelas = this.repository.totalNumberOfQuotelas();
		
		final Map<String, Double> averageRetailPriceOfQuotelasByCurrency = new HashMap<>();
		final Map<String, Double> deviationRetailPriceOfQuotelasByCurrency = new HashMap<>();
		final Map<String, Double> minimumRetailPriceOfQuotelasByCurrency = new HashMap<>();
		final Map<String, Double> maximumRetailPriceOfQuotelasByCurrency = new HashMap<>();
		
		final List<Object[]> metricsQuotelasByCurrency = this.repository.findMetricsQuotelasByCurrency();

		for (final Object[] fila : metricsQuotelasByCurrency) {
			final String currency = (String) fila[0];
			final Double avg = (Double) fila[1];
			final Double stdev = (Double) fila[2];
			final Double min = (Double) fila[3];
			final Double max = (Double) fila[4];

			averageRetailPriceOfQuotelasByCurrency.put(currency, avg);
			deviationRetailPriceOfQuotelasByCurrency.put(currency, stdev);
			minimumRetailPriceOfQuotelasByCurrency.put(currency, min);
			maximumRetailPriceOfQuotelasByCurrency.put(currency, max);
		}
		
		Integer totalNumberOfProposedDish;
		Double averageBudgetOfProposedDish;
		Double deviationBudgetOfProposedDish;
		Double minimunBudgetOfProposedDish;
		Double maximumBudgetOfProposedDish;

		Integer totalNumberOfAcceptedDish;
		Double averageBudgetOfAcceptedDish;
		Double deviationBudgetOfAcceptedDish;
		Double minimunBudgetOfAcceptedDish;
		Double maximumBudgetOfAcceptedDish;

		Integer totalNumberOfDeniedDish;
		Double averageBudgetOfDeniedDish;
		Double deviationBudgetOfDeniedDish;
		Double minimunBudgetOfDeniedDish;
		Double maximumBudgetOfDeniedDish;

		totalNumberOfProposedDish = this.repository.ratioTotalNumberOfProposedDish();
		averageBudgetOfProposedDish = this.repository.averageBudgetOfProposedDish();
		deviationBudgetOfProposedDish = this.repository.deviationBudgetOfProposedDish();
		minimunBudgetOfProposedDish = this.repository.minimunBudgetOfProposedDish();
		maximumBudgetOfProposedDish = this.repository.maximumBudgetOfProposedDish();

		totalNumberOfAcceptedDish = this.repository.ratioTotalNumberOfAcceptedDish();
		averageBudgetOfAcceptedDish = this.repository.averageBudgetOfAcceptedDish();
		deviationBudgetOfAcceptedDish = this.repository.deviationBudgetOfAcceptedDish();
		minimunBudgetOfAcceptedDish = this.repository.minimunBudgetOfAcceptedDish();
		maximumBudgetOfAcceptedDish = this.repository.maximumBudgetOfAcceptedDish();

		totalNumberOfDeniedDish = this.repository.ratioTotalNumberOfDeniedDish();
		averageBudgetOfDeniedDish = this.repository.averageBudgetOfDeniedDish();
		deviationBudgetOfDeniedDish = this.repository.deviationBudgetOfDeniedDish();
		minimunBudgetOfDeniedDish = this.repository.minimunBudgetOfDeniedDish();
		maximumBudgetOfDeniedDish = this.repository.maximumBudgetOfDeniedDish();

		result = new AdministratorDashboard();

		result.setTotalNumberOfKitchenUtensils(totalNumberOfUtensil);
		result.setAverageRetailPriceOfKitchenUtensilsGroupedByCurrency(averageRetailPriceOfUtensilByCurrency);
		result.setDeviationRetailPriceOfKitchenUtensilsGroupedByCurrency(deviationRetailPriceOfUtensilByCurrency);
		result.setMinimunRetailPriceOfKitchenUtensilsGroupedByCurrency(minimumRetailPriceOfUtensilByCurrency);
		result.setMaximunRetailPriceOfKitchenUtensilsGroupedByCurrency(maximumRetailPriceOfUtensilByCurrency);

		result.setTotalNumberOfIngredients(totalNumberOfIngredients);
		result.setAverageRetailPriceOfIngredientsGroupedByCurrency(averageRetailPriceOfIngredientsByCurrency);
		result.setDeviationRetailPriceOfIngredientsGroupedByCurrency(deviationRetailPriceOfIngredientsByCurrency);
		result.setMinimunRetailPriceOfIngredientsGroupedByCurrency(minimumRetailPriceOfIngredientsByCurrency);
		result.setMaximunRetailPriceOfIngredientsGroupedByCurrency(maximumRetailPriceOfIngredientsByCurrency);
		
		result.setTotalNumberOfQuotelas(totalNumberOfQuotelas);
		result.setAverageRetailPriceOfQuotelasGroupedByCurrency(averageRetailPriceOfQuotelasByCurrency);
		result.setDeviationRetailPriceOfQuotelasGroupedByCurrency(deviationRetailPriceOfQuotelasByCurrency);
		result.setMinimunRetailPriceOfQuotelasGroupedByCurrency(minimumRetailPriceOfQuotelasByCurrency);
		result.setMaximunRetailPriceOfQuotelasGroupedByCurrency(maximumRetailPriceOfQuotelasByCurrency);

		result.setTotalNumberOfProposedFineDishes(totalNumberOfProposedDish);
		result.setAverageBudgetOfFineDishesProposed(averageBudgetOfProposedDish);
		result.setDeviationBudgetOfFineDishesProposed(deviationBudgetOfProposedDish);
		result.setMinimunBudgetOfFineDishesProposed(minimunBudgetOfProposedDish);
		result.setMaximunBudgetOfFineDishesProposed(maximumBudgetOfProposedDish);

		result.setTotalNumberOfAcceptedFineDishes(totalNumberOfAcceptedDish);
		result.setAverageBudgetOfFineDishesAccepted(averageBudgetOfAcceptedDish);
		result.setDeviationBudgetOfFineDishesAccepted(deviationBudgetOfAcceptedDish);
		result.setMinimunBudgetOfFineDishesAccepted(minimunBudgetOfAcceptedDish);
		result.setMaximunBudgetOfFineDishesAccepted(maximumBudgetOfAcceptedDish);

		result.setTotalNumberOfDeniedFineDishes(totalNumberOfDeniedDish);
		result.setAverageBudgetOfFineDishesDenied(averageBudgetOfDeniedDish);
		result.setDeviationBudgetOfFineDishesDenied(deviationBudgetOfDeniedDish);
		result.setMinimunBudgetOfFineDishesDenied(minimunBudgetOfDeniedDish);
		result.setMaximunBudgetOfFineDishesDenied(maximumBudgetOfDeniedDish);

		return result;
	}

	@Override
	public void unbind(final Request<AdministratorDashboard> request, final AdministratorDashboard entity,
			final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "totalNumberOfIngredients",
				"averageRetailPriceOfIngredientsGroupedByCurrency",
				"deviationRetailPriceOfIngredientsGroupedByCurrency",
				"minimunRetailPriceOfIngredientsGroupedByCurrency",
				"maximunRetailPriceOfIngredientsGroupedByCurrency",
				"totalNumberOfQuotelas",
				"averageRetailPriceOfQuotelasGroupedByCurrency",
				"deviationRetailPriceOfQuotelasGroupedByCurrency",
				"minimunRetailPriceOfQuotelasGroupedByCurrency",
				"maximunRetailPriceOfQuotelasGroupedByCurrency",
				"totalNumberOfKitchenUtensils",
				"averageRetailPriceOfKitchenUtensilsGroupedByCurrency",
				"deviationRetailPriceOfKitchenUtensilsGroupedByCurrency",
				"minimunRetailPriceOfKitchenUtensilsGroupedByCurrency",
				"maximunRetailPriceOfKitchenUtensilsGroupedByCurrency",
				"totalNumberOfProposedFineDishes",
				"totalNumberOfAcceptedFineDishes",
				"totalNumberOfDeniedFineDishes",
				"averageBudgetOfFineDishesProposed",
				"averageBudgetOfFineDishesAccepted",
				"averageBudgetOfFineDishesDenied",
				"deviationBudgetOfFineDishesProposed",
				"deviationBudgetOfFineDishesAccepted",
				"deviationBudgetOfFineDishesDenied",
				"minimunBudgetOfFineDishesProposed",
				"minimunBudgetOfFineDishesAccepted",
				"minimunBudgetOfFineDishesDenied",
				"maximunBudgetOfFineDishesProposed",
				"maximunBudgetOfFineDishesAccepted",
				"maximunBudgetOfFineDishesDenied");
		
		final Set<String> currencyIngredients = entity.getDeviationRetailPriceOfIngredientsGroupedByCurrency().keySet();
		final Set<String> currencyUtensil = entity.getDeviationRetailPriceOfKitchenUtensilsGroupedByCurrency().keySet();
		final Set<String> currencyQuotelas = entity.getDeviationRetailPriceOfQuotelasGroupedByCurrency().keySet();
		
		model.setAttribute("currencyI", currencyIngredients);
		model.setAttribute("currencyU", currencyUtensil);
		model.setAttribute("currencyP", currencyQuotelas);

	}

}