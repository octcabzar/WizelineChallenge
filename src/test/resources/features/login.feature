@report @challenge
Feature: Swag Labs Challenge

  @login @desktop @chrome @login_valid
  Scenario Outline: Login with valid user
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button
    Then User is able to see the products page

    Examples:
      | user          | password                 |
      | standard_user | pj0UINrB100WN4LWiAXiqA== |

  @login @desktop @chrome @login_invalid
    Scenario Outline:
    Given User open Swag Labs login page
    And User inputs credentials '<user>' and '<password>'
    When User click on Login button

    Examples:
    | user| password |
    | | |