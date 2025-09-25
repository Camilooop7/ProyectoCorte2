package co.edu.unbosque.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Utilidad para cifrado y descifrado AES en modo CBC con PKCS5Padding.
 * Proporciona métodos para cifrar y descifrar cadenas usando una clave y un vector de inicialización (IV).
 */
public class AESUtil {

    /** Algoritmo de cifrado utilizado (AES). */
    private final static String algoritmo = "AES";

    /** Tipo de cifrado utilizado (AES/CBC/PKCS5Padding). */
    private final static String tipoCifrado = "AES/CBC/PKCS5Padding";

    /**
     * Cifra el texto plano usando la clave y el IV especificados.
     * @param llave clave de cifrado (16 bytes)
     * @param iv vector de inicialización (16 bytes)
     * @param texto texto a cifrar
     * @return texto cifrado en base64
     */
    public static String encrypt(String llave, String iv, String texto) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(tipoCifrado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes(), algoritmo);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        byte[] encrypted = null;
        try {
            encrypted = cipher.doFinal(texto.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(encodeBase64(encrypted));
    }

    /**
     * Descifra el texto cifrado usando la clave y el IV especificados.
     * @param llave clave de cifrado (16 bytes)
     * @param iv vector de inicialización (16 bytes)
     * @param encrypted texto cifrado en base64
     * @return texto descifrado
     */
    public static String decrypt(String llave, String iv, String encrypted) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(tipoCifrado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        SecretKeySpec secretKeySpec = new SecretKeySpec(llave.getBytes(), algoritmo);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        byte[] enc = decodeBase64(encrypted);
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {

            e.printStackTrace();
        }

        byte[] decrypted = null;
        try {
            decrypted = cipher.doFinal(enc);
        } catch (IllegalBlockSizeException | BadPaddingException e) {

            e.printStackTrace();
        }

        return new String(decrypted);
    }

    /**
     * Descifra el texto cifrado usando la clave y el IV predeterminados.
     * @param encrypted texto cifrado en base64
     * @return texto descifrado
     */
    public static String decrypt(String encrypted) {
        String iv = "programacioncomp";
        String key = "llavede16carater";
        return decrypt(key, iv, encrypted);
    }

    /**
     * Cifra el texto plano usando la clave y el IV predeterminados.
     * @param plainText texto a cifrar
     * @return texto cifrado en base64
     */
    public static String encrypt(String plainText) {
        String iv = "programacioncomp";
        String key = "llavede16carater";
        return encrypt(key, iv, plainText);
    }

    /*
     * public static void main(String[] args) { String text = "hola mundo"; String
     * iv = "holamundohfhfhtf"; String key = "holamundohfhfhtf";
     * System.out.println(iv.getBytes().length); String encoded = encrypt(key, iv,
     * text); System.out.println(encoded); }
     */
}