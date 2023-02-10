package corejava.tasks.figures;

import static java.lang.Math.abs;

class Triangle extends Figure {
    private final Point a;
    private final Point b;
    private final Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double area() {
        return abs((a.getX() - c.getX()) * (b.getY() - c.getY()) - (b.getX() - c.getX()) * (a.getY() - c.getY())) / 2;
    }

    @Override
    public String pointsToString() {
        return a.toString() + b.toString() + c.toString();
    }

    @Override
    public String toString() { return "Triangle[" + pointsToString() + "]"; }

    @Override
    public Point leftmostPoint() {
        return a.getX() <= b.getX() && a.getX() <= c.getX() ? a : b.getX() <= a.getX() && b.getX() <= c.getX() ? b : c;
    }
}
