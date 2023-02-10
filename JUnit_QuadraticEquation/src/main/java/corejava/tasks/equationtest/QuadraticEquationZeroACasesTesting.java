package corejava.tasks.equationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class QuadraticEquationZeroACasesTesting {
    protected QuadraticEquation quadraticEquation = new QuadraticEquation();
    private double a;
    private double b;
    private double c;

    public QuadraticEquationZeroACasesTesting(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Parameterized.Parameters
    public static Collection testCases() {
        return Arrays.asList(new Object[][] {
                { 0.0, 1.0, 2.0 },
                { 0.0, 100000.0, 2233244.0 },
                { 0.0, -32423431.0, 22434234.0 },
                { 0.0, -2323231.0, -222.0 }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroATest() {
        quadraticEquation.solve(a, b, c);
    }

}
