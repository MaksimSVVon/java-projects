package corejava.tasks.figures;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

class Circle extends Figure {
    private final Point center;
    private final double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public double area() { return PI * pow(radius, 2); }

    @Override
    public String pointsToString() { return center.toString();  }

    @Override
    public String toString() { return "Circle[" + pointsToString() + radius + "]"; }

    @Override
    public Point leftmostPoint() { return new Point(center.getX() - radius, center.getY()); }

}
