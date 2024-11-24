import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Client implements MessagingApp {
    static Messages[] ClientMessages = new Messages[100];
    static Messages[] messages = new Messages[100];
    static Contact[] contacts = new Contact[3];
    static int messageCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Socket s = new Socket("localhost", 9999);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            Client client = new Client();
            System.out.println("Chat started");

            while (true) {
                System.out.println("\n----- Menu -----");
                System.out.println("1. Send Message");
                System.out.println("2. Delete messages");
                System.out.println("3. Add contacts");
                System.out.println("4. Sending message");
                System.out.println("5. for displaying Messages");
                System.out.println("6. modify messages");
                System.out.println("7. Displaying sent messages");
                System.out.println("8. To sort messages");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:

                        while (true) {
                            System.out.print("Enter message: ");
                            String content = scanner.nextLine();

                            if (content.equalsIgnoreCase("bye")) {
                                System.out.println("Ending chat...");
                                out.writeUTF(content);
                                out.flush();
                                break;
                            }
                            ClientMessages[messageCount] = new Messages("Samar", "Samra", content, LocalDateTime.now(), Status.UNSEEN);
                            System.out.println("Message added at index " + messageCount);
                            messageCount++;
                            client.sendMessage("Samar", "Samra", content);
                            out.writeUTF(content);
                            out.flush();
                            try {
                                String response = in.readUTF();
                                System.out.println("Samra says: " + response);

                                ClientMessages[messageCount] = new Messages("Samra", "Samar", response, LocalDateTime.now(), Status.UNSEEN);
                                System.out.println("Response added at index " + messageCount);
                                messageCount++;
                            } catch (SocketException e) {
                                System.out.println("Connection closed by the server.");
                                break;
                            }
                        }
                        break;
                    case 2:
                        client.deleteMessages();
                        break;

                    case 3:
                        client.addContact();
                        break;

                    case 4:
                        client.sendingMessages();
                        break;
                    case 5:
                        client.displayMessages();
                        break;
                    case 6:
                        client.modifyMessages();
                        break;
                    case 7:
                        client.sentMessages();
                        break;

                    case 8:
                        client.sortMessages();

                    case 9:

                        System.out.println("Exiting chat...");
                        out.writeUTF("bye");
                        out.flush();
                        in.close();
                        out.close();
                        s.close();
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @Override
    public void sendMessage(String sender, String receiver, String content) {
        System.out.println("Message sent to " + receiver + ": " + content);
    }
    @Override
    public void sortMessages() {
        Arrays.sort(ClientMessages, 0, messageCount, Comparator.comparing(Messages::getTimeStamp));
        for (int i = 0; i < ClientMessages.length; i++) {
            if (ClientMessages[i] != null) {
                System.out.println(ClientMessages[i]);
            }
        }
    }
    
    @Override
    public void displayMessages(){
        for(int i = 0; i < ClientMessages.length; i++){
            if(ClientMessages[i] != null) {
                System.out.println(ClientMessages[i]);
            }
        }
    }

    @Override
    public void modifyMessages() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of contact whose message you wanna modify");
        String name = scanner.nextLine();
        System.out.println("Enter the message you want to modify: ");
        String message = scanner.nextLine();
        if (name.equals("Samra")) {
            for (int i = 0; i < ClientMessages.length; i++) {
                if (ClientMessages[i] != null && ClientMessages[i].getContent().equals(message) && ClientMessages[i].getReceiver().equals("Samra")) {
                    System.out.println("Enter the new message");
                    String modifyedmessage = scanner.nextLine();
                    ClientMessages[i].setContent(modifyedmessage);
                    System.out.println("Message modified");
                    break;
                }
            }
        }
        else if (!name.equals("Samra")) {
            for(int i = 0; i < messages.length; i++) {
                if(messages[i] != null && messages[i].getContent().equals(message) && messages[i].getReceiver().equals(name)) {
                    System.out.println("Enter the new message");
                    String modifyedmessage = scanner.nextLine();
                    messages[i].setContent(modifyedmessage);
                    System.out.println("Message modified");
                    break;

                }

            }

        }
    }
    @Override
    public void deleteMessages() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of contact whose message you wanna delete");
        String name = scanner.nextLine();
        System.out.println("Enter the message you want to delete: ");
        String message = scanner.nextLine();
        if(name.equals("Samra")) {
            for (int i = 0; i < ClientMessages.length; i++) {
                if (ClientMessages[i] != null && ClientMessages[i].getSender().equals("Samar") && ClientMessages[i].getContent().equals(message)) {
                    ClientMessages[i] = null;
                    System.out.println("Message deleted");
                    break;
                }
            }
        }
        else if(!name.equals("Samra")) {
            for(int i = 0; i < messages.length; i++) {
                if(messages[i] != null && messages[i].getReceiver().equals(name) && messages[i].getContent().equals(message)) {
                    messages[i] = null;
                }
            }
        }
    }
    @Override
    public void addContact(){
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < contacts.length; i++) {
            System.out.println("Enter the name of the contact you want to add: ");
            String name = scanner.nextLine();
            System.out.println("Enter the phone number of the contact you want to add: ");
            String phone = scanner.nextLine();
            contacts[i] = new Contact(name, phone);
            System.out.println("Contact added");
        }
        for(int i = 0; i < contacts.length; i++){
            System.out.println(contacts[i]);
        }
    }

    @Override
    public void sendingMessages() {
        Scanner scanner = new Scanner(System.in);
        String choice = "yes";
        while(choice.equalsIgnoreCase("yes")) {
            for (int i = 0; i < messages.length; i++) {
                System.out.println("Enter name");
                String name = scanner.nextLine();
                System.out.println("Enter your message");
                String message = scanner.nextLine();
                for (int j = 0; j < contacts.length; j++) {
                    if (messages[i] == null && name.equals(contacts[j].getName())) {
                        messages[i] = new Messages("Samar", name, message, LocalDateTime.now(), Status.UNSEEN);
                        System.out.println("Message sent to " + name);
                    }
                }
                System.out.println("Do you wanna continue sending messages?");
                choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("no"))
                    break;
            }

        }
    }

    @Override
    public void sentMessages() {
        int i;
        for(i = 0; i < messages.length; i++){
            if(messages[i] != null) {
                System.out.println(messages[i]);
            }
        }
    }
}