package com.epam.dsb.dk.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class PaymentPage extends BasePage {
    private static final Logger LOG = Logger.getLogger(PaymentPage.class);
    public static final By PAYMENT_TITLE_LOCATOR =By.xpath("//h1[@class='main-title']");

    public PaymentPage() {
        LOG.info("Access to 'PaymentPage.class'");
        waitForElementVisible(PAYMENT_TITLE_LOCATOR);
    }

    public String getTitleText(){
        LOG.info("Get title of the payment page '"+driver.findElement(PAYMENT_TITLE_LOCATOR).getText()+"'");
        highlightElement(PAYMENT_TITLE_LOCATOR);
        takeScreenshot();
        unHighlightElement(PAYMENT_TITLE_LOCATOR);
        return  driver.findElement(PAYMENT_TITLE_LOCATOR).getText();
    }
}
