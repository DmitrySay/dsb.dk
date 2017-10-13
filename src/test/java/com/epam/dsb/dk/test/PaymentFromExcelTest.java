package com.epam.dsb.dk.test;

import com.epam.dsb.dk.page.DeliveryIPage;
import com.epam.dsb.dk.page.PaymentPage;
import com.epam.dsb.dk.page.ResultsPage;
import com.epam.dsb.dk.bo.Journey;
import com.epam.dsb.dk.service.JourneyService;
import com.epam.dsb.dk.util.ApachePOIExcelReader;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

public class PaymentFromExcelTest extends BaseTest {
    private Journey journey;

    @BeforeTest
    public void setUpJourney(){
        ApachePOIExcelReader reader = new ApachePOIExcelReader("src/test/resources/test_data.xlsx");
        HashMap map = reader.readExcelFileToHashMap(0);
        reader.close();
        journey = new Journey();
        journey.setFrom(map.get("FROM").toString());
        journey.setTo(map.get("TO").toString());
        journey.setDate(map.get("DATE").toString());
        journey.setTime(map.get("TIME").toString());
    }

    @Test(description = "EPMFARMATS-641")
    public void verifyThatUserGetsTicketWithPassportDataTest( ) {
        ResultsPage resultsPage = new JourneyService().sendJourney(journey);

        Assert.assertEquals(resultsPage.getPageTitle(), "Results page (en)", "Title is not as expected");
        Assert.assertTrue(resultsPage.isOutboundTrainExist(), "No train/s on the page");

        DeliveryIPage deliveryIPage =
                resultsPage
                        .clickOntheFirstTrain()
                        .clickOnStandardTicketRadioBtn()
                        .clickContinueBtn();
        Assert.assertEquals(deliveryIPage.getPageTitle(), "Delivery information", "Title is not as expected");

        ApachePOIExcelReader reader = new ApachePOIExcelReader("src/test/resources/test_data.xlsx");
        HashMap map = reader.readExcelFileToHashMap(0);
        reader.close();
        PaymentPage paymentPage = deliveryIPage
                .fillYourEmailWindow(map.get("EMAIL").toString())
                .fillConfirmYourEmailWindow(map.get("CONFIRMEMAIL").toString())
                .chooseDeliveryMethodPrint()
                .selectTypeOfID(map.get("TYPEOFID").toString())
                .fillTypeInTheLast4DigitsWindow(map.get("FOURDIGITS").toString())
                .fillTravellerNameWindow(map.get("TRAVELLERSNAME").toString())
                .clickContinueBtn();
        Assert.assertEquals(paymentPage.getTitleText(), map.get("PAYMENTPAGETITLE").toString(), "Title is not as expected");
    }
}
