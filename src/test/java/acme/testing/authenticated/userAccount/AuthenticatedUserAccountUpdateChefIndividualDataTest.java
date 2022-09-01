package acme.testing.authenticated.userAccount;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuthenticatedUserAccountUpdateChefIndividualDataTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/user-account/updateUserAccountChefIndividualData.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)	
	public void updatePositive(final String username, final String password, final String organisation, final String assertion, final String link) {	

		super.clickOnMenu("Sign in");
		
		super.signIn(username, password);
		
		super.clickOnMenu("Account", "Chef data");
		
		super.fillInputBoxIn("organisation", organisation);
		super.fillInputBoxIn("assertion", assertion);
		super.fillInputBoxIn("link", link);	
		
		super.clickOnSubmit("Update");
		
		super.clickOnMenu("Account", "Chef data");
		
		
		super.checkInputBoxHasValue("organisation", organisation);
		super.checkInputBoxHasValue("assertion", assertion);
		super.checkInputBoxHasValue("link", link);	
	
		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/authenticated/user-account/updateUserAccountChefIndividualDataNegative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)	
	public void updateNegative(final String username, final String password, final String organisation, final String assertion, final String link) {		

		super.clickOnMenu("Sign in");
		
		super.signIn(username, password);
		
		super.clickOnMenu("Account", "Chef data");
		
		super.fillInputBoxIn("organisation", organisation);
		super.fillInputBoxIn("assertion", assertion);
		super.fillInputBoxIn("link", link);	
		
		super.clickOnSubmit("Update");
			
		super.checkErrorsExist();
		
		super.signOut();
	}
}
