@report @challenge
Feature: Swag Labs Challenge

  @login @desktop @chrome @login_invalid
  Scenario Outline: Login with invalid user
    Given User open Swag Labs login page
    When User input empty user and '<wrongPassword>'
    And User click on Login button
    Then User validates error label for empty user
    When User closes error message
    When User input '<user>'and empty password
    And User click on Login button
    Then User validates error label for empty password
    When User closes error message
    When User inputs credentials '<user2>' and '<wrongPassword>'
    And User click on Login button
    Then User validates the error label for wrong credentials

    Examples:
      | user          | wrongPassword | user2      |
      | standard_user | BTGPkNUz2vg=  | wrong_user |

  @login @desktop @chrome @logout
  Scenario Outline: Logout from application
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button
    Then User is able to see the products page
    When User click on on menu
    And User click on Logout
    Then User can see the login page

    Examples:
      | user          | password                 |
      | standard_user | pj0UINrB100WN4LWiAXiqA== |