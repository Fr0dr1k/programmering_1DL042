import java.awt.*;

public class Ball{
    private final Color color;
    private int xPos, yPos, radius, speed;
    private double angle;

    public Ball(int xPos, int yPos, int radius, Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.color = color;
    }

    void move(Table table){
        if((xPos-radius)<=0||(yPos-radius)<=0||(xPos+radius)>=table.getTABLE_WIDTH()||(yPos+radius)>=table.getTABLE_HEIGHT()){
            bounce();
        }
    }

    void draw(Graphics graphics){
        graphics.setColor(color);
        graphics.fillOval(xPos,yPos,2*radius,2*radius);
    }


    void bounce(){

    }

    boolean isMoving(){
        return speed!=0;
    }
}
