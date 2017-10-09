package com.epam.dsb.dk.test;

import com.epam.dsb.dk.page.DeliveryIPage;
import com.epam.dsb.dk.page.MainPage;
import com.epam.dsb.dk.page.PaymentPage;
import com.epam.dsb.dk.page.ResultsPage;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MainTest extends BaseTest {

    @Parameters({"FROM", "TO", "DATE", "TIME", "RESULTPAGETITLE", "DELIVERYINFORMATION",
            "EMAIL", "CONFIRMEMAIL", "TYPEOFID", "FOURDIGITS", "TRAVELLERSNAME", "PAYMENTPAGETITLE"})
    @Test(description = "EPMFARMATS-641")
    public void verifyThatUserGetsTicketWithPassportDataTest(
            String FROM, String TO, String DATE, String TIME,
            String RESULTPAGETITLE, String DELIVERYINFORMATION,
            String EMAIL, String CONFIRMEMAIL, String TYPEOFID,
            String FOURDIGITS, String TRAVELLERSNAME, String PAYMENTPAGETITLE) {
        MainPage mainPage = new MainPage().open();
        ResultsPage resultsPage =
                mainPage.fillFromWindow(FROM)
                        .fillToWindow(TO)
                        .fillOutboundWindow(DATE)
                        .fillTimeWindow(TIME)
                        .clickOnSearchJourneyBtn();
        Assert.assertEquals(resultsPage.getPageTitle(), RESULTPAGETITLE, "Title is not as expected");
        Assert.assertTrue(resultsPage.isOutboundTrainExist(), "No train/s on the page");

        DeliveryIPage deliveryIPage =
                resultsPage
                        .clickOntheFirstTrain()
                        .clickOnStandardTicketRadioBtn()
                        .clickContinueBtn();
        Assert.assertEquals(deliveryIPage.getPageTitle(), DELIVERYINFORMATION, "Title is not as expected");
        PaymentPage paymentPage = deliveryIPage
                .fillYourEmailWindow(EMAIL)
                .fillConfirmYourEmailWindow(CONFIRMEMAIL)
                .chooseDeliveryMethodPrint()
                .selectTypeOfID(TYPEOFID)
                .fillTypeInTheLast4DigitsWindow(FOURDIGITS)
                .fillTravellerNameWindow(TRAVELLERSNAME)
                .clickContinueBtn();
        Assert.assertEquals(paymentPage.getTitleText(), PAYMENTPAGETITLE, "Title is not as expected");
    }
}
