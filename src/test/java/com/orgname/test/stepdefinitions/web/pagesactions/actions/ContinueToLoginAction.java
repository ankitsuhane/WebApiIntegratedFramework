package com.orgname.test.stepdefinitions.web.pagesactions.actions;

import com.orgname.framework.web.BaseWebAction;
import com.orgname.framework.web.WebDriverFactory;
import com.orgname.test.stepdefinitions.web.pagesactions.pages.ContinueToLoginPage;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Scope("cucumber-glue")
public class ContinueToLoginAction extends BaseWebAction {

    @Autowired
    ContinueToLoginPage continueToLoginPage;

    @Autowired
    WebDriverFactory webDriverFactory;

    public void clickLoginButton() {
        PageFactory.initElements(webDriverFactory.getWebDriver(),continueToLoginPage);
        waitForVisibilityOfElement(continueToLoginPage.loginButton);
        continueToLoginPage.loginButton.click();
    }
}


