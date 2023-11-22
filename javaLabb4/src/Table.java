import java.awt.*;
import java.util.ArrayList;

public class Table {

    final private Color BACKGROUND_COLOR;
    private final int TABLE_HEIGHT, TABLE_WIDTH,
                    BALL_RADIUS = 15;
    private ArrayList<Ball> balls = new ArrayList<>();
    private CueBall cueBall;
    private final Coord CUE_BALL_START_POS;
    private Hole[] holes = new Hole[6];
    private Player[] players;
    int playerTurn;
    private boolean colorSet = false;

    public Table(int TABLE_HEIGHT, int TABLE_WIDTH, Color BACKGROUND_COLOR, Player[] players) {
        this.TABLE_HEIGHT = TABLE_HEIGHT;
        this.TABLE_WIDTH = TABLE_WIDTH;
        this.BACKGROUND_COLOR = BACKGROUND_COLOR;
        this.players = players;
        CUE_BALL_START_POS = new Coord(TABLE_WIDTH/4,TABLE_HEIGHT/2-BALL_RADIUS);
        addCueBall();
        setBalls();
        setHoles();
        playerTurn = (int)(Math.random()*players.length);
    }

    void setHoles(){
        final double HOLE_RADIUS = 30;
        int holeNr = 0;
        for(int col=0;col<3;col++){
            for(int row=0;row<2;row++){
                holes[holeNr] = new Hole(new Coord(col*(TABLE_WIDTH/2.0)-HOLE_RADIUS,row*(TABLE_HEIGHT)-HOLE_RADIUS),(int)HOLE_RADIUS);
                holeNr++;
            }

        }

    }

    void resetGame(){
        cueBall = new CueBall(new Coord(CUE_BALL_START_POS.x,CUE_BALL_START_POS.y),BALL_RADIUS,this);
        cueBall.vel = new Coord(0,0);
        balls.clear();
        setBalls();
        for (Player player:players) {
            player.resetScore();
        }
    }

    void addCueBall(){
        cueBall = new CueBall(new Coord(CUE_BALL_START_POS.x,CUE_BALL_START_POS.y),BALL_RADIUS,this);
        //cue = new Cue(this);
    }


    void update(){
        if(cueBall != null) {
            cueBall.move(this);
        }
        if(balls.size() > 0) {
            for (Ball ball : balls) {
                ball.move(this);
            }
        }
        scoreBall();
    }

    void nextTurn(){
        playerTurn++;
        if(playerTurn>= players.length){
            playerTurn=0;
        }
    }


    void setBallColor(Ball ballSank){
        colorSet = true;
        int otherPlayer = 0;
        if (playerTurn == 0) {
            otherPlayer = 1;
        }
        players[playerTurn].setBallType(ballSank.getBallType());
        int otherBall = 0;
        if(ballSank.getBallType()==0){
            otherBall = 1;
        }
        players[otherPlayer].setBallType(otherBall);


        for(Ball ball:balls){
            if(ball.getBallType()!=-1) {
                if (ball.getBallType() == ballSank.getBallType()) {
                    ball.color = players[playerTurn].getColor();
                } else {
                    ball.color = players[otherPlayer].getColor();
                }
            }
        }
    }

    void scoreBall(){
        for(Hole hole:holes){
            if(hole.isHit(cueBall)&&cueBall.isMoving()){
                cueBall.stop();
                cueBall.moveByHand();
                nextTurn();
            }
        }

        for(int ballNr = 0;ballNr<balls.size();ballNr++){
            for(Hole hole:holes){
                if(hole.isHit(balls.get(ballNr))){
                    if(balls.get(ballNr).getBallType()==-1){
                        //Spelaren som sköt ska förlora
                    }
                    else{
                        if(colorSet){
                            if(balls.get(ballNr).getBallType()==players[playerTurn].getBallType()){
                                players[playerTurn].increaseScore(1);
                            }
                            else{
                                nextTurn();
                            }
                        }
                        else{
                            setBallColor(balls.get(ballNr));
                            players[playerTurn].increaseScore(1);
                        }
                    }
                    balls.remove(ballNr);
                }
            }
        }
    }

    void setBalls(){
        colorSet = false;
        int ballsAdded = 0, ballsPerRow = 5;
        final int spaceBetweenBalls = 3,
                backX               = TABLE_WIDTH-TABLE_HEIGHT/4,
                topBackY            = TABLE_HEIGHT/2-5*(BALL_RADIUS)-2*spaceBetweenBalls;
        Coord topBall = new Coord(backX,topBackY);
        while (ballsPerRow>0){

            for(int i = 0;i<ballsPerRow;i++){
                int ballType;
                Color ballColor;
                if(ballsAdded%2==0){
                    ballColor = Color.DARK_GRAY;
                    ballType = 0;
                }
                else{
                    ballColor = Color.GRAY;
                    ballType = 1;
                }

                if(ballsAdded==10){
                    ballColor = Color.BLACK;
                    ballType = -1;
                }
                balls.add(new Ball(new Coord(topBall.x,topBall.y+i*(spaceBetweenBalls+2*BALL_RADIUS)),BALL_RADIUS,ballType,ballColor));
                ballsAdded++;
            }
            ballsPerRow--;
            topBall = new Coord(topBall.x-2*BALL_RADIUS-spaceBetweenBalls,topBall.y+BALL_RADIUS);
        }
    }

    void draw(Graphics2D graphics2D){
        graphics2D.setColor(BACKGROUND_COLOR);
        graphics2D.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);
        for(Ball ball : balls){
            ball.draw(graphics2D);
        }

        if(noBlasMoving()){
            cueBall.drawCue(graphics2D);
        }
        for(Hole hole:holes){
            hole.draw(graphics2D);
        }
        if(cueBall != null) {
            cueBall.draw(graphics2D);
        }
    }

    public int getTABLE_HEIGHT() {
        return TABLE_HEIGHT;
    }

    public int getTABLE_WIDTH() {
        return TABLE_WIDTH;
    }

    boolean noBlasMoving(){
        for (Ball ball : balls) {
            if (ball.isMoving()) {
                return false;
            }
        }
        if(cueBall != null){
            return !cueBall.isMoving();
        }
        return true;

    }

    public CueBall getCueBall() {
        return cueBall;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Hole[] getHoles() {
        return holes;
    }

    boolean inGame(Coord pos){
        return !(pos.x <= 0) && !((pos.x) >= TABLE_WIDTH) && !(pos.y <= 0) && !((pos.y) >= TABLE_HEIGHT);
    }

}
