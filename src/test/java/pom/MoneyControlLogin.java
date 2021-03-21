package pom;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import extensions.SeleniumUtils;
import utilities.Log;

public class MoneyControlLogin {

	public MoneyControlLoginMap map;
	WebDriver _driver;

	public MoneyControlLogin(WebDriver driver) {
		this._driver = driver;
		map = new MoneyControlLoginMap(driver);
	}

	public By getiFrameLocator() {
		return By.xpath("//iframe[@id='myframe']");
	}

	public By getLoginLinkLocator() {
		return By.xpath("//a[normalize-space()='Hello, Login']");
	}

	public void navigateToLoginFrame() {
		boolean elementDisplayed = SeleniumUtils.WaitTillElementDisplayed(_driver, getLoginLinkLocator(), 20);
		Assert.assertTrue(elementDisplayed);
		Log.info("Money control home page is displayed");

		map.LoginLink.click();
		map.LoginButton.click();

		elementDisplayed = SeleniumUtils.WaitTillElementDisplayed(_driver, getiFrameLocator(), 20);
		Assert.assertTrue(elementDisplayed);
		Log.info("Iframe is displayed");
	}

	public void switchToFrameAndEnterDetails(Map<String, String> row) {
		_driver.switchTo().frame(map.MyFrame);
		Log.info("Switched to Iframe");
		map.UserID.sendKeys(row.get("Username"));
		map.Password.sendKeys(row.get("Password"));
		Log.info("Username and Password entered on the form ");

	}

	public void validateAndSwitchBackToMainPage(Map<String, String> row) {

		String enteredvalue = map.UserID.getAttribute("value");
		Assert.assertEquals("User name is not correct", row.get("Username"), enteredvalue);
		Log.info("User Id validated");
		_driver.switchTo().defaultContent();
		map.LoginCloseButton.click();
	}

	public class MoneyControlLoginMap {
		WebDriver _driver;

		@FindBy(how = How.XPATH, using = "//a[normalize-space()='Hello, Login']")
		private WebElement LoginLink;

		@FindBy(how = How.XPATH, using = "//a[normalize-space()='Log-in']")
		private WebElement LoginButton;

		@FindBy(how = How.XPATH, using = "//*[@id='LoginModal']/div/div/button")
		private WebElement LoginCloseButton;

		@FindBy(how = How.XPATH, using = "//form[@id='login_form']//input[@id='pwd']")
		private WebElement Password;

		@FindBy(how = How.XPATH, using = "//form[@id='login_form']//input[@id='email']")
		private WebElement UserID;

		@FindBy(id = "myframe")
		private WebElement MyFrame;

		public MoneyControlLoginMap(WebDriver driver) {
			this._driver = driver;
			PageFactory.initElements(driver, this);
		}
	}

}
