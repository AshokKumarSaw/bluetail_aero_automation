package com.bluetail.aero.automation;

import org.testng.Assert;

/**
 * @description This class consist of common operations used through out the application
 * @createdAt 02/17/2020
 * @modifiedAt
 * @Modifiedby
 * @author Ashok.
 */

public class BusinessFunctions extends TestBase {

	public static boolean doLogin() {

		if (loggedIn == false) {

			try {

				// Enter email in the username field
				Assert.assertTrue(WebUIAutomation.setText("INPUT_Email_SignInPAGE", config.getProperty("Email")),
						"Unable to enter text on [Email] field.");

				// Enter password in the password field
				Assert.assertTrue(WebUIAutomation.setText("INPUT_Password_SignInPAGE", config.getProperty("Password")),
						"Unable to enter text on [password] field.");

				// Click on Login button
				Assert.assertTrue(WebUIAutomation.clickObj("BTN_SignIn_SignInPage"),
						"Clicking on the [SignIn] button isn't successful.");

				loggedIn = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return loggedIn;
	}

	public static boolean doLogout() {
		
		if (loggedIn == true) {

			// Click on Profile and Check Sign Out button is displayed or not
			Assert.assertTrue(WebUIAutomation.isObjPresent("DRPDWN_SignOut_Header", 30), "Profile button isn't present");

			// click on logout button
			Assert.assertTrue(WebUIAutomation.clickObj("DRPDWN_SignOut_Header"), "");

			Assert.assertTrue(WebUIAutomation.isObjPresent("BTN_SignOut_Profile", 30),
					"Sign out button isn't present");

			// click on logout button
			Assert.assertTrue(WebUIAutomation.clickObj("BTN_SignOut_Profile"), "");

			loggedIn = false;

			// Verify user has logged out or not
			Assert.assertTrue(WebUIAutomation.isObjPresent("BTN_SignIn_SignInPage", 30),
					"[Login] button isn't displayed.");
			

		}
		
		return loggedIn ;
	}

}
