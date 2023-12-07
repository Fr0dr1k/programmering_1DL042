import java.io.IOException;
import java.net.*;

public class Chat {
    public static void main(String[] args) {
        Chat myChat = new Chat();
    }

    Chat(){
        final int port = 60000;
        System.out.println("Starting Client");

        try {
            new Client(port);
            System.out.println("Client created");
        } catch (IOException e) {
            System.out.println("Client failed, starting Server");
            try {
                new Server(port);
                System.out.println("Server created");
            }
            catch (IOException f){
                System.out.println("Failed creating server");
            }
        }

    }
}
