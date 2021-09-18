package com.challenge.steps;

import com.challenge.pages.*;
import com.challenge.utils.Environment;

import com.challenge.utils.UtilityClass;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class SwagLabsPurchaseSteps extends ParentSteps{

    public Environment environment;
    private SwagLabsShoppingcartPage cart;
    private SwagLabsBillingDataPage billing;
    private SwagLabsCheckOutPage checkout;
    private SwagLabsProductsPage products;
    private SwagLabsFinishPage finish;

    public SwagLabsPurchaseSteps(Environment environment) {
        this.environment = environment;
        this.driver = environment.getDriver();
        this.tools = UtilityClass.getInstance(this.driver);
    }

    @When("User click on Checkout")
    public void userClickOnCheckout() {
        cart = (SwagLabsShoppingcartPage) environment.getInstance(SwagLabsShoppingcartPage.class);
        cart.clickOnCheckOut();
    }

    @Then("User see the billing address page")
    public void userSeeTheBillingAddressPage() {
        billing = (SwagLabsBillingDataPage) environment.getInstance(SwagLabsBillingDataPage.class);
        billing.get();
    }

    @Then("User can input their billing data")
    public void userCanInputTheirBillingData() {
        billing.inputData();
    }

    @When("User click on Continue")
    public void userClickOnContinue() {
        billing.clickOnContinue();
    }

    @Then("User can see the check out page")
    public void userCanSeeTheCheckOutPage() {
        checkout = (SwagLabsCheckOutPage) environment.getInstance(SwagLabsCheckOutPage.class);
        checkout.get();
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        Assert.assertTrue("Are all products present?", checkout.areAllProductsAvailable(products.getSelectedProducts()));
    }

    @When("User click on Finish")
    public void userClickOnFinish() {
        checkout.clickOnFinish();
    }

    @Then("User completed the purchase")
    public void userCompletedThePurchase() {
        finish = (SwagLabsFinishPage) environment.getInstance(SwagLabsFinishPage.class);
        finish.get();
        Assert.assertTrue("Is thank you message displayed?", finish.isMessageDisplayed());
    }
}
