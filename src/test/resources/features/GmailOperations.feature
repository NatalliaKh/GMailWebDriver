Feature: Gmail operations

  Background:
    Given I logged in Gmail with parameters:
      | username              | password     |
      | natallia.khudzinskaya | webdriver123 |

  Scenario Outline: Send draft email

    When I create draft email with <subject>
    And I check that draft email exists
    Then I send email

    Examples:
      | subject    |
      | something  |

  Scenario Outline: Remove draft email

    When I create draft email with <subject>
    And I check that draft email exists
    Then I remove email

    Examples:
      | subject         |
      | email_to_remove |

  Scenario:

    When I create draft email with Test1
    And I check that draft email exists
    Then I remove all sent emails

