Feature: Managing Training Data

  Scenario: Posting a Training
    Given a trainee with username "trainee1" exists
    And a trainer with username "trainer1" exists
    When the trainer "trainer1" creates a training session for trainee "trainee1"
    Then the training session should be successfully created

  Scenario: Deleting a Training
    Given a training session with ID 1 exists
    When the trainer requests to delete the training session with id 1
    Then the training session with ID 1 should be deleted
