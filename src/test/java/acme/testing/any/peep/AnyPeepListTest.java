package acme.testing.any.peep;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positivePeepListTest(final int recordIndex, final String moment, final String heading, final String writer, final String text, 
		 final String email) {

		// Los datos mostrados en el test dependen de una fecha reciente (1 mes según lo especificado)
		
		super.clickOnMenu("Menu","Peeps list");
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, moment);
		super.checkColumnHasValue(recordIndex, 1, heading);
		super.checkColumnHasValue(recordIndex, 2, writer);
		super.checkColumnHasValue(recordIndex, 3, text);
		super.checkColumnHasValue(recordIndex, 4, email); 
	}
}
