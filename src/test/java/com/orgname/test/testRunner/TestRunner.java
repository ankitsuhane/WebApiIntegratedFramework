package com.orgname.test.testRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber-reports/Cucumber2.json"}, 
		features = "src/test/java/com/orgname/test/features",
		glue = "com.orgname.test.stepdefinitions",
		//dryRun=true,
		 tags = {"@APIOuth or @Web"},
		//tags = {"@BrowserStack"},
		monochrome=true)

public class TestRunner {
}
