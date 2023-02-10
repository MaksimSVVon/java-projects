package corejava.tasks.figures;

class Quadrilateral extends Figure {
    private final Point a;
    private final Point b;
    private final Point c;
    private final Point d;

    public Quadrilateral(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double area() {
        double result;
        result = (new Triangle(a, b, c)).area() + (new Triangle(a, c, d)).area();
        result += (new Triangle(a, b, d)).area() + (new Triangle(b, c, d)).area();
        return result / 2;
    }

    @Override
    public String pointsToString() { return a.toString() + b.toString() + c.toString() + d.toString(); }

    @Override
    public String toString() { return "Quadrilateral[" + pointsToString() + "]"; }

    @Override
    public Point leftmostPoint() {
        double ax = a.getX(), bx = b.getX(),
                cx = c.getX(), dx = d.getX();
        return ax <= bx && ax <= cx && ax <= dx ? a :
                bx <= ax && bx <= cx && bx <= dx ? b :
                cx <= ax && cx <= bx && cx <= dx ? c : d;
    }

}
