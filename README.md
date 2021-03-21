# FrameAndGraphAutomation
---------------------------
Overview
--------
iFrame and Graph Automation is developed using Apache Maven 3.6.3 and Java (TM) SE Runtime Environment (build 1.8.0_281-b09). Please use respective versions or above when you run the test.
Google Chrome (version 89) and Microsoft Edge (version 89) browsers are handled as of now in the framework.
Tools/Packages Used

Below are the tools/packages used in the framework
-------------------------------------------------
1.	Selenium
2.	Rest Assured: API Testing
3.	Junit: Unit testing framework for assertions along with rest assured assertions
4.	Cucumber: test designed in BDD framework
5.	Jackson databind: for Json deserialization
6.	Apache common-csv: for reading CSV files
7.	Apache poi: reading/writing xlsx files
8.	log4j

Test Report
-----------
‘cucumber-reporting’ is used for reporting and run ‘mvn verify’ so cucumber reports will be generated in target/cucumber-html-reports. Please refer the below link for more details on ‘cucumber-reporting’.
https://github.com/damianszczepanik/cucumber-reporting

How to run
----------
Navigate to the Automation folder of the cloned library in the command prompt before execute the below maven commands.
•	Maven command to run the entire automation suite and default chrome browser will be used.
mvn test verify

•	Maven command to run the entire automation suite in edge browser.
mvn test verify -DbrowserName="edge"

•	Maven command to run the selenium test alone
mvn test verify -Dcucumber.filter.tags="@SeleniumTest"
