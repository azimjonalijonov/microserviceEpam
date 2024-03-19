Feature: Retrieving Trainer Summary

  Scenario: Retrieving Trainer Summary
    Given a trainer with username "Azimjon.Alijonov" exists
    When a request is made to retrieve the trainer summary
    Then the trainer summary should be successfully retrieved
