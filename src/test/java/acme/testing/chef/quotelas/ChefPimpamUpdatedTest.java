package acme.testing.chef.quotelas;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefPimpamUpdatedTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/pimpam/chef-pimpam-update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void pimpamUpdateTestPositive(final int recordIndex, final String title, final String description, final String initialPeriodDate, final String finalPeriodDate, final String budget, final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "Create a pimpam");
		
		super.fillInputBoxIn("code", "7822090887");
		super.fillInputBoxIn("title", "Titulo para update");
		super.fillInputBoxIn("description", "Descripcion para update");
		super.fillInputBoxIn("initialPeriodDate", "2024/12/01 12:05");
		super.fillInputBoxIn("finalPeriodDate", "2024/12/10 12:25");
		super.fillInputBoxIn("budget", "USD 150.00");
		super.fillInputBoxIn("link", "");
		super.clickOnSubmit("Create");

		super.clickOnMenu("Chef", "List my pimpams");
		super.checkListingExists();
		super.sortListing(1, "desc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialPeriodDate", initialPeriodDate);
		super.fillInputBoxIn("finalPeriodDate", finalPeriodDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
		super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("link", link);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/pimpam/chef-pimpam-update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void pimpamUpdateTestNegative(final int recordIndex, final String title, final String description, final String initialPeriodDate, final String finalPeriodDate, final String budget, final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my pimpams");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialPeriodDate", initialPeriodDate);
		super.fillInputBoxIn("finalPeriodDate", finalPeriodDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");
		
		super.checkErrorsExist();

		super.signOut();
	}

}
