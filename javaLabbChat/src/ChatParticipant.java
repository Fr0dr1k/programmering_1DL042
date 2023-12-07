import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import StreamManager.*;

import javax.swing.*;

public class ChatParticipant implements ObjectStreamListener{
    ChatParticipant(Socket socket) throws IOException {
        DisplayWindow displayWindow = new DisplayWindow();
        //ConsoleWriter myWriter = new ConsoleWriter(socket);
        //myWriter.start();
        InputStream myInputStream = socket.getInputStream();

        ObjectInputStream myObjectInputStream = new ObjectInputStream(myInputStream);
        //ObjectOutputStream myOutput = new ObjectOutputStream(socket.getOutputStream());

        new ObjectStreamManager(0, myObjectInputStream, this);

    }

    @Override
    public void objectReceived(int number, Object object, Exception e) {
        if (e == null) {
            System.out.println((String)object);
        }
        else {
            System.out.println(e.getMessage());
        }
    }
}

class DisplayWindow extends JPanel implements KeyListener {

    public static void main(String[] args) {
        new DisplayWindow();
    }

    DisplayWindow(){
        JFrame myFrame = new JFrame("Chat");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500,500));
        myFrame.add(this);
        addTextArea();
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(true);
        myFrame.setVisible(true);
    }

    void addTextArea(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JTextField inputField = new JTextField();
        inputField.setColumns(35);
        this.add(inputField);

        this.add(scrollPane);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

class ConsoleWriter extends Thread{
    private final Socket socket;
    Scanner myScanner = new Scanner(System.in);
    ObjectOutputStream outputStream;
    ConsoleWriter(Socket socket){
        this.socket = socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e){
            System.out.println("Cant resolve socket");
        }

    }
    public void run(){
        while (true) {
            Object in = myScanner.nextLine();
            try {
                outputStream.writeObject(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
