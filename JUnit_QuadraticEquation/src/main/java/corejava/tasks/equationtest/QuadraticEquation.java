package corejava.tasks.equationtest;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class QuadraticEquation {
    private static final double EPS = 1e-14;
    public String solve(double a, double b, double c) {
        if (abs(a) < EPS) { throw new IllegalArgumentException(); }
        double D = b * b - 4 * a * c;
        if (abs(D) < EPS) { return Double.toString(-b / (2 * a)); }
        if (D < 0.0) { return "no roots"; }
        return (-b + sqrt(D)) / (2 * a) + " " + (-b - sqrt(D)) / (2 * a);
    }

}