import java.awt.*;

public class Ball{
    Color color;
    Coord pos, vel = new Coord(0,0);
    final int RADIUS;
    final double FRICTION = 0.015,
            FRICTION_PER_UPDATE =                                 // friction applied each simulation step
            1.0 - Math.pow(1.0 - FRICTION,                        // don't ask - I no longer remember how I got to this
            100.0 /PoolGame.UPDATE_FREQUENCY);
    private int ballType;

    public Ball(Coord pos, int radius) {
        this.pos = pos;
        this.RADIUS = radius;
    }

    public Ball(Coord pos, int radius,int ballType, Color color) {
        this.pos = pos;
        this.RADIUS = radius;
        this.ballType = ballType;
        this.color = color;
    }

    boolean isColliding(Ball ball){
        double firsDistance = Coord.distance(ball.pos,this.pos);
        boolean isOverlapping = firsDistance<=2*RADIUS;

        Ball copyA = this.getACopy(),
                copyB = ball.getACopy();

        copyA.pos.increase(copyA.vel);
        copyB.pos.increase(copyB.vel);

        double newDistance = Coord.distance(copyB.pos, copyA.pos);

        return newDistance < firsDistance && isOverlapping;
    }

    void move(Table table){
        if(pos.x<=0&&vel.x<0){
            vel.x *=-1;
        }
        if((pos.x+2*RADIUS)>=table.getTABLE_WIDTH()&&vel.x>0){
            vel.x *= -1;
        }
        if(pos.y<=0&&vel.y<0){
            vel.y *= -1;
        }
        if((pos.y+2*RADIUS)>=table.getTABLE_HEIGHT()&&vel.y>0){
            vel.y *= -1;
        }

        if(vel.magnitude()>FRICTION) {
            pos.increase(vel);
        }
        collide(table);
        vel.decrease(Coord.mul(FRICTION_PER_UPDATE, vel.norm()));
    }
    void collide(Table table){
        for (Ball ball: table.getBalls()) {
            if(ball != this) {
                if (isColliding(ball)) {
                    calculateCollision(ball);
                }
            }
        }
    }

    boolean collideWhitSomething(Table table){
        if(pos.x<=0||pos.y<=0||(pos.x+2*RADIUS)>=table.getTABLE_WIDTH()||(pos.y+2*RADIUS)>=table.getTABLE_HEIGHT()){
            return true;
        }
        for(Ball ball:table.getBalls()){
            if(isColliding(ball)){
                return true;
            }
        }
        return false;
    }

    Coord calculateCollision(Ball collidingBall){
        //Ändra variabelnamn så att parow inte får pykos
        Ball a = this;
        Ball b = collidingBall;
        Coord d = Coord.sub(b.pos,a.pos).norm();
        double j = Coord.scal(b.vel,d) - Coord.scal(a.vel,d);
        Coord jD = Coord.mul(j,d);
        //return new Coord(a.vel.x+jD.x,a.vel.y+jD.y);
        a.vel.increase(Coord.mul(j,d));
        b.vel.decrease(Coord.mul(j,d));
        return null;
    }

    void draw(Graphics2D graphics2D){
        final int BORDER_THICKNESS = 3;
        graphics2D.setStroke(new BasicStroke(BORDER_THICKNESS));
        graphics2D.setColor(color);
        graphics2D.fillOval((int)pos.x,(int)pos.y,2* RADIUS,2* RADIUS);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval((int)pos.x,(int)pos.y,2* RADIUS,2* RADIUS);
    }

    boolean isMoving(){
        return vel.magnitude() > FRICTION;
    }

    Coord center(){
        return new Coord(pos.x+RADIUS,pos.y+RADIUS);
    }

    Ball getACopy(){
        Ball copy = new Ball(new Coord(pos.x,pos.y),RADIUS);
        copy.vel = new Coord(this.vel.x,this.vel.y);
        return copy;
    }
    void stop(){
        vel = new Coord(0,0);
    }

    int getBallType(){
        return ballType;
    }
}
