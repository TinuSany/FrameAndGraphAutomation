Feature: Retrieve and validate the driver details
	As a user I would like to retrive all the driver details through API call in json format and then validate the results.
	
	#Below tasks are performed in this Feature
	#	Record the driverId's returned in the response - stored in the /target/_data/DriversId.xlsx
	#	Assert that driverId: adams nationality is Belgian
	#	Capture all the driverIds whose nationality is British  - stored in the /target/_data/British Drivers.xlsx
	#	Capture the givenNames who are born between 1920 to 1925 - stored in the /target/_data/DriversBorn_1920_1925.xlsx
@Regression @RestAssuredTest
Scenario: Get the driver details
	Given I have created a request playload Ergast Developer API
      | typeOfInformation  | typeFilter|circuits | constructors | drivers | grid | results | fastest | status | constructorStandings | driverStandings | limit | offset |
      | Driver Information |           |         |              |         |      |         |         |        |                      |                 |  1000 |        |
    When I submit the request
	Then I validate the response status code is 200
	And I record the 'driverId' returned in the response on 'DriversId' excel
	And I verify the given drivers nationality is correct
	| driverId | nationality | 
	| adams    | Belgian     |
	And I capture all the driverIds whose nationality is 'British'
	And I Capture the givenNames who are born between 1920 to 1925