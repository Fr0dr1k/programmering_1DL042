import java.awt.*;
import java.awt.event.MouseEvent;

public class Coord {

    double x, y;

    Coord(double xCoord, double yCoord) {
        x = xCoord;
        y = yCoord;
    }

    Coord(MouseEvent event) {                   // Create a Coord from a mouse event
        x = event.getX();
        y = event.getY();
    }

    static final Coord ZERO = new Coord(0,0);

    double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    Coord norm() {                              // norm: a normalised vector at the same direction
        if(magnitude()==0)
            return new Coord(x,y);
        return new Coord(x / magnitude(), y / magnitude());
    }

    void increase(Coord c) {
        x += c.x;
        y += c.y;
    }

    void decrease(Coord c) {
        x -= c.x;
        y -= c.y;
    }

    void shorten(double factor){
        x *= (1-factor);
        y *= (1-factor);
    }

    static double scal(Coord a, Coord b) {      // scalar product
        return a.x * b.x + a.y * b.y;
    }

    static double scalProj(Coord a, Coord b){
        return scal(a,b.norm());
    }

    static Coord proj(Coord a, Coord b){
        return mul(scal(a,b.norm()),b.norm());
    }

    static Coord sub(Coord a, Coord b) {
        return new Coord(a.x - b.x, a.y - b.y);
    }

    static Coord mul(double k, Coord c) {       // multiplication by a constant
        return new Coord(k * c.x, k * c.y);
    }



    static double distance(Coord a, Coord b) {
        return Coord.sub(a, b).magnitude();
    }

    static void paintLine(Graphics2D graph2D, Coord a, Coord b){  // paint line between points
        graph2D.setColor(Color.black);
        graph2D.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
    }
}
