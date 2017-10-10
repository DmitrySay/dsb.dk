package com.epam.dsb.dk.service;

import com.epam.dsb.dk.page.MainPage;
import com.epam.dsb.dk.page.ResultsPage;
import com.epam.dsb.dk.bo.Journey;

public class JourneyService {

    public ResultsPage sendJourney(Journey journey) {
        MainPage mainPage = new MainPage().open();
        ResultsPage resultsPage =
                mainPage.fillFromWindow(journey.getFrom())
                        .fillToWindow(journey.getTo())
                        .fillOutboundWindow(journey.getDate())
                        .fillTimeWindow(journey.getTime())
                        .clickOnSearchJourneyBtn();
        return resultsPage;
    }
}
