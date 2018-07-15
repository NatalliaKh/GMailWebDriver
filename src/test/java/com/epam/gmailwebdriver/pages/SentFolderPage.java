package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.WebDriver;

public class SentFolderPage extends HomePage {

    protected SentFolderPage(WebDriver driver) {
        super(driver);
    }

    public SentFolderPage checkSentEmailWasAdded(String subject) {
        waitForElementVisible(getEmailInListLocator(subject));
        return this;
    }
}
