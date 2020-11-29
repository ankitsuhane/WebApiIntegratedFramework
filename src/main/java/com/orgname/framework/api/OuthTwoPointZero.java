package com.orgname.framework.api;

import com.orgname.framework.web.WebDriverFactory;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

@Lazy
@Component
@Scope("cucumber-glue")
public class OuthTwoPointZero {

    private String codeOtp;
    private String token;

    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecretKey}")
    private String clientSecretKey;
    @Value("${redirectUri}")
    private String redirectUri;
    @Value("${keys}")
    private String keys;
    @Value("${grantType}")
    private String grantType;
    @Value("${tokenUri}")
    private String tokenUri;
    @Value("${scope}")
    private String scope;
    @Value("${authUrl}")
    private String authUrl;
    @Value("${state}")
    private String state;

    @Autowired
    WebDriverFactory webDriverFactory;

    public void outhGeneration() throws InterruptedException{

        String webUI= authUrl +"?scope="+scope+"&"
                +"auth_url="+authUrl+"&client_id="+clientId+"&response_type=code&redirect_uri="+redirectUri
                +"&state="+state;
        webDriverFactory.getWebDriver().get(webUI);
        webDriverFactory.getWebDriver().findElement(By.id("identifierId")).clear();
        webDriverFactory.getWebDriver().findElement(By.id("identifierId")).sendKeys("ankitsuhane@gmail.com");
        webDriverFactory.getWebDriver().findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        webDriverFactory.getWebDriver().findElement(By.name("password")).sendKeys("Redhat121@");
        webDriverFactory.getWebDriver().findElement(By.name("password")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        String url= webDriverFactory.getWebDriver().getCurrentUrl();
        String splitUrl[]= url.split("=");
        codeOtp= splitUrl[2].split("&scope")[0];

        Map<String, String> accessToken = new HashMap();
        accessToken.put("code", codeOtp);
        accessToken.put("client_id",clientId);
        accessToken.put("client_secret",clientSecretKey);
        accessToken.put("redirect_uri",redirectUri);
        accessToken.put("grant_type",grantType);

        JsonPath jsonPath =given().urlEncodingEnabled(false).queryParams(accessToken)
                .when().log().all()
                .post(tokenUri).jsonPath();
        token= jsonPath.get("access_token");

        given().urlEncodingEnabled(false).queryParam("access_token",token).
                when().log().all().get(redirectUri).
                then().assertThat().statusCode(200);
    }
}
