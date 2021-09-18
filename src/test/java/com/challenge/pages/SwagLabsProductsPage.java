package com.challenge.pages;

import com.challenge.pojo.ProductPOJO;
import com.challenge.utils.Environment;
import com.challenge.utils.UtilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SwagLabsProductsPage extends SwagLabsParentPage<SwagLabsProductsPage> {

    private static final String MENU_CSS_LOCATOR = ".bm-burger-button";
    private static final String SHOPPING_CART_ID = "shopping_cart_container";
    private static final String PRODUCT_LIST_CSS_LOCATOR = ".inventory_container .inventory_item";
    private static final String HEADER_ID = "header_container";
    private static final String TITLE_CSS_LOCATOR = ".header_secondary_container span";
    private static final String PRODUCTS_TITLE = "PRODUCTS";
    private static final String MENU_ITEM_CSS_LOCATOR = ".menu-item";
    private static final String SORT_CSS_LOCATOR = ".product_sort_container";
    private static final String PRICE_CSS_LOCATOR = ".inventory_item_price";
    private static final String PRODUCT_DESCRIPTION_CSS_LOCATOR = ".inventory_item_desc";
    private static final String PRODUCT_NAME_CSS_LOCATOR = ".inventory_item_name";
    private static final String CART_BADGE_CSS_LOCATOR = ".shopping_cart_badge";

    private enum SORT_OPTIONS {NAME_A_TO_Z, NAME_Z_TO_A, PRICE_LOW_TO_HIGH, PRICE_HIGH_TO_LOW}

    private enum MENU_OPTIONS {ALL_ITEMS, ABOUT, LOGOUT, RESET_APP_STATE}

    public SwagLabsProductsPage(WebDriver currentDriver) {
        super(currentDriver);
    }

    private List<ProductPOJO> selectedProducts;

    @FindBy(css = MENU_CSS_LOCATOR)
    private WebElement menu;

    @FindBy(id = SHOPPING_CART_ID)
    private WebElement shoppingCart;

    @FindBy(id = HEADER_ID)
    private WebElement header;

    @FindBy(css = PRODUCT_LIST_CSS_LOCATOR)
    List<WebElement> products;

    @FindBy(css = SORT_CSS_LOCATOR)
    private WebElement sort;

    public List<WebElement> getProducts() {
        return this.products;
    }

    public List<ProductPOJO> getSelectedProducts() {
        return selectedProducts;
    }

    @Override
    protected void isLoaded() throws Error {
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PRODUCT_LIST_CSS_LOCATOR)));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(HEADER_ID)));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(MENU_CSS_LOCATOR)));
        tools.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id(SHOPPING_CART_ID)));
    }

    public boolean isTitleCorrect() {
        String titleText = header.findElement(By.cssSelector(TITLE_CSS_LOCATOR)).getText();
        return PRODUCTS_TITLE.equals(titleText);
    }

    public void clickOnMenu() {
        menu.click();
    }

    public void clickOnLogout() {
        tools.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(MENU_ITEM_CSS_LOCATOR)));
        List<WebElement> menuOptions = driver.findElements(By.cssSelector(MENU_ITEM_CSS_LOCATOR));
        BiFunction<WebElement, String, Boolean> p = (element, text) -> text.equals(element.getText().toUpperCase());
        WebElement logout = tools.findOption(MENU_OPTIONS.LOGOUT.name(), menuOptions, p);
        logout.click();
    }

    public void clickOnSortOptions() {
        sort.click();
    }

    public void clickOnLowToHighPrice() {
        List<WebElement> options = sort.findElements(By.tagName("option"));
        BiFunction<WebElement, String, Boolean> p = (element, text) -> {
            String temp = element.getText().toUpperCase()
                    .replaceAll(" ", "_").replaceAll("\\(", "").replaceAll("\\)", "");
            return text.equals(temp);
        };
        WebElement lowToHigh = tools.findOption(SORT_OPTIONS.PRICE_LOW_TO_HIGH.name(), options, p);
        lowToHigh.click();
    }

    public boolean areProductsSortedByPriceAscending() {
        tools.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(PRODUCT_LIST_CSS_LOCATOR)));
        List<Float> prices = driver.findElements(By.cssSelector(PRICE_CSS_LOCATOR)).stream()
                .map(element -> {
                    String temp = element.getText().replaceAll("\\$", "").replaceAll("\n", "");
                    return new Float(temp);
                })
                .collect(Collectors.toList());
        return prices.stream()
                .sorted()
                .collect(Collectors.toList()).equals(prices);
    }

    public void selectSeveralProducts() {
        this.selectedProducts = new ArrayList<>();
        int number = new Random().nextInt(this.products.size()) + 1;
        for (int i = 0; i < number; i++) {
            ProductPOJO product = new ProductPOJO();
            WebElement productElement = tools.getRandomElement(this.products);
            product.setDescription(productElement.findElement(By.cssSelector(PRODUCT_DESCRIPTION_CSS_LOCATOR)).getText());
            product.setPrice(new Float(productElement.findElement(By.cssSelector(PRICE_CSS_LOCATOR)).getText().replaceAll("\\$", "").replaceAll("\n", "")));
            product.setName(productElement.findElement(By.cssSelector(PRODUCT_NAME_CSS_LOCATOR)).getText());
            WebElement add = productElement.findElement(By.tagName("button"));
            if(add.getAttribute("class").contains("btn_primary")){
                add.click();
                this.selectedProducts.add(product);
            }else{
                i--;
            }
        }
    }

    public void clickOnShoppingCart() {
        this.shoppingCart.click();
    }

    public boolean isCorrectNumber() {
        Integer number = new Integer(this.shoppingCart.findElement(By.cssSelector(CART_BADGE_CSS_LOCATOR)).getText());
        return number == selectedProducts.size();
    }

    public void selectSauceLabsOnesie() {
        this.selectedProducts = new ArrayList<>();
        BiFunction<WebElement, String, Boolean> p = (element, text) -> {
            String name = element.findElement(By.cssSelector(PRODUCT_NAME_CSS_LOCATOR)).getText();
            return name.equals(text);
        };
        WebElement sauceLabsOnesie = tools.findOption("Sauce Labs Onesie", this.products, p);
        ProductPOJO product = new ProductPOJO();
        product.setDescription(sauceLabsOnesie.findElement(By.cssSelector(PRODUCT_DESCRIPTION_CSS_LOCATOR)).getText());
        product.setPrice(new Float(sauceLabsOnesie.findElement(By.cssSelector(PRICE_CSS_LOCATOR)).getText().replaceAll("\\$", "").replaceAll("\n", "")));
        product.setName(sauceLabsOnesie.findElement(By.cssSelector(PRODUCT_NAME_CSS_LOCATOR)).getText());
        sauceLabsOnesie.findElement(By.tagName("button")).click();
        this.selectedProducts.add(product);
    }
}
