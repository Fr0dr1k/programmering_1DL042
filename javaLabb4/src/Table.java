import java.awt.*;

public class Table {

    final private Color BACKGROUND_COLOR = new Color(53, 95, 3);
    private final int TABLE_HEIGHT,
            TABLE_WIDTH,
            BALL_RADIUS = 15;
    private Ball[] balls = new Ball[15];
    private CueBall cueBall;
    private final Coord CUE_BALL_START_POS;

    public Table(int TABLE_HEIGHT, int TABLE_WIDTH) {
        this.TABLE_HEIGHT = TABLE_HEIGHT;
        this.TABLE_WIDTH = TABLE_WIDTH;
        CUE_BALL_START_POS = new Coord(TABLE_WIDTH/4,TABLE_HEIGHT/2-BALL_RADIUS);

        setBalls();
        cueBall = new CueBall(CUE_BALL_START_POS,BALL_RADIUS,Color.WHITE);
        System.out.println(CUE_BALL_START_POS.y);

    }

    void setBalls(){

    }

    void shotCueBall(){

    }

    void draw(Graphics2D graphics2D){
        graphics2D.setColor(BACKGROUND_COLOR);
        graphics2D.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);
        cueBall.draw(graphics2D);
    }

    public int getTABLE_HEIGHT() {
        return TABLE_HEIGHT;
    }

    public int getTABLE_WIDTH() {
        return TABLE_WIDTH;
    }
}
