package com.epam.gmailwebdriver.stepdefs;

import com.epam.gmailwebdriver.drivermanagers.WebDriverSingleton;
import com.epam.gmailwebdriver.service.MailService;
import cucumber.annotation.After;
import cucumber.annotation.Before;

public class ScenarioHooks {

    MailService mailService = new MailService();

    @Before
    public void beforeScenario(){
    }

    @After
    public void afterScenario(){
        mailService.logout();
        WebDriverSingleton.kill();
    }
}
