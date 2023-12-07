import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import StreamManager.*;

import javax.swing.*;

public class ChatParticipant implements ObjectStreamListener{

    DisplayWindow displayWindow;
    ChatParticipant(Socket socket) throws IOException {
        displayWindow = new DisplayWindow(createOutput(socket));
        InputStream myInputStream = socket.getInputStream();

        ObjectInputStream myObjectInputStream = new ObjectInputStream(myInputStream);

        new ObjectStreamManager(0, myObjectInputStream, this);

    }

    ObjectOutputStream createOutput(Socket socket){
        try {
            return new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException e){
            System.out.println("Can not resolve socket");
            return null;//Måste fixas så att den stoppar programet istället
        }
    }

    @Override
    public void objectReceived(int number, Object object, Exception e) {
        if (e == null) {
            displayWindow.textArea.append((String)object);
        }
        else {
            System.out.println(e.getMessage());
        }
    }
}

class DisplayWindow extends JPanel {

    JTextArea textArea;
    JScrollPane scrollPane;
    JTextField inputField;
    ObjectOutputStream outputStream;

    DisplayWindow(ObjectOutputStream outputStream){
        this.outputStream = outputStream;
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
        textArea = new JTextArea();
        textArea.setRows(29);
        textArea.setEditable(false);
        //textArea.setLineWrap(true);
        //textArea.setWrapStyleWord(true);


        scrollPane = new JScrollPane(textArea);
        inputField = new JTextField(35);
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        this.add(scrollPane);
        this.add(inputField);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

    }

    void sendMessage(){
        String message = inputField.getText();
        inputField.setText("");
        System.out.println(message);
        try{
            outputStream.writeObject(message);
        }
        catch (IOException e){
            System.out.println("Error sending message");
        }
    }
}