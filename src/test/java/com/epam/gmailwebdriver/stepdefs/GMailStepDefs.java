package com.epam.gmailwebdriver.stepdefs;

import com.epam.gmailwebdriver.bo.Email;
import com.epam.gmailwebdriver.bo.User;
import com.epam.gmailwebdriver.service.MailService;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


import java.util.Map;

public class GMailStepDefs {

    private static final String SEND_TO_EMAIL = "natallia_khudzinskaya@epam.com";
    private static final String EMAIL_BODY = "Test";

    private MailService mailService;
    private User user;
    private Email email;

    @Given("^I logged in Gmail with parameters:$")
    public void iLoggedInGmailWithParameters(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps(String.class, String.class).get(0);
        mailService = new MailService();
        user = new User(data.get("username"), data.get("password"));
        mailService.loginToMail(user);
    }

    @When("I create draft email with ([\\w ]+)")
    public void iCreateDraftEmailWithSubject(String subject) {
        email = new Email(SEND_TO_EMAIL, subject, EMAIL_BODY);
        mailService.createDraftEmail(email);
    }

    @And("I check that draft email exists")
    public void iCheckThatDraftEmailWithSubjectExists() {
        mailService.checkDraftMailExists(email);
    }

    @Then("^I send email$")
    public void iSendEmailWithSubject() {
        mailService.sendEmail(email);
    }

    @Then("^I remove email$")
    public void iRemoveEmail() {
        mailService.removeEmail(email);
    }

    @Then("^I remove all sent emails$")
    public void iRemoveAllSentEmails() {
        mailService.removeEmails();
    }
}



