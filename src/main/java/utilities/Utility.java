package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Properties;

public class Utility {
	// This method returns the current absolute folder path
	public static String getCurrentPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		// to remove '.' symbol.
		return path.substring(0, path.length() - 1);
	}

	// This method returns a property value from a given property file
	public static String getPropertyValue(String propFileName, String key) throws IOException {
		String pathString = getCurrentPath();
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				pathString.concat(String.format("\\src\\test\\java\\resources\\%s.properties", propFileName)));
		prop.load(fis);
		fis.close();
		return prop.getProperty(key);
	}

	// This method initialize the logger file. It replaces the existing file.
	public static void inItLogger() throws IOException {
		String pathString = Utility.getCurrentPath();
		PrintStream log = new PrintStream(
				new FileOutputStream(pathString.concat("\\target\\_log\\RestAssuredLogging.txt"), false));
		log.close();
	}

	// This method append a string to the logger.
	public static void AppendLogger(String logs) throws IOException {
		String pathString = Utility.getCurrentPath();
		PrintStream log = new PrintStream(
				new FileOutputStream(pathString.concat("\\target\\_log\\RestAssuredLogging.txt"), true));
		log.append(logs);
		log.close();
	}

	// This method append Scenario name and status to the logger at the end of the
	// Scenario execution.
	public static void AfterScenarioAppendLogger(String scenarioName, String status) throws IOException {
		String pathString = Utility.getCurrentPath();
		PrintStream log = new PrintStream(
				new FileOutputStream(pathString.concat("\\target\\_log\\RestAssuredLogging.txt"), true));
		String logText = String.format("\nINFO : Scenario '%S' finished with status as '%S' at %S", scenarioName,
				status, LocalDateTime.now().toString());
		log.append(
				"\n************************************************************************************************************************\n");
		log.append(logText);
		log.append(
				"\n************************************************************************************************************************\n");
		log.close();
	}

	// This method append Scenario name to the logger at the beginning of the
	// Scenario execution.
	public static void BeforeScenarioAppendLogger(String scenarioName) throws IOException {
		String pathString = Utility.getCurrentPath();
		PrintStream log = new PrintStream(
				new FileOutputStream(pathString.concat("\\target\\_log\\RestAssuredLogging.txt"), true));
		String logText = String.format("\nINFO : Scenario '%S' started at %S", scenarioName,
				LocalDateTime.now().toString());
		log.append(
				"\n************************************************************************************************************************\n");
		log.append(logText);
		log.append(
				"\n************************************************************************************************************************\n");
		log.close();
	}

	// This method converts an RGB colour code Hex.
	public static String convertRgbToHex(String rgb) {
		String hex = null;
		rgb = rgb.toLowerCase();
		if (rgb.contains("rgb")) {
			rgb = rgb.replace("rgb", "").replace(")", "").replace("(", "");
			String[] splitValues = rgb.split(",");
			int r = Integer.parseInt(splitValues[0].trim());
			int g = Integer.parseInt(splitValues[1].trim());
			int b = Integer.parseInt(splitValues[2].trim());
			hex = String.format("#%02X%02X%02X", r, g, b);
			Log.info("Sucessfully converted from RGB to HEX");
		}

		return hex;
	}

}
