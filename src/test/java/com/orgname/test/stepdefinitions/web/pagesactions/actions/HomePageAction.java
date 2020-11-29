package com.orgname.test.stepdefinitions.web.pagesactions.actions;

import com.orgname.framework.web.BaseWebAction;
import com.orgname.test.stepdefinitions.web.pagesactions.pages.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Scope("cucumber-glue")
public class HomePageAction extends BaseWebAction {

    @Lazy
    @Autowired
    HomePage homePage;

    @Lazy
    @Autowired
    ContinueToLoginAction continueToLoginAction;

    @Value("${webui}")
    private String webUI;

     public void login(){
         webDriverFactory.getWebDriver().get(webUI);
         PageFactory.initElements(webDriverFactory.getWebDriver(), homePage);
         homePage.login.click();
         continueToLoginAction.clickLoginButton();
    }
}