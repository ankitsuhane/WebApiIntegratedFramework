package com.orgname.test.stepdefinitions.web.pagesactions.pages;

import com.orgname.framework.web.BaseWebAction;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Scope("cucumber-glue")
public class HomePage extends BaseWebAction {

    @FindBy(css = ".login-new > .classicTxt")
    public WebElement login;

    @FindBy (css=".error-code")
    public WebElement pageNotFound;
}
