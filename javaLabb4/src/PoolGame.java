import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PoolGame extends JPanel implements ActionListener, MouseListener {

    private final int WINDOW_WIDTH = 800,
            WINDOW_HEIGHT = 500;
    Table poolTable;

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Pool Game");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PoolGame myPoolGame = new PoolGame();
        myFrame.addMouseListener(myPoolGame);
        myFrame.add(myPoolGame);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(true);
        myFrame.setVisible(true);
        final int TIMER_DELAY = 10;
        Timer timer = new Timer(TIMER_DELAY, myPoolGame);
        timer.start();
    }

    public PoolGame(){
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

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
