package com.epam.dsb.dk.page;

import com.epam.dsb.dk.util.Browser;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    private static final Logger LOG = Logger.getLogger(BasePage.class);
    private static final int WAIT_ELEMENT_TIMEOUT = 10;
    private static final String SCREENSHOTS_NAME_TPL = "target/screenshots/scr_";


    protected BasePage() {
        LOG.info("Access to 'BasePage.class'");
        this.driver = Browser.getInstance();
    }

    protected void waitForElementVisible(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void waitForElementEnabled(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void selectByVisibleText(By locator, String text) {
        waitForElementEnabled(locator);
        takeHighlightScreenshot(locator);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public String getPageTitle() {
        LOG.info("Page title is '" + driver.getTitle() + "'");
        return driver.getTitle();
    }

    public List<WebElement> findElements(By locator) {
        highlightElement(locator);
        takeScreenshot();
        unHighlightElement(locator);
        List<WebElement> list = driver.findElements(locator);
        return list;
    }

    public void submit(final By locator) {
        waitForElementVisible(locator);
        LOG.info("Submitting element'" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
        takeHighlightScreenshot(locator);
        driver.findElement(locator).submit();
    }

    public void click(final By locator) {
        waitForElementVisible(locator);
        LOG.info("Clicking element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
        takeHighlightScreenshot(locator);
        driver.findElement(locator).click();
    }

    protected void type(final By locator, String text) {
        waitForElementVisible(locator);
        highlightElement(locator);
        LOG.info("Typing text '" + text + "' to input form '" + driver.findElement(locator).getAttribute("name") + "' (Located: " + locator + ")");
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        takeScreenshot();
        unHighlightElement(locator);
    }

    public void takeHighlightScreenshot(By locator) {
        highlightElement(locator);
        takeScreenshot();
        unHighlightElement(locator);
    }

    public void highlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", driver.findElement(locator));
    }

    public void unHighlightElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", driver.findElement(locator));
    }

    public void waitMilliseconds(int time) {
        try {
            Thread.sleep(time);
            LOG.info(String.format("Waiting ... = %s milliseconds", time));
        } catch (Exception e) {
            LOG.info(e.getMessage());
            e.printStackTrace();
        }
    }

    public void takeScreenshot() {
        WebDriver driver = Browser.getInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd_MMMMM_yyyy_HH_mm_ss_SS");
            String screenshotName = SCREENSHOTS_NAME_TPL + dateFormat.format(new Date());
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            LOG.info("Saved screenshot: " + screenshotName);
        } catch (IOException e) {
            LOG.error("Failed to make screenshot");
        }
    }
}