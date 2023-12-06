import java.awt.*;

public class Person {
    Coord pos, vel;
    final int RADIUS;
    boolean isSick, isAlive = true;
    final double riskOfInfection = 0.5,riskOfDeath = 0.2/100.0, SECONDS_TO_RECOVERY = 5;
    double secondsLeftToRecovery = 5;


    public Person(Coord pos, int radius, boolean isSick, Coord speed) {
        this.pos = pos;
        this.RADIUS = radius;
        this.isSick = isSick;
        this.vel = speed;
    }

    boolean isColliding(Person ball){
        double firsDistance = Coord.distance(ball.pos,this.pos);
        boolean isOverlapping = firsDistance<=2*RADIUS;

        Person copyA = this.getACopy(),
                copyB = ball.getACopy();

        copyA.pos.increase(copyA.vel);
        copyB.pos.increase(copyB.vel);

        double newDistance = Coord.distance(copyB.pos, copyA.pos);

        return newDistance < firsDistance && isOverlapping;
    }

    void update(Table table){
        if(isAlive) {
            move(table);
            collide(table);
        }
        if(isSick) {
            getBetter();
            if (Math.random() < riskOfDeath) {
                die();
            }
        }

    }

    void die(){
        isAlive = false;
        isSick = false;
    }

    void getBetter(){
        if(secondsLeftToRecovery>0){
            secondsLeftToRecovery-=1.0/ Simulation.UPDATE_FREQUENCY;
        }
        else{
            isSick = false;
        }
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
        pos.increase(vel);
    }

    void collide(Table table){
        for (Person ball: table.getPeople()) {
            if(ball != this) {
                if (isColliding(ball)&& ball.isAlive) {
                    calculateCollision(ball);
                    if(isSick){
                        ball.getInfected();
                    }
                }
            }
        }
    }

    void calculateCollision(Person collidingBall){
        //Ändra variabelnamn så att parow inte får pykos
        Person a = this;
        Person b = collidingBall;
        Coord d = Coord.sub(b.pos,a.pos).norm();
        double j = Coord.scal(b.vel,d) - Coord.scal(a.vel,d);
        a.vel.increase(Coord.mul(j,d));
        b.vel.decrease(Coord.mul(j,d));
    }

    void getInfected(){
        if(Math.random()<riskOfInfection){
            isSick = true;
            secondsLeftToRecovery = SECONDS_TO_RECOVERY;
        }
    }

    void draw(Graphics2D graphics2D){
        final int BORDER_THICKNESS = 3;
        graphics2D.setStroke(new BasicStroke(BORDER_THICKNESS));
        if(isSick){
            graphics2D.setColor(Color.RED);
        }
        else{
            graphics2D.setColor(Color.WHITE);
        }
        if(!isAlive){
            graphics2D.setColor(Color.BLACK);
        }

        if(isAlive) {
            graphics2D.fillOval((int) pos.x, (int) pos.y, 2 * RADIUS, 2 * RADIUS);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawOval((int) pos.x, (int) pos.y, 2 * RADIUS, 2 * RADIUS);
        }else if(Simulation.simPaused){
            graphics2D.fillOval((int) pos.x, (int) pos.y, 2 * RADIUS, 2 * RADIUS);
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawOval((int) pos.x, (int) pos.y, 2 * RADIUS, 2 * RADIUS);
        }

    }

    boolean isMoving(){
        return vel.magnitude() > 0.01;
    }

    void makeSick(){
        if(isAlive){
            isSick = true;
        }

    }

    Coord center(){
        return new Coord(pos.x+RADIUS,pos.y+RADIUS);
    }

    Person getACopy(){
        Person copy = new Person(new Coord(pos.x,pos.y),RADIUS,isSick,vel);
        copy.vel = new Coord(this.vel.x,this.vel.y);
        return copy;
    }

    public void setVel(Coord vel) {
        this.vel = vel;
    }
}
