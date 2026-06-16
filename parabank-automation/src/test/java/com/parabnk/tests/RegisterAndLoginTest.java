package com.parabnk.tests;

import com.aventstack.extentreports.Status;
import com.parabnk.pages.HomePage;
import com.parabnk.pages.LoginPage;
import com.parabnk.pages.RegisterPage;
import com.parabnk.utilities.ExtentManager;
import com.parabnk.utilities.RandomDataGenerator;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * End-to-end suite for ParaBank:
 *
 * <ol>
 *   <li><b>TC1 Registration</b> - register a brand-new, uniquely named user.</li>
 *   <li><b>TC2 Logout</b> - log the (auto-logged-in) user out.</li>
 *   <li><b>TC3 Login</b> - log back in with the same credentials and verify
 *       the Accounts Overview page.</li>
 * </ol>
 *
 * <p>The generated username/password are stored as instance fields so they are
 * shared across the ordered test methods (TestNG uses one class instance for
 * all methods by default).</p>
 */
public class RegisterAndLoginTest extends BaseTest {

    // Credentials generated in TC1 and reused in TC3.
    private static String generatedUsername;
    private static String generatedPassword;

    @Test(priority = 1, description = "TC1: Register a new user with a unique username")
    public void testUserRegistration() {
        // Generate unique, reusable credentials for this run.
        generatedUsername = RandomDataGenerator.generateUsername();
        generatedPassword = RandomDataGenerator.generatePassword();
        ExtentManager.getTest().log(Status.INFO,
                "Generated username: <b>" + generatedUsername + "</b>");
        log.info("Generated credentials -> username: {}, password: {}",
                generatedUsername, generatedPassword);

        RegisterPage registerPage = new RegisterPage(driver).open();
        ExtentManager.getTest().log(Status.INFO, "Opened ParaBank registration page.");

        registerPage.registerUser(
                RandomDataGenerator.randomFirstName(),
                RandomDataGenerator.randomLastName(),
                RandomDataGenerator.randomStreetAddress(),
                RandomDataGenerator.randomCity(),
                RandomDataGenerator.randomState(),
                RandomDataGenerator.randomZipCode(),
                RandomDataGenerator.randomPhoneNumber(),
                RandomDataGenerator.randomSsn(),
                generatedUsername,
                generatedPassword
        );

        // Verify the success message.
        Assert.assertTrue(registerPage.isRegistrationSuccessful(),
                "Registration success message was not displayed.");
        log.info("Registration success message: {}", registerPage.getSuccessMessage());
        ExtentManager.getTest().log(Status.PASS,
                "Registration successful: " + registerPage.getSuccessMessage());

        // Capture a screenshot after successful registration.
        attachScreenshot("TC1_Registration_Success");
    }

    @Test(priority = 2, dependsOnMethods = "testUserRegistration",
            description = "TC2: Log the user out")
    public void testLogout() {
        HomePage homePage = new HomePage(driver);

        // Sanity check: we should currently be logged in.
        Assert.assertTrue(homePage.isUserLoggedIn(),
                "Expected an active session before logging out.");

        homePage.logout();
        ExtentManager.getTest().log(Status.INFO, "Clicked 'Log Out'.");

        // After logout, the Customer Login panel should be back.
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginPanelDisplayed(),
                "Customer Login panel was not displayed after logout.");
        log.info("User logged out successfully.");
        ExtentManager.getTest().log(Status.PASS, "User logged out successfully.");
    }

    @Test(priority = 3, dependsOnMethods = "testLogout",
            description = "TC3: Log back in with the newly created user")
    public void testLoginWithNewUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(generatedUsername, generatedPassword);
        ExtentManager.getTest().log(Status.INFO,
                "Submitted login form for user: " + generatedUsername);

        HomePage homePage = new HomePage(driver);

        // Verify successful login and the Accounts Overview page.
        Assert.assertTrue(homePage.isUserLoggedIn(),
                "Login failed - 'Log Out' link not present.");
        Assert.assertTrue(homePage.isAccountsOverviewDisplayed(),
                "'Accounts Overview' page was not displayed after login.");
        log.info("Login successful; Accounts Overview is displayed.");
        ExtentManager.getTest().log(Status.PASS,
                "Login successful and Accounts Overview page verified.");

        // Capture a screenshot after successful login.
        attachScreenshot("TC3_Login_Success");
    }
}