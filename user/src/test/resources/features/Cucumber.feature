Feature: User Management

  Scenario: Create a new user
    Given the user service is running
    When I create a user with name "John Doe" and email "john4.doe@example.com"
    Then the user should be created successfully
    Then I get the details of the user with id 1
