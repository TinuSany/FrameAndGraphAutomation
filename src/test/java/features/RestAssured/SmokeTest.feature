Feature: Smoke Test Ergast Developer API
	As a user I would like to smoke test different options of Smoke Test Ergast Developer API.
# Please use typeFilter column for drivers filter. PLEASE LEAVE drivers column blank and add it under typeFilter for filtering based on drivers id.

@Regression @Smoke @RestAssuredTest
Scenario Outline: Verify Driver Information with valid request
	Given I have created a request playload Ergast Developer API
		| typeOfInformation   | typeFilter   | season   | round   | circuits   | constructors   | drivers   | grid   | results   | fastest   | status   | constructorStandings   | driverStandings   | limit   | offset   | 
		| <typeOfInformation> | <typeFilter> | <season> | <round> | <circuits> | <constructors> | <drivers> | <grid> | <results> | <fastest> | <status> | <constructorStandings> | <driverStandings> | <limit> | <offset> | 
    When I submit the request
    Then I validate the response status code is 200
    Then I verify the record total, limit, offset and table name
    |ExpectedNonZeroRecords  |limit   | offset  |
    |<ExpectedNonZeroRecords>|<limit> | <offset>|
    Examples:
      | TCNO | typeOfInformation       | typeFilter | season  | round | circuits | constructors | drivers | grid | results | fastest | status | constructorStandings | driverStandings | limit | offset | ExpectedNonZeroRecords | 
      | 1    | Season List             |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 2    | Race Schedule           |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 3    | Race Results            |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 4    | Qualifying Results      |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 5    | Qualifying Results      | 1          |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 6    | Constructor Standings   |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 7    | Constructor Standings   | 3          |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 8    | Driver Standings        |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 8    | Driver Standings        | 2          |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 10   | Driver Information      |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 11   | Driver Information      | alonso     |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 12   | Constructor Information |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 13   | Constructor Information | ferrari    |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 14   | Circuit Information     |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 15   | Circuit Information     | monza      |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 16   | Finishing Status        |            |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 17   | Finishing Status        | 0          |         |       |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 18   | Lap Times               |            | current | last  |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 19   | Lap Times               | 5          | 2016    | 5     |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 20   | Pit Stops               |            | 2015    | last  |          |              |         |      |         |         |        |                      |                 |       |        | Y                      | 
      | 21   | Pit Stops               | 0          | current | 15    |          |              |         |      |         |         |        |                      |                 |       |        | Y                      |                         
                        
