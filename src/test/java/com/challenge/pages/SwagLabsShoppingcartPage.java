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

public class SwagLabsShoppingcartPage extends SwagLabsParentPage<SwagLabsShoppingcartPage> {

    private static final String BUTTON_ACTION_CSS_LOCATOR = ".btn_action";
    private static final String CART_LIST_CSS_LOCATOR = ".cart_list";
    private static final String CART_ITEM_CSS_LOCATOR = ".cart_item";
    private static final String ITEM_NAME_CSS_LOCATOR = ".inventory_item_name";
    private static final String CHECK_OUT_BUTTON = ".checkout_button";

    public SwagLabsShoppingcartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = CART_ITEM_CSS_LOCATOR)
    private List<WebElement> addedProducts;

    @FindBy(css = CHECK_OUT_BUTTON)
    private WebElement checkout;

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BUTTON_ACTION_CSS_LOCATOR)));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CART_LIST_CSS_LOCATOR)));
    }

    public boolean areCorrectProducts(List<ProductPOJO> products) {
        return this.addedProducts.stream()
                .allMatch(added -> products.stream()
                        .anyMatch(product -> {
                            String temp = added.findElement(By.cssSelector(ITEM_NAME_CSS_LOCATOR)).getText();
                            return product.getName().equals(temp);
                        }));
    }

    public void clickOnCheckOut() {
        this.checkout.click();
    }
}
