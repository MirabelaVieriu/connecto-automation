Feature: Login feature

  Scenario: User fills login form with valid credentials
    Given I open the page "http://localhost:3000/sign-in"
    When I fill the input "email" with "maria.vieriu@student.usv.ro"
    And I fill the input "password" with "parola123"
    And I click on the "Autentificare" button
    Then I should be redirected to "/participant"
