@report @challenge
Feature: Swag Labs Challenge - Products page

  @products @desktop @chrome @sort_price_low_to_high
  Scenario Outline: Sort products from low to high price
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button
    Then User is able to see the products page
    When User click on sorting section
    And User click on lower to high price
    Then User validates products are sorted in ascending order by price

    Examples:
      | user          | password                 |
      | standard_user | pj0UINrB100WN4LWiAXiqA== |

  @products @desktop @chrome @add_products
  Scenario Outline: Add products to shopping cart
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button
    Then User is able to see the products page
    When User selects several products
    And User click on shopping cart
    Then User can see the added products

    Examples:
      | user          | password                 |
      | standard_user | pj0UINrB100WN4LWiAXiqA== |

  @products @desktop @chrome @add_sauce_labs_onesie
  Scenario Outline: Select a specific product
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button
    Then User is able to see the products page
    When User select the Sauce Labs Onesie
    And User click on shopping cart
    Then User can see the added products

    Examples:
      | user          | password                 |
      | standard_user | pj0UINrB100WN4LWiAXiqA== |

  @products @desktop @chrome @purchase_products
  Scenario Outline: Complete purchase
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button
    Then User is able to see the products page
    When User selects several products
    And User click on shopping cart
    Then User can see the added products
    When User click on Checkout
    Then User see the billing address page
    Then User can input their billing data
    When User click on Continue
    Then User can see the check out page
    When User click on Finish
    Then User completed the purchase

    Examples:
      | user          | password                 |
      | standard_user | pj0UINrB100WN4LWiAXiqA== |