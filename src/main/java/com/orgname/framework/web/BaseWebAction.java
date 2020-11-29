package com.orgname.framework.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public abstract class BaseWebAction {

    @Value("${webdriver.wait.secs}")
    private int secs;

    @Autowired
    protected WebDriverFactory webDriverFactory;

    protected boolean waitForPageTitle(String pageTitle) {
        return new WebDriverWait(webDriverFactory.getWebDriver(), secs)
                .until(titleIs(pageTitle));
    }

   protected void waitForVisibilityOfElement(WebElement elementIdentifier) {
        new WebDriverWait(webDriverFactory.getWebDriver(), secs)
                .until(visibilityOf(elementIdentifier));
    }

    protected void waitForElementToBeClickable(WebElement elementIdentifier) {
        new WebDriverWait(webDriverFactory.getWebDriver(), secs)
                .until(elementToBeClickable(elementIdentifier));
    }
}