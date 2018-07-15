package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    private static final String EMAIL_LIST_LOCATOR_FORMAT = "//span[@class='bog' and text()='%s']";
    private static final String EMAIL_FOLDER_LOCATOR_FORMAT = "//a[@href='%s' and @target='_top']";
    private static final String DRAFTS_FOLDER_URL = "https://mail.google.com/mail/#drafts";
    private static final String SENT_FOLDER_URL = "https://mail.google.com/mail/#sent";

    @FindBy(xpath = "//div[@class='T-I J-J5-Ji T-I-KE L3']")
    WebElement openNewEmail;

    @FindBy(xpath = "//a[@class='gb_b gb_db gb_R']")
    WebElement profileButton;

    @FindBy(xpath = "//a[@class='gb_za gb_Zf gb_6f gb_Ke gb_Eb']")
    WebElement logoutButton;

    private enum EmailFolder {
        SENT(SENT_FOLDER_URL),
        DRAFTS(DRAFTS_FOLDER_URL);

        private final String url;

        EmailFolder(final String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return url;
        }
    }

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
        getFolder(EmailFolder.DRAFTS).click();
        driver.get(DRAFTS_FOLDER_URL);
        return new DraftFolderPage(driver);
    }

    public SentFolderPage openSentFolderPage() {
        getFolder(EmailFolder.SENT).click();
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

    private WebElement getFolder(EmailFolder folder) {
        return waitForElementVisible(By.xpath(String.format(EMAIL_FOLDER_LOCATOR_FORMAT, folder.toString())));
    }
}
