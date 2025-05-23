# Design Doc

## 1. Problem Statement
Implement the code for a checkout system that handles pricing schemes such as “apples cost 50 cents, three apples cost $1.30.”

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

```
Scan: "A"    -> 50
Scan: "AB"   -> 80
Scan: "AAA"  -> 130
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