package extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utilities.Log;

public class GraphUtils {

	// Method to get class name of the color used in the money control graphs for a
	// given legend
	public String getLegendColourClassName(WebElement graph, String legendName) {

		String legendColourClassName = "";

		String xPathText = String.format(".//*[@class='highcharts-legend']//*[text()='%s']", legendName);
		WebElement legendTagTspanElement = graph.findElement(By.xpath(xPathText));
		WebElement legendTagGElement = legendTagTspanElement.findElement(By.xpath("./../.."));
		String classnames = legendTagGElement.getAttribute("class");

		String pattern = "highcharts-color-\\d+";
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(classnames);
		if (m.find()) {
			legendColourClassName = m.group(0).replaceAll("highcharts-", "");
			Log.info(String.format("Color class name for legend %s is %s", legendName, legendColourClassName));
		} else {
			Log.error(String.format("Couldn't find the color class name for legend %s", legendName));
		}
		return legendColourClassName;
	}

	// Method to get fill color used in the money control graphs for a given legend
	public String getLegendFillColour(WebElement graph, String legendName) {

		String legendFillColour = "";

		String xPathText = String.format(".//*[@class='highcharts-legend']//*[text()='%s']", legendName);
		WebElement legendTagTspanElement = graph.findElement(By.xpath(xPathText));
		WebElement legendTagRectElement = legendTagTspanElement
				.findElement(By.xpath("./../following-sibling::*[name()='rect']"));

		if (legendTagRectElement != null) {
			legendFillColour = legendTagRectElement.getAttribute("fill");
			Log.info(String.format("Fill colour for legend %s is %s", legendName, legendFillColour));
		} else {
			Log.error(String.format("Couldn't find the fill colour for legend %s", legendName));
		}
		return legendFillColour;
	}

	// Method to get class names of the color used in the money control graphs for a
	// given graph
	public Map<String, String> getAllLegendColourClassName(WebElement graph) {

		Map<String, String> ledgerClass = new HashMap<>();

		String xPathText = ".//*[contains(@class,'highcharts-legend-item')]/*[name()='text']";
		List<WebElement> legendNames = graph.findElements(By.xpath(xPathText));
		for (WebElement legendName : legendNames) {
			String legendNameText = legendName.getText().trim();
			String legendColourClassName = getLegendColourClassName(graph, legendNameText);
			ledgerClass.put(legendNameText, legendColourClassName);
			Log.info(String.format("Color class name for legend %s is %s", legendNameText, legendColourClassName));
		}
		return ledgerClass;
	}

	// Method to get all fill color used in the money control graphs legend for a
	// given graph
	public Map<String, String> getAllLegendFillColour(WebElement graph) {

		Map<String, String> ledgerFillColor = new HashMap<>();

		String xPathText = ".//*[contains(@class,'highcharts-legend-item')]/*[name()='text']";
		List<WebElement> legendNames = graph.findElements(By.xpath(xPathText));
		for (WebElement legendName : legendNames) {
			String legendNameText = legendName.getText().trim();
			String legendFillColour = getLegendFillColour(graph, legendNameText);
			ledgerFillColor.put(legendNameText, legendFillColour);
			Log.info(String.format("Fill colour for legend %s is %s", legendNameText, legendFillColour));
		}

		return ledgerFillColor;
	}

	// Method to get X axis labels for a given graph
	public List<String> getXAxisLabels(WebElement graph) {

		List<String> xAxisLabels = new ArrayList<String>();
		String xPathText = ".//*[contains(@class,'highcharts-xaxis-labels')]//*[name()='text']";
		List<WebElement> xAxisLabelsElements = graph.findElements(By.xpath(xPathText));
		for (WebElement xAxisLabelElement : xAxisLabelsElements) {
			String label = xAxisLabelElement.getText();
			xAxisLabels.add(label);
			Log.info(String.format("Label %s is identified", label));
		}

		return xAxisLabels;
	}
	
	// Method to get X axis labels for a given graph
		public Map<String, String> getXAxisLabelsAndIndex(WebElement graph) {

			Map<String, String> xAxisLabels = new HashMap<>();
			String xPathText = ".//*[contains(@class,'highcharts-xaxis-labels')]//*[name()='text']";
			List<WebElement> xAxisLabelsElements = graph.findElements(By.xpath(xPathText));
			int i = 0;
			for (WebElement xAxisLabelElement : xAxisLabelsElements) {
				String label = xAxisLabelElement.getText();
				xAxisLabels.put(label,String.valueOf(i));
				i++;
				Log.info(String.format("Label %s is identified", label));
			}

			return xAxisLabels;
		}

	// Method to get Y axis labels for a given graph
	public List<String> getYAxisLabels(WebElement graph) {

		List<String> yAxisLabels = new ArrayList<String>();
		String xPathText = ".//*[contains(@class,'highcharts-yaxis-labels')]//*[name()='text']";
		List<WebElement> yAxisLabelsElements = graph.findElements(By.xpath(xPathText));
		for (WebElement yAxisLabelElement : yAxisLabelsElements) {
			String label = yAxisLabelElement.getText();
			yAxisLabels.add(label);
			Log.info(String.format("Label %s is identified", label));
		}

		return yAxisLabels;
	}

	// Method to get last Y axis labels for a given graph
	public String getLastYAxisLabel(WebElement graph) {

		String lastYAxisLabel = "";
		String xPathText = ".//*[contains(@class,'highcharts-yaxis-labels')]//*[name()='text']";
		List<WebElement> yAxisLabelsElements = graph.findElements(By.xpath(xPathText));

		lastYAxisLabel = yAxisLabelsElements.get(yAxisLabelsElements.size() - 1).getText().trim();
		Log.info(String.format("Last Y Axis label is %s", lastYAxisLabel));

		return lastYAxisLabel;
	}

	// Method to get the plot area height for a given graph
	public int getPlotAreaHeight(WebElement graph) {

		int plotareaHeight = 0;

		String xPathText = ".//*[contains(@class,'highcharts-plot-background')]";
		WebElement plotarea = graph.findElement(By.xpath(xPathText));
		plotareaHeight = Integer.parseInt(plotarea.getAttribute("height"));

		Log.info(String.format("Plot Area Height is %s", plotareaHeight));

		return plotareaHeight;
	}
}
