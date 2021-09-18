package com.challenge.pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SwagLabsBillingDataPage extends SwagLabsParentPage<SwagLabsBillingDataPage> {

    private static final String SECONDARY_HEADER_CSS_LOCATOR = ".header_secondary_container";
    private static final String INPUT_CSS_LOCATOR = ".form_group input";
    private static final String CONTINUE_ID = "continue";

    private enum INPUT_PLACEHOLDERS {FIRST_NAME, LAST_NAME, ZIP_POSTAL_CODE}

    public SwagLabsBillingDataPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = INPUT_CSS_LOCATOR)
    private List<WebElement> inputs;

    @FindBy(id = CONTINUE_ID)
    private WebElement continueButton;

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("input")));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SECONDARY_HEADER_CSS_LOCATOR)));
    }

    public void inputData() {
        Faker faker = new Faker();
        this.inputs
                .forEach(input -> this.fillData(input, faker));
    }

    private void fillData(WebElement input, Faker faker) {
        String placeholder = input.getAttribute("placeholder").toUpperCase().replaceAll(" ", "_").replaceAll("/", "_");
        INPUT_PLACEHOLDERS type = INPUT_PLACEHOLDERS.valueOf(placeholder);
        switch (type) {
            case FIRST_NAME:
                input.sendKeys(faker.name().firstName());
                break;
            case LAST_NAME:
                input.sendKeys(faker.name().lastName());
                break;
            case ZIP_POSTAL_CODE:
                input.sendKeys(faker.address().zipCode());
                break;
        }
    }

    public void clickOnContinue() {
        continueButton.click();
    }
}
