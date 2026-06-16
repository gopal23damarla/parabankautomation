package com.parabnk.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ExtentManager {

    private static final Logger log = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    private ExtentManager() {
    }

    /** Creates (once) and returns the shared ExtentReports instance. */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = ConfigReader.getExtentReportPath();
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("ParaBank Automation Report");
            spark.config().setReportName("Registration & Login Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application", "ParaBank");
            extent.setSystemInfo("Browser", ConfigReader.getBrowser());
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            log.info("Extent report initialised at {}", reportPath);
        }
        return extent;
    }

    /** Starts a new test node and stores it for the current thread. */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        TEST.set(test);
        return test;
    }

    /** Returns the test node bound to the current thread. */
    public static ExtentTest getTest() {
        return TEST.get();
    }

    /** Flushes (writes) the report to disk - call once at the end of the run. */
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            log.info("Extent report flushed to disk.");
        }
    }
}