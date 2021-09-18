package com.challenge.utils;

import org.junit.Assert;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;

public class UtilityClass {

    private static WebDriverWait wait;
    private final Encryption encryption = Encryption.getInstance();
    private static UtilityClass instance;
    private static String sessionId;
    private static WebDriver driver;

    public UtilityClass(WebDriver currentDriver) {
        driver = currentDriver;
        wait = new WebDriverWait(currentDriver, 10);
    }

    public static String getSessionIdFromDriver(WebDriver driver) {
        return driver.toString().split("\\(")[1].split("\\)")[0];
    }

    public static UtilityClass getInstance(WebDriver currentDriver) {
        if (instance == null || !isSameSession(currentDriver, sessionId)) {
            sessionId = getSessionIdFromDriver(currentDriver);
            instance = new UtilityClass(currentDriver);
        }
        return instance;
    }

    public static boolean isSameSession(WebDriver currentDriver, String session) {
        String currentSession = getSessionIdFromDriver(currentDriver);
        if (session == null) {
            return false;
        } else {
            return currentSession.equals(session);
        }
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public String decrypt(String source) {
        return this.encryption.decrypt(source);
    }

    public WebElement findOption(final String text, List<WebElement> list, BiFunction<WebElement, String, Boolean> p) {
        Optional<WebElement> optional = list.stream()
                .filter(element -> p.apply(element, text))
                .findFirst();
        Assert.assertTrue(optional.isPresent());
        return optional.get();
    }

    public void clickOnButton(WebElement button) {
        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            button.sendKeys(Keys.ENTER);
        }
    }

    public void clearInput(WebElement option) {
        String temp = option.getAttribute("value");
        while (!"".equals(temp)) {
            option.sendKeys(Keys.BACK_SPACE);
            temp = option.getAttribute("value");
        }
    }

    public WebElement getRandomElement(List<WebElement> products) {
        int random = new Random().nextInt(products.size());
        return products.get(random);
    }
}
