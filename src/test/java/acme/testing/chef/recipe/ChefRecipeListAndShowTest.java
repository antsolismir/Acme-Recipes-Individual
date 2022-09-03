package acme.testing.chef.recipe;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefRecipeListAndShowTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/recipe/recipe-list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveChefRecipeListTest(final int recordIndex, final String key, 
		final String code, final String heading, final String description,
		final String preparationNotes, final String info, final String totalPrice, final String published) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my recipes");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, heading);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, totalPrice);
		super.checkColumnHasValue(recordIndex, 3, published);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("heading", heading);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("money", totalPrice);
		super.checkInputBoxHasValue("preparationNotes", preparationNotes);
		super.checkInputBoxHasValue("info", info);

		super.signOut();
	}


	
}
