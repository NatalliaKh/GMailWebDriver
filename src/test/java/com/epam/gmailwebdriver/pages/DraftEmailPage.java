package com.epam.gmailwebdriver.pages;

import com.epam.gmailwebdriver.reporting.MyLogger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DraftEmailPage extends HomePage {

    @FindBy(name = "to")
    WebElement emailReceiverField;

    @FindBy(xpath = "//div[@class='oL aDm az9']/child::span")
    WebElement emailReceiverAddress;

    @FindBy(name = "subjectbox")
    WebElement emailSubjectField;

    @FindBy(xpath = "//div[@aria-label='Message Body']")
    WebElement emailBodyField;

    @FindBy(xpath = "//img[@aria-label='Save & Close']")
    WebElement saveToDraft;

    @FindBy(xpath = "//div[text()='Send' and @role='button']")
    WebElement sendEmail;

    @FindBy(xpath = "//div[@aria-label='Discard draft' and @role='button']")
    WebElement removeEmail;

    protected DraftEmailPage() {
        super();
    }

    public DraftEmailPage fillEmailReceiver(String receiverAddress) {
        waitForElementVisible(emailReceiverField);
        MyLogger.info("Fill Email receiver '" + receiverAddress);
        emailReceiverField.sendKeys(receiverAddress);
        return this;
    }

    public DraftEmailPage fillEmailSubject(String subject) {
        waitForElementVisible(emailSubjectField);
        MyLogger.info("Fill Email subject '" + subject);
        emailSubjectField.sendKeys(subject);
        return this;
    }

    public DraftEmailPage fillEmailBody(String body) {
        waitForElementVisible(emailBodyField);
        MyLogger.info("Fill Email body '" + body);
        emailBodyField.sendKeys(body);
        return this;
    }

    public HomePage saveToDraft() {
        waitForElementVisible(saveToDraft);
        MyLogger.info("Save to draft ");
        saveToDraft.click();
        return this;
    }

    public String getEmailReceiverAddress() {
        waitForElementVisible(emailReceiverAddress);
        MyLogger.info("Get Email receiver address");
        return emailReceiverAddress.getText();
    }

    public String getEmailSubject() {
        waitForElementVisible(emailSubjectField);
        MyLogger.info("Get Email subject");
        return emailSubjectField.getAttribute("value");
    }

    public String getEmailBody() {
        waitForElementVisible(emailBodyField);
        MyLogger.info("Get Email body");
        return emailBodyField.getText();
    }

    public HomePage sendEmail() {
        waitForElementVisible(sendEmail);
        MyLogger.info("Send Email");
        sendEmail.click();
        return new HomePage();
    }

    public HomePage removeEmail() {
        waitForElementVisible(removeEmail);
        MyLogger.info("Remove Email");
        removeEmail.click();
        return new HomePage();
    }
}
