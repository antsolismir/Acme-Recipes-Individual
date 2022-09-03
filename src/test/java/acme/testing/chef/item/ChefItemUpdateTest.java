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

package acme.testing.chef.item;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ChefItemUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/chef/item/item-update-ingredient-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String key, final String itemType, final String name, final String code, final String description, final String retailPrice,final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my ingredients");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("itemType", itemType);
		super.clickOnSubmit("Update");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("name", name);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("itemType", itemType);
		super.checkInputBoxHasValue("link", link);
		
		super.clickOnSubmit("Publish");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("published", "true");
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/chef/item/item-update-ingredient-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String key, final String itemType, final String name, final String code, final String description, final String retailPrice,final String link) {
		super.signIn("chef1", "chef1");

		super.clickOnMenu("Chef", "List my ingredients");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.fillInputBoxIn("name", name);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("itemType", itemType);
		super.clickOnSubmit("Update");
		
		super.checkErrorsExist();

		super.signOut();
	}

	
}
