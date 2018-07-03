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

public class WebDriverGMail {

    private static final int POLLING_TIME = 3;
    private static final int USER_TIMEOUT = 30;
    private static final String DRAFT_FOLDER_URL = "https://mail.google.com/mail/#drafts";
    private static final String SENT_FOLDER_URL = "https://mail.google.com/mail/#sent";
    private WebDriver driver;
    private String emailSubject;
    private String emailTo;
    private String emailBody;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "d:\\_webdriver\\chromedriver\\chromedriver.exe");
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

    private WebElement testEnterEmail() {
        WebElement gmailLogin = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector("input#identifierId.whsOnd.zHQkBf"));
            }
        });
        Assert.assertTrue(gmailLogin.isDisplayed(), "Login field is not found.");
        WebElement gmailLoginNextButton = (new WebDriverWait (driver, 10)).until(new ExpectedCondition<WebElement>() {

            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector("div#identifierNext"));
            }
        });
        Assert.assertTrue(gmailLoginNextButton.isDisplayed(), "Next button on login page is not found.");
        gmailLogin.sendKeys("natallia.khudzinskaya");
        gmailLoginNextButton.click();

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(USER_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement gmailPassword = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.name("password"));
            }
        });
        Assert.assertTrue(gmailPassword.isDisplayed(), "Password page is not found.");
        return gmailPassword;
    }

    private WebElement testPasswordEnter(WebElement gmailPassword) {
        WebElement gmailPasswordnNextButton = (new WebDriverWait (driver, 10)).until(new ExpectedCondition<WebElement>() {

            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.id("passwordNext"));
            }
        });
        Assert.assertTrue(gmailPasswordnNextButton.isDisplayed(), "Next button on the password page is not found.");
        gmailPassword.sendKeys("webdriver123");
        gmailPasswordnNextButton.click();
        Wait<WebDriver> wait1 = new FluentWait<WebDriver>(driver)
                .withTimeout(USER_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(POLLING_TIME, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        WebElement createEmail = wait1.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//div[@class='T-I J-J5-Ji T-I-KE L3']"));
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
                return webDriver.findElement(By.name("to"));
            }
        });
        Assert.assertTrue(toField.isDisplayed(), "'To' field is not found.");
        emailTo = "natallia_khudzinskaya@epam.com";
        toField.sendKeys(emailTo);
        WebElement subjectField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.name("subjectbox"));
            }
        });
        Assert.assertTrue(subjectField.isDisplayed(), "'Subject' field is not found.");
        emailSubject = UUID.randomUUID().toString();
        subjectField.sendKeys(emailSubject);

        WebElement bodyField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//div[@class='Am Al editable LW-avf']"));
            }
        });
        Assert.assertTrue(bodyField.isDisplayed(), "'Body' field is not found.");
        emailBody = "Test";
        bodyField.sendKeys(emailBody);
    }

    private void testSaveToDraft() {
        WebElement closeButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//img[@class='Ha']"));
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
                return webDriver.findElement(By.xpath("//a[@href='" + folder + "']"));
            }
        });
        Assert.assertTrue(draftFolderButton.isDisplayed(), "Folder is not found.");
        draftFolderButton.click();

        try {
            WebElement draftEmail = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
                @Nullable
                public WebElement apply(@Nullable WebDriver webDriver) {
                    return webDriver.findElement(By.xpath("//span[@class='bog' and text()='" + emailSubject + "']"));
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
                return webDriver.findElement(By.xpath("//span[@email='" + emailTo + "' and not(@class)]"));
            }
        });
        Assert.assertTrue(toField.isDisplayed(), "To address is not correct.");

        WebElement subjectField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.name("subjectbox"));
            }
        });
        Assert.assertTrue(subjectField.isDisplayed(), "Email subject is not found.");
        Assert.assertEquals(subjectField.getAttribute("value"), emailSubject, "Email subject is not correct.");
        WebElement bodyField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//div[@class='Am Al editable LW-avf']"));
            }
        });
        Assert.assertTrue(bodyField.isDisplayed(), "Email body is not found.");
        Assert.assertEquals(bodyField.getText(), emailBody, "Email body is not correct.");

    }

    private void testSendEmail() {
        WebElement sendButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//div[@class='T-I J-J5-Ji aoO T-I-atl L3' and @role='button']"));
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
                return webDriver.findElement(By.xpath("//a[@class='gb_b gb_ib gb_R']"));
            }
        });
        Assert.assertTrue(profileButton.isDisplayed(), "Profile button is not found.");
        profileButton.click();

        WebElement logoutButton = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.xpath("//a[@class='gb_Ea gb_1f gb_8f gb_Oe gb_Jb']"));
            }
        });
        Assert.assertTrue(logoutButton.isDisplayed(), "Logout button is not found.");
        logoutButton.click();

        WebElement passwordField = (new WebDriverWait(driver, 10)).until(new ExpectedCondition<WebElement>() {
            @Nullable
            public WebElement apply(@Nullable WebDriver webDriver) {
                return webDriver.findElement(By.cssSelector("div#password"));
            }
        });
        Assert.assertTrue(passwordField.isDisplayed(), "User was not logout.");

    }
    @Test
    public void testGmailOperations() {

        driver.get("https://gmail.com");
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
}
