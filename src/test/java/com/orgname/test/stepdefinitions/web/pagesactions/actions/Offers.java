package com.orgname.test.stepdefinitions.web.pagesactions.actions;

import com.orgname.framework.web.BaseWebAction;
import com.orgname.test.stepdefinitions.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Lazy
@Component
@Scope("cucumber-glue")

public class Offers extends BaseWebAction {

    @Autowired
    HomePageAction homePageAction;

    public static int i1=1;
    public String url = "https://www.marketsmojo.com/mojo/proceed?uid=";

    final ExcelUtils eu = new ExcelUtils ("C:\\Users\\asuhane\\Downloads\\mojo", "offers");

    public void login1() throws IOException, InterruptedException {
        try {
            callMethod();
        } catch (Exception e) {
            i1++;
            login1();
        } finally {
            i1++;
            login1();
            if(i1==11725)
                return;

        }
    }

    private void callMethod () throws IOException, InterruptedException {
        if (i1 == 1) {
            homePageAction.firstTime(url);
            i1++;
        }
            webDriverFactory.getWebDriver().get(url + i1);
            List<WebElement> results = webDriverFactory.getWebDriver().findElements(By.cssSelector(".col-md-9.col-xs-8>div.proceedhead3"));
            eu.setExcelValue("offer","TC"+i1, "URL", url + i1);
            eu.setExcelValue("offer","TC"+i1, "Name", results.get(1).getText());
            eu.setExcelValue("offer","TC"+i1, "package", results.get(2).getText());
            eu.setExcelValue("offer","TC"+i1, "Amount", results.get(3).getText());
            i1++;
    }
}

