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


/**class Server{
    Server(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        server.close();
        new ChatParticipant(socket);
    }
}


class Client{
    Client(int port) throws IOException {
        Socket socket = new Socket("localhost",port);
        new ChatParticipant(socket);
    }
}*/