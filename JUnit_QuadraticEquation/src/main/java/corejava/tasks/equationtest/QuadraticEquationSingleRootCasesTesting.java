package corejava.tasks.equationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class QuadraticEquationSingleRootCasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();
    private double a;
    private double b;
    private double c;
    private double expected;

    public QuadraticEquationSingleRootCasesTesting(double a, double b, double c, double expected) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection testCases() {
        return Arrays.asList(new Object[][] {
                { 1.0, 4.0, 4.0, -2.0 },
                { 2.0, 8.0, 8.0, -2.0 },
                { 12.0, -12.0, 3.0, 0.5 },
                { 36.0, 12.0, 1.0, -0.16666666666666666 }
        });
    }

    @Test
    public void singleRootTest() {
        assertEquals(quadraticEquation.solve(a, b, c), Double.toString(expected));
    }
}