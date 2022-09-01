package acme.testing.administrator.systemConfiguration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AdministratorSystemConfigurationUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/system-configuration/system-configuration-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10) 
	public void positiveSystemConfigurationUpdateTest(final int recordIndex, final String defaultCurrency, final String acceptedCurrencies, 
		final String spamTermsEs, final String spamTermsEn, final String spamThreshold) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator","See system configuration"); 
		super.checkFormExists();
		super.fillInputBoxIn("acceptedCurrencies", acceptedCurrencies);
		super.fillInputBoxIn("defaultCurrency", defaultCurrency);
		super.fillInputBoxIn("spamTermsEs", spamTermsEs);
		super.fillInputBoxIn("spamTermsEn", spamTermsEn);
		super.fillInputBoxIn("spamThreshold", spamThreshold);	
		super.clickOnSubmit("Update");

		super.clickOnMenu("Administrator","See system configuration"); 
		super.checkFormExists();
		super.checkInputBoxHasValue("acceptedCurrencies", acceptedCurrencies);
		super.checkInputBoxHasValue("defaultCurrency", defaultCurrency);
		super.checkInputBoxHasValue("spamTermsEs", spamTermsEs);
		super.checkInputBoxHasValue("spamTermsEn", spamTermsEn);
		super.checkInputBoxHasValue("spamThreshold", spamThreshold);	
		
		super.signOut();
	
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/system-configuration/system-configuration-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10) 
	public void negativeSystemConfigurationUpdateTest(final int recordIndex, final String defaultCurrency, final String acceptedCurrencies, 
		final String spamTermsEs, final String spamTermsEn, final String spamThreshold) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator","See system configuration"); 
		super.checkFormExists();
		super.fillInputBoxIn("acceptedCurrencies", acceptedCurrencies);
		super.fillInputBoxIn("defaultCurrency", defaultCurrency);
		super.fillInputBoxIn("spamTermsEs", spamTermsEs);
		super.fillInputBoxIn("spamTermsEn", spamTermsEn);
		super.fillInputBoxIn("spamThreshold", spamThreshold);	
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}
	
	@Test
    @Order(30)
    public void hackingTest() {
        super.navigate("/administrator/system-configuration/show");
        super.checkPanicExists();

        super.signIn("chef1", "chef1");
        super.navigate("/administrator/system-configuration/show");
        super.checkPanicExists();
        super.signOut();

        super.signIn("epicure1", "epicure1");
        super.navigate("/administrator/system-configuration/show");
        super.checkPanicExists();
        super.signOut();
    }
	
}