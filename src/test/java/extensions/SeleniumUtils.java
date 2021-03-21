package extensions;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import utilities.Log;

import utilities.Utility;

public class SeleniumUtils {

	
	// Method to wait for the presence of a WebElement
		public static boolean WaitTillElementDisplayed(WebDriver driver, By by,int waitTimeInSeconds) {
			if (driver != null && by != null) {
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						  .withTimeout(Duration.ofSeconds(waitTimeInSeconds))
						  .pollingEvery(Duration.ofSeconds(1))
						  .ignoring(NoSuchElementException.class);
				WebElement element;
				element= wait.until(ExpectedConditions.presenceOfElementLocated(by));
				if (element !=null)
				{
					Log.info("WebElement Displayed");
					return true;
				}
				else
				{
					Log.warn("WebElement not displayed");
				}
					
			} else {
				Log.error("Either driver or by is null");
			}
			return false;
		}

	
	// Method to scroll down to the WebElement
	public static void scrollToWebElement(WebDriver driver, WebElement element) {
		if (driver != null && element != null) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
			Log.info("Scroll is successfully completed");
		} else {
			Log.error("Either driver or element is null");
		}
	}

	// Method to take the screenshot of visible WebPage
	public static void takeScreenShot(WebDriver driver, String fileName) {

		if (driver != null && fileName != "") {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			// Move image file to new destination
			String fileLocation = Utility.getCurrentPath()
					.concat(String.format("target\\_screenShot\\%s.png", fileName));
			File DestFile = new File(fileLocation);
			// Copy file at destination
			try {
				FileUtils.copyFile(SrcFile, DestFile);
				Log.info("Screenshot saved to the location - " + fileLocation);
			} catch (IOException e) {
				Log.error("File copy is failed");
				e.printStackTrace();
			}

		} else {
			Log.error("Either driver or file name is not valid");
		}

	}

	// Method to take the screenshot of visible WebPage
	public static void takeScreenShot(WebElement element, String fileName) {

		if (element != null && fileName != "") {
			TakesScreenshot scrShot = ((TakesScreenshot) element);
			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			// Move image file to new destination
			String fileLocation = Utility.getCurrentPath()
					.concat(String.format("target\\_screenShot\\%s.png", fileName));
			File DestFile = new File(fileLocation);
			// Copy file at destination
			try {
				FileUtils.copyFile(SrcFile, DestFile);
				Log.info("Screenshot saved to the location - " + fileLocation);
			} catch (IOException e) {
				Log.error("File copy is failed");
				e.printStackTrace();
			}

		} else {
			Log.error("Either driver or file name is not valid");
		}

	}
}
