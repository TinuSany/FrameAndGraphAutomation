Feature: Verify UI details against Ergast Developer API
	As a user I would like to verify the UI details against API.
	
	#This is the integration test to ensure that front end back end are working correctly. 
	# Details displayed on the 'http://ergast.com/mrd/query/' is compared against the values returned from API.
	# Both Selenium and RestAssured are used in this test. 
	
@IntegrationTest @RestAssuredTest
Scenario Outline: Verify driver details against UI
	Given I have created a request playload Ergast Developer API
		| typeOfInformation   | typeFilter  | circuits  | constructors   | drivers   | grid   | results   | fastest   | status   | constructorStandings   | driverStandings   | limit   | offset   |
		| <typeOfInformation> | <typeFilter>| <circuits>| <constructors> | <drivers> | <grid> | <results> | <fastest> | <status> | <constructorStandings> | <driverStandings> | <limit> | <offset> |
    When I submit the request
    Then I validate the response status code is 200
    Then I verify the record total is 1
    Then I compare the details against UI screen
      | DriverName   | ResultType          | Season | 
      | <DriverName> | <typeOfInformation> | All    | 
    Examples:
		| typeOfInformation  | typeFilter         | circuits | constructors | drivers | grid | results | fastest | status | constructorStandings | driverStandings | limit | offset | DriverName         |
		| Driver Information | hamilton           |          |              |         |      |         |         |        |                      |                 | 10    |        | Lewis Hamilton     |
		| Driver Information | michael_schumacher |          |              |         |      |         |         |        |                      |                 | 10    |        | Michael Schumacher |