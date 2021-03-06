package com.bluetail.aero.automation;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Launching_Bluetail_Aero_Site extends TestBase {
	public Launching_Bluetail_Aero_Site() {
		super();
	}

	@BeforeClass
	public void doSkip() {

		initialize();

	}

	/**
	 * @param testID
	 * @param url
	 * @throws InterruptedException
	 */

	@Test(dataProvider = "getTestData")
	public void SmokeTestCase(String testID, String url) throws InterruptedException {

		System.out.println("Starting Test:" + testID);
		if (testID != "") {
			launchBrowser();
			Thread.sleep(2000);

			// Open Page URL: https://refstagingapp.bluetail.aero/
			Reporter.log("Opening the Bluetail_Aero page URL : " + url);
			driver.navigate().to(url);
			Reporter.log(" The Bluetail_Aero page URL is opened : " + url);
			Thread.sleep(3000);
			Reporter.log(" Logging into the application : " + url);
			BusinessFunctions.doLogin();
			Thread.sleep(3000);
			WebUIAutomation.clickObj("ICON_Add_User");
			
			
			Reporter.log(" Logging out from application : " + url);
			WebUIAutomation.clickObj("BTN_Logout");
			Thread.sleep(3000);
			WebUIAutomation.popUpHandler();
			Thread.sleep(3000);
			Reporter.log(" Logged out from application : " + url);

		}

		else {
			throw new SkipException("Skipping / Ignoring - Script not Ready for Execution ");

		}
	}

	@AfterMethod
	public void preparingToShutDown() {
		try {

			closeBrowser();

		} catch (Exception e) {

		}

	}

	@DataProvider(name = "getTestData")
	public static Object[][] getData() {

		return WebUIAutomation.getData("bluetail");

	}
}
