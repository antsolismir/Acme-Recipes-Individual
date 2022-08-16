package acme.testing.administrator.systemConfiguration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AdministatorSystemConfigurationShowTest extends TestHarness{
	
	// Lifecycle management ---------------------------------------------------

		// Test cases -------------------------------------------------------------

		@ParameterizedTest
		@CsvFileSource(resources = "/administrator/system-configuration/list.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positive(final int recordIndex, final String defaultCurrency, final String acceptedCurrencies, 
			final String spamTermsEs, final String spamTermsEn, final String spamThreshold) {
			
			super.signIn("administrator", "administrator");

			super.clickOnMenu("Administrator", "See system configuration");

			super.checkFormExists();
			super.checkInputBoxHasValue("defaultCurrency", defaultCurrency);
			super.checkInputBoxHasValue("acceptedCurrencies", acceptedCurrencies);
			super.checkInputBoxHasValue("spamTermsEs", spamTermsEs);
			super.checkInputBoxHasValue("spamTermsEn", spamTermsEn);
			super.checkInputBoxHasValue("spamThreshold", spamThreshold);

			super.signOut();
		}

		// Ancillary methods ------------------------------------------------------


}