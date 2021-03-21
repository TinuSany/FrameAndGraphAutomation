Feature: Verify share holder summary graph details on the money control stock price page for a given stock
	As a user I would like to verify the legends, colour codes and share percentage on the share holding summary graph

@SeleniumTest
Scenario Outline: Verify shareholder details graph
	Given I am on the on the stock price and quote page for a given stock
	When I navigated to the shareholding section
	And I captured the screenshot of the "summary" graph
	Then I verify "summary" graph legends
	  | Legend1   | Legend2   | Legend3   | Legend4   | Legend5   | 
      | <Legend1> | <Legend2> | <Legend3> | <Legend4> | <Legend5> | 
	Then I verify shareholding percentage and graph fill colour
		| Legend1   | ShareHoldingLegend1   | Legend2   | ShareHoldingLegend2   | Legend3   | ShareHoldingLegend3   | Legend4   | ShareHoldingLegend4   | Legend5   | ShareHoldingLegend5   | 
		| <Legend1> | <ShareHoldingLegend1> | <Legend2> | <ShareHoldingLegend2> | <Legend3> | <ShareHoldingLegend3> | <Legend4> | <ShareHoldingLegend4> | <Legend5> | <ShareHoldingLegend5> | 
    Examples:
		| Legend1  | ShareHoldingLegend1 | Legend2 | ShareHoldingLegend2 | Legend3 | ShareHoldingLegend3 | Legend4 | ShareHoldingLegend4 | Legend5 | ShareHoldingLegend5 | 
		| Promoter | 35.06%              | FII     | 20.31%              | DII     | 15.22%              | Public  | 29.4%               | Others  | 0.01%               | 