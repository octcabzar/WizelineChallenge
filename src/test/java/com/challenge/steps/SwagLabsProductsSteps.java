package com.challenge.steps;

import com.challenge.pages.SwagLabsLoginPage;
import com.challenge.pages.SwagLabsProductsPage;
import com.challenge.pages.SwagLabsShoppingcartPage;
import com.challenge.utils.Environment;
import com.challenge.utils.UtilityClass;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class SwagLabsProductsSteps extends ParentSteps{

    public Environment environment;
    private SwagLabsLoginPage login;
    private SwagLabsProductsPage products;
    private SwagLabsShoppingcartPage cart;

    public SwagLabsProductsSteps(Environment environment) {
        this.environment = environment;
        this.driver = environment.getDriver();
        this.tools = UtilityClass.getInstance(this.driver);
    }

    @When("User click on on menu")
    public void userClickOnOnMenu() {
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        products.clickOnMenu();
    }

    @And("User click on Logout")
    public void userClickOnLogout() {
        products.clickOnLogout();
    }

    @When("User click on sorting section")
    public void userClickOnSortingSection() {
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        products.clickOnSortOptions();
    }

    @And("User click on lower to high price")
    public void userClickOnLowerToHighPrice() {
        products.clickOnLowToHighPrice();
    }

    @Then("User validates products are sorted in ascending order by price")
    public void userValidatesProductsAreSortedInAscendingOrderByPrice() {
        Assert.assertTrue("Are products sorted correctly?", products.areProductsSortedByPriceAscending());
    }

    @When("User selects several products")
    public void userSelectsSeveralProducts() {
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        products.selectSeveralProducts();
        Assert.assertTrue("Is correct number?", products.isCorrectNumber());
    }

    @And("User click on shopping cart")
    public void userClickOnShoppingCart() {
        products.clickOnShoppingCart();
    }

    @Then("User can see the added products")
    public void userCanSeeTheAddedProducts() {
        cart = (SwagLabsShoppingcartPage) environment.getInstance(SwagLabsShoppingcartPage.class);
        cart.get();
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        Assert.assertTrue("Is all products available?", cart.areCorrectProducts(products.getSelectedProducts()));
    }

    @When("User select the Sauce Labs Onesie")
    public void userSelectTheSauceLabsOnesie() {
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        products.selectSauceLabsOnesie();
    }
}
