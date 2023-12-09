import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

import StreamManager.*;

import javax.swing.*;

public class ChatParticipant implements ObjectStreamListener{

    DisplayWindow displayWindow;
    Socket socket;
    ChatParticipant(Socket socket) throws IOException {
        this.socket = socket;
        displayWindow = new DisplayWindow(this.socket);
        InputStream myInputStream = socket.getInputStream();

        ObjectInputStream myObjectInputStream = new ObjectInputStream(myInputStream);

        new ObjectStreamManager(0, myObjectInputStream, this);

    }

    @Override
    public void objectReceived(int number, Object object, Exception e) {
        if (e == null) {
            if(Objects.equals((String) object, "::exit::")){
                displayWindow.friendLeft();
                displayWindow.exit();
            }
            else{
                displayWindow.textArea.append(displayWindow.makeMessage((String)object,"friend: "));
            }
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
    JButton closeButton;
    Socket socket;
    JFrame myFrame;

    public static void main(String[] args) {
        new DisplayWindow(null);
    }

    DisplayWindow(Socket socket){
        this.socket = socket;
        this.outputStream = createOutput(this.socket);
        myFrame = new JFrame("Chat");
        //myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        myFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exit();
            }
        });

        this.setPreferredSize(new Dimension(500,500));
        myFrame.add(this);
        addCloseButton();
        addTextArea();
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
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

    void exit(){
        sendCloseMessage();
        try {
            socket.close();
        }
        catch (IOException e){
            System.out.println("Error closing socket");
        }

        myFrame.dispose();
    }

    void sendCloseMessage(){
        try {
            outputStream.writeObject((Object)"::exit::");
        }
        catch (IOException f){
            System.out.println("Error sending message");
        }
    }

    void addCloseButton(){
        closeButton = new JButton("Close chat");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        this.add(closeButton);
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

    void friendLeft(){
        JTextArea closeText = new JTextArea("Friend left the chat");
        closeText.setFont(new Font("arial",Font.BOLD,25));
        JDialog dialog = new JDialog();
        dialog.add(closeText);
        dialog.setPreferredSize(new Dimension(250,100));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);

        dialog.setVisible(true);
    }

    void sendMessage(){
        String message = inputField.getText();
        inputField.setText("");
        System.out.println(message);
        try{
            outputStream.writeObject(message);
            textArea.append(makeMessage(message,"> "));
        }
        catch (IOException e){
            System.out.println("Error sending message");
        }
    }

    String makeMessage(String message, String nameOfSender){
        return nameOfSender+message+"\n";
    }
}