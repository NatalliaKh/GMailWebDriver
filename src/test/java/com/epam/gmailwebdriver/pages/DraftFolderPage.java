package com.epam.gmailwebdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DraftFolderPage extends HomePage {

    protected DraftFolderPage(WebDriver driver) {
        super(driver);
    }

    public DraftEmailPage openDraftEmail(String subject) {
        WebElement email = waitForElementVisible(getEmailInListLocator(subject));
        email.click();
        return new DraftEmailPage(driver);
    }

    public DraftFolderPage checkDraftEmailWasRemoved(String subject) {
        driver.navigate().refresh();
        waitForElementInvisible(getEmailInListLocator(subject));
        return this;
    }
}
