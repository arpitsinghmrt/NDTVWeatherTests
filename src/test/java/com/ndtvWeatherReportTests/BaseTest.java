package com.ndtvWeatherReportTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import util.FileIOOperations;

import java.util.concurrent.TimeUnit;


public class BaseTest {

    private static WebDriver driver;
    FileIOOperations fileIOOperations = new FileIOOperations();

    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeClass
    public void testSetup() {
        fileIOOperations.deleteScreenshotFolder();

        if (driver == null) {
            if (fileIOOperations.readPropertyFromConfig("webDriver").equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", fileIOOperations.readPropertyFromConfig("chromeDriverPath"));
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
            }
            if (fileIOOperations.readPropertyFromConfig("webDriver").equals("firefox")) {
                System.setProperty("webdriver.gecko.driver", fileIOOperations.readPropertyFromConfig("geckoDriverPath"));
                final FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                firefoxOptions.addArguments("--maximized");
                firefoxOptions.addArguments("--disable-notifications");
                driver = new FirefoxDriver(firefoxOptions);
            }
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
