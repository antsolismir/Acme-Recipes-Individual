package acme.testing.epicure.memorandum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class EpicureMemorandumGroupTest extends TestHarness {
	
	@ParameterizedTest
    @CsvFileSource(resources = "/epicure/memorandum/epicure-memorandum-group-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void positiveChefMemorandumGroupPositiveTest(final int recordIndex, final String sequenceNumber,final String instantiationMoment,final String report, final String link, final String dish) {

        super.signIn("epicure1", "epicure1");

        super.clickOnMenu("Epicure", "List my dishes");
        super.checkListingExists();
        super.sortListing(1, "asc");
        super.clickOnListingRecord(0);
        super.clickOnButton("Memorandums");
        
        super.checkColumnHasValue(recordIndex, 0, sequenceNumber);
        super.checkColumnHasValue(recordIndex, 1, instantiationMoment);
        
        super.clickOnListingRecord(recordIndex);
        super.checkFormExists();
        super.checkInputBoxHasValue("sequenceNumber", sequenceNumber);
        super.checkInputBoxHasValue("instantiationMoment", instantiationMoment);
        super.checkInputBoxHasValue("report", report);
        super.checkInputBoxHasValue("link", link);
        super.checkInputBoxHasValue("dish.code", dish);
        
        super.signOut();
    }
	
}
