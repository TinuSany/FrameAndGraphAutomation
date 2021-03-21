Feature: Verify Money Control Login Page
	As a user I would like to test whether I can enter the username and password on the login frame

@SeleniumTest
Scenario Outline: Verify Money Control Login Page
	Given I am on the money control login page
	When I enter the Username and Password on the login page
      | Username   | Password   | 
      | <Username> | <Password> | 
	Then I verify the <Username> on the login page
	  | Username   | 
      | <Username> | 
    Examples:
      | Username  | Password   | 
      | Tinu      | Test123    | 
     # | Tinu Sany | Test123456 | 