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

## 3. Final Design

### Classes and Responsibilities

- **CheckoutEngine**

  - Scans items.
  - Tracks quantities (`Map<String, Integer>`).
  - Delegates total price calculation to pricing rules (`Map<String, PricingRule>`).

- **PricingRule (Interface)**

  - `int calculatePrice(int quantity);`
  - Different implementations for different pricing styles.

- **RegularPricingRule**

  - Basic per unit price (no discount).

- **BuyNForYPricingRule**

  - Buy N for Y discounts (e.g., 3 for 130).

### Key Design Choice

**Strategy Pattern**: CheckoutEngine delegates pricing to PricingRule classes, following **Open/Closed Principle**, new rules don’t require changing Checkout.  
**Maps** for fast itemCode to rule and itemCode to quantity lookups.

## 4. Implementation

- **CheckoutEngine**

  - `scan(String itemCode)`: Increment quantity in map.
  - `getTotal()`: Iterate items in itemCodeToQuantityMap and use pricing rules to calculate total.

- **PricingRules**
  - `RegularPricingRule`: simple `quantity * unitPrice`.
  - `BuyNForYPricingRule`: first determine how many complete bundle discounts apply and how many leftover items are charged at unit price.

## 5. Testing

I used a **TDD approach**:

- Wrote failing tests before implementation.
- Implemented minimal code to pass tests.
- Refactored for clean, small methods.

Covered:
| Test Category | What It Verifies |
|----------------------------------|------------------------------------------------------|
| **Basic cases** | Single items, multiple items, no discounts |
| **BuyNForYPricingRule** | Discounts apply correctly for A (3 for 130) and B (2 for 45) |
| **Incremental scanning** | Total updates correctly as items are scanned one by one |
| **Complex mixed items** | Discounts + regular pricing work together |
| **Edge cases** | Empty cart, invalid itemCodes, mixed valid/invalid input |

## 6. Future Improvements

- **Item Abstraction**:  
  If richer product data is needed (e.g., weight, name, category), introduce an `Item` object.

- **Logging & Monitoring**:  
  Add logs for scanned items and totals to help with debugging and support CI/CD pipelines.

- **BigDecimal for Currency**:  
  Currently, prices and totals are handled as integers (cents) to avoid floating point errors.  
  In real world currency systems (dollars, decimal fractions), switch to `BigDecimal` to ensure precise calculations.

- **Performance Optimization**:  
  For very large item lists that is not just letters, consider caching strategies.

## 7. Design Principles Used

- **Single Responsibility Principle**:  
  Each class has one clear responsibility.
- **Open/Closed Principle**:  
  New pricing rules extend the system without modifying `Checkout`.
- **Strategy Pattern**:  
  Flexible and decoupled pricing logic.
- **Clean Code**:
  - Descriptive names.
  - Small methods.
  - No duplication.
