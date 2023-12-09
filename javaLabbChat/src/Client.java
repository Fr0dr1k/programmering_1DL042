import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try {
            new Client(60000);
            System.out.println("Client created");
        }
        catch (IOException e){
            System.out.println("No existing server");
        }
    }

    ConcatHandler contacts;
    Client(int port) throws IOException {
        contacts = new ConcatHandler("src/testFile.txt");
        Socket socket = new Socket("localhost",port);
        new ChatParticipant(socket);
    }

    String pickIp(){
        JFileChooser file = new JFileChooser("src");
        file.showSaveDialog(null);
        contacts.loadContacts(file.getSelectedFile());

        JComboBox comboBox = new JComboBox(contacts.getAllNames());
        //comboBox.setSelectedIndex(4);
        //comboBox.addActionListener(this);


        return "localhost";
    }

    void printContacts(){
        ArrayList<String[]> bufferedContacts = contacts.readContacts();
        for(int i=0;i<bufferedContacts.size();i++){
            System.out.println("["+i+"]"+bufferedContacts.get(i)[0]);
        }
    }
}

class ConcatHandler {
    private String filePath;
    final String ipSplitSymbolise = ":::";
    ArrayList<Contact> bufferedContacts = new ArrayList<>();

    ConcatHandler(String filePath){
        this.filePath = filePath;
        System.out.println(bufferedContacts.toString());
    }

    void addContact(String contactName, String ipAdress, int port){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(contactName + ipSplitSymbolise + ipAdress + ipSplitSymbolise + port + "\n");
            bufferedContacts.add(new Contact(contactName,ipAdress, port));

            //bw.write(new char[]{contactName,ipAdress},0,2);
        }
        catch (IOException e){
            System.out.println("Error writing to file");
        }

    }

    String[] getAllNames(){
        String[] names = new String[bufferedContacts.size()];
        for (int i=0;i<bufferedContacts.size();i++) {
            names[i] = bufferedContacts.get(i).name;
        }
        return names;
    }

    String getContactName(int indexOfContact){
        try{
            return bufferedContacts.get(indexOfContact).name;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    String getContactIp(int indexOfContact){
        try{
            return bufferedContacts.get(indexOfContact).ip;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    int getContactPort(int indexOfContact){
        try{
            return bufferedContacts.get(indexOfContact).port;
        }
        catch (IndexOutOfBoundsException e){
            return -1;
        }
    }

    void loadContacts(File filePath){
        bufferedContacts.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while (br.ready()){
                ///System.out.println(br.readLine());
                String line[] = br.readLine().split(ipSplitSymbolise);
                bufferedContacts.add(new Contact(line[0],line[1],Integer.parseInt(line[2])));
            }
        }
        catch (IOException e){
            System.out.println("Error reading file");
        }
    }

    ArrayList<String[]> readContacts(){
        ArrayList<String[]> contacts = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            while (br.ready()){
                ///System.out.println(br.readLine());
                String line = br.readLine();
                contacts.add(line.split(ipSplitSymbolise));
            }
        }
        catch (IOException e){
            System.out.println("Error reading file");
            return null;
        }
        return contacts;
    }

}

class Contact{
    String name, ip;
    int port;

    public Contact(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
}
