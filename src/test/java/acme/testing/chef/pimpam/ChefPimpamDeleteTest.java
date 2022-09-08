package acme.testing.chef.pimpam;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefPimpamDeleteTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/pimpam/chef-pimpam-delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deleteTest(final int recordIndex, final String code, final String title, final String description, final String initialPeriodDate, final String finalPeriodDate, final String budget, final String link, final String itemCode, final String itemName) {
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

		super.clickOnMenu("Chef", "List my pimpams");
		super.checkListingExists();
		super.checkNotListingEmpty();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(0, 0, code);
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		
		super.signOut();
	}
	
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.navigate("/chef/pimpam/delete");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.navigate("/chef/pimpam/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("epicure1", "epicure1");
		super.navigate("/chef/pimpam/delete");
		super.checkPanicExists();
		super.signOut();
	
	}

}
