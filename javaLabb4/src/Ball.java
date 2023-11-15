import java.awt.*;

public class Ball{
    private final Color color;
    private final Coord pos;
    private int radius,
            speed;
    private double angle;

    public Ball(Coord pos, int radius, Color color) {
        this.pos = pos;
        this.radius = radius;
        this.color = color;
    }

    void move(Table table){
        if((pos.x-radius)<=0||(pos.y-radius)<=0||(pos.x+radius)>=table.getTABLE_WIDTH()||(pos.y+radius)>=table.getTABLE_HEIGHT()){
            bounce();
        }
    }

    void draw(Graphics2D graphics2D){
        final int BORDER_THICKNESS = 3;
        graphics2D.setStroke(new BasicStroke(BORDER_THICKNESS));
        graphics2D.setColor(color);
        graphics2D.fillOval((int)pos.x,(int)pos.y,2*radius,2*radius);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval((int)pos.x,(int)pos.y,2*radius,2*radius);


    }


    void bounce(){

    }

    boolean isMoving(){
        return speed!=0;
    }
}
