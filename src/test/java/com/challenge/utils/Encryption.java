package com.challenge.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

public class Encryption {

    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String encryptionKey = "SwagLabsAutomationEncryption";
    private Cipher cipher;
    private static Encryption instance;

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
            SecretKey key = skf.generateSecret(ks);
            this.cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String decrypt(String source){
        String decrypt = "";
        byte[] encryptedBytes = Base64.decodeBase64(source);
        try {
            byte[] decryptedBytes = this.cipher.doFinal(encryptedBytes);
            decrypt = new String(decryptedBytes);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }
}
