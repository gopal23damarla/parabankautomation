package com.parabnk.pages;

import com.parabnk.utilities.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    // ---- Locators ----
    private final By firstName       = By.id("customer.firstName");
    private final By lastName        = By.id("customer.lastName");
    private final By address         = By.id("customer.address.street");
    private final By city            = By.id("customer.address.city");
    private final By state           = By.id("customer.address.state");
    private final By zipCode         = By.id("customer.address.zipCode");
    private final By phoneNumber     = By.id("customer.phoneNumber");
    private final By ssn             = By.id("customer.ssn");
    private final By username        = By.id("customer.username");
    private final By password        = By.id("customer.password");
    private final By confirmPassword = By.id("repeatedPassword");
    private final By registerButton  = By.xpath("//input[@value='Register']");

    // Post-registration success message: "Your account was created successfully..."
    private final By successHeading  = By.xpath("//h1[@class='title' and contains(text(),'Welcome')]");
    private final By successMessage  = By.xpath("//p[contains(text(),'Your account was created successfully')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage open() {
        navigateTo(ConfigReader.getBaseUrl());
        return this;
    }

    public RegisterPage registerUser(String firstNameVal, String lastNameVal, String addressVal,
                                     String cityVal, String stateVal, String zipVal,
                                     String phoneVal, String ssnVal, String usernameVal,
                                     String passwordVal) {
        log.info("Registering new user with username: {}", usernameVal);

        type(firstName, firstNameVal);
        type(lastName, lastNameVal);
        type(address, addressVal);
        type(city, cityVal);
        type(state, stateVal);
        type(zipCode, zipVal);
        type(phoneNumber, phoneVal);
        type(ssn, ssnVal);
        type(username, usernameVal);
        type(password, passwordVal);
        type(confirmPassword, passwordVal);

        click(registerButton);
        log.info("Registration form submitted.");
        return this;
    }

    /** @return true if the post-registration success message is shown. */
    public boolean isRegistrationSuccessful() {
        return isDisplayed(successMessage);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    public String getWelcomeHeading() {
        return getText(successHeading);
    }
}