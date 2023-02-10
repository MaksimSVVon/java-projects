package corejava.tasks.factorialtest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FactorialBadInputTesting {

    Factorial factorial = new Factorial();

    @Test
    void testNullInput(){
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial(null));
    }

    @Test
    void testNegativeInput(){
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("-1"));
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("-123423"));
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("-12.3"));
    }

    @Test
    void testFractionalInput(){
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("42.42"));
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("0.0"));
    }

    @Test
    void testNonDigitalInput(){
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("02340--23-23---l"));
        assertThrows(IllegalArgumentException.class, () -> factorial.factorial("awesome_test"));
    }


}
