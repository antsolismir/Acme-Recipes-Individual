package acme.testing.epicure.dish;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class EpicurePublishTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/epicure/dish/epicure-dish-publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
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
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("request", request);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
		super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
		super.checkInputBoxHasValue("link", link);
		
		super.clickOnSubmit("Publish");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 3, "true");
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/epicure/dish/epicure-dish-publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String status, final String code, final String request, final String budget, final String initialPeriodDate, final String finalPeriodDate, final String link) {
		
		super.signIn("epicure5", "epicure5");
		
		super.clickOnMenu("Epicure", "List my dishes");
		super.checkListingExists();

		if(recordIndex==0) {
			super.clickOnButton("Create");
			super.fillInputBoxIn("code", "ZZZ-000");
			super.fillInputBoxIn("request", "Esto va a cambiar");
			super.fillInputBoxIn("budget", "GBP 05.00");
			super.fillInputBoxIn("initialPeriodDate", "2024/05/05 00:00");
			super.fillInputBoxIn("finalPeriodDate", "2024/06/06 12:00");
			super.fillInputBoxIn("link", "https://estelinkdesaparecera.com");
			
			super.clickOnSubmit("Create");
			super.clickOnMenu("Epicure", "List my dishes");
		}
		
		super.checkListingExists();
		super.sortListing(1, "asc");
		super.clickOnListingRecord(0);
		
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("request", request);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("initialPeriodDate", initialPeriodDate);
		super.fillInputBoxIn("finalPeriodDate", finalPeriodDate);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Publish");
		
		super.checkErrorsExist();

		super.signOut();
		
	}

}
