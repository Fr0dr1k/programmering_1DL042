import java.awt.*;

public class Cue {
    private Ball cueBall;
    private final Color AIM_COLOR = Color.WHITE, POWER_BAR_COLOR = Color.RED;
    private boolean isAiming, mouseInGame;

    private Coord mouse = Coord.ZERO;
    private Coord aimStart = Coord.ZERO;

    public Cue(Ball cueBall) {
        this.cueBall = cueBall;
    }

    void startAiming(Coord aimStart){
        this.aimStart = aimStart;
        isAiming = true;
    }

    void stopAiming(Coord aimFinished){
        isAiming = false;
        this.aimFinished = aimFinished;
        //double power = Coord.distance(aimStart,aimFinished);

        Coord aimStartToFinish = Coord.sub(aimStart,aimFinished),
                aimStartToBall = Coord.sub(aimStart,new Coord(cueBall.pos.x+cueBall.RADIUS,cueBall.pos.y+cueBall.RADIUS));

        shortingForce = Coord.proj(aimStartToFinish,aimStartToBall);

        Coord velocity = Coord.mul(Math.sqrt(10.0 * shortingForce.magnitude() / PoolGame.UPDATE_FREQUENCY),
                shortingForce.norm());
        cueBall.shot(velocity);
    }
    Coord shortingForce;
    Coord aimFinished = Coord.ZERO;

    void draw(Graphics2D graphics2D){
        if(mouseInGame) {
            graphics2D.setColor(Color.white);
            final int CUE_GIRTH = 5;
            graphics2D.setStroke(new BasicStroke(CUE_GIRTH));
            //Coord.paintLine(graphics2D, cueBall.pos, mouse);
            graphics2D.drawLine((int)cueBall.pos.x+cueBall.RADIUS,(int)cueBall.pos.y+ cueBall.RADIUS,(int)mouse.x,(int)mouse.y);

            graphics2D.fillOval((int)aimStart.x-10,(int)aimStart.y-10,20,20);
            graphics2D.fillOval((int)aimFinished.x-10,(int)aimFinished.y-10,20,20);
        }
    }

    void shot(){
        //cueBall.vel =

        Coord mouseLine = Coord.sub(mouse,cueBall.pos);
        //Coord powerVector = Coord.proj()
    }

    public void setMouseInGame(boolean mouseInGame) {
        this.mouseInGame = mouseInGame;
    }

    public void updateMouse(Coord mouse) {
        this.mouse = mouse;
    }

}
