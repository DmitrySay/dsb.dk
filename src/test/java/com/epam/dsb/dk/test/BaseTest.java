package com.epam.dsb.dk.test;

import com.epam.dsb.dk.util.Browser;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.uncommons.reportng.HTMLReporter;

@Listeners({HTMLReporter.class})
public class BaseTest {
    private static final Logger LOG = Logger.getLogger(BaseTest.class);

    @BeforeClass
    public void setUp() {
    }

    @AfterClass
    public void closeBrowser() {
       Browser.closeBrowser();
    }
}