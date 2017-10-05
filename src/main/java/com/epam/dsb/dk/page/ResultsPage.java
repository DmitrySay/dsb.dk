package com.epam.dsb.dk.page;

import org.openqa.selenium.By;

public class ResultsPage extends BasePage {
    private static final By LOGO_LOCATOR = By.xpath("//a[text()='DSB']");
    private static final By AD_RETURN_JOURNEY_LOCATOR = By.xpath("//div[@id='add-journey']//span[2]");
    private static final By OUTBOUNDS_LOCATOR = By.xpath("//div[@id='search-results']//div[contains(@id, 'outBound')]");
    private static final By STANDARD_TICKET_RADIO_BTN_LOCATOR = By.xpath("//a[@id='0']/div/div[1]");
    private static final By CONTINUE_BTN_LOCATOR = By.xpath("//button[@type='submit'][text()='Continue']");

    public ResultsPage() {
        LOG.info("Access to 'ResultsPage.class'");
        waitForElementEnabled(AD_RETURN_JOURNEY_LOCATOR);
    }

    public Boolean isOutboundTrainExist(){
        return findElements(OUTBOUNDS_LOCATOR).size()!=0;
    }

    public ResultsPage clickOntheFirstTrain(){
        LOG.info("Click on the first train");
        findElements(OUTBOUNDS_LOCATOR).get(0).click();
        return this;
    }

    public ResultsPage clickOnStandardTicketRadioBtn(){
        click(STANDARD_TICKET_RADIO_BTN_LOCATOR);
        return this;
    }

    public DeliveryIPage clickContinueBtn(){
        click(CONTINUE_BTN_LOCATOR);
        return new DeliveryIPage();
    }

}
