package utility;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PassEncoding {
    public static String encryption(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("123456");
        String encryptedPassword = encryptor.encrypt(password);
        System.out.println("Encrypted: "+encryptedPassword);
        return encryptedPassword;
    }

   public static String decryption(String encryptedPass){
       StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
       decryptor.setPassword("123456");
       String decryptedPassword = decryptor.decrypt(encryptedPass);
       System.out.println("Decrypted: "+ decryptedPassword);
       return decryptedPassword;
   }

    public static void main(String[] args) {
       // encryption("Myjava@123");

       decryption("4FuK+ddZdTx2+LKvmdBZPIbymKo8onWS");
    }



}

