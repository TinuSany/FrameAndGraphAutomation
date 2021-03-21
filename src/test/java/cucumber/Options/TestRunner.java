package cucumber.Options;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/java/features"},
plugin="json:target/jsonReports/jsonReport.json", 
glue={"stepDefinitions","hooks"}

//use the tag parameter for restricting the test to a specific tag. ~ can be used for except one tag
//,tags="@SeleniumTest"
)
public class TestRunner {

}
