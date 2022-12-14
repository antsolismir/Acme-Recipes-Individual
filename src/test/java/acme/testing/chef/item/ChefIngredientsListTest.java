package acme.testing.chef.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefIngredientsListTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/item/item-ingredient.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveChefIngredientsListTest(final int recordIndex, final String key, final String itemType, final String published, final String name, final String code, final String description, final String retailPrice,final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my ingredients");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, name);
		super.checkColumnHasValue(recordIndex, 1, retailPrice);


		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("name", name);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("itemType", itemType);
		super.checkInputBoxHasValue("published", published);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}


}
