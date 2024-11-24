import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;

public class Server {

    static Messages[] serverMessages = new Messages[100];
    static int messageCount = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        System.out.println("server (Samra) is waiting for connection....");
        Socket s = ss.accept();
        System.out.println("Connection is established now!");

        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String input = in.readUTF();
                System.out.println("Samar says: " + input);

                if (input.equalsIgnoreCase("bye")) {
                    System.out.println("Client ended the chat.");
                    break;
                }
                System.out.print("Enter your response: ");
                String response = br.readLine();
                out.writeUTF(response);
                out.flush();

                // Store message in serverMessages array
                //if (messageCount < 100) {
                    //serverMessages[messageCount++] = new Messages("Samra", "Samar", response, LocalDateTime.now(),Status.UNSEEN);
                //}
            }
        } catch (SocketException e) {
            System.out.println("Connection closed by the client.");
        } finally {
            // Close resources after loop ends
            in.close();
            out.close();
            s.close();
            ss.close();
            System.out.println("Server has closed the connection.");
        }

    }
}
