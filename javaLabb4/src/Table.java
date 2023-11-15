public class Table {
    private final int CUE_BALL_START_X,CUE_BALL_START_Y, TABLE_HEIGHT,TABLE_WIDTH;
    private Ball[] balls = new Ball[15];
    private CueBall cueBall;

    public Table(int TABLE_HEIGHT, int TABLE_WIDTH) {
        this.TABLE_HEIGHT = TABLE_HEIGHT;
        this.TABLE_WIDTH = TABLE_WIDTH;
        CUE_BALL_START_X = TABLE_WIDTH/3;
        CUE_BALL_START_Y = TABLE_HEIGHT/2;
        setBalls();

    }

    void setBalls(){

    }

    void shotCueBall(){

    }

    public int getTABLE_HEIGHT() {
        return TABLE_HEIGHT;
    }

    public int getTABLE_WIDTH() {
        return TABLE_WIDTH;
    }
}
