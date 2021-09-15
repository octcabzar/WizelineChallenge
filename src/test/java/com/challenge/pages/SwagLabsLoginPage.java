package com.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SwagLabsLoginPage extends SwagLabsParentPage<SwagLabsLoginPage>{

    private static final String USER_PASS_INPUTS_CSS_LOCATOR = ".form_group input";
    private static final String LOGIN_BUTTON_ID = "login-button";

    private enum INPUT_PLACEHOLDERS{USERNAME, PASSWORD};

    public SwagLabsLoginPage(WebDriver driver) {
        super(driver);
    }

    private static SwagLabsLoginPage instance;

    @FindBy(css = USER_PASS_INPUTS_CSS_LOCATOR)
    private List<WebElement> inputs;

    @FindBy(id = LOGIN_BUTTON_ID)
    private WebElement loginButton;

    public static SwagLabsLoginPage getInstance(WebDriver driver) {
        if(instance == null){
            instance = new SwagLabsLoginPage(driver);
        }
        return instance;
    }

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(USER_PASS_INPUTS_CSS_LOCATOR)));
        tools.getWait().until(ExpectedConditions.elementToBeClickable(By.id(LOGIN_BUTTON_ID)));
    }

    public void sendCredentials(String user, String pass) {
        this.inputs = this.driver.findElements(By.cssSelector(USER_PASS_INPUTS_CSS_LOCATOR));
        tools.findOption(INPUT_PLACEHOLDERS.USERNAME.name(), this.inputs).sendKeys(user);
        tools.findOption(INPUT_PLACEHOLDERS.PASSWORD.name(), this.inputs).sendKeys(pass);
    }

    public void clickOnLoginButton() {
        tools.clickOnButton(loginButton);
    }
}
