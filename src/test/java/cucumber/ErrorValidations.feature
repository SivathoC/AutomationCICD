
@tag
Feature: Error Validcations
  I want to use this template for my feature file
  
  pre-requisite test in cucumber can be separated fgrom actual scenario using the keyword Backgroud
  
  @ErrorValidation
  Scenario Outline: Positive test of Error Validcations
    Given I landed on Ecommerce Page
    When Logged in with username <username> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples: 
     | username 									| password 				| 
     | sivathoc@gmail.com				|	password1234$	|
     | zintlec@gmail.com					|	password1234$	|