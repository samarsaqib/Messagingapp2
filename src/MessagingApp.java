public interface MessagingApp {
    void sendMessage(String sender, String receiver, String content);
    void displayMessages();
    void modifyMessages();
    void deleteMessages();
    void addContact();
    void sendingMessages();
    void sentMessages();
    void sortMessages();
}
