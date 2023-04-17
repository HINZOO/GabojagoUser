package com.project.gabojago.gabojagouser.lib;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.util.Base64;

  public class AESEncryption {

    private static final String ALGORITHM="AES";
    private static final String CIPHER_ALGORITHM="AES/ECB/PKCS5Padding";
    private static final int BLOCK_SIZE=128;
    private static Key secretKey;
    private static final String KEY_FILE_NAME="secretKey.ser";
    static {
      try {
        File secretKeyFile=new File(KEY_FILE_NAME);
        if(secretKeyFile.exists()){
          ObjectInputStream ois=new ObjectInputStream(new FileInputStream(KEY_FILE_NAME));
          secretKey=(Key)ois.readObject();

        }else{
          KeyGenerator kg=KeyGenerator.getInstance(ALGORITHM);
          kg.init(BLOCK_SIZE);
          secretKey=kg.generateKey();
          ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(KEY_FILE_NAME));
          oos.writeObject(secretKey);

        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    public static String encryptValue(String value) throws Exception{
      Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      byte[] encryptBytes=cipher.doFinal(value.getBytes());
      return Base64.getEncoder().encodeToString(encryptBytes);
    }
    public static String decryptValue(String encryptValue) throws Exception{
      byte[] encryptBytes=Base64.getDecoder().decode(encryptValue);
      Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE,secretKey);
      byte[] decryptBytes=cipher.doFinal(encryptBytes);
      return new String(decryptBytes);
    }
}
