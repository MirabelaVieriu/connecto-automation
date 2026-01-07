Feature: Signup feature

  Background:
    Given I open the page "http://localhost:3000/sign-up"

  # Positive scenario
  Scenario: User signs up with valid data
    When I fill the input "name" with "Maria Vieriu"
    And I fill the input "email" with "<random_email>"
    And I fill the input "password" with "parola123"
    And I fill the input "confirmPassword" with "parola123"
    And I click on the "Înregistrare" button
    Then I should be redirected to "/sign-up"

  # Negative scenarios
  Scenario: Signup with empty name
    When I fill the input "name" with ""
    And I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I fill the input "password" with "parola123"
    And I fill the input "confirmPassword" with "parola123"
    And I click on the "Înregistrare" button
    Then the register form should not be submitted

  Scenario: Signup with password mismatch
    When I fill the input "name" with "Maria Vieriu"
    And I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I fill the input "password" with "parola123"
    And I fill the input "confirmPassword" with "wrongPassword"
    And I click on the "Înregistrare" button
    Then I should see an error message "Parolele nu coincid"

  Scenario: Signup with short password
    When I fill the input "name" with "Maria Vieriu"
    And I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I fill the input "password" with "short"
    And I fill the input "confirmPassword" with "short"
    And I click on the "Înregistrare" button
    Then I should see an error message "Parola este prea scurtă"