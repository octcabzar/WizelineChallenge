package com.challenge.utils;

import com.challenge.pages.SwagLabsLoginPage;
import org.junit.Assert;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

public class UtilityClass {

    private final WebDriverWait wait;
    private final WebDriver driver;
    private static UtilityClass instance;
    private final Encryption encryption = Encryption.getInstance();
    private static String sessionId;

    public UtilityClass(WebDriver driver){
        this.driver=driver;
        this.wait = new WebDriverWait(driver,10);
        sessionId = this.getSessionIdFromDriver(driver);
    }

    private static String getSessionIdFromDriver(WebDriver driver) {
        return driver.toString().split("\\(")[1].split("\\)")[0];
    }

    public static UtilityClass getInstance(WebDriver driver){
        if(instance == null || !isSameSession(driver)){
            instance = new UtilityClass(driver);
        }
        return instance;
    }

    private static boolean isSameSession(WebDriver driver) {
        String newSessionId = getSessionIdFromDriver(driver);
        if(sessionId == null){
            sessionId = getSessionIdFromDriver(driver);
            return false;
        }else{
            return sessionId.equals(newSessionId);
        }

    }

    public WebDriverWait getWait(){
        return this.wait;
    }

    public String decrypt(String source){
        return this.encryption.decrypt(source);
    }

    public WebElement findOption(final String option, List<WebElement> list) {
        Optional<WebElement> optional = list.stream()
                .filter(element-> option.equals(element.getAttribute("placeholder").toUpperCase()))
                .findFirst();
        Assert.assertTrue(optional.isPresent());
        return optional.get();
    }

    public void clickOnButton(WebElement button) {
        try{
            button.click();
        }catch (ElementClickInterceptedException e){
            button.sendKeys(Keys.ENTER);
        }
    }
}
