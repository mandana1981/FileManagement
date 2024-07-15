package utility;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author M.nia
 * this is a class for encryption
 * @see StandardPBEStringEncryptor
 * has two methods for encryption and decryption,each method has the private key
 */
public class PassEncoding {
    /**
     * this is encryption method for system passwords
     * @param password as a plain text
     * @return an encrypted password
     */
    public static String encryption(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("123456");
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
        decryptor.setPassword("123456");
        String decryptedPassword = decryptor.decrypt(encryptedPass);
        return decryptedPassword;
    }




}

