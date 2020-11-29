package com.orgname.test.stepdefinitions;

import com.orgname.test.AppConfig;
import org.springframework.test.context.ContextConfiguration;


import cucumber.api.java8.En;

/**
 * This class bootstraps the Spring Config for the test to run.
 *
 */
@ContextConfiguration(classes = {AppConfig.class})
public class StepsBase implements En {
}