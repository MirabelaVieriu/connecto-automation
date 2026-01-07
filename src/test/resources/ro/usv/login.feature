Feature: Login feature

  Background:
    Given I open the page "http://localhost:3000/sign-in"

  # Positive scenario
  Scenario: User fills login form with valid credentials
    When I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I fill the input "password" with "parola123"
    And I click on the "Autentificare" button
    Then I should be redirected to "/participant"

  # Negative scenarios

  Scenario: Login with invalid email
    When I fill the input "email" with "invalid@student.usv.ro"
    And I fill the input "password" with "parola123"
    And I click on the "Autentificare" button
    Then I should see an error message "Date incorecte"

  Scenario: Login with invalid password
    When I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I fill the input "password" with "wrongPassword"
    And I click on the "Autentificare" button
    Then I should see an error message "Date incorecte"

  Scenario: Login with empty password
    When I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I click on the "Autentificare" button
    Then the login form should not be submitted
