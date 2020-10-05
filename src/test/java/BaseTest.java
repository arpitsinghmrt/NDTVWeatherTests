import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import util.GenericActions;
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

        if (fileIOOperations.readPropertyFromConfig("webDriver").equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", fileIOOperations.readPropertyFromConfig("chromeDriverPath"));
            final ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(false);
            driver = new ChromeDriver(chromeOptions);
        }
        if (fileIOOperations.readPropertyFromConfig("webDriver").equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", fileIOOperations.readPropertyFromConfig("geckoDriverPath"));
            final FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(false);
            driver = new FirefoxDriver(firefoxOptions);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
