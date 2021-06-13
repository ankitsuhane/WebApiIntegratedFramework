package com.orgname.test.stepdefinitions;



/**
 * GlobalConstants Class is created to define all the constants 
 * @author msp6962
 *
 */
public class GlobalConstants {
	
/*	private GlobalConstants (){
		
	}*/
	
	//Global Variable primary for the Custom Driver Class
	public static final String GTESTRESOURCEPATH="\\src\\test\\resources";
	public static final String GMAINRESOURCEPATH="\\src\\main\\resources";
	public static final long GIMPLICITAPPTIMEOUT=50L;
	public static final long GPAGELOADTIMEOUT=20L; 
	public static final String GHOMEPATH=System.getProperty("user.dir");  //GHOMEPATH is a variable to set the project base location
	public static final String GCHROMEDRIVERPROP="webdriver.chrome.driver";
	public static final String GFIREFOXDRIVERPROP="webdriver.gecko.driver";
	public static final String GIEDRIVERPROP="webdriver.ie.driver";
	public static final String GWINIUMDRIVEREXE="\\Winium.Desktop.Driver.exe";
	public static final String GWINIUMDRIVERURL="http://localhost:9999";
	public static final String GFPFAPPLICATIONPATH="\\\\kwdstr08\\grp\\GRP1\\ISS\\FPF\\Software Configuration\\AppBuilder Utilities\\Environment Select Utility\\Environment Select Utility.exe";
	
	//Global Variable primary for the Test Data
	public static final String GTESTDATAPATH="\\src\\test\\resources\\TestData\\";
	public static final String GTESTDATAFILE= "Testdata";
	public static final String GFILEEXT = ".xlsx";
	public static final String GEXECUTEDSELENIUM= "Executed_Selenium";
	public static final String GTESTSCENARIOFILE = "Test_Scenario.xlsm";
	public static final String GSCENARIONAMEKEY= "ScenarioName";
	public static final String GDEPENDENTSCENARIONAMEKEY= "DependentScenarioName";
	public static final String GSCENARIONAMEPASSED= "PASSED";
	public static final String GSCENARIONAMEFAILED= "FAILED";

	//Config folder location where you want to store all the text or CSV files and read data.
	//gFilePath=
	
	//Excel sheet for the Assertions 
	public static final String GASSERTIONFILEPATH= "\\src\\test\\resources\\Assertions";
	public static final String GASSERTIONFILENAME= "Assertions.xlsx";
	
	//Result folder location where you want to store all the your screenshots and Result file.
	public static final String GEXTENTREPORT= GHOMEPATH+"\\Results\\ExtentReport";
	public static final String GSCREENSHOTLOC= GHOMEPATH+"\\Results\\Screenshots";
	public static final String GRESULTSLOC= GHOMEPATH+"\\Results";

	//For Replacing the time in waitForElement
	public static final long GFLUNETWAITTIMEOUTSEC= 120L;
	public static final long GFLUNETWAITPOOLINGTIMEOUTMILI= 250L;

	//For Replacing the String in Grid
	public static final String GREPLACEALLTOMAKEDOUBLE= "[^0-9|.|-]";
	
	//For Timelng class the format that you want to return for your operation
	public static final String GDATETIMEFORMAT1= "dd_MMM_yyyy";
	public static final String GDATETIMEFORMAT= "dd-M-yyyy hh:mm:ss";
	public static final String GDATEFORMAT= "dd/MM/yyyy";
	public static final String GDATETIMEFORMAT2= "dd_MMM_yyyy_hh_mm_ss";

}
