package com.orgname.test.stepdefinitions;

import com.orgname.framework.web.WebDriverFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;

public class Hooks {

        private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

        @Autowired
        WebDriverFactory webDriverFactory;

        @Before ("@APIOuth or @Web")
        public void setupWeb(){
            logger.info("Setting up Web Driver!");
            webDriverFactory.setUpWebDriver();
        }

        @Before ("@API")
        public void setupApi(){
            logger.info("Setting up Api");
        }

        @After ("@APIOuth or @Web or @BrowserStack")
        public void tearDownWeb(Scenario scenario) {
            logger.info("Tear down Web Driver!");
            if (scenario.isFailed()) {
                try {
                    scenario.write("Current page title is: " + webDriverFactory.getWebDriver().getTitle());
                    byte[] screenshot = ((TakesScreenshot) webDriverFactory.getWebDriver()).getScreenshotAs(OutputType.BYTES);
                    scenario.embed(screenshot, "img/png");
                } catch (WebDriverException somePlatformsDontSupportScreenShots) {
                    logger.error(somePlatformsDontSupportScreenShots.getMessage());
                }
            }
            webDriverFactory.getWebDriver().manage().deleteAllCookies();
            webDriverFactory.getWebDriver().close();
            webDriverFactory.getWebDriver().quit();
        }
        @After ("API")
        public void tearDownApi(Scenario scenario) {

        }
        @Before ("@BrowserStack")
        public void setupWebBrowserStack() throws MalformedURLException {
            logger.info("Setting up Web Driver for BrowserStack!");
            webDriverFactory.setUpWebDriverBrowserStack();
        }


}
