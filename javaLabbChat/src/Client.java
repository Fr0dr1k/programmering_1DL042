import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
        Socket socket = new Socket(pickIp(),port);
        new ChatParticipant(socket);
    }

    String pickIp(){
        //Gör saker i gFrame eller något
        printContacts();
        System.out.println("Pick a contact");
        Scanner in = new Scanner(System.in);
        int pickedContact = in.nextInt();
        return contacts.getContactIp(pickedContact);
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
    ArrayList<String[]> bufferedContacts = new ArrayList<>();

    ConcatHandler(String filePath){
        this.filePath = filePath;
        bufferedContacts = readContacts();
        System.out.println(bufferedContacts.toString());
    }

    void addContact(String contactName, String ipAdress){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(contactName + ipSplitSymbolise + ipAdress + "\n");
            bufferedContacts.add(new String[]{contactName,ipAdress});

            //bw.write(new char[]{contactName,ipAdress},0,2);
        }
        catch (IOException e){
            System.out.println("Error writing to file");
        }
        bufferedContacts.add(new String[]{contactName,ipAdress});

    }

    String getContactIp(int indexOfContact){
        try{
            return bufferedContacts.get(indexOfContact)[1];
        }
        catch (IndexOutOfBoundsException e){
            return null;
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
