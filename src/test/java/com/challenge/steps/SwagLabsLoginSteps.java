package com.challenge.steps;

import com.challenge.pages.SwagLabsLoginPage;
import com.challenge.pages.SwagLabsProductsPage;
import com.challenge.utils.Environment;
import com.challenge.utils.UtilityClass;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

public class SwagLabsLoginSteps extends ParentSteps {

    private static final String SWAG_LABS_MAIN_URL = "https://www.saucedemo.com/";
    private SwagLabsLoginPage login;
    private SwagLabsProductsPage products;
    private final SoftAssertions soft = new SoftAssertions();

    private enum ERRORS {PASSWORD_IS_REQUIRED, USERNAME_AND_PASSWORD_DO_NOT_MATCH, USERNAME_IS_REQUIRED}

    public SwagLabsLoginSteps(Environment environment) {
        this.environment = environment;
        this.driver = environment.getDriver();
        this.tools = UtilityClass.getInstance(this.driver);
    }

    @Given("User open Swag Labs login page")
    public void userOpenSwagLabsLoginPage() {
        this.driver.navigate().to(SWAG_LABS_MAIN_URL);
        login = (SwagLabsLoginPage) environment.getInstance(SwagLabsLoginPage.class);
        login.get();
    }

    @And("User inputs credentials {string} and {string}")
    public void userInputsCredentialsUserAndPassword(String user, String password) {
        login.sendCredentials(user, tools.decrypt(password));
    }

    @Then("User is able to see the products page")
    public void userIsAbleToSeeTheProductsPage() {
        products = (SwagLabsProductsPage) environment.getInstance(SwagLabsProductsPage.class);
        products.get();
        Assert.assertTrue("Title expected: PRODUCTS", products.isTitleCorrect());
        Assert.assertTrue(String.format("Products available: %s", products.getProducts().size()), products.getProducts().size() > 0);
        Assert.assertTrue(String.format("Page URL: %s", driver.getCurrentUrl()), driver.getCurrentUrl().contains("inventory"));
    }

    @When("User click on Login button")
    public void userClickOnLoginButton() {
        login.clickOnLoginButton();
    }

    @When("User input empty user and {string}")
    public void userInputEmptyUserAndPassword(String password) {
        login.sendCredentials("", tools.decrypt(password));
    }

    @Then("User validates error label for empty user")
    public void userValidatesErrorLabelForEmptyUser() {
        SoftAssertions soft = new SoftAssertions();
        boolean isUserNameCorrect = login.isUserNameCorrect();
        String comment = String.format("Is user name correct?: %s", isUserNameCorrect);
        soft.assertThat(isUserNameCorrect).as(comment).isTrue();
        boolean isPasswordCorrect = login.isPasswordCorrect();
        comment = String.format("Is password correct?: %s", isPasswordCorrect);
        soft.assertThat(isPasswordCorrect).as(comment).isTrue();
        Assert.assertTrue("Is correct message displayed?", login.isCorrectMessage(ERRORS.USERNAME_IS_REQUIRED.name()));
    }

    @When("User closes error message")
    public void userClosesErrorMessage() {
        login.closeErrorMessage();
    }

    @Then("User validates the error label for wrong credentials")
    public void userValidatesTheErrorLabelForWrongCredentials() {
        boolean isUserNameCorrect = login.isUserNameCorrect();
        String comment = String.format("Is user name correct?: %s", isUserNameCorrect);
        soft.assertThat(isUserNameCorrect).as(comment).isTrue();
        boolean isPasswordCorrect = login.isPasswordCorrect();
        comment = String.format("Is password correct?: %s", isPasswordCorrect);
        soft.assertThat(isPasswordCorrect).as(comment).isTrue();
        Assert.assertTrue("Is correct message displayed?", login.isCorrectMessage(ERRORS.USERNAME_AND_PASSWORD_DO_NOT_MATCH.name()));
    }

    @When("User input {string}and empty password")
    public void userInputUserAndEmptyPassword(String user) {
        login.sendCredentials(user, "");
    }

    @Then("User validates error label for empty password")
    public void userValidatesErrorLabelForEmptyPassword() {
        boolean isUserNameCorrect = login.isUserNameCorrect();
        String comment = String.format("Is user name correct?: %s", isUserNameCorrect);
        soft.assertThat(isUserNameCorrect).as(comment).isTrue();
        boolean isPasswordCorrect = login.isPasswordCorrect();
        comment = String.format("Is password correct?: %s", isPasswordCorrect);
        soft.assertThat(isPasswordCorrect).as(comment).isTrue();
        Assert.assertTrue("Is correct message displayed?", login.isCorrectMessage(ERRORS.PASSWORD_IS_REQUIRED.name()));
    }

    @Then("User can see the login page")
    public void userCanSeeTheLoginPage() {
        login.get();
    }
}
