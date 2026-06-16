package com.parabnk.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final Properties PROPERTIES = new Properties();
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    // Load the file as soon as the class is first referenced.
    static {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            PROPERTIES.load(input);
            log.info("Configuration loaded from {}", CONFIG_FILE);
        } catch (IOException e) {
            log.error("Failed to load configuration file: {}", CONFIG_FILE, e);
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    // Utility class - prevent instantiation.
    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue).trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    // ---- Strongly typed convenience getters for the keys used in this project ----

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getLoginUrl() {
        return get("login.url");
    }

    public static String getBrowser() {
        return get("browser", "chrome");
    }

    public static boolean isHeadless() {
        return getBoolean("headless");
    }

    public static int getImplicitWait() {
        return getInt("implicit.wait");
    }

    public static int getExplicitWait() {
        return getInt("explicit.wait");
    }

    public static int getPageLoadTimeout() {
        return getInt("page.load.timeout");
    }

    public static String getScreenshotPath() {
        return get("screenshot.path", "test-output/screenshots/");
    }

    public static String getExtentReportPath() {
        return get("extent.report.path", "test-output/ExtentReport.html");
    }
}