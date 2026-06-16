package com.parabnk.pages;

import com.parabnk.utilities.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent of all page objects.
 *
 * <p>Holds the {@link WebDriver}, a shared {@link WaitUtils} and a set of
 * reusable, logged, exception-handled element actions (click, type, getText, …)
 * so individual pages stay small and declarative.</p>
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WaitUtils wait;
    protected final Logger log = LogManager.getLogger(getClass());

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    /** Opens a URL. */
    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    /** Waits for clickability then clicks. */
    protected void click(By locator) {
        log.info("Clicking element: {}", locator);
        wait.waitForClickable(locator).click();
    }

    /** Waits for visibility, clears, then types. */
    protected void type(By locator, String text) {
        log.info("Typing '{}' into: {}", text, locator);
        WebElement element = wait.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /** Waits for visibility then returns the trimmed text. */
    protected String getText(By locator) {
        String text = wait.waitForVisibility(locator).getText().trim();
        log.info("Read text '{}' from: {}", text, locator);
        return text;
    }

    /**
     * Returns {@code true} if the element is present and visible, without
     * throwing if it is absent.
     */
    protected boolean isDisplayed(By locator) {
        try {
            return wait.waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            log.warn("Element not displayed: {}", locator);
            return false;
        }
    }

    /** Lightweight presence check (no wait). */
    protected boolean isPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /** Returns the current page title. */
    public String getPageTitle() {
        return driver.getTitle();
    }
}