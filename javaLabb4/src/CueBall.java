import java.awt.*;

public class CueBall extends Ball{


    public CueBall(Coord pos, int radius, Table table){
        super(pos,radius);
        this.color = Color.WHITE;
        this.table=table;
    }
    void shot(Coord force){
        vel = force;
    }

    private Table table;
    private final Color AIM_COLOR = Color.WHITE, POWER_BAR_COLOR = Color.RED;
    private boolean isAiming, mouseInGame, movingMode;

    private Coord mouse = new Coord(0,0),
            aimStart    = new Coord(0,0),
            powerBar    = new Coord(0,0);

    void moveByHand(){
        movingMode=true;
        this.stop();
    }

    void placeCueBall(Coord mouse){
        if(movingMode&&isOkPlacement(mouse)){
            this.pos = mouse;
            movingMode = false;
        }
    }

    boolean isOkPlacement(Coord placement){
        //Ända så man tar in en boll istället så att in game blir bättre
        if(!table.inGame(placement)){
            return false;
        }
        for(Ball ball: table.getBalls()){
            if(Coord.distance(placement,ball.pos)<=2*RADIUS){
                return false;
            }
        }
        for(Hole hole:table.getHoles()){
            if(hole.isHit(new Ball(placement,RADIUS))){
                return false;
            }
        }
        return true;
    }

    void startShot(Coord aimStart){
        if(table.noBlasMoving()&&!movingMode) {
            this.aimStart = aimStart;
            isAiming = true;
        }
    }

    Coord calcVelocity(Coord aimFinished){
        Coord aimStartToFinish = Coord.sub(aimStart, aimFinished),
                aimStartToBall = Coord.sub(aimStart, this.center());

        Coord shortingForce = Coord.proj(aimStartToFinish, aimStartToBall);
        Coord velocity = Coord.mul(Math.sqrt(shortingForce.magnitude()/3.0),shortingForce.norm()); //Typ från parow
        final double MAX_VELOCITY = 10.0; //Fick fram av att test
        if(velocity.magnitude()>MAX_VELOCITY){
            velocity = Coord.mul(MAX_VELOCITY,shortingForce.norm());
        }
        return velocity;
    }

    void shootShot(Coord aimFinished){
        if(!movingMode){
            if(table.noBlasMoving()/**&&Coord.distance(aimFinished,cueBall.pos)<Coord.distance(aimStart,cueBall.pos)*/) {
                isAiming = false;
                this.shot(calcVelocity(aimFinished));
                table.startShot();
            }
        }
    }

    void drawCue(Graphics2D graphics2D){
        if(movingMode){
            this.pos = mouse;
        }
        if(table.inGame(mouse)&&!movingMode) {
            graphics2D.setColor(AIM_COLOR);
            final int CUE_GIRTH = 5;
            graphics2D.setStroke(new BasicStroke(CUE_GIRTH));
            graphics2D.drawLine((int)this.center().x,(int)this.center().y,(int)mouse.x,(int)mouse.y);
            graphics2D.fillOval((int)mouse.x-10,(int)mouse.y-10,20,20);
            if(isAiming) {
                drawPowerBarr(Coord.sub(mouse, pos).norm(), graphics2D);
            }

        }
    }

    void drawPowerBarr(Coord directionOfAim, Graphics2D graphics2D){
        Coord dirOfPower = Coord.mul(-1,directionOfAim);
        double scaleToPower = calcVelocity(powerBar).magnitude();
        dirOfPower = Coord.mul(scaleToPower*10,dirOfPower);
        dirOfPower.increase(this.center());
        graphics2D.setColor(POWER_BAR_COLOR);
        graphics2D.setStroke(new BasicStroke(6));
        graphics2D.drawLine((int)pos.x+RADIUS,(int)pos.y+RADIUS,(int)dirOfPower.x,(int)dirOfPower.y);

    }


    boolean isAloudToShot(){
        if(table.noBlasMoving()){
            return false;
        }
        return true;
    }

    public void updateMouse(Coord mouse) {
        this.mouse = mouse;
    }

    public void updatePowerBar(Coord mouse){
        this.powerBar = mouse;
    }

}
