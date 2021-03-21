package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;

import extensions.PathAndQueryParamHandler;
import extensions.RestAssuredUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.Driver;
import pojo.Drivers;
import utilities.ScenarioContext;
import utilities.Utility;
import utilities.XlsxFileHandler;

public class RecordAndValidateDriverDetailsStepDefinitions extends RestAssuredUtils {
	

	@Given("I have created a request playload Ergast Developer API")
	public void i_have_created_a_request_playload_ergast_developer_api(DataTable dataTable) throws Exception {

		//Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		
		//Assuming there is only one data record in the DataTable - one header record and one data record
		Map<String, String> row = rows.get(0);
		String pathParamText = "";
		
		//calling Path And Query Param builder
		pathParamText = PathAndQueryParamHandler.GetPathAndQueryParamPath(row, pathParamText);
		ScenarioContext.setContext("PathParamText", pathParamText);

	}

	@When("I submit the request")
	public void i_submit_the_request() {
		
		//Retrieving saved PathParamText from ScenarioContext
		String pathParamText = (String) ScenarioContext.getContext("PathParamText");
		Response response;
		//Submit request for a valid response case
		try {
			response = given().spec(requestSpecification()).when().get(pathParamText).then()
					.spec(responseSpecification()).extract().response();
			ScenarioContext.setContext("Response", response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@When("I submit an invalid request")
	public void i_submit_an_invalid_request() {
		//Retrieving saved PathParamText from ScenarioContext
		String pathParamText = (String) ScenarioContext.getContext("PathParamText");
		Response response;
		//Submit request for a invalid response case
		try {
			response = given().spec(requestSpecification()).when().get(pathParamText).then()
					.spec(invalidResponseSpecification()).extract().response();
			ScenarioContext.setContext("Response", response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("I validate the response status code is {int}")
	public void i_validate_the_response_status_code_is(int statusCode) throws IOException {
		
		//Retrieving saved Response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		Assert.assertEquals(response.getStatusCode(), statusCode);
		
		//if status code is 200 validate the message and base URL on the response
		if(statusCode == 200)
		{
			Assert.assertTrue("Status line should contain OK", response.getStatusLine().contains("OK"));
			Assert.assertTrue("Response body should contain base URI",
					getJsonKeyValue(response, "MRData.xmlns").startsWith(Utility.getPropertyValue("api", "BASE_URL")));
		}
		//if status code is not 200 validate the message contains Bad request
		else
		{
			Assert.assertTrue("Status line should contain OK", response.getStatusLine().contains("Bad Request"));
		}
		
	}

	@Then("I record the {string} returned in the response on {string} excel")
	public void i_record_the_returned_in_the_response_on_excel(String fieldName, String fileName) throws Exception {
		
		//Retrieving saved Response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		
		// Fetch driver details from response
		Drivers drivers = response.jsonPath().getObject("MRData.DriverTable", Drivers.class);
		
		//Create a new List<String> for all the driverId
		List<String> driversList = new ArrayList<String>();
		for (Driver dr : drivers.getDrivers()) {
			switch (fieldName) {
			case "driverId":
				driversList.add(dr.getDriverId());
				break;
			default:
				throw new Exception("Option not handled");
			}
		}
		//save driverId list to the xlsx file
		XlsxFileHandler.createFile(fileName, fieldName, fieldName, driversList);
	}

	@Then("I verify the given drivers nationality is correct")
	public void i_verify_the_given_drivers_nationality_is_correct(DataTable dataTable) {
		
		//Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		
		//Retrieving saved Response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		Drivers drivers = response.jsonPath().getObject("MRData.DriverTable", Drivers.class);
		String expDriverId = row.get("driverId");
		
		//filtering list based on a criteria using stream and lambada expression
		List<Driver> driversMatching = drivers.getDrivers().stream().filter(d -> d.getDriverId().equals(expDriverId))
				.collect(Collectors.toList());

		
		Assert.assertTrue("Only one record with specified Driver Id should be returned", driversMatching.size() == 1);
		
		//asserting nationality
		Assert.assertTrue("Only one record with specified Driver Id should be returned",
				driversMatching.get(0).getNationality().equalsIgnoreCase(row.get("nationality")));

	}

	@Then("I capture all the driverIds whose nationality is {string}")
	public void i_capture_all_the_driver_ids_whose_nationality_is_british(String nationality) throws IOException, InterruptedException {
		
		//Retrieving saved Response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		Drivers drivers = response.jsonPath().getObject("MRData.DriverTable", Drivers.class);
		
		//filtering list based on a criteria using stream and lambada expression
		List<Driver> driversMatching = drivers.getDrivers().stream().filter(d -> d.getNationality().equals(nationality))
				.collect(Collectors.toList());
		
		//Create a new List<String> for all the driverIds returned
		List<String> driversId = new ArrayList<String>();
		for(Driver dr : driversMatching)
		{
			driversId.add(dr.getDriverId());
		}
		
		//save driverId list to the xlsx file
		XlsxFileHandler.createFile("British Drivers", "driverId", "driverId", driversId);

	}

	@Then("I Capture the givenNames who are born between {int} to {int}")
	public void i_capture_the_given_names_who_are_born_between_to(Integer year1, Integer year2) throws IOException, InterruptedException {
		
		//Retrieving saved Response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		Drivers drivers = response.jsonPath().getObject("MRData.DriverTable", Drivers.class);
		
		//filtering list based on a criteria using stream and lambada expression
		List<Driver> driversMatching = drivers.getDrivers().stream().
				filter(d -> Integer.parseInt(d.getDateOfBirth().substring(0,4)) >= year1 && 
						Integer.parseInt(d.getDateOfBirth().substring(0,4)) <= year2)
				.collect(Collectors.toList());
		
		//Create a new List<String> for all the given names returned
		List<String> driversGivenName = new ArrayList<String>();
		for(Driver dr : driversMatching)
		{
			driversGivenName.add(dr.getGivenName());
		}
		
		//save driversGivenName list to the xlsx file
		XlsxFileHandler.createFile("DriversBorn_1920_1925", "givenName", "givenName", driversGivenName);

	}
	
	@Then("I verify the record total, limit, offset and table name")
	public void i_verify_the_record_total_limit_offset_and_table_name(DataTable dataTable) {
		
		//Retrieving saved Response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		
		//Converting DataTable object to a List<Map<String, String>>
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		Map<String, String> row = rows.get(0);
		
		//store total, limit and offset from response
		int actTotal = Integer.parseInt(getJsonKeyValue(response, "MRData.total"));
		int actLimit = Integer.parseInt(getJsonKeyValue(response, "MRData.limit"));
		int actOffset = Integer.parseInt(getJsonKeyValue(response, "MRData.offset"));
		
		//Assert Total based on ExpectedNonZeroRecords on the Datatable
		if (row.get("ExpectedNonZeroRecords") !=null && row.get("ExpectedNonZeroRecords").trim().equalsIgnoreCase("Y"))
			Assert.assertTrue("Total Records in the response should be greater than 0.", actTotal>0);
		else
			Assert.assertTrue("Total Records in the response should be equal to 0.", actTotal==0);

		//Assert Total based on limit on the Datatable
		if (row.get("limit") !=null && row.get("limit").trim().length() > 0 && row.get("limit").trim().matches("\\d+"))
		{
			int expLimit = Integer.parseInt(row.get("limit").trim());
			
			//if it is more than 1000, limit to 1000.
			if (expLimit > 1000)
				Assert.assertTrue("Limit should match with the input record", actLimit == 1000);
			else
				Assert.assertTrue("Limit should match with the input record", actLimit == expLimit);
		}
		
		//if limit is blank use the default 30 for comparison
		else if (row.get("limit") ==null || row.get("limit").trim().length() == 0)
			Assert.assertTrue("Limit in the response should be equal to 30.", actLimit==30);
		
		//if limit is non integer value, use 0 for comparison
		else
			Assert.assertTrue("Limit in the response should be equal to 0.", actLimit==0);
			
		//if offset value from DataTable is an integer value, use the value from DataTable for comparison
		if (row.get("offset") !=null && row.get("offset").trim().length() > 0 && row.get("offset").trim().matches("\\d+"))
			Assert.assertTrue("Offset should match with the input record", actOffset == Integer.parseInt(row.get("offset").trim()) );
		//if it is blank use the default 30 for comparison
		else
			//else use default value 0.
			Assert.assertTrue("Offset in the response should be equal to 0.", actOffset==0);
		
		String tableName = (String)ScenarioContext.getContext("ResultTableName");
		String resultTable = getJsonKeyValue(response, "MRData."+tableName);
		
		//Assert respective result table is displayed on the response or not for a valid request
		Assert.assertTrue("Result Table should not be empty",resultTable.length() > 0);

	}

}
