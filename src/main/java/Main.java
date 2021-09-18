public class Main {
    public static void main(String[] args) {
        Encryption encryption = Encryption.getInstance();
        System.out.println(encryption.encrypt("other"));
    }
}
