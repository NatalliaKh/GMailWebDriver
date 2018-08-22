package com.epam.gmailwebdriver.pages;

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
    private WebElement openNewEmail;

    @FindBy(xpath = "//a[contains (@aria-label, 'Google Account')]")
    private WebElement profileButton;

    @FindBy(xpath = "//a[text()='Sign out']")
    private WebElement logoutButton;

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

    public HomePage() {
        super();
    }

    protected By getEmailInListLocator(String emailSubject) {
        return By.xpath(String.format(EMAIL_LIST_LOCATOR_FORMAT, emailSubject));
    }

    public DraftEmailPage openNewEmail() {
        waitForElementVisible(openNewEmail);
        openNewEmail.click();
        return new DraftEmailPage();
    }

    public DraftFolderPage openDraftFolderPage() {
        getFolder(EmailFolder.DRAFTS).click();
        return new DraftFolderPage();
    }

    public SentFolderPage openSentFolderPage() {
        getFolder(EmailFolder.SENT).click();
        return new SentFolderPage();
    }

    public HomePage openProfile() {
        waitForElementVisible(profileButton);
        profileButton.click();
        return this;
    }

    public LoginPage logout() {
        waitForElementVisible(logoutButton);
        logoutButton.click();
        return new LoginPage();
    }

    private WebElement getFolder(EmailFolder folder) {
        return waitForElementVisible(By.xpath(String.format(EMAIL_FOLDER_LOCATOR_FORMAT, folder.toString())));
    }
}
