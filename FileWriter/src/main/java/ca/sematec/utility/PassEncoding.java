package ca.sematec.utility;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.ResourceBundle;

/**
 * @author M.nia
 * this is a class for encryption
 * @see StandardPBEStringEncryptor
 * has two methods for encryption and decryption,each method has the private key
 */
public class PassEncoding {
    static ResourceBundle resourceBundle=null;
    static {
         resourceBundle= ResourceBundle.getBundle("keys");
    }
    /**
     * this is encryption method for system passwords
     * @param password as a plain text
     * @return an encrypted password
     */
    public static String encryption(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(resourceBundle.getString("private.key"));
        String encryptedPassword = encryptor.encrypt(password);
        return encryptedPassword;
    }

    /**
     * this is decryption method for password decryption
     * @param encryptedPass
     * @return a decrypted password
     */

    public static String decryption(String encryptedPass) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(resourceBundle.getString("private.key"));
        String decryptedPassword = decryptor.decrypt(encryptedPass);
        return decryptedPassword;
    }

//    public static void main(String[] args) {
//        //System.out.println(encryption("123456"));
//        System.out.println(decryption("HUcMkDWZB9B2yviLCblzXg=="));
//    }




}

