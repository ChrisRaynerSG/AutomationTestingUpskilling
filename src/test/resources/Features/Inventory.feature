Feature: Inventory

  In order to buy items
  As a User logged into the webpage
  I should see the items available
  AND be able to add them to my cart

  Background:
    Given I am on the inventory page

  Scenario: See all items displayed on inventory page
    Then I should see 6 items

  Scenario: Adding an item to the cart updates the cart
    When I add an item to my cart
    Then I should see the cart updated
