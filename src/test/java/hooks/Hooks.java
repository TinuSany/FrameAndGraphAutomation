package hooks;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.Log;
import utilities.ScenarioContext;
import utilities.Utility;

public class Hooks {
	
	private WebDriver driver;
	// This hook will be executed before each Scenario and this has the higher priority
	@Before(order = 1)
	public void inIt() {
		boolean inItStatus = ScenarioContext.initializeScenarioContext();
		if (inItStatus)
			try {
				DOMConfigurator.configure("log4j.xml");
				Utility.inItLogger();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	// This hook will be executed before each Scenario for Selenium Scenario
	@Before(order = 2, value = "@SeleniumTest")
	public void beforeScenarioUpdateLogger(Scenario s) throws Exception {
		Log.startTestCase(s.getName().toString());
		String fileLocation;
		String browserName = System.getProperty("browserName");
		if (browserName!=null)
		{
			browserName = browserName.trim().toUpperCase();
		}
		else
		{
			//setting the browser name when it is blank
			System.setProperty("browserName", "Chrome");
			browserName = "CHROME";
			Log.info("Chrome is selected as default browser");
		}
		switch (browserName) {
	
		case "CHROME":
			fileLocation = Utility.getCurrentPath().concat("src\\test\\_drivers\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", fileLocation);
			driver = new ChromeDriver();
			break;

		case "EDGE":
			fileLocation = Utility.getCurrentPath().concat("src\\test\\_drivers\\msedgedriver.exe");
			System.setProperty("webdriver.edge.driver", fileLocation);
			driver = new EdgeDriver();
			break;
		default:
			throw new Exception(String.format("Browser %s is not handled",System.getProperty("browserName").toString()));
		}
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Utility.getPropertyValue("selenium", "IMPLICITWAIT_TIME_IN_SECONDS")), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		ScenarioContext.setContext("Driver",driver);
		Log.info(String.format("Browser %s initiated",System.getProperty("browserName").toString()));
	}

	// This hook will be executed before each RestAssured Scenario
	@Before(order = 3, value = "@RestAssuredTest")
	public void beforeScenarioUpdateRestAssuredLogger(Scenario s) {
		try {
			Utility.BeforeScenarioAppendLogger(s.getName().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// This hook will be executed after each RestAssured Scenario
	@After(order = 3, value = "@SeleniumTest")
	public void afterScenarioUpdateLogger(Scenario s) {

		String testStatus = s.getStatus().toString();
		if (testStatus.equalsIgnoreCase("passed"))
			Log.info("Test Scenario '" + s.getName().toString() + "' passed.");
		else
			Log.error("Test Scenario '" + s.getName().toString() + "' failed.");
		Log.endTestCase(s.getName().toString());
		if (driver!= null)
		{
			driver.quit();
			Log.info(String.format("Browser %s closed",System.getProperty("browserName").toString()));
		}
	}

	// This hook will be executed after each RestAssured Scenario
	@After(order = 3, value = "@RestAssuredTest")
	public void afterScenarioUpdateRestAssuredLogger(Scenario s) {

		try {
			Utility.AfterScenarioAppendLogger(s.getName().toString(), s.getStatus().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
