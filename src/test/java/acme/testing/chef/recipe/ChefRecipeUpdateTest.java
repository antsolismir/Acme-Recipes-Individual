/*
 * EmployerJobUpdateTest.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.chef.recipe;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefRecipeUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/chef/recipe/recipe-update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String heading, final String description, final String preparationNotes, final String info) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my recipes");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("preparationNotes", preparationNotes);
		super.fillInputBoxIn("info", info);
		super.clickOnSubmit("Update");
		
		super.checkListingExists();
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/chef/recipe/recipe-update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String heading, final String description, final String preparationNotes, final String info) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my recipes");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.fillInputBoxIn("heading", heading);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("preparationNotes", preparationNotes);
		super.fillInputBoxIn("info", info);
		super.clickOnSubmit("Update");
		
		super.checkErrorsExist();

		super.signOut();
	}

	
}
