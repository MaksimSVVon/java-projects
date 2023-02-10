package corejava.tasks.factorialtest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorialRegularInputTesting {

    Factorial factorial = new Factorial();

    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("3", "6"),
                Arguments.of("4", "24"),
                Arguments.of("6", "720"),
                Arguments.of("7", "5040"),
                Arguments.of("8", "40320"),
                Arguments.of("9", "362880"),
                Arguments.of("10", "3628800"),
                Arguments.of("12", "479001600"),
                Arguments.of("15", "1307674368000"),
                Arguments.of("20", "2432902008176640000")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void regularCasesTest(String in, String expected) {
        assertEquals(factorial.factorial(in), expected);
    }
}
