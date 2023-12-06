import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    Client(int port) throws IOException {
        Socket socket = new Socket("localhost",port);
        ConcatHandler contacts = new ConcatHandler("src/testFile.txt");
        contacts.readContacts();
        new ChatParticipant(socket);
    }

    void printContacts(ConcatHandler concatHandler){
        ArrayList<String[]> contacts = concatHandler.readContacts();
    }
}

class ConcatHandler {
    private String filePath;
    final String ipSplitSymbolise = ":::";
    ArrayList<String[]> contactInformation;

    ConcatHandler(String filePath){
        this.filePath = filePath;
        contactInformation = readContacts();
    }

    void addContact(String contactName, String ipAdress){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            //bw.write(contactName + ipSplitSymbolise + ipAdress + "\n");

            //bw.write(new char[]{contactName,ipAdress},0,2);
        }
        catch (IOException e){
            System.out.println("Error writing to file");
        }
        contactInformation.add(new String[]{contactName,ipAdress});

    }

    String[] getContactInformation(int indexOfContactInContacts){
        return null;
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

class Contact implements Serializable{
    String name,ip;
}
