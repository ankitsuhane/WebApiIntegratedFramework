package com.orgname.framework.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@Scope("cucumber-glue")
public class WebDriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private WebDriver webDriver;

    @Value("${browser}")
    private String browser;

    @Value("${geckodriver}")
    private String geckoDriver;

    @Value("${chromedriver}")
    private String chromeDriver;

    @Value("${webdriver.wait.secs}")
    private int webDriverWait;

    @Bean
    @Scope("cucumber-glue")
    public void setUpWebDriver() throws IllegalStateException {
        switch (browser.toLowerCase()) {
            case "firefox":
                logger.info("Running Firefox Web Driver with Driver Path:- {}", geckoDriver);
                //System.setProperty("webdriver.gecko.driver", geckoDriver);
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();

                break;
            case "chrome":
                logger.info("Running Chrome Web Driver with Driver Path:- {}");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("--start-maximized");

                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver(chromeOptions);
                break;
            default:
                String errorMessage = String.format("%s is not a recognised option.", browser);
                throw new IllegalStateException(errorMessage);
        }
        logger.info(String.format("Browser is set to %s", browser));
    }

    @Bean
    @Scope("cucumber-glue")
    public void setUpWebDriverBrowserStack() throws MalformedURLException {
        final String USERNAME = "ankitsuhane1";
        final String AUTOMATE_KEY = "paJK42mAW6u8pTAopa9X";
        final String BrowserStackUrl = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "80");
        caps.setCapability("name", "ankitsuhane1's First Test");

        webDriver = new RemoteWebDriver(new URL(BrowserStackUrl), caps);

    }

    public final WebDriver getWebDriver() {	return webDriver; }
}