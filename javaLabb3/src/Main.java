import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Main {

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("My Clock");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Clock myClock = new Clock();
        final int WINDOW_SIZE = 500;
        myClock.setPreferredSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE));
        myFrame.addMouseListener(myClock);
        myFrame.add(myClock);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        final int TIMER_DELAY = 10;
        Timer timer = new Timer(TIMER_DELAY, myClock);
        timer.start();
    }
}

class Clock extends JPanel implements ActionListener, MouseListener{
    final int HOUR_STEPS = 12,
            SECOND_AND_MINUTE_STEPS = 60;
    private double hours = LocalDateTime.now().getHour() + LocalDateTime.now().getMinute()/(double)(SECOND_AND_MINUTE_STEPS),
            minutes = LocalDateTime.now().getMinute()+LocalDateTime.now().getSecond()/(double)(SECOND_AND_MINUTE_STEPS);

    double timeToAngle(double time,double stepsOnClock){
        final double ONE_LAP = 2*Math.PI;
        return (time*ONE_LAP)/stepsOnClock;
    }

    void drawMarker(double angle,int width, int length, int distanceFromCenter, Graphics2D graphics2D){
        final double FIX_ANGLE_OFFSET = Math.PI/2;
        angle -= FIX_ANGLE_OFFSET;
        final int CENTER = 250;
        final Color COLOR = Color.BLACK;
        graphics2D.setStroke(new BasicStroke(width));
        graphics2D.setColor(COLOR);
        int x1 = CENTER+(int)(Math.cos(angle)*distanceFromCenter),
                y1 = CENTER+(int)(Math.sin(angle)*distanceFromCenter),
                x2 = x1+(int)(Math.cos(angle)*length),
                y2 = y1+(int)(Math.sin(angle)*length);
        graphics2D.drawLine(x1,y1,x2,y2);

    }

    void drawHand(int width,int length,double angle,Color color, Graphics2D graphics2D){
        final double FIX_ANGLE_OFFSET = Math.PI/2;
        angle -= FIX_ANGLE_OFFSET;
        final int CENTER = 250;
        graphics2D.setStroke(new BasicStroke(width));
        graphics2D.setColor(color);
        int x2 = CENTER + (int)(Math.cos(angle)*length),
                y2 = CENTER + (int)(Math.sin(angle)*length);
        graphics2D.drawLine(CENTER,CENTER,x2,y2);
    }

    void drawNumbering(double angle,int number,int numberSize,int xOffset,int yOffset, int distanceFromCenter, Graphics graphics){
        final double FIX_ANGLE_OFFSET = (Math.PI/2 - Math.PI/6);
        angle -= FIX_ANGLE_OFFSET;
        final int CENTER = 250;
        int x = CENTER+(int)(Math.cos(angle)*distanceFromCenter)-xOffset,
                y = CENTER+(int)(Math.sin(angle)*distanceFromCenter+yOffset);
        graphics.drawString(""+number,x,y);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawBackground(graphics);
        drawDate(graphics);
        drawAllMarkers(graphics);
        drawClockHands(graphics);

    }

    void drawDate(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;

        final int DATE_X = 155,
                DATE_Y = 330,
                DATE_WIDTH = 200,
                DATE_HEIGHT = 50,
                DATE_CORNER_ARC = 30,
                TEXT_SIZE = 20,
                TEXT_X = 175,
                TEXT_Y = 360;

        final Color DATE_COLOR = Color.LIGHT_GRAY,
                TEXT_COLOR = Color.BLACK,
                BORDER_COLOR = Color.BLACK;

        graphics.setColor(DATE_COLOR);
        graphics2D.fillRoundRect(DATE_X,DATE_Y,DATE_WIDTH,DATE_HEIGHT,DATE_CORNER_ARC,DATE_CORNER_ARC);
        graphics2D.setColor(BORDER_COLOR);
        graphics2D.drawRoundRect(DATE_X,DATE_Y,DATE_WIDTH,DATE_HEIGHT,DATE_CORNER_ARC,DATE_CORNER_ARC);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);

        graphics.setFont(new Font("Arial",Font.BOLD,TEXT_SIZE));
        graphics.setColor(TEXT_COLOR);

