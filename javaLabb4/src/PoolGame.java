import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 * Att göra
 * -Fixa så att man inte kan skjuta bollen bakåt
 *
 *
 * */

public class PoolGame extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    public static final int WINDOW_WIDTH    = 800,
                        WINDOW_HEIGHT       = 500,
                        UPDATE_FREQUENCY    = 100;
    Table poolTable;

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Pool Game");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PoolGame myPoolGame = new PoolGame();
        myFrame.add(myPoolGame);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(true);
        myFrame.setVisible(true);
        Timer timer = new Timer(1000/UPDATE_FREQUENCY, myPoolGame);
        timer.start();
    }

    public PoolGame(){
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        poolTable = new Table(WINDOW_HEIGHT,WINDOW_WIDTH);

    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        poolTable.draw(graphics2D);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        poolTable.update();

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        poolTable.cue.startAiming(new Coord(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        poolTable.cue.stopAiming(new Coord(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        poolTable.cue.setMouseInGame(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        poolTable.cue.setMouseInGame(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        poolTable.cue.updateMouse(new Coord(e));
    }
}
