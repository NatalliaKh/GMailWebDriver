package com.epam.gmailwebdriver;

import com.epam.gmailwebdriver.pages.DraftEmailPage;
import com.epam.gmailwebdriver.pages.HomePage;
import com.epam.gmailwebdriver.pages.LoginPage;
import com.epam.gmailwebdriver.pages.SentFolderPage;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class WebDriverGMailTest {

    private static final String USER_NAME = "natallia.khudzinskaya";
    private static final String PASSWORD = "webdriver123";
    private static final String SEND_TO_EMAIL = "natallia_khudzinskaya@epam.com";
    private static final String EMAIL_BODY = "Test";

    private WebDriver driver;

    @BeforeMethod
    public void beforeClass() {
//        DesiredCapabilities capabilities = DesiredCapabilities.firefox(); // use firefox node
        DesiredCapabilities capabilities = DesiredCapabilities.chrome(); // use chrome node
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--lang=en");
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        try {
            driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void testSendDraftEmailScenario() {

        String emailTo = SEND_TO_EMAIL;
        String emailSubject = UUID.randomUUID().toString();
        String emailBody = EMAIL_BODY;

        HomePage homePage = new LoginPage(driver)
                .open()
                .fillUsername(USER_NAME)
                .enterUsername()
                .fillPassword(PASSWORD)
                .enterPassword();
        homePage.openNewEmail()
                .fillEmailReceiver(emailTo)
                .fillEmailSubject(emailSubject)
                .fillEmailBody(emailBody)
                .saveToDraft();
        DraftEmailPage draftEmailPage = homePage.openDraftFolderPage()
                .openDraftEmail(emailSubject);

        Assert.assertEquals(draftEmailPage.getEmailReceiverAddress(), emailTo, "Email receiver address is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailSubject(), emailSubject, "Email subject is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailBody(), emailBody, "Email body is not correct.");

        draftEmailPage.sendEmail()
                .openDraftFolderPage()
                .checkDraftEmailWasRemoved(emailSubject)
                .openSentFolderPage()
                .checkSentEmailWasAdded(emailSubject)
                .openProfile()
                .logout();
    }

    @Test
    public void testRemoveDraftEmailScenario() {
        String emailTo = SEND_TO_EMAIL;
        String emailSubject = UUID.randomUUID().toString();
        String emailBody = EMAIL_BODY;

        HomePage homePage = new LoginPage(driver)
                .open()
                .fillUsername(USER_NAME)
                .enterUsername()
                .fillPassword(PASSWORD)
                .enterPassword();
        homePage.openNewEmail()
                .fillEmailReceiver(emailTo)
                .fillEmailSubject(emailSubject)
                .fillEmailBody(emailBody)
                .saveToDraft();
        DraftEmailPage draftEmailPage = homePage.openDraftFolderPage()
                .openDraftEmail(emailSubject);

        Assert.assertEquals(draftEmailPage.getEmailReceiverAddress(), emailTo, "Email receiver address is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailSubject(), emailSubject, "Email subject is not correct.");
        Assert.assertEquals(draftEmailPage.getEmailBody(), emailBody, "Email body is not correct.");

        draftEmailPage.removeEmail()
                .openDraftFolderPage()
                .checkDraftEmailWasRemoved(emailSubject)
                .openProfile()
                .logout();
    }

    @Test(dependsOnMethods = "testSendDraftEmailScenario")
    public void testRemoveAllSentEmailsScenario() {
        HomePage homePage = new LoginPage(driver)
                .open()
                .fillUsername(USER_NAME)
                .enterUsername()
                .fillPassword(PASSWORD)
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
