package com.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SwagLabsFinishPage extends SwagLabsParentPage<SwagLabsFinishPage> {


    private static final String SECONDARY_HEADER_CSS_LOCATOR = ".header_secondary_container";
    private static final String COMPLETE_CSS_LOCATOR = ".complete-text";
    private static final String THANK_YOU_MESSAGE_CSS_LOCATOR = "checkout_complete_container";

    private enum MESSAGES{THANK_YOU_FOR_YOUR_ORDER}

    public SwagLabsFinishPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SECONDARY_HEADER_CSS_LOCATOR)));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(COMPLETE_CSS_LOCATOR)));
    }

    public boolean isMessageDisplayed() {
        String message =  driver.findElement(By.id(THANK_YOU_MESSAGE_CSS_LOCATOR)).getText().toUpperCase().replaceAll(" ","_");
        return message.contains(MESSAGES.THANK_YOU_FOR_YOUR_ORDER.name());
    }
}
