Feature: Trainer Management

  Scenario: Posting a new trainer
    Given I have the following trainer information: "Azimjon" "Alijonov" "1"
    When I post the trainer information
    Then the trainer is successfully created

  Scenario: Getting trainer information
    Given I have the trainer username "Azimjon.Alijonov"
    When I get trainer information
    Then I receive the trainer details

  Scenario: Getting trainers with specializations
    Given I have the trainer username "Azimjon.Alijonov"
    When I get trainers with specializations
    Then I receive a list of trainers with specializations

  Scenario: Activating or deactivating a trainer
    Given I have the trainer username "Azimjon.Alijonov"
    When I activate or deactivate the trainer
    Then the trainer status is successfully changed
