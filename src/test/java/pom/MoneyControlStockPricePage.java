package pom;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import org.junit.Assert;

import extensions.GraphUtils;
import utilities.Utility;

public class MoneyControlStockPricePage extends GraphUtils {

	public MoneyControlStockPricePageMap map;
	WebDriver _driver;

	public MoneyControlStockPricePage(WebDriver driver) {
		this._driver = driver;
		map = new MoneyControlStockPricePageMap(driver);
	}

	public By getShareHoldingHeadingLocator() {
		return By.xpath("//h2[normalize-space()='Shareholding']");
	}

	public void verifyPieChart(WebElement chart, Map<String, String> row) {
		Map<String, String> legendClasses = getAllLegendColourClassName(chart);
		Map<String, String> legendFills = getAllLegendFillColour(chart);
		for (String k : row.keySet()) {
			if (!k.contains("ShareHolding")) {
				String legendName = row.get(k);
				String shareholdingPercentage = row.get("ShareHolding".concat(k));
				String expectedColourClass = legendClasses.get(legendName);
				String expectedFillColour = legendFills.get(legendName);

				String xpathText = String.format(".//*[contains(@class,'%s')", expectedColourClass);
				String legendFillColor = chart
						.findElement(By.xpath(xpathText.concat(" and contains(@class,'highcharts-point')]")))
						.getAttribute("fill");
				String legendStrokeColor = chart
						.findElement(By.xpath(
								xpathText.concat(" and contains(@class,'highcharts-data-label') and name()='path']")))
						.getAttribute("stroke");
				String legendDataLabel = chart
						.findElement(By.xpath(
								xpathText.concat(" and contains(@class,'highcharts-data-label')]//*[name()='text']")))
						.getText();
				legendDataLabel = legendDataLabel.substring(0, legendDataLabel.length() / 2).trim();
				if (legendFillColor.contains("rgb")) {
					
					legendFillColor = Utility.convertRgbToHex(legendFillColor);
				}

				if (legendStrokeColor.contains("rgb")) {
					
					legendStrokeColor = Utility.convertRgbToHex(legendStrokeColor);
				}

				Assert.assertEquals(String.format("Fill color of %s is not correct",legendName), expectedFillColour, legendFillColor);
				Assert.assertEquals(String.format("Data Label connector stroke color of %s is not correct",legendName),expectedFillColour, legendStrokeColor);
				Assert.assertEquals(String.format("Shareholding Percentage is not matching for %s",legendName),shareholdingPercentage, legendDataLabel);
			}
		}
	}

	public void verifyBarChart(WebElement chart, Map<String, String> row) {
		Map<String, String> legendClasses = getAllLegendColourClassName(chart);
		Map<String, String> legendFills = getAllLegendFillColour(chart);
		Map<String, String> xAxisLabels = this.getXAxisLabelsAndIndex(chart);

		// get the plot area height
		int plotAreaHeight = getPlotAreaHeight(chart);
		int maxYaxis = Integer.parseInt(this.getLastYAxisLabel(chart));

		for (String k : row.keySet()) {
			//System.out.println(k);
			if (k.contains("Legend")) {
				String legendName = row.get(k);
				//System.out.println(legendName);
				String expectedColourClass = legendClasses.get(legendName);
				String expectedFillColour = legendFills.get(legendName);

				for (String k1 : row.keySet()) {

					if (k1.contains(legendName)) {
						String shareholdingPercentage = row.get(k1);
						String xAxisLabel = k1.replace(legendName, "").trim();
						Assert.assertTrue("X Axis Value from the DataTable doesn't Present on the Chart",
								xAxisLabels.containsKey(xAxisLabel));
						int chartIndex = Integer.parseInt(xAxisLabels.get(xAxisLabel));
						String xpathText = String.format(".//*[contains(@class,'%s')", expectedColourClass);
						List<WebElement> charts = chart
								.findElements(By.xpath(xpathText.concat(" and contains(@class,'highcharts-point')]")));
						String chartFillColor = charts.get(chartIndex).getAttribute("fill");
						int actchartHeight = Integer.parseInt(charts.get(chartIndex).getAttribute("height"));

						List<WebElement> chartDataLabels = chart.findElements(By.xpath(
								xpathText.concat(" and contains(@class,'highcharts-data-label')]//*[name()='text']")));
						String legendDataLabel = chartDataLabels.get(chartIndex).getText();
						legendDataLabel = legendDataLabel.substring(0, legendDataLabel.length() / 2).trim();

						if (chartFillColor.contains("rgb")) {
							chartFillColor = Utility.convertRgbToHex(chartFillColor);
						}

						float sp = Float.parseFloat(shareholdingPercentage);
						int expChartHeight = Math.round((sp * plotAreaHeight) / maxYaxis);
						// Need to discount a height of 1 because data label are rounded and actual
						// chart is not rounded.
						
						Assert.assertTrue("Height of the chart is not correct", (expChartHeight == actchartHeight
								|| (expChartHeight - 1) == actchartHeight || expChartHeight == (actchartHeight - 1)));

						Assert.assertEquals(expectedFillColour, chartFillColor);
						Assert.assertEquals("Shareholding Percentage is not matching", shareholdingPercentage,
								legendDataLabel);

					}

				}

			}
		}
	}

	public class MoneyControlStockPricePageMap {
		WebDriver _driver;

		@FindBy(how = How.XPATH, using = "//div[@id='Promoter']//*[local-name()='svg']")
		public WebElement promotersHoldingChart;

		@FindBy(how = How.XPATH, using = "//div[@id='summary_pie_chart']//*[name()='svg']")
		public WebElement summaryHoldingChart;

		@FindBy(how = How.XPATH, using = "//h2[normalize-space()='Shareholding']")
		public WebElement shareHoldingHeading;

		public MoneyControlStockPricePageMap(WebDriver driver) {
			this._driver = driver;
			PageFactory.initElements(driver, this);
		}
	}

}
