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
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected static final Logger LOG = Logger.getLogger(BasePage.class);
    private static final int WAIT_ELEMENT_TIMEOUT = 10;
    private static final String SCREENSHOTS_NAME_TPL = "target/screenshots/scr";


    protected BasePage() {
        this.driver = Browser.getInstance();
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected void waitForElementVisible(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void waitForElementEnabled(By locator) {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForAjaxProcessed() {
        new WebDriverWait(driver, WAIT_ELEMENT_TIMEOUT).until(isAjaxFinished());
    }
    private ExpectedCondition<Boolean> isAjaxFinished (){
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return(Boolean)((JavascriptExecutor)driver).executeScript("return jQuery.active == 0");
            }
        };
    }

    public void selectByVisibleText(By locator, String text) {
        waitForElementEnabled(locator);
        highlightElement(locator);
        takeScreenshot();
        unHighlightElement(locator);
        WebElement element =driver.findElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public String getPageTitle(){
        LOG.info("Page title is '" + driver.getTitle()+"'");
        return driver.getTitle();
    }

    public List<WebElement> findElements(By locator){
        highlightElement(locator);
        takeScreenshot();
        unHighlightElement(locator);
        List<WebElement> list = driver.findElements(locator);
        return list;
    }

    public void submit(final By locator) {
        waitForElementVisible(locator);
        LOG.info("Submitting '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
        highlightElement(locator);
        takeScreenshot();
        unHighlightElement(locator);
        driver.findElement(locator).submit();
    }

    public void click(final By locator) {
        waitForElementVisible(locator);
        LOG.info("Clicking element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
        highlightElement(locator);
        takeScreenshot();
        unHighlightElement(locator);
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

    public void takeHighlightScreenshot(By locator){
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

    public void takeScreenshot() {
        WebDriver driver = Browser.getInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            LOG.info("Saved screenshot: " + screenshotName);
        } catch (IOException e) {
            LOG.error("Failed to make screenshot");
        }
    }
}