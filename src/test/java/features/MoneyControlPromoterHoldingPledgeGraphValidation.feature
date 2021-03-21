Feature: Verify promoter holding and pledge graph details on the money control stock price page for a given stock
	As a user I would like to verify the legends, colour codes and data labels on the promoter holding vs pledge graph

@SeleniumTest
Scenario Outline: Verify shareholder details graph
	Given I am on the on the stock price and quote page for a given stock
	When I navigated to the shareholding section
	And I captured the screenshot of the "promoter" graph
	Then I verify "promoter" graph legends
	  | Legend1   | Legend2   | 
      | <Legend1> | <Legend2> |  
	Then I verify the color codes, labels and height of the promoter graph
      | Legend1   |Dec 2019Holding(%)   | Mar 2020Holding(%)   | Jun 2020Holding(%)   | Sep 2020Holding(%)   | Dec 2020Holding(%)   | 
      | <Legend1> |<Dec 2019Holding(%)> | <Mar 2020Holding(%)> | <Jun 2020Holding(%)> | <Sep 2020Holding(%)> | <Dec 2020Holding(%)> | 
	
	 
    Examples:
      | Legend1    | Legend2                            | Dec 2019Holding(%) | Mar 2020Holding(%) | Jun 2020Holding(%) | Sep 2020Holding(%) | Dec 2020Holding(%) | 
      | Holding(%) | Pledges as % of promoter shares(%) | 37.92              | 36.17              | 36.11              | 36.01              | 35.06              |