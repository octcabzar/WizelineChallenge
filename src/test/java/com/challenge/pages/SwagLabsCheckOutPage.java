package com.challenge.pages;

import com.challenge.pojo.ProductPOJO;
import com.challenge.utils.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SwagLabsCheckOutPage extends SwagLabsParentPage<SwagLabsCheckOutPage> {

    private static final String FINISH_ID = "finish";
    private static final String CONTAINER_ID = "checkout_summary_container";
    private static final String CART_ITEM_CSS_LOCATOR = ".cart_item";
    private static final String ITEM_NAME_CSS_LOCATOR = ".inventory_item_name";

    public SwagLabsCheckOutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = FINISH_ID)
    private WebElement finishButton;

    @FindBy(id = CONTAINER_ID)
    private WebElement container;

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(CONTAINER_ID)));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(FINISH_ID)));
    }

    public boolean areAllProductsAvailable(List<ProductPOJO> selectedProducts) {
        List<WebElement> products = driver.findElements(By.cssSelector(CART_ITEM_CSS_LOCATOR));
        return products.stream()
                .allMatch(product -> selectedProducts.stream().anyMatch(selected -> {
                    String name = product.findElement(By.cssSelector(ITEM_NAME_CSS_LOCATOR)).getText();
                    return name.equals(selected.getName());
                }));
    }

    public void clickOnFinish() {
        finishButton.click();
    }
}
