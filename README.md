# Design Doc

## 1. Problem Statement
Implement the code for a checkout system that handles pricing schemes such as “apples cost 50 cents, three apples cost $1.30.”

Implement the code for a supermarket checkout that calculates the total price of a number of items. In a normal supermarket, things are identified using Stock Keeping Units, or SKUs. In our store, we’ll use individual letters of the alphabet (A, B, C, and so on). Our goods are priced individually. In addition, some items are multipriced: buy n of them, and they’ll cost you y cents. For example, item ‘A’ might cost 50 cents individually, but this week we have a special offer: buy three ‘A’s and they’ll cost you $1.30. In fact this week’s prices are:

**Example**:
```
  Item   Unit      Special
         Price     Price
  --------------------------
    A     50       3 for 130
    B     30       2 for 45
    C     20
    D     15
```
Our checkout accepts items in any order, so that if we scan a B, an A, and another B, we’ll recognize the two B’s and price them at 45 (for a total price so far of 95). Because the pricing changes frequently, we need to be able to pass in a set of pricing rules each time we start handling a checkout transaction.

```
Scan: "A"    -> 50
Scan: "AB"   -> 80
Scan: "BAB"  -> 95
```
The interface to the checkout should look like:
```
co = CheckOut.new(pricing_rules)
co.scan(item)
co.scan(item)
    :    :
price = co.total
```


## 2. Requirements

### Functional Requirements
- Scan any number of items in any order (e.g., "ABBA").

- Need to be able to pass in a set of pricing rules each time we start handling a checkout transaction.

- Apply standard and special pricing rules correctly.

- Return correct total at any point.

### Non-Functional Requirements

- Easy to extend with new rule types. 

- Easy to test and maintain (via TDD).

- Decoupled pricing logic.

## 3. Possible Solutions

## 4. Final Design

## 5. Implementation

## 6. Testing

## 7. Future Extension

## 8. Design Principles Used