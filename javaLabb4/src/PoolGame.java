import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/***
 * Att göra
 *
 *
 * Om man missar blir det nästa spelares tur
 * Om man inte träffar något ska motståndaren få flytta på bollen
 * Gör så att man förlorar om man skjuter ner den svarta
 * Gör så att det vissas en vinnare i slutet
 * Lägg in power bar
 *
 *
 * */

public class PoolGame extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    static final int WINDOW_WIDTH    = 900,
                        WINDOW_HEIGHT       = 800,
                        UPDATE_FREQUENCY    = 100,
                        SCORE_BORD_HEIGHT   = 200,
                        GAME_BORDER         = 20,
                        GAME_HEIGHT = WINDOW_HEIGHT-SCORE_BORD_HEIGHT-2*GAME_BORDER,
                        GAME_WIDTH = WINDOW_WIDTH-2*GAME_BORDER;
    Table poolTable;
    Player[] players;
    JButton resetButton;
    Timer timer;

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Pool Game");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PoolGame myPoolGame = new PoolGame();
        myFrame.add(myPoolGame);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(true);
        myFrame.setVisible(true);
    }

    public PoolGame(){
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        addPlayers();
        poolTable = new Table(GAME_HEIGHT,GAME_WIDTH, new Color(53, 95, 3),players);
        addResetButton(this);
        timer = new Timer(1000/UPDATE_FREQUENCY, this);
        timer.start();
    }

    void addResetButton(JPanel myPanel){
        resetButton = new JButton("Reset ");
        final int buttonX       = 700,
                buttonY         = 670,
                buttonWidth     = 150,
                buttonHeight    = 80;
        resetButton.setBounds(buttonX,buttonY,buttonWidth,buttonHeight);
        resetButton.setFont(new Font("Arial",Font.BOLD,20));

        resetButton.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        resetButton.setBorder(line);

        this.setLayout(null);
        this.add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poolTable.resetGame();
            }
        });

    }

    void addPlayers(){
        players = new Player[2];
        players[0] = new Player("Player 1",1,Color.RED);
        players[1] = new Player("Player 2",2,Color.BLUE);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        drawGame(graphics2D);
        drawBorder(graphics);
        drawScore(graphics);
        drawPlayers(graphics2D);
    }

    void drawBorder(Graphics graphics){
        graphics.setColor(new Color(31, 84, 5));
        graphics.fillRect(0,0,WINDOW_WIDTH,GAME_BORDER);
        graphics.fillRect(0,0,GAME_BORDER,GAME_HEIGHT+2*GAME_BORDER);
        graphics.fillRect(0,GAME_HEIGHT+GAME_BORDER,WINDOW_WIDTH,GAME_BORDER);
        graphics.fillRect(GAME_WIDTH+GAME_BORDER,0,GAME_BORDER,GAME_HEIGHT+GAME_BORDER);
    }

    void drawGame(Graphics2D graphics2D){
        graphics2D.translate(GAME_BORDER,GAME_BORDER);
        poolTable.draw(graphics2D);
        graphics2D.translate(-GAME_BORDER,-GAME_BORDER);
    }

    void drawScore(Graphics graphics){
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0,GAME_HEIGHT+2*GAME_BORDER,WINDOW_WIDTH,WINDOW_HEIGHT-2*GAME_BORDER-GAME_HEIGHT);
    }

    void drawPlayers(Graphics2D graphics2D){
        final int PLAYER_1_X    = 50,
                PLAYER_1_Y      = 670,
                PLAYER_2_X      = 500,
                PLAYER_2_Y      = 670,
                PLAYER_TURN_X   = 270,
                PLAYER_TURN_Y   = 670;
        players[0].draw(PLAYER_1_X,PLAYER_1_Y,graphics2D);
        players[1].draw(PLAYER_2_X,PLAYER_2_Y,graphics2D);
        graphics2D.setFont(new Font("Arial",Font.BOLD,30));
        graphics2D.setColor(players[poolTable.playerTurn].getColor());
        graphics2D.drawString(players[poolTable.playerTurn].getName()+" turn",PLAYER_TURN_X,PLAYER_TURN_Y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        poolTable.update();

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Coord mosePos = new Coord(e);
        poolTable.getCueBall().placeCueBall(new Coord(mosePos.x-GAME_BORDER,mosePos.y-GAME_BORDER));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(poolTable.getCueBall() != null) {
            Coord mosePos = new Coord(e);
            poolTable.getCueBall().startShot(new Coord(mosePos.x-GAME_BORDER,mosePos.y-GAME_BORDER));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(poolTable.getCueBall() != null) {
            Coord mosePos = new Coord(e);
            poolTable.getCueBall().shootShot(new Coord(mosePos.x-GAME_BORDER,mosePos.y-GAME_BORDER));
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(poolTable.getCueBall() != null) {
            Coord mosePos = new Coord(e);
            poolTable.getCueBall().updatePowerBar(new Coord(mosePos.x-GAME_BORDER,mosePos.y-GAME_BORDER));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(poolTable.getCueBall() != null) {
            Coord mosePos = new Coord(e);
            poolTable.getCueBall().updateMouse(new Coord(mosePos.x-GAME_BORDER,mosePos.y-GAME_BORDER));
        }
    }
}
