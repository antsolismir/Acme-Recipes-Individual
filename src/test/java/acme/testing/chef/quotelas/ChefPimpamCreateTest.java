package acme.testing.chef.quotelas;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefPimpamCreateTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/quotela/chef-pimpam-create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveChefPimpamCreateTest(final int recordIndex, final String code, final String title, final String description, final String initialPeriodDate, final String finalPeriodDate, final String budget, final String link, final String itemCode, final String itemName) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "Create a quotela");
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialPeriodDate", initialPeriodDate);
		super.fillInputBoxIn("finalPeriodDate", finalPeriodDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		
		super.clickOnMenu("Chef", "List my pimpams");
		super.checkListingExists();
		super.sortListing(0, "desc");
		
		super.checkColumnHasValue(0, 0, code);
        super.checkColumnHasValue(0, 2, title);
        super.checkColumnHasValue(0, 3, budget);


        super.clickOnListingRecord(0);
        super.checkFormExists();
        super.checkInputBoxHasValue("code", code);
        super.checkInputBoxHasValue("title", title);
        super.checkInputBoxHasValue("description", description);
        super.checkInputBoxHasValue("initialPeriodDate", initialPeriodDate);
        super.checkInputBoxHasValue("finalPeriodDate", finalPeriodDate);
        super.checkInputBoxHasValue("budget", budget);
        super.checkInputBoxHasValue("link", link);
        super.checkInputBoxHasValue("item.code", itemCode);
        super.checkInputBoxHasValue("item.name", itemName);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/pimpam/chef-pimpam-create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeChefPimpamCreateTest(final int recordIndex, final String code, final String title, final String description, final String initialPeriodDate, final String finalPeriodDate, final String budget, final String link, final String itemCode, final String itemName) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "Create a pimpam");
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("initialPeriodDate", initialPeriodDate);
		super.fillInputBoxIn("finalPeriodDate", finalPeriodDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
		super.signOut();
	}
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.navigate("/chef/pimpam/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.navigate("/chef/pimpam/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("epicure1", "epicure1");
		super.navigate("/chef/pimpam/create");
		super.checkPanicExists();
		super.signOut();
	
	}

}
