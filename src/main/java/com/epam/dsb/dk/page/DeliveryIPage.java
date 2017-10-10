package com.epam.dsb.dk.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class DeliveryIPage extends BasePage {
    private static final Logger LOG = Logger.getLogger(DeliveryIPage.class);
    private static final By DP_CONTINUE_BTN_LOCATOR = By.xpath("//button[@type='submit'][text()='Continue']");
    private static final By YOUR_EMAIL_WINDOW_LOCATOR = By.id("EmailAddress");
    private static final By CONFIRM_YOUR_EMAIL_WINDOW_LOCATOR = By.id("ConfirmEmailAddress");
    private static final By PRINT_RADIO_BTN_LOCATOR = By.xpath("//div[@class='form-fake']");
    private static final By TYPE_OF_ID_LOCATOR = By.name("PrintDeliveryInfo.IdentificationTypeId");
    private static final By TYPE_IN_THE_LAST_4_DIGEST_WINDOW_LOCATOR = By.id("identification-value");
    private static final By TRAVELLER_NAME_WINDOW_LOCATOR = By.id("full-name");

    public DeliveryIPage() {
        LOG.info("Access to 'DeliveryIPage.class'");
        waitForElementEnabled(DP_CONTINUE_BTN_LOCATOR);
    }


    public DeliveryIPage fillYourEmailWindow(String email) {
        LOG.info("Fill 'Your e-mail' window");
        type(YOUR_EMAIL_WINDOW_LOCATOR, email);
        return this;
    }

    public DeliveryIPage fillConfirmYourEmailWindow(String email) {
        LOG.info("Fill 'Confirm e-mail' window");
        type(CONFIRM_YOUR_EMAIL_WINDOW_LOCATOR, email);
        return this;
    }

    public DeliveryIPage chooseDeliveryMethodPrint() {
        LOG.info("Click on the radio box 'Print'");
        WebElement element = driver.findElement(PRINT_RADIO_BTN_LOCATOR);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    public DeliveryIPage selectTypeOfID(String text) {
        LOG.info("Select type of ID");
        selectByVisibleText(TYPE_OF_ID_LOCATOR, text);
        return this;
    }

    public DeliveryIPage fillTypeInTheLast4DigitsWindow(String text) {
        LOG.info("TypeInTheLast4DigitsWindow");
        type(TYPE_IN_THE_LAST_4_DIGEST_WINDOW_LOCATOR, text);
        return this;
    }

    public DeliveryIPage fillTravellerNameWindow(String text) {
        LOG.info("Fill Traveller's Name Window");
        type(TRAVELLER_NAME_WINDOW_LOCATOR, text);
        return this;
    }

    public PaymentPage clickContinueBtn() {
        click(DP_CONTINUE_BTN_LOCATOR);
        return new PaymentPage();
    }
}
