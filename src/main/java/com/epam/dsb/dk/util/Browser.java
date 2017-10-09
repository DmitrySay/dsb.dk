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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Browser {
    private static final Logger LOG = Logger.getLogger(Browser.class);
    private static int PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS = 20;
    private static int COMMAND_DEFAULT_TIMEOUT_SECONDS = 20;
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
                System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "FIREFOX":
                System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "IEXPLORE":
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.ignoreZoomSettings();
                options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
                driver = new InternetExplorerDriver(options);
                break;
            case "EDGE":
                System.setProperty("webdriver.edge.driver", "src/test/resources/MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                break;
            case "REMOTE.CHROME":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
                try {
                    driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                } catch (MalformedURLException e) {
                    LOG.info(e.getMessage());
                }
                break;
            default:
                try {
                    throw new Exception("Selenium.properties could not load");
                } catch (Exception e) {
                    LOG.info(e.getMessage());
                }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(COMMAND_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        return driver;
    }

    private static void initProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/test/resources/selenium.properties");
            prop.load(input);
            type = prop.getProperty("browser").toUpperCase();
            COMMAND_DEFAULT_TIMEOUT_SECONDS = Integer.parseInt(prop.getProperty("command_timeout_seconds"));
            PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS = Integer.parseInt(prop.getProperty("page_load_timeout_seconds"));
        } catch (IOException e) {
            LOG.info(e.getMessage());
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                LOG.info(e.getMessage());
            }
        }
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
