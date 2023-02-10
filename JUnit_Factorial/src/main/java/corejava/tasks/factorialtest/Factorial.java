package corejava.tasks.factorialtest;

import java.math.BigInteger;

public class Factorial {
    public String factorial(String n) {
        int value;
        try {
            value = Integer.parseInt(n);
            if (value < 0) { throw new NumberFormatException(); }
        } catch (NumberFormatException exc) {
            throw new IllegalArgumentException(exc);
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= value; ++i) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        System.out.println(factorial.factorial("10000"));
    }
}
