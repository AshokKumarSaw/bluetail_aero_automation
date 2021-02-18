package com.bluetail.aero.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeSuite;

/**
 * @description This class takes care of the complete initialization process,
 *              which initializes and launch the browser.
 * @createdAt 02/17/2020
 * @modifiedAt
 * @Modifiedby
 * @author Ashok.
 */

public class TestBase {

	public static Properties config = null;
	public static Properties OR = null;
	public static boolean loggedIn = false;
	public WebDriver wbDv = null;
	public static EventFiringWebDriver driver = null;
	public static MSExcelAutomation datatable = null;
	protected DesiredCapabilities capabilities = new DesiredCapabilities();
	String osName = null;
	public static String userInValidEmail = "";
	public static String userValidEmail = "";

	@BeforeSuite
	public void initialize() {

		try {
			osName = System.getProperty("os.name");
			if (osName == null) {
				throw new IOException("os.name not found");
			}
			osName = osName.toLowerCase(Locale.ENGLISH);

			if (osName.contains("mac os")) {
				config = new Properties();
				FileInputStream fp = new FileInputStream(
						System.getProperty("user.dir") + "/src/main/java/resources/config.properties");
				config.load(fp);

				OR = new Properties();
				FileInputStream fo = new FileInputStream(
						System.getProperty("user.dir") + "/src/main/java/resources/ObjectRepo.properties");
				OR.load(fo);

				datatable = new MSExcelAutomation(
						System.getProperty("user.dir") + "/src/main/java/resources/controller.xlsx");

			}

			if (osName.contains("windows")) {
				config = new Properties();
				FileInputStream fp = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\main\\java\\resources\\config.properties");
				config.load(fp);

				OR = new Properties();
				FileInputStream fo = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\main\\java\\resources\\ObjectRepo.properties");
				OR.load(fo);

				datatable = new MSExcelAutomation(
						System.getProperty("user.dir") + "\\src\\main\\java\\resources\\controller.xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void launchBrowser() {
		try {
			osName = System.getProperty("os.name");
			if (osName == null) {
				throw new IOException("os.name not found");
			}
			osName = osName.toLowerCase(Locale.ENGLISH);

			if (osName.contains("windows")) {
				if (config.getProperty("browserType").equalsIgnoreCase("chrome")) {

					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "\\src\\main\\java\\resources\\chromedriver.exe");
					wbDv = new ChromeDriver();

				} else if (config.getProperty("browserType").equalsIgnoreCase("Firefox")) {

			          System.setProperty("webdriver.gecko.driver",
			              System.getProperty("user.dir") + "\\src\\main\\java\\resources\\geckodriver_v0.24.0.exe");
			          wbDv = new FirefoxDriver();

			          /*
			           * capabilities.setCapability(InternetExplorerDriver.
			           * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			           */

			        }

			} else if (osName.contains("mac os")) {
				if (config.getProperty("browserType").equalsIgnoreCase("chrome")) {

					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "/src/main/java/resources/chromedriver");
					wbDv = new ChromeDriver();

				} else if (config.getProperty("browserType").equalsIgnoreCase("Firefox")) {

			          System.setProperty("webdriver.gecko.driver",
			              System.getProperty("user.dir") + "\\src\\main\\java\\resources\\geckodriver_v0.24.0.exe");
			          wbDv = new FirefoxDriver();

			          /*
			           * capabilities.setCapability(InternetExplorerDriver.
			           * INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			           */

			        }

			}

			driver = new EventFiringWebDriver(wbDv);

// putting an implicit wait after every Action or Event
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			// opening the browser
			driver.navigate().to(config.getProperty("testSiteURL"));

			// maximizing the windows
			driver.manage().window().maximize();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static WebElement getObject(String locatorKey) {

		WebElement obj = null;

		try {
			String webLocator = OR.getProperty(locatorKey);
			String locatorType = webLocator.split(":-")[0];
			String locatorValue = webLocator.split(":-")[1];

			switch (locatorType.toLowerCase().trim()) {

			case "id":

				obj = driver.findElement(By.id(locatorValue));
				break;

			case "name":

				obj = driver.findElement(By.name(locatorValue));
				break;

			case "xpath":

				obj = driver.findElement(By.xpath(locatorValue));
				break;

			case "classname":
			case "class":

				obj = driver.findElement(By.className(locatorValue));
				break;

			case "linktext":
			case "link":

				obj = driver.findElement(By.linkText(locatorValue));
				break;

			case "partialLinkText":

				obj = driver.findElement(By.partialLinkText(locatorValue));
				break;

			case "tagname":
			case "tag":

				obj = driver.findElement(By.tagName(locatorValue));
				break;

			case "cssSelector":
			case "css":

				obj = driver.findElement(By.cssSelector(locatorValue));
				break;

			default:
				throw new Exception("Unknown locator type '" + locatorType + "'");

			}

		} catch (Exception e) {

			e.printStackTrace();
			obj = null;
		}

		return obj;
	}

	public static void closeBrowser() {

		driver.quit();
	}

	public static boolean checkRunmode(String testName) {

		String a = config.getProperty(testName);

		return (a.equalsIgnoreCase("N"));

	}

}