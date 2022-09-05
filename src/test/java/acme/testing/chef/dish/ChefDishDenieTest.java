package acme.testing.chef.dish;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefDishDenieTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/dish/chef-dish-denie-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void ChefDishDeniePositiveTest(final int recordIndex, final String code, final String request, final String budget, final String initialPeriodDate, final String finalPeriodDate, final String link) {
		
		super.signIn("epicure5", "epicure5");
		
		super.clickOnMenu("Epicure", "List my dishes");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("request", request);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("initialPeriodDate", initialPeriodDate);
		super.fillInputBoxIn("finalPeriodDate", finalPeriodDate);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Create");
		
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.clickOnListingRecord(recordIndex);
		
		super.clickOnSubmit("Publish");
		
		super.signOut();
		
		super.signIn("chef5", "chef5");
		
		super.clickOnMenu("Chef", "List my dishes");
		super.checkListingExists();
		
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.clickOnListingRecord(recordIndex);
		
		super.checkFormExists();
		super.checkInputBoxHasValue("status", "PROPOSED");
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("request", request);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
		super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
		super.checkInputBoxHasValue("link", link);
		
		super.clickOnSubmit("Denie");
		
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.checkColumnHasValue(recordIndex, 0, "DENIED");
		
		super.signOut();
	}

}
