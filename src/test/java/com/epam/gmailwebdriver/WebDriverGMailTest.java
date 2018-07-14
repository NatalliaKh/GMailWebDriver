package com.epam.gmailwebdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WebDriverGMailTest {

    private static final int POLLING_TIME = 3;
    private static final int USER_TIMEOUT = 30;
    private static final String DRAFT_FOLDER_URL = "https://mail.google.com/mail/#drafts";
    private static final String SENT_FOLDER_URL = "https://mail.google.com/mail/#sent";
    private static final String GMAIL_LINK = "https://gmail.com";
    private static final String LOGIN_SELECTOR = "input#identifierId.whsOnd.zHQkBf";
    private static final String LOGIN_NEXT_BUTTON_SELECTOR = "div#identifierNext";
    private static final String LOGIN_USER_NAME = "natallia.khudzinskaya";
    private static final String LOGIN_PASSWORD_SELECTOR = "password";
    private static final String PASSWORD_NEXT_BUTTON_SELECTOR = "passwordNext";
    private static final String USER_PASSWORD = "webdriver123";
    private static final String CREATE_EMAIL_SELECTOR = "//div[@class='T-I J-J5-Ji T-I-KE L3']";
    private static final String TO_FIELD_SELECTOR = "to";
    private static final String SEND_TO_EMAIL = "natallia_khudzinskaya@epam.com";
    private static final String SUBJECT_FIELD_LOCATOR = "subjectbox";
    private static final String BODY_FIELD_LOCATOR = "//div[@class='Am Al editable LW-avf']";
    private static final String EMAIL_BODY = "Test";
    private static final String CLOSE_BUTTON_LOCATOR = "//img[@class='Ha']";
    private static final String DRAFT_FOLDER_BUTTON_LOCATOR_FORMAT = "//a[@href='%s']";
    private static final String DRAFT_EMAIL_LOCATOR_FORMAT = "//span[@class='bog' and text()='%s']";
    private static final String TO_FIELD_LOCATOR_FORMAT = "//span[@email='%s' and not(@class)]";
    private static final String SEND_BUTTON_LOCATOR = "//div[@class='T-I J-J5-Ji aoO T-I-atl L3' and @role='button']";
    private static final String PROFILE_BUTTON_LOCATOR = "//a[@class='gb_b gb_db gb_R']";
    private static final String LOGOUT_BUTTON_LOCATOR = "//a[@class='gb_za gb_Zf gb_6f gb_Ke gb_Eb']";
    private static final String PASSWORD_FIELD_LOCATOR = "div#password";


    private WebDriver driver;
    private String emailSubject;
    private String emailTo;
    private String emailBody;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        emailSubject = null;
        emailTo = null;
        emailBody = null;
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void testGmailOperations() {

        driver.get(GMAIL_LINK);
        WebElement gmailPassword = testEnterEmail();
        WebElement createEmail = testPasswordEnter(gmailPassword);
        testCreateEmail(createEmail);
        testSaveToDraft();
        testEmailFields();
        testSendEmail();
        testDraftEmailWasRemoved();
        testSentEmailWasAdded();
        testGmailLogout();
    }

    private WebElement testEnterEmail() {
        WebElement gmailLogin = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector(LOGIN_SELECTOR));
            }
        });
        Assert.assertTrue(gmailLogin.isDisplayed(), "Login field is not found.");
        WebElement gmailLoginNextButton = (new WebDriverWait (driver, 10)).until(new ExpectedCondition<WebElement>() {

            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector(LOGIN_NEXT_BUTTON_SELECTOR));
            }
        });
        Assert.assertTrue(gmailLoginNextButton.isDisplayed(), "Next button on login page is not found.");
        gmailLogin.sendKeys(LOGIN_USER_NAME);
        gmailLoginNextButton.click();

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(USER_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement gmailPassword = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.name(LOGIN_PASSWORD_SELECTOR));
            }
        });
        Assert.assertTrue(gmailPassword.isDisplayed(), "Password page is not found.");
        return gmailPassword;
    }

    private WebElement testPasswordEnter(WebElement gmailPassword) {
        WebElement gmailPasswordnNextButton = (new WebDriverWait (driver, 10)).until(new ExpectedCondition<WebElement>() {

            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.id(PASSWORD_NEXT_BUTTON_SELECTOR));
            }
        });
        Assert.assertTrue(gmailPasswordnNextButton.isDisplayed(), "Next button on the password page is not found.");
        gmailPassword.sendKeys(USER_PASSWORD);
        gmailPasswordnNextButton.click();
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
                .withTimeout(USER_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement createEmail = wait1.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.xpath(CREATE_EMAIL_SELECTOR));
            }
        });
        Assert.assertTrue(createEmail.isDisplayed(), "Login is failed.");
        return createEmail;
    }
    private void testCreateEmail(WebElement createEmail) {
        createEmail.click();
        WebElement toField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.name(TO_FIELD_SELECTOR));
            }
        });
        Assert.assertTrue(toField.isDisplayed(), "'To' field is not found.");
        emailTo = SEND_TO_EMAIL;
        toField.sendKeys(emailTo);
        WebElement subjectField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.name(SUBJECT_FIELD_LOCATOR));
            }
        });
        Assert.assertTrue(subjectField.isDisplayed(), "'Subject' field is not found.");
        emailSubject = UUID.randomUUID().toString();
        subjectField.sendKeys(emailSubject);

        WebElement bodyField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(BODY_FIELD_LOCATOR));
            }
        });
        Assert.assertTrue(bodyField.isDisplayed(), "'Body' field is not found.");
        emailBody = EMAIL_BODY;
        bodyField.sendKeys(emailBody);
    }

    private void testSaveToDraft() {
        WebElement closeButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(CLOSE_BUTTON_LOCATOR));
            }
        });
        Assert.assertTrue(closeButton.isDisplayed(), "Close button is not found.");
        closeButton.click();

        WebElement draftEmail = findEmail(DRAFT_FOLDER_URL);
        Assert.assertTrue(draftEmail.isDisplayed(), "Draft email is not found.");
        draftEmail.click();
    }

    private void testDraftEmailWasRemoved() {
        WebElement draftEmail = findEmail(DRAFT_FOLDER_URL);
        Assert.assertTrue(draftEmail == null, "Draft email was not sent.");
    }

    private void testSentEmailWasAdded() {
        WebElement sentEmail = findEmail(SENT_FOLDER_URL);
        Assert.assertTrue(sentEmail.isDisplayed(), "Sent email is not added.");
    }

    private WebElement findEmail(String folder) {
        WebElement draftFolderButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(String.format(DRAFT_FOLDER_BUTTON_LOCATOR_FORMAT, folder)));
            }
        });
        Assert.assertTrue(draftFolderButton.isDisplayed(), "Folder is not found.");
        draftFolderButton.click();

        try {
            WebElement draftEmail = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
                @Nullable
                public WebElement apply(@Nullable WebDriver webDriver) {
                    return webDriver.findElement(By.xpath(String.format(DRAFT_EMAIL_LOCATOR_FORMAT, emailSubject)));
                }
            });
            return draftEmail;
        } catch (TimeoutException e) {
            return null;
        }
    }

    private void testEmailFields() {
        WebElement toField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(String.format(TO_FIELD_LOCATOR_FORMAT, emailTo)));
            }
        });
        Assert.assertTrue(toField.isDisplayed(), "To address is not correct.");

        WebElement subjectField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.name(SUBJECT_FIELD_LOCATOR));
            }
        });
        Assert.assertTrue(subjectField.isDisplayed(), "Email subject is not found.");
        Assert.assertEquals(subjectField.getAttribute("value"), emailSubject, "Email subject is not correct.");
        WebElement bodyField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(BODY_FIELD_LOCATOR));
            }
        });
        Assert.assertTrue(bodyField.isDisplayed(), "Email body is not found.");
        Assert.assertEquals(bodyField.getText(), emailBody, "Email body is not correct.");

    }

    private void testSendEmail() {
        WebElement sendButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(SEND_BUTTON_LOCATOR));
            }
        });
        Assert.assertTrue(sendButton.isDisplayed(), "Send button is not found.");
        sendButton.click();
        driver.navigate().refresh();
    }
    private void testGmailLogout() {
        WebElement profileButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(PROFILE_BUTTON_LOCATOR));
            }
        });
        Assert.assertTrue(profileButton.isDisplayed(), "Profile button is not found.");
        profileButton.click();

        WebElement logoutButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath(LOGOUT_BUTTON_LOCATOR));
            }
        });
        Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not found.");
        logoutButton.click();

        WebElement passwordField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector(PASSWORD_FIELD_LOCATOR));
            }
        });
        Assert.assertTrue(passwordField.isDisplayed(), "User was not logout.");

    }

}
