import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    public static final int WINDOW_WIDTH    = 800,
                        WINDOW_HEIGHT       = 500,
                        SIM_WIDTH = 200,
                        UPDATE_FREQUENCY    = 100;
    static public boolean simPaused = false;
    Table simulationOfSicknes;
    JButton pauseButton;
    Timer timer;

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Simulation");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mySimulation = new Main();
        myFrame.add(mySimulation);

        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(true);
        myFrame.setVisible(true);
    }

    public Main(){
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(WINDOW_WIDTH+SIM_WIDTH,WINDOW_HEIGHT));
        simulationOfSicknes = new Table(WINDOW_HEIGHT,WINDOW_WIDTH, Color.LIGHT_GRAY);

        pauseButton = new JButton("Pause");
        final int buttonX       = 850,
                buttonY         = 100,
                buttonWidth     = 100,
                buttonHeight    = 50;
        pauseButton.setBounds(buttonX,buttonY,buttonWidth,buttonHeight);
        this.setLayout(null);
        this.add(pauseButton);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseSim();
            }
        });
        timer = new Timer(1000/UPDATE_FREQUENCY, this);
        timer.start();
    }

    void pauseSim(){
        simPaused = !simPaused;
        if(simPaused){
            timer.stop();
            repaint();
        }
        else{
            timer.start();
        }
    }


    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        simulationOfSicknes.draw(graphics2D);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        simulationOfSicknes.update();

        repaint();
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if(simPaused) {
            simulationOfSicknes.makePeopleSick(new Coord(e));
            repaint();
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}