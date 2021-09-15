package com.challenge.steps;

import com.challenge.pages.SwagLabsLoginPage;
import com.challenge.pages.SwagLabsProductsPage;
import com.challenge.utils.Environment;
import com.challenge.utils.UtilityClass;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class SwagLabsLoginSteps extends ParentSteps{

    private static final String SWAG_LABS_MAIN_URL = "https://www.saucedemo.com/";
    public Environment environment;
    private SwagLabsLoginPage login;
    private SwagLabsProductsPage products;

    public SwagLabsLoginSteps(Environment environment) {
        this.environment = environment;
        this.driver = environment.getDriver();
        this.tools = UtilityClass.getInstance(this.driver);
    }

    @Given("User open Swag Labs login page")
    public void userOpenSwagLabsLoginPage(){
        this.driver.navigate().to(SWAG_LABS_MAIN_URL);
        login = SwagLabsLoginPage.getInstance(driver);
        login.get();
    }

    @And("User inputs credentials {string} and {string}")
    public void userInputsCredentialsUserAndPassword(String user, String password) {
        login.sendCredentials(user, tools.decrypt(password));
    }

    @Then("User is able to see the products page")
    public void userIsAbleToSeeTheProductsPage() {
        products = SwagLabsProductsPage.getInstance(this.driver);
        Assert.assertTrue("Title expected: PRODUCTS", products.isTitleCorrect());
        Assert.assertTrue(String.format("Products available: %s", products.getProducts().size()), products.getProducts().size()>0);
        Assert.assertTrue(String.format("Page URL: %s", driver.getCurrentUrl()), driver.getCurrentUrl().contains("inventory"));
    }

    @When("User click on Login button")
    public void userClickOnLoginButton() {
        login.clickOnLoginButton();
    }
}
