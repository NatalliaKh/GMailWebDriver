package com.epam.gmailwebdriver.pages;

import com.epam.gmailwebdriver.utils.Screenshoter;
import org.openqa.selenium.WebElement;

public class DraftFolderPage extends HomePage {

    protected DraftFolderPage() {
        super();
    }

    public DraftEmailPage openDraftEmail(String subject) {
        WebElement email = waitForElementVisible(getEmailInListLocator(subject));
        highlightElement(driver, email);
        Screenshoter.takeScreenshot();
        unHighlightElement(driver, email);
        email.click();
        return new DraftEmailPage();
    }

    public DraftFolderPage checkDraftEmailWasRemoved(String subject) {
        driver.navigate().refresh();
        waitForElementInvisible(getEmailInListLocator(subject));
        return this;
    }
}
