package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.GenericActions;


public class WeatherPage {

    WebDriver driver;
    GenericActions genericActions;

    public WeatherPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        genericActions = new GenericActions(driver);

    }

    @FindBy(id = "searchBox")
    WebElement searchLocation;


    public void enterSearchLocation(String city) {
        genericActions.setText(searchLocation, city);
    }

    public void selectCityFromDropDown(String city) {
        genericActions.waitAndClick(driver.findElement(By.xpath("//label[@for='" + city + "']")));
    }

    public void selectCityPinOnMap(String city) {
        genericActions.waitAndClick(driver.findElement(By.xpath("//div[contains(@class, 'cityText') and text() = '" + city + "']/..//div/span[2]")));
    }

    public boolean isCityInfoPopUpDisplayed(String city) {
        return genericActions.verifyElementPresent(driver.findElement(By.xpath("//span[contains(text(), '" + city + "')]/../../..")));
    }

    public String getTemperatureInFahrenheit(String city) {
        return genericActions.getText(driver.findElement((By.xpath("//span[contains(text(), '" + city + "')]/../..//span/b[contains(text(), 'Fahrenheit')]"))));
    }

    public String getTemperatureInDegrees(String city) {
        return genericActions.getText(driver.findElement((By.xpath("//span[contains(text(), '" + city + "')]/../..//span/b[contains(text(), 'Degrees')]"))));

    }
}
