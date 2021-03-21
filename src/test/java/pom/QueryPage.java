package pom;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pojo.Driver;

public class QueryPage {

	public QueryPageMap map;
	WebDriver _driver;

	public QueryPage(WebDriver driver) {
		this._driver = driver;
		map = new QueryPageMap(driver);
	}

	public void waitTillPageLoad(WebElement w) {
		// Making the execution to stop until Page to load
		WebDriverWait wait = new WebDriverWait(_driver, 5);
		wait.until(ExpectedConditions.visibilityOf(w));
	}

	// search for the driver details based on the values from data table.
	public void enterDriverDetailsAndClickSearch(String resultType, String season, String driverName) {
		waitTillPageLoad(map.ResultTypeCombBox());
		Select resultTypeCombBox = new Select(map.ResultTypeCombBox());
		resultTypeCombBox.selectByVisibleText(resultType);

		Select seasonCombBox = new Select(map.SeasonCombBox());
		seasonCombBox.selectByVisibleText(season);

		Select driverNameCombBox = new Select(map.DriverCombBox());
		driverNameCombBox.selectByVisibleText(driverName);
		map.SearchButton().click();

	}

	// handle the new window opened and switch to the new window
	public void switchToChildWindow() {
		String mainWindow = _driver.getWindowHandle();
		Set<String> windowHandles = _driver.getWindowHandles();
		Iterator<String> windowItarator = windowHandles.iterator();
		while (windowItarator.hasNext()) {
			String childWindow = windowItarator.next();
			if (!mainWindow.equalsIgnoreCase(childWindow)) {
				_driver.switchTo().window(childWindow);
				break;
			}
		}
	}

	// Fetch the details from Webpage and return as a Driver object
	public Driver captureDriverDetails() {
		waitTillPageLoad(map.DriverTableDataRows());
		String driverId = map.QueryTableDataByRowAndColumn(1, 2).getText().trim();
		String[] name = map.DriverTableDataByRowAndColumn(1, 1).getText().trim().split(" ");
		String permanentNumber = map.DriverTableDataByRowAndColumn(1, 2).getText().trim();
		String nationality = map.DriverTableDataByRowAndColumn(1, 3).getText().trim();
		String dob = map.DriverTableDataByRowAndColumn(1, 4).getText().trim();
		String url = map.DriverTableLinkByRow(1).getAttribute("href").trim();
		Driver d;
		if (permanentNumber.length() > 0)
			d = new Driver(driverId, url, name[0].trim(), name[1].trim(), dob, nationality, permanentNumber, null);
		else
			d = new Driver(driverId, url, name[0].trim(), name[1].trim(), dob, nationality, null, null);
		return d;

	}

	// close all the opened browsers by Selenium
	public void closeAllWindows() {
		_driver.quit();
	}

	public class QueryPageMap {
		// WebElement details

		WebDriver _driver;

		public QueryPageMap(WebDriver driver) {
			this._driver = driver;
		}

		public WebElement ResultTypeCombBox() {
			return _driver.findElement(By.xpath("//form[1]/table[1]/tbody[1]/tr[1]/td[2]/select[1]"));
		}

		public WebElement SeasonCombBox() {
			return _driver.findElement(By.xpath("//select[@onchange='javascript:onChangeQuerySeason()']"));
		}

		public WebElement DriverCombBox() {
			return _driver.findElement(By.xpath("//form[1]/table[1]/tbody[1]/tr[3]/td[2]/select[1]"));
		}

		public WebElement SearchButton() {
			return _driver.findElement(By.xpath("//input[@onclick='javascript:querySubmit()']"));
		}

		public WebElement DriverTableHeaderCols() {
			return _driver.findElement(By.xpath("//div[1]/div[1]/div[2]//table[1]//tr[2]/th"));
		}

		public WebElement DriverTableDataRows() {
			return _driver.findElement(By.xpath("//div[1]/div[1]/div[2]//table[1]//tr"));
		}

		public WebElement DriverTableDataByRowAndColumn(int rw, int col) {
			String xPathText = String.format("//div[1]/div[1]/div[2]//table[1]//tr[%d]/td[%d]", rw + 2, col);
			return _driver.findElement(By.xpath(xPathText));
		}

		public WebElement DriverTableLinkByRow(int rw) {
			String xPathText = String.format("//div[1]/div[1]/div[2]//table[1]//tr[%d]/td[5]/a", rw + 2);
			return _driver.findElement(By.xpath(xPathText));
		}

		public WebElement QueryTableDataByRowAndColumn(int rw, int col) {
			String xPathText = String.format("//div[1]/table[1]//tr[%d]/td[%d]", rw + 2, col);
			return _driver.findElement(By.xpath(xPathText));
		}

	}

}