import java.awt.*;

public class Ball{
    final Color color;
    Coord pos, vel = Coord.ZERO;
    final int RADIUS;
    final double FRICTION = 0.015,
            FRICTION_PER_UPDATE =                                 // friction applied each simulation step
            1.0 - Math.pow(1.0 - FRICTION,                       // don't ask - I no longer remember how I got to this
            100.0 /PoolGame.UPDATE_FREQUENCY);

    public Ball(Coord pos, int radius, Color color) {
        this.pos = pos;
        this.RADIUS = radius;
        this.color = color;
    }

    void shot(Coord force){
        vel = force;
    }

    boolean isColliding(Ball ball){
        return Coord.distance(ball.pos,this.pos)<=2*RADIUS;
    }

    void move(Table table){
        if((pos.x)<=0||(pos.x+2*RADIUS)>=table.getTABLE_WIDTH()){
            vel.x *=-1;
        }
        if((pos.y)<=0||(pos.y+2*RADIUS)>=table.getTABLE_HEIGHT()){
            vel.y*=-1;
        }
        for (Ball ball: table.getBalls()) {
            if(isColliding(ball)){
                Coord collisionResult = calculateCollision(ball);
                ball.shot(collisionResult);
                this.shot(collisionResult);

            }
        }
        //vel.shorten(FRICTION);
        //System.out.println(vel.x + " : "+vel.y);
        pos.increase(vel);
        //System.out.println(vel.norm().magnitude());
        vel.decrease(Coord.mul(FRICTION_PER_UPDATE, vel.norm()));
    }

    Coord calculateCollision(Ball collidingBall){
        //Gör matte och skit antar jag
    }

    void draw(Graphics2D graphics2D){
        final int BORDER_THICKNESS = 3;
        graphics2D.setStroke(new BasicStroke(BORDER_THICKNESS));
        graphics2D.setColor(color);
        graphics2D.fillOval((int)pos.x,(int)pos.y,2* RADIUS,2* RADIUS);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval((int)pos.x,(int)pos.y,2* RADIUS,2* RADIUS);
    }


    void bounce(){

    }

    boolean isMoving(){
        return vel.magnitude() > FRICTION;
    }
}
