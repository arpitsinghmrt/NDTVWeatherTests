package util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GenericActions {

    WebDriver driver;

    public GenericActions(WebDriver driver) {
        this.driver = driver;
    }

    WebDriverWait webDriverWait = null;


    public void takeSreenshot() {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File sourceFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("screenshots/" + new java.util.Date() + driver.getTitle() + ".png");
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitAndClick(WebElement element) {
        webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        this.takeSreenshot();
    }

    public void setText(WebElement element, String text) {
        webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
        this.takeSreenshot();
    }

    public String getText(WebElement element) {
        webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }
}
