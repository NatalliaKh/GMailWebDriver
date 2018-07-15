package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SentFolderPage extends HomePage {

    private static final String EMPTY_EMAIL_LIST_LOCATOR = "//td[@style='text-align:center' and @class='TC']";

    @FindBy(xpath = "//div[@style='margin-right: 0px;']//span[@role='checkbox']")
    WebElement selectAllCheckbox;

    @FindBy(xpath = "//div[@role='button' and @aria-label='Delete']")
    WebElement removeEmailsButton;

    @FindBy(xpath = "//button[@name='ok']")
    WebElement confirmRemoveEmails;

    protected SentFolderPage(WebDriver driver) {
        super(driver);
    }

    public SentFolderPage checkSentEmailWasAdded(String subject) {
        waitForElementVisible(getEmailInListLocator(subject));
        return this;
    }

    public SentFolderPage chooseAllSentEmails() {
        waitForElementVisible(selectAllCheckbox);
        selectAllCheckbox.click();
        return this;
    }

    public SentFolderPage removeEmails() {
        waitForElementVisible(removeEmailsButton);
        removeEmailsButton.click();
        return this;
    }

    public SentFolderPage confirmRemoveEmails() {
        waitForElementVisible(confirmRemoveEmails);
        confirmRemoveEmails.click();
        return this;
    }

    public WebElement getEmptyEmailList() {
        return waitForElementVisible(By.xpath(EMPTY_EMAIL_LIST_LOCATOR));
    }
}
