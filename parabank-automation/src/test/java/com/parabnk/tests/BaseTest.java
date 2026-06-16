package com.parabnk.tests;

import com.aventstack.extentreports.Status;
import com.parabnk.utilities.DriverManager;
import com.parabnk.utilities.ExtentManager;
import com.parabnk.utilities.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public abstract class BaseTest {

    protected WebDriver driver;
    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        ExtentManager.getInstance();
        log.info("===== TEST SUITE STARTED =====");
    }

    @BeforeClass(alwaysRun = true)
    public void launchBrowser() {
        driver = DriverManager.initDriver();
        log.info("Browser launched for class: {}", getClass().getSimpleName());
    }

    @BeforeMethod(alwaysRun = true)
    public void startTestNode(Method method) {
        log.info("----- Starting test: {} -----", method.getName());
        ExtentManager.createTest(method.getName(), method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logResult(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.FAILURE -> {
                log.error("Test FAILED: {}", result.getName(), result.getThrowable());
                ExtentManager.getTest().log(Status.FAIL, "Test failed: " + result.getThrowable());
                String path = ScreenshotUtils.capture(driver, result.getName() + "_FAILURE");
                if (path != null) {
                    ExtentManager.getTest().addScreenCaptureFromPath(path);
                }
            }
            case ITestResult.SUCCESS ->
                    ExtentManager.getTest().log(Status.PASS, "Test passed.");
            case ITestResult.SKIP ->
                    ExtentManager.getTest().log(Status.SKIP, "Test skipped: " + result.getThrowable());
            default -> { /* no-op */ }
        }
        log.info("----- Finished test: {} -----", result.getName());
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        DriverManager.quitDriver();
        log.info("Browser closed for class: {}", getClass().getSimpleName());
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentManager.flush();
        log.info("===== TEST SUITE FINISHED =====");
    }

    /** Helper: attach a success screenshot to the current report node. */
    protected void attachScreenshot(String label) {
        String path = ScreenshotUtils.capture(driver, label);
        if (path != null) {
            ExtentManager.getTest().addScreenCaptureFromPath(path);
        }
    }
}