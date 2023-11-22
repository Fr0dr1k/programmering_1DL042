import java.awt.*;

public class Player {
    private int score = 0,ballType;
    private Color color;
    private String name;

    public Player(String name, int ballType, Color color) {
        this.name = name;
        this.ballType = ballType;
        this.color = color;
    }

    void increaseScore(int points){
        score += points;
    }

    void resetScore(){
        score = 0;
    }

    int getScore(){
        return score;
    }

    public Color getColor() {
        return color;
    }

    public int getBallType() {
        return ballType;
    }

    public String getName() {
        return name;
    }

    void draw(int x, int y, Graphics2D graphics2D){
        graphics2D.setColor(color);
        graphics2D.setFont(new Font("Arial",Font.BOLD,40));
        graphics2D.drawString(name,x,y);
        graphics2D.setFont(new Font("Arial",Font.BOLD,30));
        graphics2D.drawString("Score: "+score,x+10,y+40);
    }

    void setBallType(int ballType){
        this.ballType=ballType;
    }
}
