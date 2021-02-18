package com.bluetail.aero.automation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * @description This class consists methods for all the actions.
 * @createdAt 02/17/2020
 * @modifiedAt
 * @Modifiedby
 * @author Ashok.
 */
public class WebUIAutomation extends TestBase {

	public static boolean isElementPresent(String locatorKey) {

		try {
			WebElement webElem = getObject(locatorKey);
			if (!(webElem == null)) {

				return true;
			} else {

				return false;
			}

		} catch (Exception e) {

			return false;
		}
	}

	public static boolean clickObj(String locatorKey) {

		try {
			if (isElementPresent(locatorKey)) {

				getObject(locatorKey).click();
				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			return false;
		}
	}

	public static boolean setText(String locatorKey, String value) {

		try {

			if (isElementPresent(locatorKey)) {

				getObject(locatorKey).clear();
				getObject(locatorKey).sendKeys(value);

				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			return false;
		}
	}

	public static boolean isObjPresent(String locatorKey, int maxTime) {

		try {
			for (int i = 0; i <= maxTime; i++) {

				Thread.sleep(1000);
				if (isElementPresent(locatorKey)) {

					return true;
				}
			}

		} catch (Exception e) {

			return false;
		}

		return false;

	}

	public static boolean selectValueFromDropDown(String locatorKey, String value) {

		try {
			Select element;
			if (!value.equals("")) {

				element = new Select(getObject(locatorKey));
				element.selectByValue(value);
			}

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public static Object[][] getData(String sheetName) {

		if (!(datatable.isSheetExist(sheetName))) {

			datatable = null;
			return new Object[1][0];

		}

		int rows = datatable.getRowCount(sheetName) - 1;
		if (rows <= 0) {

			Object[][] testData = new Object[1][0];
			return testData;
		}

		rows = datatable.getRowCount(sheetName);
		int cols = datatable.getColumnCount(sheetName);

		Object data[][] = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {

			for (int colNum = 0; colNum < cols; colNum++) {

				data[rowNum - 2][colNum] = datatable.getCellData(sheetName, colNum, rowNum);
			}

		}

		return data;
	}

	public static boolean checkTestCaseRunmode(String testcase) {

		for (int i = 2; i <= datatable.getRowCount("Test-Cases"); i++) {

			if (datatable.getCellData("Test-Cases", "Test-Case-Name", i).equalsIgnoreCase(testcase)) {

				if (datatable.getCellData("Test-Cases", "Runmode", i).equalsIgnoreCase("Y")) {

					return true;

				} else {

					return false;
				}

			}
		}

		return false;
	}

	public static void takeScreenshot(String fileName) throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\screenshots\\" + fileName
				+ CommonFunctions.getCurrentDateTimeStamp() + ".png"));
	}

	public static void scrollDownOnScreen() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,5250)", "");

	}
	
	public static void scrollDownOnScreen(int pixel) {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + pixel +")", "");

	}

	public static void scrollUpOnScreen() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
	}

	public static void scrollDownToBottom() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");

	}

	public static boolean moveToElement(String locatorKey) {

		try {

			WebElement webElem = getObject(locatorKey);

			if (!(webElem == null)) {
				Actions actions = new Actions(driver);
				actions.moveToElement(webElem).perform();
				actions.release(webElem);
				return true;

			} else {

				return false;
			}

		} catch (Exception e) {

			return false;
		}
	}

	public static void popUpHandler() {
		// Switching to Alert
		Alert alert = driver.switchTo().alert();
		alert.getText();
		alert.accept();
		System.err.println("on popup");
	}

	// Switch to new window opened
	public static void SwitchWindows() {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

}
