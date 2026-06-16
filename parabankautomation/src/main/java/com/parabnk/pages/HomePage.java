package com.parabnk.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page object for the authenticated area of ParaBank: the left navigation
 * menu (containing "Log Out") and the "Accounts Overview" page.
 */
public class HomePage extends BasePage {

    // ---- Locators ----
    private final By logoutLink            = By.linkText("Log Out");
    private final By accountsOverviewLink  = By.linkText("Accounts Overview");
    private final By accountsOverviewTitle = By.xpath("//h1[contains(@class,'title') and normalize-space()='Accounts Overview']");
    private final By welcomeMessage        = By.xpath("//p[@class='smallText']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /** Clicks the "Log Out" link in the left navigation menu. */
    public void logout() {
        log.info("Clicking 'Log Out'.");
        click(logoutLink);
    }

    /** @return true if the "Log Out" link is present (i.e. a user is logged in). */
    public boolean isUserLoggedIn() {
        return isPresent(logoutLink);
    }

    /** Navigates to the Accounts Overview page via the left menu. */
    public HomePage goToAccountsOverview() {
        click(accountsOverviewLink);
        return this;
    }

    /** @return true if the "Accounts Overview" page heading is visible. */
    public boolean isAccountsOverviewDisplayed() {
        return isDisplayed(accountsOverviewTitle);
    }

    public String getWelcomeText() {
        return getText(welcomeMessage);
    }
}