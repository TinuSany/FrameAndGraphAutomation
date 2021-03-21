package extensions;

import java.util.Map;

import utilities.ScenarioContext;

public class PathAndQueryParamHandler {

	// This is the main driver used for creating dynamic path and query parameters based on the data provided on the datatable
	public static String GetPathAndQueryParamPath(Map<String, String> row, String pathParamText) throws Exception {
		String typeOfInformation = "";
		String tableName = "";

		// include season path parameter if it is not empty
		String season = row.get("season");
		if (season != null && season.length() > 0) {
			pathParamText = pathParamText.concat("/" + season);
		}

		// include round path parameter if it is not empty
		String round = row.get("round");
		if (round != null && round.length() > 0) {
			pathParamText = pathParamText.concat("/" + round);
		}

		// iterate through different parameters
		for (String keyName : row.keySet()) {
			String keyValue = row.get(keyName);

			// Identifying typeOfInformation key as it needs special processing
			if (keyName.equalsIgnoreCase("typeOfInformation")) {
				switch (keyValue.trim()) {
				case "Season List":
					typeOfInformation = "seasons";
					tableName = "SeasonTable";
					break;
				case "Race Schedule":
					typeOfInformation = "races";
					tableName = "RaceTable";
					break;
				case "Race Results":
					typeOfInformation = "results";
					tableName = "RaceTable";
					break;
				case "Qualifying Results":
					typeOfInformation = "qualifying";
					tableName = "RaceTable";
					break;
				case "Constructor Standings":
					typeOfInformation = "constructorStandings";
					break;
				case "Driver Standings":
					typeOfInformation = "driverStandings";
					break;
				case "Driver Information":
					tableName = "DriverTable";
					typeOfInformation = "drivers";
					break;
				case "Constructor Information":
					typeOfInformation = "constructors";
					tableName = "ConstructorTable";
					break;
				case "Circuit Information":
					typeOfInformation = "circuits";
					tableName = "CircuitTable";
					break;
				case "Finishing Status":
					typeOfInformation = "status";
					tableName = "StatusTable";
					break;
				case "Lap Times":
					typeOfInformation = "laps";
					tableName = "RaceTable";
					break;
				case "Pit Stops":
					typeOfInformation = "pitstops";
					tableName = "RaceTable";
					break;
				}
			}

			else if (keyValue != null && keyValue.trim() != "") {

				if (!keyName.trim().equalsIgnoreCase(typeOfInformation)) {
					switch (keyName.trim()) {
					case "circuits":
					case "constructors":
					case "drivers":
					case "grid":
					case "results":
					case "fastest":
					case "status":
						// standings
					case "constructorStandings":
					case "driverStandings":
						pathParamText = pathParamText.concat("/" + keyName);
						pathParamText = pathParamText.concat("/" + keyValue);
						break;
					// these cases are handled seperately
					case "typeOfInformation":
					case "typeFilter":
					case "season":
					case "round":
					case "limit":
					case "offset":

						break;

					default:
						throw new Exception("Invalid Table column name : " + keyName);
					}
				}

			}

		}
		// for future validation
		ScenarioContext.setContext("ResultTableName", tableName);

		// handling typeOfInformation key. If there is a filter it will be handled in
		// the if block, if not else block
		String keyValue = row.get("typeFilter");
		if (keyValue != null && keyValue.trim() != "") {
			pathParamText = pathParamText.concat("/" + typeOfInformation);
			pathParamText = pathParamText.concat("/" + keyValue.trim() + ".json");
		} else
			pathParamText = pathParamText.concat("/" + typeOfInformation + ".json");

		// handling limit query parameter
		keyValue = row.get("limit");
		if (keyValue != null && keyValue.trim() != "") {
			pathParamText = pathParamText.concat("?" + "limit");
			pathParamText = pathParamText.concat("=" + keyValue.trim());
			ScenarioContext.setContext("limit", keyValue.trim());

		}
		// handling offset query parameter
		keyValue = row.get("offset");
		if (keyValue != null && keyValue.trim() != "") {
			// checking whether this is the first query parameter or not
			if (pathParamText.contains("?"))
				pathParamText = pathParamText.concat("&" + "offset");
			else
				pathParamText = pathParamText.concat("?" + "offset");
			pathParamText = pathParamText.concat("=" + keyValue.trim());
			ScenarioContext.setContext("offset", keyValue.trim());
		}
		return pathParamText;
	}

}
