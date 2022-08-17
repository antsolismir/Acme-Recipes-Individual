package acme.testing.authenticated.bulletin;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuthenticatedBulletinListAllTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/bulletin/bulletin-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveAuthenticatedAnnouncementListAllTest(final int recordIndex, final String key, final String instantiationMoment, final String heading, final String text, final String critical, final String info, final String criticalForm) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Authenticated", "List Bulletins");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, heading);
		super.checkColumnHasValue(recordIndex, 1, instantiationMoment);
		super.checkColumnHasValue(recordIndex, 2, criticalForm);


		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("heading", heading);
		super.checkInputBoxHasValue("instantiationMoment", instantiationMoment);
		super.checkInputBoxHasValue("critical", critical);
		super.checkInputBoxHasValue("text", text);
		super.checkInputBoxHasValue("info", info);

		super.signOut();
	}

}
