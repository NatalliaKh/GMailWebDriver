package com.epam.gmailwebdriver.service;

import com.epam.gmailwebdriver.bo.Email;
import com.epam.gmailwebdriver.bo.User;
import com.epam.gmailwebdriver.pages.DraftEmailPage;
import com.epam.gmailwebdriver.pages.HomePage;
import com.epam.gmailwebdriver.pages.LoginPage;
import com.epam.gmailwebdriver.pages.SentFolderPage;
import org.testng.Assert;

import static org.testng.Assert.assertTrue;

public class MailService {
    public HomePage loginToMail(User user) {
        return new LoginPage()
                .open()
                .fillUsername(user.getUserName())
                .enterUsername()
                .fillPassword(user.getPassword())
                .enterPassword();
    }

    public void createDraftEmail(Email email) {
        new HomePage().openNewEmail()
                .fillEmail(email)
                .saveToDraft();
    }

    public boolean checkDraftMailExists(Email email) {
        DraftEmailPage draftEmailPage = new HomePage()
                .openDraftFolderPage()
                .openDraftEmail(email.getSubject());

        Assert.assertEquals(draftEmailPage.getEmailReceiverAddress(), email.getReceiver(), "Email receiver address is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailSubject(), email.getSubject(), "Email subject is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailBody(), email.getBody(), "Email body is not correct.");
        return true;
    }

    public void logout() {
        new HomePage().openProfile()
                .logout();
    }

    public boolean sendEmail(Email email) {
        new DraftEmailPage().sendEmail()
                .openDraftFolderPage()
                .checkDraftEmailWasRemoved(email.getSubject())
                .openSentFolderPage()
                .checkSentEmailWasAdded(email.getSubject());
        return true;
    }

    public boolean removeEmail(Email email) {
        new DraftEmailPage().removeEmail()
                .openDraftFolderPage()
                .checkDraftEmailWasRemoved(email.getSubject());
        return true;
    }

    public boolean removeEmails() {
        SentFolderPage sentFolderPage = new HomePage().openSentFolderPage()
                .chooseAllSentEmails()
                .removeEmails()
                .confirmRemoveEmails();

        assertTrue(sentFolderPage.getEmptyEmailList().isDisplayed(),"Sent email list is not empty.");
        return true;
    }
}
