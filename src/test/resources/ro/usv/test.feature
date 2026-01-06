Feature:Test Feature

  Scenario Outline: Add two numbers generically
    Given I have entered <a> into the calculator
    And I have entered <b> into the calculator
    When I press add
    Then the result should be <result>

    Examples:
      | a  | b  | result |
      | 5  | 7  | 12     |
      | 2  | 3  | 5      |
      | 10 | 15 | 25     |

