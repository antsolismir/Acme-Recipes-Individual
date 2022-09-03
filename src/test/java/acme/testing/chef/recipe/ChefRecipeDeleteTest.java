package acme.testing.chef.recipe;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefRecipeDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/chef/recipe/recipe-delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String heading) {
		super.signIn("chef2", "chef2");

		super.clickOnMenu("Chef", "List my recipes");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, heading);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.signOut();
	}
	
}