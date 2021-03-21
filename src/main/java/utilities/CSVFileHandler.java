package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class CSVFileHandler {

	//header names from CSV file	
	static String[] HEADERS = { "driverId", "driverRef", "number", "code", "forename", "surname", "dob", "nationality","url"};
		
	/********************************************************************************************************************
	 * Method getDriverDetailsByRef() is used to retrieve driver details from the CSV file based on the driverRef.
	 * It takes fileName and driverRef as arguments and return a CSVRecord. CSV file should be present in the 
	 * src\test\_data folder.
	 *******************************************************************************************************************/
		public static CSVRecord getDriverDetailsByRef(String fileName, String driverRef) throws IOException {
			CSVRecord result = null;
			
			String fileLocation = Utility.getCurrentPath().concat(String.format("src\\test\\_data\\%s",fileName));
		    Reader in = new FileReader(fileLocation);
		    Iterable<CSVRecord> records = CSVFormat.DEFAULT
		      .withHeader(HEADERS)
		      .withFirstRecordAsHeader()
		      .parse(in);
		    for (CSVRecord record : records) {
		        if (record.get("driverRef").equals(driverRef))
		        {
		        	return record;
		        }
		    }
		    return result;
		}
		
		/********************************************************************************************************************
		 * Method getAllDriverDetails() is used to retrieve all driver details from the CSV file. 
		 * It takes fileName as an argument and return a Iterable<CSVRecord>. CSV file should be present in the 
		 * src\test\_data folder.
		 *******************************************************************************************************************/	
		public static Iterable<CSVRecord> getAllDriverDetails(String fileName) throws IOException {
			String fileLocation = Utility.getCurrentPath().concat(String.format("src\\test\\_data\\%s",fileName));
		    Reader in = new FileReader(fileLocation);
		    Iterable<CSVRecord> records = CSVFormat.DEFAULT
		      .withHeader(HEADERS)
		      .withFirstRecordAsHeader()
		      .parse(in);
		    return records;
		}

}
