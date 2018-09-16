package com.epam.gmailwebdriver.pages;

import com.epam.gmailwebdriver.reporting.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    private static final String EMAIL_LIST_LOCATOR_FORMAT = "//span[@class='bog']/child::span[text()='%s']";
    private static final String EMAIL_FOLDER_LOCATOR_FORMAT = "//a[contains(text(),'%s') and @target='_top']/parent::*/parent::*";
    private static final String DRAFTS_FOLDER = "Drafts";
    private static final String SENT_FOLDER = "Sent";

    @FindBy(xpath = "//div[text()='Compose']")
    WebElement openNewEmail;

    @FindBy(xpath = "//a[contains (@aria-label, 'Google Account')]")
    WebElement profileButton;

    @FindBy(xpath = "//a[text()='Sign out']")
    WebElement logoutButton;

    private enum EmailFolder {
        SENT(SENT_FOLDER),
        DRAFTS(DRAFTS_FOLDER);

        private final String url;

        EmailFolder(final String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return url;
        }
    }

    protected HomePage() {
        super();
    }

    protected By getEmailInListLocator(String emailSubject) {
        return By.xpath(String.format(EMAIL_LIST_LOCATOR_FORMAT, emailSubject));
    }

    public DraftEmailPage openNewEmail() {
        waitForElementVisible(openNewEmail);
        MyLogger.info("Open new Email");
        openNewEmail.click();
        return new DraftEmailPage();
    }

    public DraftFolderPage openDraftFolderPage() {
        getFolder(EmailFolder.DRAFTS).click();
        MyLogger.info("Open Draft folder");
        return new DraftFolderPage();
    }

    public SentFolderPage openSentFolderPage() {
        getFolder(EmailFolder.SENT).click();
        MyLogger.info("Open Sent folder");
        return new SentFolderPage();
    }

    public HomePage openProfile() {
        waitForElementVisible(profileButton);
        MyLogger.info("Open profile");
        profileButton.click();
        return this;
    }

    public LoginPage logout() {
        waitForElementVisible(logoutButton);
        MyLogger.info("Logout");
        logoutButton.click();
        return new LoginPage();
    }

    private WebElement getFolder(EmailFolder folder) {
        return waitForElementVisible(By.xpath(String.format(EMAIL_FOLDER_LOCATOR_FORMAT, folder.toString())));
    }
}
