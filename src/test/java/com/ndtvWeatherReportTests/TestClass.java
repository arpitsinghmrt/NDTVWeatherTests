package com.ndtvWeatherReportTests;

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
import util.ExcelReader;
import util.FileIOOperations;
import util.TemperatureComparator;

import java.io.IOException;
import java.util.HashMap;

public class TestClass extends BaseTest {
    WebDriver driver;
    FileIOOperations fileIOOperations = new FileIOOperations();

    private static final Logger logger = Logger.getLogger(TestClass.class);
    private static final String city = "Amritsar";
    private static final String apiKey = "7fe67bf08c80ded756e598d6f8fedaea";

    HttpHandler httpHandler = new HttpHandler();
    ExcelReader excelReader = new ExcelReader();
    TemperatureComparator temperatureComparator = new TemperatureComparator();
    Gson gson = new Gson();

    double tempInFahrenheitNdtv;
    double tempInCelsiusNdtv;
    double tempFromApi;


    @Test
    public void getWeatherInfoFromNDTV() {
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
        Assert.assertTrue(weatherPage.isCityInfoPopUpDisplayed(city), city + "'s weather information not displayed");

        tempInFahrenheitNdtv = Double.parseDouble((weatherPage.getTemperatureInFahrenheit(city).split(":")[1]).trim());
        tempInCelsiusNdtv = Double.parseDouble((weatherPage.getTemperatureInDegrees(city).split(":")[1]).trim());

        logger.info("temperature in celsius from NDTV: " + tempInCelsiusNdtv);
        logger.info("temperature in fahrenheit from NDTV: " + tempInFahrenheitNdtv);


    }

    @Test(dependsOnMethods = {"getWeatherInfoFromNDTV"})
    public void getWeatherInfoFromAPI() {

        HashMap<String, String> params = new HashMap<>();
        params.put("q", city);
        params.put("appid", apiKey);
        params.put("units", "metric");
        Response response = httpHandler.getRequest(fileIOOperations.readPropertyFromConfig("apiUrl"), params);
        Assert.assertEquals(response.code(), 200, "invalid response code returned");
        WeatherInformation weatherInformation = gson.fromJson(response.body() != null ? response.body().charStream() : null, WeatherInformation.class);

        tempFromApi = weatherInformation.getMain().getTemp();
        logger.info("temperature from API: " + tempFromApi);
    }

    @Test(dependsOnMethods = {"getWeatherInfoFromAPI"})
    public void compareTemperatures() throws IOException {
        logger.info("Variance for temperature unit Celsius is: " + (temperatureComparator.compareTemperature(tempInCelsiusNdtv, tempFromApi)));
        logger.info("Variance for temperature unit Fahrenheit is: " + (temperatureComparator.compareTemperatureInFahrenheit(tempInFahrenheitNdtv, tempFromApi)));

        Assert.assertEquals((temperatureComparator.compareTemperature(tempInCelsiusNdtv, tempFromApi)), excelReader.readVarianceForUnit("celsius"), "Variance is not equal to " + excelReader.readVarianceForUnit("celsius") + " for unit celsius");
        Assert.assertEquals((temperatureComparator.compareTemperatureInFahrenheit(tempInFahrenheitNdtv, tempFromApi)), excelReader.readVarianceForUnit("fahrenheit"), "Variance is not equal to " + excelReader.readVarianceForUnit("fahrenheit") + " for unit fahrenheit");

    }
}
