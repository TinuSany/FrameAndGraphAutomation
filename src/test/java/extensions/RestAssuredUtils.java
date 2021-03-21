package extensions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.hamcrest.Matchers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.Utility;

public class RestAssuredUtils {
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	public static ResponseSpecification invalidResSpec;

	// Creating a RequestSpecification for both valid and invalid requests
	public RequestSpecification requestSpecification() throws IOException {
		if (reqSpec == null) {
			String pathString = Utility.getCurrentPath();
			PrintStream log = new PrintStream(
					new FileOutputStream(pathString.concat("\\target\\_log\\RestAssuredLogging.txt"), true));
			reqSpec = new RequestSpecBuilder().setBaseUri(Utility.getPropertyValue("api", "BASE_URL"))
					.setBasePath(Utility.getPropertyValue("api", "BASE_PATH"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return reqSpec;
		}
		return reqSpec;
	}

	// Creating a ResponseSpecification for valid responses
	public ResponseSpecification responseSpecification() throws IOException {
		if (resSpec == null) {
			long MAX_RESPONSE_TIME = Long
					.parseLong(Utility.getPropertyValue("api", "MAX_RESPONSE_TIME_IN_MILLISECONDS"));
			resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
					.expectResponseTime(Matchers.lessThan(MAX_RESPONSE_TIME)).build();
			return resSpec;
		}
		return resSpec;
	}

	// Creating a ResponseSpecification for invalid responses
	public ResponseSpecification invalidResponseSpecification() throws IOException {
		if (invalidResSpec == null) {
			long MAX_RESPONSE_TIME = Long
					.parseLong(Utility.getPropertyValue("api", "MAX_RESPONSE_TIME_IN_MILLISECONDS"));
			invalidResSpec = new ResponseSpecBuilder().expectStatusCode(400).expectContentType(ContentType.HTML)
					.expectResponseTime(Matchers.lessThan(MAX_RESPONSE_TIME)).build();
			return invalidResSpec;
		}
		return invalidResSpec;
	}

	// Get a Json Key value for a given key from responses
	public String getJsonKeyValue(Response response, String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}

}
