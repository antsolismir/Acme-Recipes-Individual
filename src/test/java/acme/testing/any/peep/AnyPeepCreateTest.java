package acme.testing.any.peep;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepCreateTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void testPositive(final int recordIndex, final String heading, final String writer, final String text, final String email, final String confirmation) {
		super.clickOnMenu("Menu","Peeps list");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("writer", writer);
		super.fillInputBoxIn("text", text);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("confirmation", confirmation);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Menu","Peeps list");
		super.checkListingExists();

		super.sortListing(1, "asc");
		super.checkColumnHasValue(recordIndex, 1, heading);
		super.checkColumnHasValue(recordIndex, 2, writer);
		super.checkColumnHasValue(recordIndex, 3, text);
		super.checkColumnHasValue(recordIndex, 4, email);
	}

}
