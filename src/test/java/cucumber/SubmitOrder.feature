
@tag
Feature: Purrchase the order from Ecommerce Website
  I want to use this template for my feature file
  
  pre-requisite test in cucumber can be separated fgrom actual scenario using the keyword Backgroud
  
  Background:
  Given I landed on Ecommerce Page
  
  
  @Regression
  Scenario Outline: Positive test of sumbitting the order
    Given Logged in with username <username> and password <password>
    When I add product <productName> to cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." confirmation message is displayed

    Examples: 
     | username 									| password 							| productName			|
     | sivathoc@gmail.com				|	Password1234$				| ZARA COAT 3			|
     | zintlec@gmail.com					|	Password1234$				| IPHONE 13 PRO		|