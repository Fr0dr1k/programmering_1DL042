import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            System.out.println("Starting server");
            new Server(60000);
            System.out.println("Server created");
        }
        catch (IOException f){
            System.out.println("Failed creating server");
        }
    }
    Server(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        System.out.println("test");
        new ChatParticipant(socket);
        System.out.println("server closing");
        server.close();
    }
}

class ServerDisplay extends ChatParticipant{

    ServerDisplay(Socket socket) throws IOException {
        super(socket);
    }
}

