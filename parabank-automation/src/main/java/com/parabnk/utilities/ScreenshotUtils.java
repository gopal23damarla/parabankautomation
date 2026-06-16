package com.parabnk.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {
    }

    /**
     * Saves a screenshot to the configured screenshot directory.
     *
     * @param driver   the active WebDriver
     * @param testName a label used in the file name
     * @return the absolute path of the saved file, or {@code null} on failure
     */
    public static String capture(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dir = ConfigReader.getScreenshotPath();
        String fileName = testName + "_" + timestamp + ".png";

        File destination = new File(dir + fileName);
        try {
            // Ensure the directory exists.
            destination.getParentFile().mkdirs();
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            log.info("Screenshot saved: {}", destination.getAbsolutePath());
            return destination.getAbsolutePath();
        } catch (IOException e) {
            log.error("Failed to capture screenshot for '{}'", testName, e);
            return null;
        }
    }

    /**
     * Returns the screenshot as a Base64 string so it can be embedded directly
     * into the Extent HTML report (no external file dependency).
     */
    public static String captureAsBase64(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            log.error("Failed to capture Base64 screenshot", e);
            return null;
        }
    }
}