package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pom.MoneyControlLogin;

import utilities.Log;
import utilities.ScenarioContext;
import utilities.Utility;

public class FrameLoginValidationStepDefinitions {

	@Given("I am on the money control login page")
	public void i_am_on_the_money_control_login_page() {
		WebDriver driver = (WebDriver) ScenarioContext.getContext("Driver");
		try {
			driver.get(Utility.getPropertyValue("selenium", "moneycontrolhome"));
			Log.info(String.format("Navigated to the URL : %s",
					Utility.getPropertyValue("selenium", "moneycontrolhome")));
		} catch (IOException e) {
			Log.error("Navigation to the URL is failed");
			e.printStackTrace();
		}
		MoneyControlLogin page = new MoneyControlLogin(driver);
		page.navigateToLoginFrame();

	}

	@When("I enter the Username and Password on the login page")
	public void i_enter_the_username_and_password_on_the_login_page(io.cucumber.datatable.DataTable dataTable) {
		// Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		WebDriver driver = (WebDriver) ScenarioContext.getContext("Driver");

		MoneyControlLogin page = new MoneyControlLogin(driver);
		page.switchToFrameAndEnterDetails(row);

	}

	@Then("I verify the Tinu on the login page")
	public void i_verify_the_tinu_on_the_login_page(DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		WebDriver driver = (WebDriver) ScenarioContext.getContext("Driver");

		MoneyControlLogin page = new MoneyControlLogin(driver);

	}

}
