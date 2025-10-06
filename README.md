# CSC 207: Text Editor

**Author**: _(TODO: fill me in)_

## Resources Used

+ _(TODO: fill me in)_
+ ...
+ ...

## Simple String Buffer Efficiency Analysis
+ The relevant inputs to the method is the length of the string.
+ The critical operation is string.substring - each letter that is copied.
+ For insert:
    1. When we're adding to the end of the string, we have complexity f(n) = n + 1, since we're copying all elements of the string and then adding one more.
    2. When we're adding to the beginning of the string, we have complexity f(n) = 2n+1, since we're moving the cursor all the way to the left, then copying all the elements of the string, and then adding one more.
+ The insert function has complexity O(n).
+ This implementation is inefficient, because we cannot modify the existing string since strings are immutable in Java. I have to create new string objects and copy the characters into them each time I want to add a character.

## Changelog

_(TODO: fill me in with a log of your committed changes)_
