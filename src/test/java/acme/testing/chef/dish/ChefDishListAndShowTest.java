package acme.testing.chef.dish;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefDishListAndShowTest extends TestHarness {
	
	@ParameterizedTest
    @CsvFileSource(resources = "/chef/dish/chef-dish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void positiveChefDishListAndShowPositiveTest(final int recordIndex, final String status, final String code, final String request, final String budget, final String creationDate, final String initialPeriodDate, final String finalPeriodDate, final String link) {

        super.signIn("chef1", "chef1");

        super.clickOnMenu("Chef", "List my dishes");
        super.checkListingExists();
        super.sortListing(1, "asc");

        super.checkColumnHasValue(recordIndex, 0, status);
        super.checkColumnHasValue(recordIndex, 1, code);
        super.checkColumnHasValue(recordIndex, 2, creationDate);

        super.clickOnListingRecord(recordIndex);
        super.checkFormExists();
        super.checkInputBoxHasValue("status", status);
        super.checkInputBoxHasValue("code", code);
        super.checkInputBoxHasValue("request", request);
        super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
        super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
        super.checkInputBoxHasValue("link", link);

        super.signOut();
    }

}

