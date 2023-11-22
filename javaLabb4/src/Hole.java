import java.awt.*;

public class Hole {
    private Coord pos;
    private final int RADIUS;
    final Color color = Color.BLACK;
    public Hole(Coord pos,int radius){
        this.pos = pos;
        RADIUS = radius;
    }

    boolean isHit(Ball ball){
        final int OVERLAP  = 5;
        return Coord.distance(center(), ball.center()) < (ball.RADIUS + this.RADIUS - OVERLAP);
    }

    void draw(Graphics2D graphics2D){
        graphics2D.setColor(color);
        graphics2D.fillOval((int)pos.x,(int)pos.y,2*RADIUS,2*RADIUS);
    }

    Coord center(){
        return new Coord(pos.x+RADIUS,pos.y+RADIUS);
    }
}
