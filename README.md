# Todd's Promotion Engine
This package demonstrates a base set of classes that store items in a cart while applying an optional set of promotions.
The promotions offer discounts for an item or a collection of items. A cart total price is computed from the items 
(both unpromoted and promoted) in the cart.

### How to Extend/Use
* Extend BasePromotion to create different promotion types
* Implement IRepository to create different data stores. Be sure to add a new RepositoryType for the new implementation,
  and configure it in the RepositoryFactory.setRepositoryType method.

### Assumptions
* The number of items of each sku will not exceed the maximum value of an integer: 2147483647 (hence int's are used
  throughout the code for adding/removing items, getting counts, etc.)
* Items and Cart do not need to be thread safe. I assume that there is only one process that is adding/removing items.
  (It's obviously easy to make them thread safe, and there's a very small performance cost for doing so.) The biggest
  danger is calling the getTotalPrice method which caches the price once it is calculated. However if the Cart contents
  are modified while it is calculating the wrong price will be cached and returned for future calls to getTotalPrice.

### Choices
* Monetary values should not be stored/computed as integers due to rounding inaccuracies. Nor should money be computed 
  as a double or a float since decimal point inaccuracies are guaranteed (3.14 can be stored as 3.1399999 which, when
  accumulated with other things over time will introduce errors). Storing money as a BigDecimal, while a syntactic 
  pain, is a great and safe way to compute monetary values.

### Future Improvements
* Change SKU objects from Strings to a concrete class (Sku is a good class name). Passing Strings around long term is a bad 
  idea, strict typing prevents future potential bugs.
* Implement the DatabaseRepository linking this engine to real data (with a cache).
* Add decision logic (prioritization) for which promotions to apply to a cart. Presumably the promotion combination that
  gives the lowest cart cost would be preferred, or other schemes.
