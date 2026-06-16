package com.parabnk.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    /**
     * Initialises Chrome (only if not already initialised for this thread),
     * applies timeouts from config and maximises the window.
     */
    public static WebDriver initDriver() {
        if (DRIVER.get() == null) {
            String browser = ConfigReader.getBrowser().toLowerCase();
            log.info("Initialising browser: {}", browser);

            WebDriver driver;
            if (browser.equals("chrome")) {
                // WebDriverManager downloads/caches the matching ChromeDriver binary.
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(buildChromeOptions());
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
            driver.manage().timeouts()
                    .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
            driver.manage().window().maximize();

            DRIVER.set(driver);
            log.info("Browser initialised successfully.");
        }
        return DRIVER.get();
    }

    /** Returns the active driver for the current thread. */
    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            throw new IllegalStateException("Driver not initialised. Call initDriver() first.");
        }
        return DRIVER.get();
    }

    /** Quits the browser and clears the ThreadLocal to avoid memory leaks. */
    public static void quitDriver() {
        if (DRIVER.get() != null) {
            log.info("Quitting browser.");
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }

    /** Builds Chrome options, honouring the headless flag from config. */
    private static ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        return options;
    }
}