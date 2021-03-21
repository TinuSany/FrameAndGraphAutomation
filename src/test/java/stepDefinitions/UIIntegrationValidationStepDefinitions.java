package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import extensions.RestAssuredUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import pojo.Driver;
import pojo.Drivers;
import pom.QueryPage;
import utilities.ScenarioContext;
import utilities.Utility;

public class UIIntegrationValidationStepDefinitions extends RestAssuredUtils {
	private static WebDriver driver = null;
	@Then("I compare the details against UI screen")
	public void i_compare_the_details_against_ui_screen(DataTable dataTable) {
		
		//Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

		//Assuming there is only one data record in the DataTable - one header record and one data record
		Map<String, String> row = rows.get(0);
		
		//Retrieving saved response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		Drivers drivers = response.jsonPath().getObject("MRData.DriverTable", Drivers.class);
		
		//fetch first driver record
		Driver driverFromAPI = drivers.getDrivers().get(0);
		
		//setting code to null as front end doesn't use this field as of now
		driverFromAPI.setCode(null);
		
		//Chrome Browser needs to be installed for performing this step. Chrome version needed is v89.
		try 
		{
			//Selenium webdriver - chromedriver path. This is include in the package.
			String fileLocation = Utility.getCurrentPath().concat("src\\test\\_drivers\\chromedriver.exe");
			
			//Inializing webdriver for selenium test
			System.setProperty("webdriver.chrome.driver", fileLocation);
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Utility.getPropertyValue("selenium", "IMPLICITWAIT_TIME_IN_SECONDS")), TimeUnit.SECONDS);
			
			//navigating to http://ergast.com/mrd/query/. Url is taken from property file for future maintenance.
			driver.get(Utility.getPropertyValue("selenium", "Url"));
			
			//creating Page Object
			QueryPage queryPage = new QueryPage(driver);
			
			//maximize the browser
			driver.manage().window().maximize();
			
			//search for the driver details based on the values from data table.
			queryPage.enterDriverDetailsAndClickSearch(row.get("ResultType"), row.get("Season"), row.get("DriverName"));
			
			//handle the new window opened
			queryPage.switchToChildWindow();
			
			//Fetch the details from Webpage and return as a Driver object
			Driver d = queryPage.captureDriverDetails();
			
			//close all the browsers
			queryPage.closeAllWindows();
			
			//Assert details on the UI and API for the given driver is matching or not
			Assert.assertTrue("UI and API should have the same details", d.equals(driverFromAPI));
			

		} catch (IOException e) {
			if (driver != null)
				driver.quit();
			e.printStackTrace();
		}

	}

}
