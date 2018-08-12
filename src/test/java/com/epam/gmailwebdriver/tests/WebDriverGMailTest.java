package com.epam.gmailwebdriver.tests;

import com.epam.gmailwebdriver.bo.Email;
import com.epam.gmailwebdriver.bo.User;
import com.epam.gmailwebdriver.pages.DraftEmailPage;
import com.epam.gmailwebdriver.pages.HomePage;
import com.epam.gmailwebdriver.pages.LoginPage;
import com.epam.gmailwebdriver.pages.SentFolderPage;
import com.epam.gmailwebdriver.drivermanagers.WebDriverSingleton;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.UUID;

public class WebDriverGMailTest {

    private static final String SEND_TO_EMAIL = "natallia_khudzinskaya@epam.com";
    private static final String EMAIL_BODY = "Test";

    private User user;

    @BeforeMethod
    public void beforeClass() {
        user = new User("natallia.khudzinskaya", "webdriver123");
    }

    @AfterMethod
    public void afterClass() {
        WebDriverSingleton.kill();
    }

    @Test
    public void testSendDraftEmailScenario() {

        Email email = new Email(SEND_TO_EMAIL, UUID.randomUUID().toString(), EMAIL_BODY);
        HomePage homePage = new LoginPage()
                .open()
                .fillUsername(user.getUserName())
                .enterUsername()
                .fillPassword(user.getPassword())
                .enterPassword();
        homePage.openNewEmail()
                .fillEmail(email)
                .saveToDraft();
        DraftEmailPage draftEmailPage = homePage.openDraftFolderPage()
                .openDraftEmail(email.getSubject());

        Assert.assertEquals(draftEmailPage.getEmailReceiverAddress(), email.getReceiver(), "Email receiver address is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailSubject(), email.getSubject(), "Email subject is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailBody(), email.getBody(), "Email body is not correct.");

        draftEmailPage.sendEmail()
                .openDraftFolderPage()
                .checkDraftEmailWasRemoved(email.getSubject())
                .openSentFolderPage()
                .checkSentEmailWasAdded(email.getSubject())
                .openProfile()
                .logout();
    }

    @Test
    public void testRemoveDraftEmailScenario() {

        Email email = new Email(SEND_TO_EMAIL, UUID.randomUUID().toString(), EMAIL_BODY);
        HomePage homePage = new LoginPage()
                .open()
                .fillUsername(user.getUserName())
                .enterUsername()
                .fillPassword(user.getPassword())
                .enterPassword();
        homePage.openNewEmail()
                .fillEmail(email)
                .saveToDraft();
        DraftEmailPage draftEmailPage = homePage.openDraftFolderPage()
                .openDraftEmail(email.getSubject());

        Assert.assertEquals(draftEmailPage.getEmailReceiverAddress(), email.getReceiver(), "Email receiver address is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailSubject(), email.getSubject(), "Email subject is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailBody(), email.getBody(), "Email body is not correct.");

        draftEmailPage.removeEmail()
                .openDraftFolderPage()
                .checkDraftEmailWasRemoved(email.getSubject())
                .openProfile()
                .logout();
    }

    @Test(dependsOnMethods = "testSendDraftEmailScenario")
    public void testRemoveAllSentEmailsScenario() {
        HomePage homePage = new LoginPage()
                .open()
                .fillUsername(user.getUserName())
                .enterUsername()
                .fillPassword(user.getPassword())
                .enterPassword();
        SentFolderPage sentFolderPage = homePage.openSentFolderPage()
                .chooseAllSentEmails()
                .removeEmails()
                .confirmRemoveEmails();

        Assert.assertTrue(sentFolderPage.getEmptyEmailList().isDisplayed(),"Sent email list is not empty.");

        homePage.openProfile()
                .logout();
    }
}
