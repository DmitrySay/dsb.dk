package com.epam.dsb.dk.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends BasePage {
    private static final Logger LOG = Logger.getLogger(MainPage.class);
    public static final String URL = "https://www.dsb.dk/en";
    private static final By FIND_JOURNEY_AND_PRICE_LOCATOR = By.xpath("//h1[text()='Find journey and price']");
    private static final By FROM_WINDOW_LOCATOR = By.id("DepartLocation1");
    private static final By TO_WINDOW_LOCATOR = By.id("ArriveLocation2");
    private static final By SEARCH_JOURNEY_BTN_LOCATOR = By.id("formSubmitBtn");
    private static final By OUTBOUND_WINDOW_LOCATOR = By.id("SearchDate3");
    private static final String OUTBOUND_WINDOW_DATE_LOCATOR_PATTERN = "//tbody/tr//td[@data-day='%s']/button";
    private static final String FROM_WINDOW_LOCATOR_PATTERN = "//ul[@id='react-autosuggest-DepartLocation']//span[text()='%s']";
    private static final String TO_WINDOW_LOCATOR_PATTERN = "//ul[@id='react-autosuggest-ArriveLocation']//span[text()='%s']";
    private static final By TIME_WINDOW_LOCATOR = By.id("SearchTime");
    private static final String TIME_WINDOW_LOCATOR_PATTERN = "//ul[@id='react-autosuggest-SearchTime']//strong[text()='%s']";
    private static final By NUMBER_TRAVELLERS_LOCATOR = By.id("Passengers4");
    private static final By NUMBER_TRAVELLERS_ADULTS_PLUS_LOCATOR = By.xpath("//a[@id='PassengersAdults' AND @class='counter__increase']");
    private static final By SEAT_RESERVATIONS_LOCATOR = By.id("SeatReservations1");

    public MainPage() {
        LOG.info("Access to 'MainPage.class'");
    }

    public MainPage open() {
        driver.get(URL);
        LOG.info("Open the website = " + URL);
        return this;
    }

    public MainPage fillFromWindow(String station) {
        LOG.info("Fill 'FROM' window");
        waitForElementVisible(FROM_WINDOW_LOCATOR);
        driver.findElement(FROM_WINDOW_LOCATOR).click();
        WebElement el = driver.findElement(By.xpath(String.format(FROM_WINDOW_LOCATOR_PATTERN, station)));
        LOG.info("Select station '" + station + "'");
        new Actions(driver).moveToElement(el).click().perform();
        takeHighlightScreenshot(FROM_WINDOW_LOCATOR);
        return this;
    }

    public MainPage fillToWindow(String station) {
        LOG.info("Fill 'TO' window");
        waitForElementVisible(TO_WINDOW_LOCATOR);
        driver.findElement(TO_WINDOW_LOCATOR).click();
        WebElement el = driver.findElement(By.xpath(String.format(TO_WINDOW_LOCATOR_PATTERN, station)));
        LOG.info("Select station '" + station + "'");
        new Actions(driver).moveToElement(el).click().perform();
        takeHighlightScreenshot(TO_WINDOW_LOCATOR);
        return this;
    }

    public MainPage fillOutboundWindow(String date) {
        LOG.info("Fill 'OUTBOUND' window");
        click(OUTBOUND_WINDOW_LOCATOR);
        By outboundWindowdateLocator = By.xpath(String.format(OUTBOUND_WINDOW_DATE_LOCATOR_PATTERN, date));
        click(outboundWindowdateLocator);
        return this;
    }

    public MainPage fillTimeWindow(String time) {
        LOG.info("Fill 'TIME' window");
        waitForElementVisible(TIME_WINDOW_LOCATOR);
        driver.findElement(TIME_WINDOW_LOCATOR).click();
        driver.findElement(TIME_WINDOW_LOCATOR).clear();
        driver.findElement(TIME_WINDOW_LOCATOR).sendKeys(time);
        WebElement el = driver.findElement(By.xpath(String.format(TIME_WINDOW_LOCATOR_PATTERN, time)));
        new Actions(driver).moveToElement(el).click().perform();
        takeHighlightScreenshot(TIME_WINDOW_LOCATOR);
        return this;
    }

    public ResultsPage clickOnSearchJourneyBtn() {
        LOG.info("Click on the 'Search Journey' button");
        submit(SEARCH_JOURNEY_BTN_LOCATOR);
        return new ResultsPage();
    }

}
