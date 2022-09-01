package acme.testing.epicure.dish;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class EpicureDishCreateTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/epicure/dish/epicure-dish-create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String status, final String code, final String request, final String budget, final String initialPeriodDate, final String finalPeriodDate, final String link) {
		
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
		
		super.clickOnMenu("Epicure", "List my dishes");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, status);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 3, "false");
		super.clickOnListingRecord(recordIndex);
		
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("request", request);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
		super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
		super.checkInputBoxHasValue("link", link);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/epicure/dish/epicure-dish-create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String status, final String code, final String request, final String budget, final String initialPeriodDate, final String finalPeriodDate, final String link) {
		
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
		
		super.checkErrorsExist();

		super.signOut();
		
	}

}
