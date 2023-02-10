package corejava.tasks.equationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class QuadraticEquationTwoRootsCasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();
    private double a;
    private double b;
    private double c;
    private String expected;

    public QuadraticEquationTwoRootsCasesTesting(double a, double b, double c, String expected) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.expected = Arrays.stream(expected.split(" "))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    @Parameterized.Parameters
    public static Collection testCases() {
        return Arrays.asList(new Object[][] {
                { 10.0, -69.0, 108.0, "4.5 2.4" },
                { 4.0, -13.0, 9.0, "1.0 2.25" },
                { 4.0, -19.0, 12.0, "4.0 0.75" },
                { 5.0, -176.0, -333, "37.0 -1.8" }
        });
    }

    @Test
    public void twoRootsCaseTest() {
        String result = Arrays.stream(quadraticEquation.solve(a, b, c).split(" "))
                .distinct()
                .sorted()
                .collect(Collectors.joining(" "));
        assertEquals(result, expected);
    }
}
