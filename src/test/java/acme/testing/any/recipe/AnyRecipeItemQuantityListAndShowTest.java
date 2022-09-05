package acme.testing.any.recipe;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyRecipeItemQuantityListAndShowTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/recipe/recipe-itemQuantity-list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveChefRecipeListTest(final int recordIndex, final String code, final String heading, final String description,
		final String preparationNotes, final String info, final String amount1, final String itemName1, final String amount2, 
		final String itemName2) {
		
		super.clickOnMenu("Menu", "List recipes");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, heading);
		super.checkColumnHasValue(recordIndex, 1, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("heading", heading);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("preparationNotes", preparationNotes);
		super.checkInputBoxHasValue("info", info);

		super.clickOnButton("Items");
		super.checkListingExists();
		
		super.checkColumnHasValue(0, 0, amount1);
		super.checkColumnHasValue(0, 1, itemName1);
		super.checkColumnHasValue(1, 0, amount2);
		super.checkColumnHasValue(1, 1, itemName2);

	}


	
}
