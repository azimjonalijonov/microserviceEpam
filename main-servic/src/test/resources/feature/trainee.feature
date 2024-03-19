Feature: Trainee Management

  Scenario: Posting a new trainee
    Given I have the following trainee information firstname "azimjon" lastname "alijonov" address "123 Street"
    When I post the trainee information
    Then the trainee is successfully created

  Scenario: Getting trainee information
    Given I have the trainee username "azimjon.alijonov1"
    When I get trainee information
    Then I receive the trainee details

  Scenario: Updating trainee information
    Given I have the trainee username "azimjon.alijonov"
    And I update the trainee information with firstname "john"
    When I update the trainee information
    Then the trainee information is successfully updated

  Scenario: Deleting a trainee
    Given I have the trainee username "azimjon.alijonov"
    When I delete the trainee
    Then the trainee is successfully deleted
