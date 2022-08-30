package acme.testing.chef.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefItemCreateAndPublishTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/item/item-create-ingredient-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveChefIngredientCreateTest(final int recordIndex, final String key, final String itemType, final String name, final String code, final String description, final String retailPrice, final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "Create an item");
		
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("itemType", itemType);
		super.clickOnSubmit("Create");
		
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
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("itemType", itemType);
		
		super.clickOnSubmit("Publish");
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/item/item-create-ingredient-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeChefIngredientCreateTest(final int recordIndex, final String key, final String itemType, final String name, final String code, final String description, final String retailPrice,final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "Create an item");

		super.checkFormExists();
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("itemType", itemType);
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}


}
