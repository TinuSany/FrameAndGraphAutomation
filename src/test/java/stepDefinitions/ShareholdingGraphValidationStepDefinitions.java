package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;


import extensions.SeleniumUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pom.MoneyControlStockPricePage;
import utilities.Log;
import utilities.ScenarioContext;
import utilities.Utility;

public class ShareholdingGraphValidationStepDefinitions {
	
	@Given("I am on the on the stock price and quote page for a given stock")
	public void i_am_on_the_on_the_stock_price_and_quote_page_for_a_given_stock() {
	   
		WebDriver driver = (WebDriver)ScenarioContext.getContext("Driver");
		try {
			driver.get(Utility.getPropertyValue("selenium", "moneycontrol"));
			Log.info(String.format("Navigated to the URL : %s",Utility.getPropertyValue("selenium", "moneycontrol")));
		} catch (IOException e) {
			Log.error("Navigation to the URL is failed");
			e.printStackTrace();
		}
		MoneyControlStockPricePage page = new MoneyControlStockPricePage(driver);
				
		boolean elementDisplayed = SeleniumUtils.WaitTillElementDisplayed(driver, page.getShareHoldingHeadingLocator(), 20);
		Assert.assertTrue(elementDisplayed);
		Log.info("Shareholding heading is displayed");
		

	}
	
	@When("I navigated to the shareholding section")
	public void i_navigated_to_the_shareholding_section() {
		WebDriver driver = (WebDriver)ScenarioContext.getContext("Driver");
		MoneyControlStockPricePage page = new MoneyControlStockPricePage(driver);
		SeleniumUtils.scrollToWebElement(driver, page.map.summaryHoldingChart);
		Log.info("Shareholding summary grapgh is visible on the page");

	}
	
	@When("I captured the screenshot of the {string} graph")
    public void i_captured_the_screenshot_of_the_something_graph(String graphType) throws Exception {
		
		WebDriver driver = (WebDriver)ScenarioContext.getContext("Driver");
		MoneyControlStockPricePage page = new MoneyControlStockPricePage(driver);
		
		switch (graphType.toLowerCase()) {
		case "summary":
			SeleniumUtils.takeScreenShot(page.map.summaryHoldingChart, "ShareholdingSummary");		
			break;
		case "promoter":
			SeleniumUtils.takeScreenShot(page.map.promotersHoldingChart, "PromoterHolding");		
			break;


		default:
			throw new Exception("Option not handled");
		}
		
	}
	
	@Then("I verify {string} graph legends")
	public void i_verify_graph_legends(String graphType, DataTable dataTable) throws Exception {

		//Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		WebDriver driver = (WebDriver)ScenarioContext.getContext("Driver");
		MoneyControlStockPricePage page = new MoneyControlStockPricePage(driver);
		
		Map<String, String> legends;
		
		switch (graphType.toLowerCase()) {
		case "summary":
			legends = page.getAllLegendColourClassName(page.map.summaryHoldingChart);		
			break;
		case "promoter":
			legends = page.getAllLegendColourClassName(page.map.promotersHoldingChart);		
			break;


		default:
			throw new Exception("Option not handled");
		}
		
		

		for (String k : row.keySet())
		{
			Assert.assertTrue(legends.containsKey(row.get(k)));
		}
		
	}
	@Then("I verify shareholding percentage and graph fill colour")
	public void i_verify_shareholding_percentage_and_graph_fill_colour(DataTable dataTable) {
	    
		//Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		WebDriver driver = (WebDriver)ScenarioContext.getContext("Driver");
		MoneyControlStockPricePage page = new MoneyControlStockPricePage(driver);
		page.verifyPieChart(page.map.summaryHoldingChart,row);
	}


	@Then("I verify the color codes, labels and height of the promoter graph")
	public void i_verify_the_color_codes_labels_and_height_of_the_promoter_graph(DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		WebDriver driver = (WebDriver)ScenarioContext.getContext("Driver");
		MoneyControlStockPricePage page = new MoneyControlStockPricePage(driver);
		page.verifyBarChart(page.map.promotersHoldingChart,row);
	
	}
}
