package acme.testing.any.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyItemListAlKitchenUtensilsTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/item/item-kitchen-utensil.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	//"title", "creationMoment", "critical"
	public void positiveAnyItemListAllKitchenUtensilsTest(final int recordIndex, final String key, final String itemType, final String name, final String code, final String description, final String retailPrice,final String link) {


		super.clickOnMenu("All principals", "List kitchen utensils");
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


	}

}

