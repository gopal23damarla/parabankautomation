package com.parabnk.pages;

import com.parabnk.utilities.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
  // ---- Locators ----
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton   = By.xpath("//input[@value='Log In']");
    private final By loginPanel    = By.xpath("//h2[text()='Customer Login']");
    private final By errorMessage  = By.xpath("//p[@class='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** Opens the login (index) page. */
    public LoginPage open() {
        navigateTo(ConfigReader.getLoginUrl());
        return this;
    }

    /** Performs login with the supplied credentials. */
    public void login(String usernameVal, String passwordVal) {
        log.info("Logging in as: {}", usernameVal);
        type(usernameField, usernameVal);
        type(passwordField, passwordVal);
        click(loginButton);
    }

    /** @return true when the "Customer Login" panel is visible (i.e. logged out). */
    public boolean isLoginPanelDisplayed() {
        return isDisplayed(loginPanel);
    }

    public boolean isErrorDisplayed() {
        return isPresent(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}