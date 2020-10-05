import com.google.gson.Gson;
import httpOperations.HttpHandler;
import okhttp3.Response;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.WeatherPage;
import pojos.WeatherInformation;
import util.FileIOOperations;

import java.util.HashMap;

public class TestClass extends BaseTest {
    WebDriver driver;
    FileIOOperations fileIOOperations = new FileIOOperations();

    private Logger logger = Logger.getLogger(TestClass.class);
    private static final String city = "Amritsar";
    HttpHandler httpHandler = new HttpHandler();
    private static final String apiKey = "7fe67bf08c80ded756e598d6f8fedaea";

    Gson gson = new Gson();


    @Test
    public void getWeatherInforomNDTV() {
        driver = BaseTest.getDriver();
        HomePage homePage = new HomePage(driver);
        WeatherPage weatherPage = new WeatherPage(driver);
        driver.get(fileIOOperations.readPropertyFromConfig("webAppUrl"));
        logger.info("NDTV Home Page opened");
        homePage.clickOnSubMenu();
        homePage.clickOnWeather();

        logger.info("Moved to the weather page now");

        weatherPage.enterSearchLocation(city);
        weatherPage.selectCityFromDropDown(city);
        weatherPage.selectCityPinOnMap(city);
        logger.info("Searched for the weather information for: " + city);
    }

    @Test
    public void getWeatherInfoFromAPI() {

        HashMap<String, String> params = new HashMap<>();
        params.put("q", city);
        params.put("appid", apiKey);
        Response response = httpHandler.getRequest(fileIOOperations.readPropertyFromConfig("apiUrl"), params);
        Assert.assertEquals(response.code(), 200, "invalid response code returned");
        WeatherInformation weatherInformation = gson.fromJson(response.body() != null ? response.body().charStream() : null, WeatherInformation.class);

    }
}
