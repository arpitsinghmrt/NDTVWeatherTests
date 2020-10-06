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


public class GenericActions {

    private final WebDriver driver;
    private static final Logger logger = Logger.getLogger(GenericActions.class);

    WebDriverWait webDriverWait = null;
    public GenericActions(WebDriver driver) {
        this.driver = driver;
    }



    public void takeSreenshot() {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File sourceFile = scrShot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("screenshots/" + new java.util.Date() + driver.getTitle() + ".png");
        try {
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Screenshot captured and saved as: "+destinationFile);
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

    public boolean verifyElementPresent(WebElement element) {
        if (element.isDisplayed())
            return true;
        else
            return false;
    }
}
