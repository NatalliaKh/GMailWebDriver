package com.epam.gmailwebdriver;

import com.epam.gmailwebdriver.pages.DraftEmailPage;
import com.epam.gmailwebdriver.pages.HomePage;
import com.epam.gmailwebdriver.pages.LoginPage;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

public class WebDriverGMailTest {

    private static final String USER_NAME = "natallia.khudzinskaya";
    private static final String PASSWORD = "webdriver123";
    private static final String SEND_TO_EMAIL = "natallia_khudzinskaya@epam.com";
    private static final String EMAIL_BODY = "Test";

    private WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        driver = new ChromeDriver(options);
    }

    @AfterClass
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
}
