import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.WeatherPage;
import util.FileIOOperations;
import util.GenericActions;

public class TestClass extends BaseTest {
    GenericActions genericActions;
    HomePage homePage;
    WeatherPage weatherPage;
    WebDriver driver;
    FileIOOperations fileIOOperations = new FileIOOperations();

    @Test
    public void test() {
        driver = BaseTest.getDriver();
        homePage = new HomePage(driver);
        weatherPage = new WeatherPage(driver);
        genericActions = new GenericActions(driver);


        driver.get(fileIOOperations.readPropertyFromConfig("webAppUrl"));

        homePage.clickOnSubMenu();
        homePage.clickOnWeather();

        String city = "Amritsar";

        weatherPage.enterSearchLocation(city);
        weatherPage.selectCityFromDropDown(city);
        weatherPage.selectCityPinOnMap(city);

    }

}
