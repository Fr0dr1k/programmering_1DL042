import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Server(int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();
        server.close();
        new ChatParticipant(socket);
    }
}
