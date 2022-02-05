Todd's Promotion Engine
=
This is a work in progress for a company whose name shall not be mentioned.

Choices
-
* Money (prices) should not be computed as an integer due to rounding issues. Nor should money be computed as a double or
  float as decimal point inaccuracies are guaranteed. Storing money as a BigDecimal, while a syntactic pain, is a great
  and safe way to compute monetary values.

Assumptions
-
* The number of items will not exceed the maximum value of an integer (2147483647)

Future Improvements
-
* Replace SKU's being stored as a String with a concrete class (Sku is a good name).
    * because passing Strings around long term is a bad idea, strict typing prevents future bugs
