# Test Factorial

This exercise is about getting familiar with unit testing and JUnit 5 approach in particular.


1. Design and code a `factorial` method in
the [Factorial](src/main/java/corejava/tasks/factorialtest/Factorial.java) class. Here are some details:

- the method takes a String as a parameter, transforms it to an integer value and counts its factorial.
- The method returns a result as a String.
- String parameter `n` must represent a non-negative integer number. If it does not, throw an IllegalArgumentException.

2. Complete the test classes:

- [FactorialBadInputTesting](src/main/java/corejava/tasks/factorialtest/FactorialBadInputTesting.java)\
  There are four empty methods that you must complete:
    - `testNullInput` - test a null input cases
    - `testNegativeInput` - test a negative number input cases
    - `testFractionalInput` - test a fractional cases
    - `testNonDigitalInput` - test a non-digit cases
- [FactorialCsvParametrizedTesting](src/main/java/corejava/tasks/factorialtest/FactorialCsvParametrizedTesting.java)\
  it is a parameterized test, that takes arguments from the [csvCases.csv](src/main/resources/csvCases.csv) file. Do
  not change the csv file, only implement the method.
- [FactorialMethodSourceParametrizedTesting](src/main/java/corejava/tasks/factorialtest/FactorialMethodSourceParametrizedTesting.java)\
  it is a parameterized test, that takes arguments from the `testCases` method. You must complete the test method and
  introduce the `testCases` method, that provides following cases:
    - "1", "1"
    - "2", "2"
    - "5", "120"
- [FactorialRegularInputTesting](src/main/java/corejava/tasks/factorialtest/FactorialRegularInputTesting.java)\
  it is a test class where you can add regular test cases. Consider covering cases that are not present in other test
  classes.

To pass the exercise, your tests must correctly detect flaws of some other implementations. 

Your solution method must pass your tests while other implementation must fail your tests in some cases.

Hint: [Factorial reference](https://en.wikipedia.org/wiki/Factorial)