        graphics.drawString(""+LocalDate.now().format(formatter),TEXT_X,TEXT_Y);

    }
    void drawBackground(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;

        final int CLOCK_FACE_SIZE = 476,
                CLOCK_FACE_X = 12,
                CLOCK_FACE_Y = 12,
                BORDER_THICKNESS = 2;
        final Color CLOCK_FACE_COLOR = Color.GRAY;
        graphics.setColor(CLOCK_FACE_COLOR);
        graphics.fillOval(CLOCK_FACE_X,CLOCK_FACE_Y,CLOCK_FACE_SIZE,CLOCK_FACE_SIZE);

        graphics.setColor(Color.BLACK);
        graphics2D.setStroke(new BasicStroke(BORDER_THICKNESS));
        graphics2D.drawOval(CLOCK_FACE_X, CLOCK_FACE_Y, CLOCK_FACE_SIZE,CLOCK_FACE_SIZE);
    }

    void drawAllMarkers(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;
        final double ONE_LAP_IN_PI = 2*Math.PI,
                STEP_HOUR_MARKER = Math.PI/6,
                STEP_MINUTE_MARKER = Math.PI/30;
        final int HOUR_MARKER_WIDTH = 5,
                HOUR_MARKER_LENGTH = 30,
                HOUR_MARKER_DISTANCE_FROM_CENTER = 195,
                MINUTE_MARKER_WIDTH = 3,
                MINUTE_MARKER_LENGTH = 15,
                MINUTE_MARKER_DISTANCE_FROM_CENTER = 210,
                NUMBERS_SIZE = 30,
                NUMBER_DISTANCE_FROM_CENTER = 170,
                NUMBER_X_OFFSET = 13,
                NUMBER_Y_OFFSET = 10;

        graphics.setFont(new Font("Areal",Font.BOLD,NUMBERS_SIZE));
        for(double v = 0, number = 1;v<=ONE_LAP_IN_PI;v+=STEP_HOUR_MARKER, number++){
            drawMarker(v,HOUR_MARKER_WIDTH,HOUR_MARKER_LENGTH,HOUR_MARKER_DISTANCE_FROM_CENTER,graphics2D);
            drawNumbering(v,(int)number,NUMBERS_SIZE,NUMBER_X_OFFSET,NUMBER_Y_OFFSET,NUMBER_DISTANCE_FROM_CENTER,graphics);
        }

        for(double v = 0; v<=ONE_LAP_IN_PI;v+=STEP_MINUTE_MARKER){
            drawMarker(v,MINUTE_MARKER_WIDTH,MINUTE_MARKER_LENGTH,MINUTE_MARKER_DISTANCE_FROM_CENTER,graphics2D);
        }
    }

    void drawClockHands(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics;
        final int CENTER = 250,
                HOUR_HAND_WIDTH = 10,
                HOUR_HAND_LENGTH = 100,
                MINUTE_HAND_WIDTH = 5,
                MINUTE_HAND_LENGTH = 150,
                SECOND_HAND_WIDTH = 3,
                SECOND_HAND_LENGTH = 200,
                CENTER_CAP_WIDTH = 18;

        final Color HAND_COLOR = Color.BLACK,
                SECOND_COLOR = Color.RED;;


        drawHand(HOUR_HAND_WIDTH, HOUR_HAND_LENGTH, timeToAngle(hours, HOUR_STEPS)+spinningAngeHHand, HAND_COLOR,
                graphics2D);

        drawHand(MINUTE_HAND_WIDTH, MINUTE_HAND_LENGTH, timeToAngle(minutes, SECOND_AND_MINUTE_STEPS)+spinningAngeMHand,
                HAND_COLOR, graphics2D);

        drawHand(SECOND_HAND_WIDTH, SECOND_HAND_LENGTH, timeToAngle(LocalDateTime.now().getSecond(),
                SECOND_AND_MINUTE_STEPS)+spinningAngeSHand, SECOND_COLOR, graphics2D);

        graphics.setColor(HAND_COLOR);
        graphics.fillOval(CENTER - CENTER_CAP_WIDTH / 2, CENTER - CENTER_CAP_WIDTH / 2, CENTER_CAP_WIDTH, CENTER_CAP_WIDTH);

    }


    private double spinningAngeSHand = 0, spinningAngeMHand = 0, spinningAngeHHand = 0;

    void startSpin(){
        final double hourTotalRotation = -4*Math.PI,
                minuteTotalRotation = 6*Math.PI,
                secondTotalRotation = -6*Math.PI;

        spinningAngeHHand = hourTotalRotation;
        spinningAngeMHand = minuteTotalRotation;
        spinningAngeSHand = secondTotalRotation;
    }

    void updateSpin(){
        final double HOUR_SPINNING_SPEED = Math.PI/20,
                MINUTE_SPINNING_SPEED = -Math.PI/7,
                SECOND_SPINNING_SPEED = Math.PI/12;
        if(spinningAngeHHand<0) {
            spinningAngeHHand += HOUR_SPINNING_SPEED;
        }
        if(spinningAngeMHand>0){
            spinningAngeMHand += MINUTE_SPINNING_SPEED;
        }
        if(spinningAngeSHand<0) {
            spinningAngeSHand += SECOND_SPINNING_SPEED;
        }

    }

    @Override
    public void actionPerformed (ActionEvent e) {
        hours = LocalDateTime.now().getHour() + LocalDateTime.now().getMinute() / (double) (SECOND_AND_MINUTE_STEPS);
        minutes = LocalDateTime.now().getMinute() + LocalDateTime.now().getSecond() / (double) (SECOND_AND_MINUTE_STEPS);
        updateSpin();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        startSpin();
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