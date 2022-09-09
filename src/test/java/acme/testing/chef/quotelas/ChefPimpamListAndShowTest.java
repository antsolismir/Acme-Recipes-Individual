package acme.testing.chef.quotelas;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

//examen
//NOTA IMPORTANTE hay que cambiar el nombre del paquete y la carpeta de los csv de test pimpam por el nombre que venga

public class ChefPimpamListAndShowTest extends TestHarness {
	
	@ParameterizedTest
    @CsvFileSource(resources = "/chef/pimpam/chef-pimpam-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
    @Order(10)
    public void positiveChefPimpamListAndShowPositiveTest(final int recordIndex, final String code, final String title, final String description, final String initialPeriodDate, final String finalPeriodDate, final String budget, final String link, final String itemCode, final String itemName) {

        super.signIn("chef1", "chef1");

        super.clickOnMenu("Chef", "List my pimpams");
        super.checkListingExists();
        super.sortListing(0, "asc");

        super.checkColumnHasValue(recordIndex, 0, code);
        super.checkColumnHasValue(recordIndex, 2, title);
        super.checkColumnHasValue(recordIndex, 3, budget);

        super.clickOnListingRecord(recordIndex);
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
	
	@Test
	@Order(30)
	public void hackingTest() {
		super.navigate("/chef/pimpam/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.navigate("/chef/pimpam/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("epicure1", "epicure1");
		super.navigate("/chef/pimpam/list");
		super.checkPanicExists();
		super.signOut();
	
	}

}