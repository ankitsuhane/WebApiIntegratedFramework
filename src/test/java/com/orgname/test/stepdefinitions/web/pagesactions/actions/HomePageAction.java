package com.orgname.test.stepdefinitions.web.pagesactions.actions;

import com.orgname.framework.web.BaseWebAction;
import com.orgname.test.stepdefinitions.ExcelUtils;
import com.orgname.test.stepdefinitions.web.pagesactions.pages.HomePage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

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
    public static int i=3300;
    public static int i1=1;
    public String url = "https://www.marketsmojo.com/mojo/home";

    public void login()  {
        try{ callMethod(); }
        catch(Exception e){ i++; login(); }
        finally { i++; login(); }
}

    private void callMethod() throws IOException, InterruptedException{
        if(i1==1) {
            firstTime(url);
            i1++;
        }

        final ExcelUtils eu = new ExcelUtils ("C:\\Users\\ankit\\Downloads\\EQ260521_CSV", "bhavcopy");

        for (; i < 3530; i++) {
            System.out.println("i: "+ i);
            webDriverFactory.getWebDriver().get(url);
            Thread.sleep(2000);

            String scCode= eu.getExcelValue("EQ270521", "TC" + i, "SC_CODE");
            CharSequence values= scCode.subSequence(0,scCode.length());
            for (int j = 0; j < values.length(); j++) {
                webDriverFactory.getWebDriver().findElement(By.cssSelector("div>div.autocomplete>#search_input")).
                        sendKeys(values.subSequence(j,j+1));
                if(j >values.length()-2)
                    Thread.sleep(2000);
            }
            Thread.sleep(3000);
            WebElement element = webDriverFactory.getWebDriver().
                    findElement(By.cssSelector("div[class='searchimphold']>div>ul>li>a>span[class='suggest-name ng-binding']"));
            Thread.sleep(1000);

            String url1= webDriverFactory.getWebDriver().
                    findElement(By.cssSelector("div[class='searchimphold']>div>ul>li>a"))
                    .getAttribute("ng-href");

            eu.setExcelValue("EQ270521", "TC" + i, "LAST", url1);

            Actions action = new Actions(webDriverFactory.getWebDriver());
            action.moveToElement(element).pause(Duration.ofSeconds(2)).click().build().perform();

            Thread.sleep(5000);
            String MojoResult = webDriverFactory.getWebDriver().findElement(By.cssSelector("div>[class='precentage ng-tns-c93-0']")).
                    getText();
            eu.setExcelValue("EQ270521", "TC" + i, "OPEN", MojoResult);
            List<WebElement> webElementList=  webDriverFactory.getWebDriver().
                    findElements(By.cssSelector(".info-dot>span"));
            for (int count=1; count<5 ;count++) {
                eu.setExcelValue("EQ270521", "TC" + i, "MojoCol"+count, webElementList.get(count-1).getAttribute("class"));
                count++;
            }
            Assert.assertTrue(eu.setExcelValue("EQ270521", "TC" + i, "HIGH",
                    webDriverFactory.getWebDriver().getCurrentUrl()));
            System.out.println("BSEID: "+ webDriverFactory.getWebDriver().findElement(By.cssSelector("#topsensex>span")).getText());
            Assert.assertTrue(eu.setExcelValue("EQ270521", "TC" + i, "LOW",
                    webDriverFactory.getWebDriver().findElement(By.cssSelector("#topsensex>span")).getText()));

        }
    }

    public void firstTime(String url) throws IOException, InterruptedException{
        webDriverFactory.getWebDriver().get(url);
        Thread.sleep(2000);
        webDriverFactory.getWebDriver().findElement(By.linkText("EXISTING USER LOGIN")).click();
        Thread.sleep(2000);
        webDriverFactory.getWebDriver().findElement(By.cssSelector("#emailID")).sendKeys("ashoksarojjbp@gmail.com");
        webDriverFactory.getWebDriver().findElement(By.cssSelector("#regpassword")).sendKeys("Redhat123");
        Thread.sleep(2000);
        webDriverFactory.getWebDriver().findElement(By.linkText("SIGN IN")).click();
    }
}