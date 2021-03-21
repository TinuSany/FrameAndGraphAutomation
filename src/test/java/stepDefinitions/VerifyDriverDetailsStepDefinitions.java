package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;

import extensions.RestAssuredUtils;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import pojo.Driver;
import pojo.Drivers;
import utilities.CSVFileHandler;
import utilities.ScenarioContext;

public class VerifyDriverDetailsStepDefinitions extends RestAssuredUtils {
	
	@Then("^I compare the details against \"([^\"]*)\" file$")
    public void i_compare_the_details_against_something_file(String fileName) throws IOException {
		
		//Retrieving saved response from ScenarioContext
		Response response = (Response) ScenarioContext.getContext("Response");
		
		//Retrieving list of drivers from response
		Drivers drivers = response.jsonPath().getObject("MRData.DriverTable", Drivers.class);
		List<Driver> driversListFromAPI = drivers.getDrivers();
	
		//Retrieving list of drivers from CSV
		List<Driver> driversListFromCSV = new ArrayList<Driver> ();
		Iterable<CSVRecord> csvRecords = CSVFileHandler.getAllDriverDetails(fileName);
		
		//Converting Iterable<CSVRecord> to List<Driver>
		for(CSVRecord csvRecord : csvRecords)
		{ 
			Driver d = new Driver();
			
			String permanentNumber= csvRecord.get("number").trim();
			String code= csvRecord.get("code").trim();

			d.setDriverId(csvRecord.get("driverRef").trim());
			d.setUrl(csvRecord.get("url").trim());
			d.setGivenName(csvRecord.get("forename").trim());
			d.setFamilyName(csvRecord.get("surname").trim());
			d.setDateOfBirth(csvRecord.get("dob").trim());
			d.setNationality(csvRecord.get("nationality").trim());
			
			//special processing for permanentNumber and code to set null as API deserialization returns null when it is blank
			if (permanentNumber.equals("\\N") ||  permanentNumber.trim() == "")
				d.setPermanentNumber(null);
			else
				d.setPermanentNumber(permanentNumber);
			if (code.equals("\\N") ||  code.trim() == "")
				d.setCode(null);
			else
				d.setCode(code);
			
			driversListFromCSV.add(d);
		}
		
		//Difference between driversListFromCSV and driversListFromAPI and expected 0 difference
		List<Driver> difference = driversListFromCSV.stream()
	            .filter(element -> !driversListFromAPI.contains(element))
	            .collect(Collectors.toList());
		Assert.assertEquals("Differences between API List and CSV List should be 0.", 0,difference.size());
		
		//Difference between driversListFromAPI and driversListFromCSV and expected 0 difference
		difference = driversListFromAPI.stream()
	            .filter(element -> !driversListFromCSV.contains(element))
	            .collect(Collectors.toList());
		Assert.assertEquals("Differences between API List and CSV List should be 0.", 0,difference.size());
		
    }
	
	@Then("I verify the record total is {int}")
	public void i_verify_the_record_total_is(Integer total) {
		
		//Assert total against the value passed from step
		Response response = (Response) ScenarioContext.getContext("Response");
		Assert.assertTrue("Record total should match",Integer.parseInt(getJsonKeyValue(response, "MRData.total")) == total);

	}

}
