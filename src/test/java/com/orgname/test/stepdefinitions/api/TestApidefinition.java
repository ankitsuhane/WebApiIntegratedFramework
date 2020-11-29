package com.orgname.test.stepdefinitions.api;

import com.orgname.framework.api.OuthTwoPointZero;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class TestApidefinition implements En {

    @Autowired
    OuthTwoPointZero outhTwoPointZero;

    public TestApidefinition() {
        Given("^Hit API URL$", () -> {
            outhTwoPointZero.outhGeneration();
        });
        When("^check the reponse$", () -> {
        });
    }
}
