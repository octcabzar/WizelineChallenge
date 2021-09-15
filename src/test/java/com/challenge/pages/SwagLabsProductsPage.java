package com.challenge.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SwagLabsProductsPage extends SwagLabsParentPage<SwagLabsProductsPage>{

    private static final String MENU_ID = "react-burger-menu-btn";
    private static final String SHOPPING_CART_ID = "shopping_cart_container";
    private static final String PRODUCT_LIST_CSS_LOCATOR = ".inventory_container .inventory_item";
    private static final String HEADER_ID = "header_container";
    private static final String TITLE_CSS_LOCATOR = ".header_secondary_container span";
    private static final String PRODUCTS_TITLE = "PRODUCTS";

    public SwagLabsProductsPage(WebDriver driver) {
        super(driver);
    }

    private static SwagLabsProductsPage instance;

    public static SwagLabsProductsPage getInstance(WebDriver driver) {
        if(instance == null){
            instance = new SwagLabsProductsPage(driver);
        }
        return instance;
    }

    @FindBy(id = MENU_ID)
    private WebElement menu;

    @FindBy(id = SHOPPING_CART_ID)
    private WebElement shoppingCart;

    @FindBy(id = HEADER_ID)
    private WebElement header;
    
    @FindBy(css = PRODUCT_LIST_CSS_LOCATOR)
    List<WebElement> products;

    public List<WebElement> getProducts() {
        return products;
    }

    @Override
    protected void isLoaded() throws Error {

    }

    public boolean isTitleCorrect() {
        String titleText = header.findElement(By.cssSelector(TITLE_CSS_LOCATOR)).getText();
        return PRODUCTS_TITLE.equals(titleText);
    }
}
