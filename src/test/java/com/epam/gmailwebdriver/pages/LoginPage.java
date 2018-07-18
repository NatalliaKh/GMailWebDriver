package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    private static final String GMAIL_LINK = "https://gmail.com";

    @FindBy(xpath = "//input[@type='email']")
    WebElement usernameField;

    @FindBy(css = "div#identifierNext")
    WebElement usernameNextButton;

    @FindBy(name = "password")
    WebElement passwordField;

    @FindBy(id = "passwordNext")
    WebElement passwordNextButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(GMAIL_LINK);
        return this;
    }

    public LoginPage fillUsername(String username){
        waitForElementVisible(usernameField);
        usernameField.sendKeys(username);
        return this;
    }

    public LoginPage enterUsername() {
        waitForElementVisible(usernameNextButton);
        usernameNextButton.click();
        return this;
    }

    public LoginPage fillPassword(String password){
        waitForElementVisible(passwordField);
        passwordField.sendKeys(password);
        return this;
    }

    public HomePage enterPassword() {
        waitForElementVisible(passwordNextButton);
        passwordNextButton.click();
        return new HomePage(driver);
    }
}
