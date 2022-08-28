package acme.testing.administrator.bulletin;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AdministatorBulletinCreateTest extends TestHarness{
	
	// Lifecycle management ---------------------------------------------------

		// Test cases -------------------------------------------------------------
	// Lifecycle managment 
	
	// Test cases
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/bulletin/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest (final int recordIndex, final String heading, final String text, final String critical, final String info,final String criticalForm) {
		
		String confirmation;
		confirmation = "true";
		
		super.signIn("administrator", "administrator");
	
		super.clickOnMenu("Administrator", "Create a bulletin");
		
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("critical", critical);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("confirmation", confirmation);
		
		super.clickOnSubmit("Create");
		
		super.clickOnMenu("Authenticated", "List Bulletins");
		super.checkListingExists();
		super.sortListing(1, "desc");
		
		super.checkColumnHasValue(recordIndex, 0, heading);
		super.checkColumnHasValue(recordIndex, 2, critical);

		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("heading", heading);
		super.checkInputBoxHasValue("text", text);
		super.checkInputBoxHasValue("critical", criticalForm);
		super.checkInputBoxHasValue("info", info);

		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/bulletin/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String heading, final String text, final String critical, final String info) {

		String confirmation;
		confirmation = "true";
		
		super.signIn("administrator", "administrator");
	
		super.clickOnMenu("Administrator", "Create a bulletin");
		
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("critical", critical);
		super.fillInputBoxIn("info", info);
		super.fillInputBoxIn("confirmation", confirmation);
		
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}
	
}