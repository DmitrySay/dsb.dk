package com.epam.dsb.dk.test;

import com.epam.dsb.dk.page.DeliveryIPage;
import com.epam.dsb.dk.page.MainPage;
import com.epam.dsb.dk.page.PaymentPage;
import com.epam.dsb.dk.page.ResultsPage;
import com.epam.dsb.dk.test.bo.Journey;
import com.epam.dsb.dk.test.service.JourneyService;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PassportTest extends BaseTest {

    @Test(description = "EPMFARMATS-641")
    public void verifyThatUserGetsTicketWithPassportDataTest( ) {
        Journey journey = new Journey();
        journey.setFrom("København H");
        journey.setTo("Aarhus H");
        journey.setDate("30");
        journey.setTime("12:00");
        ResultsPage resultsPage = new JourneyService().sendJourney(journey);

        Assert.assertEquals(resultsPage.getPageTitle(), "Results page (en)", "Title is not as expected");
        Assert.assertTrue(resultsPage.isOutboundTrainExist(), "No train/s on the page");

        DeliveryIPage deliveryIPage =
                resultsPage
                        .clickOntheFirstTrain()
                        .clickOnStandardTicketRadioBtn()
                        .clickContinueBtn();
        Assert.assertEquals(deliveryIPage.getPageTitle(), "Delivery information", "Title is not as expected");
        PaymentPage paymentPage = deliveryIPage
                .fillYourEmailWindow("mail@google.com")
                .fillConfirmYourEmailWindow("mail@google.com")
                .chooseDeliveryMethodPrint()
                .selectTypeOfID("Passport")
                .fillTypeInTheLast4DigitsWindow("1234")
                .fillTravellerNameWindow("John Smith")
                .clickContinueBtn();
        Assert.assertEquals(paymentPage.getTitleText(), "Payment", "Title is not as expected");
    }
}
