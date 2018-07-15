package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    private static final String EMAIL_LIST_LOCATOR_FORMAT = "//span[@class='bog' and text()='%s']";
    private static final String DRAFT_FOLDER_URL = "https://mail.google.com/mail/#drafts";
    private static final String SENT_FOLDER_URL = "https://mail.google.com/mail/#sent";

    @FindBy(xpath = "//div[@class='T-I J-J5-Ji T-I-KE L3']")
    WebElement openNewEmail;

    @FindBy(xpath = "//a[@class='gb_b gb_db gb_R']")
    WebElement profileButton;

    @FindBy(xpath = "//a[@class='gb_za gb_Zf gb_6f gb_Ke gb_Eb']")
    WebElement logoutButton;

    protected HomePage(WebDriver driver) {
        super(driver);
    }

    protected By getEmailInListLocator(String emailSubject) {
        return By.xpath(String.format(EMAIL_LIST_LOCATOR_FORMAT, emailSubject));
    }

    public DraftEmailPage openNewEmail() {
        waitForElementVisible(openNewEmail);
        openNewEmail.click();
        return new DraftEmailPage(driver);
    }

    public DraftFolderPage openDraftFolderPage() {
        driver.get(DRAFT_FOLDER_URL);
        return new DraftFolderPage(driver);
    }

    public SentFolderPage openSentFolderPage() {
        driver.get(SENT_FOLDER_URL);
        return new SentFolderPage(driver);
    }

    public HomePage openProfile() {
        waitForElementVisible(profileButton);
        profileButton.click();
        return this;
    }

    public LoginPage logout() {
        waitForElementVisible(logoutButton);
        logoutButton.click();
        return new LoginPage(driver);
    }
}
