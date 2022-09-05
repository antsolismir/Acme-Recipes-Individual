package acme.testing.chef.memorandum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefMemorandumCreateTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/memorandum/chef-memorandum-create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String report, final String link) {
		
		super.signIn("chef1", "chef1");

	    super.clickOnMenu("Chef", "List my dishes");
	    super.checkListingExists();
	    super.sortListing(1, "asc");
	    super.clickOnListingRecord(0);
	    super.clickOnButton("Memorandums");
		
	    super.clickOnButton("Create memorandum");
		super.fillInputBoxIn("report", report);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("confirmation","true");
		
		super.clickOnSubmit("Create memorandum");
		
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.clickOnListingRecord(0);
		
		super.checkFormExists();
		super.checkInputBoxHasValue("report", report);
		super.checkInputBoxHasValue("link", link);
		
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/chef/memorandum/chef-memorandum-create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String report, final String link) {
		
		super.signIn("chef1", "chef1");

	    super.clickOnMenu("Chef", "List my dishes");
	    super.checkListingExists();
	    super.sortListing(1, "asc");
	    super.clickOnListingRecord(0);
	    super.clickOnButton("Memorandums");
		
	    super.clickOnButton("Create memorandum");
	    super.fillInputBoxIn("report", report);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("confirmation","true");
		
		super.clickOnSubmit("Create memorandum");
		
		super.checkErrorsExist();

		super.signOut();
		
	}

}
