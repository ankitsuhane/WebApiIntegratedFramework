package com.orgname.test.stepdefinitions.web.pagesactions;

import com.orgname.test.stepdefinitions.web.pagesactions.actions.HomePageAction;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import static org.junit.Assert.assertEquals;

public class TestStepDefinition implements En {

	@Lazy
	@Autowired
	HomePageAction homePageAction;

	public TestStepDefinition() {
		
		Given("I want to click onlinesbi link", () -> {
			homePageAction.login();
		});

		When("do nothing", () -> {
			System.out.println("do nothing:" );
		});

		Then("do nothings", () -> {
			assertEquals(0, 0);
		});
	}
}