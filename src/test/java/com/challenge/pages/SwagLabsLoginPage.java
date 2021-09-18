package com.challenge.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.function.BiFunction;

public class SwagLabsLoginPage extends SwagLabsParentPage<SwagLabsLoginPage> {

    private static final String USER_PASS_INPUTS_CSS_LOCATOR = ".form_group input";
    private static final String FORM_GROUP_CSS_LOCATOR = ".form_group";
    private static final String LOGIN_BUTTON_ID = "login-button";
    private static final String ERROR_CONTAINER_CSS_LOCATOR = ".error-message-container";
    private static final String ERROR_CROSS_BUTTON_CSS_LOCATOR = ".error-button";

    private enum INPUT_PLACEHOLDERS {USERNAME, PASSWORD}

    public SwagLabsLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = USER_PASS_INPUTS_CSS_LOCATOR)
    private List<WebElement> inputs;

    @FindBy(id = LOGIN_BUTTON_ID)
    private WebElement loginButton;

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(USER_PASS_INPUTS_CSS_LOCATOR)));
        tools.getWait().until(ExpectedConditions.elementToBeClickable(By.id(LOGIN_BUTTON_ID)));
    }

    /*
        Note: If in input we use clear method, WebElement will still have the previous value. Hence is clearing with backspace.
     */
    public void sendCredentials(String user, String pass) {
        this.inputs = driver.findElements(By.cssSelector(USER_PASS_INPUTS_CSS_LOCATOR));
        BiFunction<WebElement, String, Boolean> p = (element, text) -> text.equals(element.getAttribute("placeholder").toUpperCase());
        tools.clearInput(tools.findOption(INPUT_PLACEHOLDERS.USERNAME.name(), this.inputs, p));
        if(!"".equals(user)){
            tools.findOption(INPUT_PLACEHOLDERS.USERNAME.name(), this.inputs, p).sendKeys(user);
        }
        tools.clearInput(tools.findOption(INPUT_PLACEHOLDERS.PASSWORD.name(), this.inputs, p));
        if(!"".equals(pass)){
            tools.findOption(INPUT_PLACEHOLDERS.PASSWORD.name(), this.inputs, p).sendKeys(pass);
        }
    }

    public void clickOnLoginButton() {
        tools.clickOnButton(loginButton);
    }

    /*
        Note: Although username is correct, when password is incorrect and login is clicked
        it will show that username is wrong which is not true.
        This is not a blocker but not great UX.
     */
    public boolean isUserNameCorrect() {
        return this.isInputCorrect(INPUT_PLACEHOLDERS.USERNAME.name());
    }

    private boolean isInputCorrect(String name) {
        List<WebElement> formGroups = driver.findElements(By.cssSelector(FORM_GROUP_CSS_LOCATOR));
        BiFunction<WebElement, String, Boolean> p = (element, text) -> {
            String temp = element.findElement(By.tagName("input")).getAttribute("placeholder").toUpperCase();
            return temp.equals(text);
        };
        WebElement userElement = tools.findOption(name, formGroups, p);
        return !userElement.findElement(By.tagName("svg")).getAttribute("class").contains("error_icon");
    }

    public boolean isPasswordCorrect() {
        return this.isInputCorrect(INPUT_PLACEHOLDERS.PASSWORD.name());
    }

    public boolean isCorrectMessage(String message) {
        List<WebElement> errorContainers = driver.findElements(By.cssSelector(ERROR_CONTAINER_CSS_LOCATOR));
        Assert.assertEquals(errorContainers.size(), 1);
        WebElement errorContainer = errorContainers.get(0);
        String temp = errorContainer.getText().toUpperCase().replaceAll(" ", "_");
        return temp.contains(message);
    }

    public void closeErrorMessage() {
        WebElement closeButton = driver.findElement(By.cssSelector(ERROR_CROSS_BUTTON_CSS_LOCATOR));
        closeButton.click();
    }
}
