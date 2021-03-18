Feature: Fixture data

  Scenario: Fixture data is loaded when first scenario is ran
    Given I have fixture data defined
    When I run this scenario
    Then the fixture data is loaded once

  Scenario: Fixture data is not loaded when the second scenario is ran
    Given I have fixture data defined
    When I run this scenario
    Then the fixture data is loaded once

  Scenario: Eagerly loaded fixture is loaded at the start without dependency injection
    Given I have eagerly loaded fixture data defined
    And the fixture data class is not declared as a constructor argument in the scenario steps
    When I run this scenario
    Then the eagerly loaded fixture data is loaded once

