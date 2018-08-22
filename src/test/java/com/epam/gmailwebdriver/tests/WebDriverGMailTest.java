package com.epam.gmailwebdriver.tests;

import com.epam.gmailwebdriver.bo.Email;
import com.epam.gmailwebdriver.bo.User;
import com.epam.gmailwebdriver.pages.DraftEmailPage;
import com.epam.gmailwebdriver.pages.HomePage;
import com.epam.gmailwebdriver.pages.LoginPage;
import com.epam.gmailwebdriver.pages.SentFolderPage;
import com.epam.gmailwebdriver.drivermanagers.WebDriverSingleton;
import com.epam.gmailwebdriver.service.MailService;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.UUID;

import static org.testng.Assert.assertTrue;

public class WebDriverGMailTest {

    private static final String SEND_TO_EMAIL = "natallia_khudzinskaya@epam.com";
    private static final String EMAIL_BODY = "Test";

    private User user;
    private MailService mailService;
    private Email email;

    @BeforeClass
    public void beforeClass() {
        user = new User("natallia.khudzinskaya", "webdriver123");
        mailService = new MailService();
        email = new Email(SEND_TO_EMAIL, UUID.randomUUID().toString(), EMAIL_BODY);
    }

    @AfterMethod
    public void after() {
        WebDriverSingleton.kill();
    }

    @Test
    public void testSendDraftEmailScenario() {

        mailService.loginToMail(user);
        mailService.createDraftEmail(email);
        assertTrue(mailService.checkDraftMailExists(email));
        assertTrue(mailService.sendEmail(email));
        mailService.logout();
    }

    @Test
    public void testRemoveDraftEmailScenario() {

        mailService.loginToMail(user);
        mailService.createDraftEmail(email);
        assertTrue(mailService.checkDraftMailExists(email));
        assertTrue(mailService.removeEmail(email));
        mailService.logout();
    }

    @Test(dependsOnMethods = "testSendDraftEmailScenario")
    public void testRemoveAllSentEmailsScenario() {
        mailService.loginToMail(user);
        assertTrue(mailService.removeEmails());
        mailService.logout();
    }
}
