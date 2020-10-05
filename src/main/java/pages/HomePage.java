package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.GenericActions;

public class HomePage {

    WebDriver driver;
    GenericActions genericActions;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        genericActions=new GenericActions(driver);
    }


    @FindBy(id = "h_sub_menu")
    WebElement subMenu;
    @FindBy(xpath = "//a[contains(text(),'WEATHER')]")
    WebElement weatherOption;

    public void clickOnSubMenu() {
        genericActions.waitAndClick(subMenu);
    }

    public void clickOnWeather() {
        genericActions.waitAndClick(weatherOption);
    }

}
