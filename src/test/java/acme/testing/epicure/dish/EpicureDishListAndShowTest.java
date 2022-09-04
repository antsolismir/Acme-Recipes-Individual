package acme.testing.epicure.dish;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class EpicureDishListAndShowTest extends TestHarness{
	@ParameterizedTest
    @CsvFileSource(resources = "/epicure/dish/epicure-dish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void positiveEpicurePositiveTest(final int recordIndex, final String status, final String code, final String request, final String budget, final String creationDate, final String initialPeriodDate, final String finalPeriodDate, final String link, final String epicure, final String chef) {

        super.signIn("epicure1", "epicure1");

        super.clickOnMenu("Epicure", "List my dishes");
        super.checkListingExists();
        super.sortListing(1, "asc");

        super.checkColumnHasValue(recordIndex, 0, status);
        super.checkColumnHasValue(recordIndex, 1, code);

        super.clickOnListingRecord(recordIndex);
        super.checkFormExists();
        super.checkInputBoxHasValue("status", status);
        super.checkInputBoxHasValue("code", code);
        super.checkInputBoxHasValue("request", request);
        super.checkInputBoxHasValue("budget", budget);
//        super.checkInputBoxHasValue("creationDate", creationDate);
        super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
        super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
        super.checkInputBoxHasValue("link", link);

        super.signOut();
    }

}
