Feature: Verify driver details
	As a user I would like to verify API returned driver details against a trusted source - downloaded csv file.
	
	#This is the functional test to ensure that data provided by API is correct. In normal scenario this will be performed against the database.
	# In this test values returned by the API is compared against CSV files available in the http://ergast.com/mrd/db#csv.
 

@Regression @RestAssuredTest
Scenario: Verify driver details against csv file
	Given I have created a request playload Ergast Developer API
      | typeOfInformation  | typeFilter|circuits | constructors | drivers | grid | results | fastest | status | constructorStandings | driverStandings | limit | offset |
      | Driver Information |           |         |              |         |      |         |         |        |                      |                 |  1000 |        |
    When I submit the request
    Then I validate the response status code is 200
    Then I compare the details against "drivers.csv" file