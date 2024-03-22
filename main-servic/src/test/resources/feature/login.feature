Feature: User Login

  Scenario: Successful login
    Given a user with username "azimjon.alijonov1" and password "ufGOVCN8Rs"
    When the user tries to login with username "azimjon.alijonov1" and password "ufGOVCN8Rs"
    Then the user should receive a JWT token

  Scenario: User does not exist
    Given no user with username "nonexistentuser"
    When the user tries to login with wrongusername "nonexistentuser" and password "password"
    Then the user should receive an error message indicating the account does not exist
