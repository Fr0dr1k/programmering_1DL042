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

    private Coord mouse = new Coord(0,0);
    private Coord aimStart = new Coord(0,0);

    void moveByHand(){
        movingMode=true;
    }

    void placeCueBall(Coord mouse){
        if(movingMode&&isOkPlacement(mouse)){
            this.pos = mouse;
            movingMode = false;
        }


    }

    boolean isOkPlacement(Coord placement){
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

    void startAiming(Coord aimStart){
        if(table.noBlasMoving()&&!movingMode) {
            this.aimStart = aimStart;
            isAiming = true;
        }
    }

    void stopAiming(Coord aimFinished){
        if(!movingMode){
            if(table.noBlasMoving()/**&&Coord.distance(aimFinished,cueBall.pos)<Coord.distance(aimStart,cueBall.pos)*/) {
                isAiming = false;
                this.aimFinished = aimFinished;
                Coord aimStartToFinish = Coord.sub(aimStart, aimFinished),
                        aimStartToBall = Coord.sub(aimStart, new Coord(this.pos.x + this.RADIUS, this.pos.y + this.RADIUS));

                shortingForce = Coord.proj(aimStartToFinish, aimStartToBall);

                Coord velocity = Coord.mul(Math.sqrt(10.0 * shortingForce.magnitude() / PoolGame.UPDATE_FREQUENCY),
                        shortingForce.norm());
                this.shot(velocity);
            }
        }
    }
    Coord shortingForce;
    Coord aimFinished = new Coord(0,0);

    void drawCue(Graphics2D graphics2D){
        if(movingMode){
            this.pos = mouse;
        }
        if(table.inGame(mouse)&&!movingMode) {
            graphics2D.setColor(AIM_COLOR);
            final int CUE_GIRTH = 5;
            graphics2D.setStroke(new BasicStroke(CUE_GIRTH));
            graphics2D.drawLine((int)this.pos.x+this.RADIUS,(int)this.pos.y+ this.RADIUS,(int)mouse.x,(int)mouse.y);
            //graphics2D.fillOval((int)mouse.x-10,(int)mouse.y-10,20,20);
            graphics2D.fillOval((int)mouse.x-10,(int)mouse.y-10,20,20);

        }
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

}
