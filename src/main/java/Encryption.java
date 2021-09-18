import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

public class Encryption {

    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String encryptionKey = "SwagLabsAutomationEncryption";
    private Cipher cipher;
    private static Encryption instance;
    private SecretKey key;

    public static Encryption getInstance() {
        if(instance == null){
            instance = new Encryption();
        }
        return instance;
    }

    public Encryption(){
        final byte[] arrayBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
        try {
            KeySpec ks = new DESedeKeySpec(arrayBytes);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
            this.cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
            key = skf.generateSecret(ks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String source){
        String encrypted = "";
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] sourceBytes = source.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = this.cipher.doFinal(sourceBytes);
            encrypted = new String(Base64.encodeBase64(encryptedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encrypted;
    }

    public String decrypt(String source){
        String decrypt = "";
        byte[] encryptedBytes = Base64.decodeBase64(source);
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = this.cipher.doFinal(encryptedBytes);
            decrypt = new String(decryptedBytes);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }
}
