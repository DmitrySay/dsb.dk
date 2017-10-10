package com.epam.dsb.dk.util;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Browser {
    private static final Logger LOG = Logger.getLogger(Browser.class);
    private static int PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS = 20;
    private static int COMMAND_DEFAULT_TIMEOUT_SECONDS = 20;
    private static final String PROPERTIES_FILE = "selenium.properties";
    private static final String HUB_URL = "http://localhost:4444/wd/hub";
    private static final String WEBDRIVER_EDGE = "webdriver.edge.driver";
    private static final String WEBDRIVER_CHROME = "webdriver.chrome.driver";
    private static final String WEBDRIVER_FIREFOX = "webdriver.gecko.driver";
    private static final String WEBDRIVER_IEXPLORER = "webdriver.ie.driver";
    private static final String PATH_TO_WEBDRIVER_CHROME = "src/test/resources/chromedriver.exe";
    private static final String PATH_TO_WEBDRIVER_FIREFOX = "src/test/resources/geckodriver.exe";
    private static final String PATH_TO_WEBDRIVER_IEXPLORER = "src/test/resources/IEDriverServer.exe";
    private static final String PATH_TO_WEBDRIVER_EDGE = "src/test/resources/MicrosoftWebDriver.exe";
    private static WebDriver instance = null;
    private static String type = "";

    private Browser() {
    }

    public static WebDriver getInstance() {
        if (instance == null) {
            try {
                return instance = init();
            } catch (Exception e) {
                LOG.info(e.getMessage());
            }
        }
        return instance;
    }

    private static WebDriver init() {
        WebDriver driver = null;
        initProperties();
        switch (type) {
            case "CHROME":
                System.setProperty(WEBDRIVER_CHROME, PATH_TO_WEBDRIVER_CHROME);
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                System.setProperty(WEBDRIVER_FIREFOX, PATH_TO_WEBDRIVER_FIREFOX);
                driver = new FirefoxDriver();
                break;
            case "IEXPLORE":
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.ignoreZoomSettings();
                options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                System.setProperty(WEBDRIVER_IEXPLORER, PATH_TO_WEBDRIVER_IEXPLORER);
                driver = new InternetExplorerDriver(options);
                break;
            case "EDGE":
                System.setProperty(WEBDRIVER_EDGE, PATH_TO_WEBDRIVER_EDGE);
                driver = new EdgeDriver();
                break;
            case "REMOTE.CHROME":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                System.setProperty(WEBDRIVER_CHROME, PATH_TO_WEBDRIVER_CHROME);
                try {
                    driver = new RemoteWebDriver(new URL(HUB_URL), capabilities);
                } catch (MalformedURLException e) {
                    LOG.info(e.getMessage());
                }
                break;
            default:
                try {
                    throw new Exception("Selenium.properties could not be load");
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(COMMAND_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return driver;
    }

    private static void initProperties() {
        PropertiesResourceManager prop = new PropertiesResourceManager(PROPERTIES_FILE);
        type = prop.getProperty("browser").toUpperCase();
        COMMAND_DEFAULT_TIMEOUT_SECONDS = Integer.parseInt(prop.getProperty("command_timeout_seconds"));
        PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS = Integer.parseInt(prop.getProperty("page_load_timeout_seconds"));
    }

    public static void closeBrowser() {
        if (instance != null) {
            try {
                instance.quit();
            } catch (Exception e) {
                LOG.info(e.getMessage());
            } finally {
                instance = null;
            }
        }
    }
}
