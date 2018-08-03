package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    private static final String EMAIL_LIST_LOCATOR_FORMAT = "//span[@class='bog' and text()='%s']";
    private static final String EMAIL_FOLDER_LOCATOR_FORMAT = "//a[contains(text(),'%s') and @target='_top']/parent::*/parent::*";
    private static final String DRAFTS_FOLDER = "Drafts";
    private static final String SENT_FOLDER = "Sent Mail";

    @FindBy(xpath = "//div[text()='COMPOSE']")
    WebElement openNewEmail;

    @FindBy(xpath = "//a[contains (@title, 'Google Account')]")
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
        new Actions(driver).sendKeys("gd").build().perform();
        return new DraftFolderPage(driver);
    }

    public SentFolderPage openSentFolderPage() {
        getFolder(EmailFolder.SENT).click();
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
