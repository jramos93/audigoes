package audigoes.ues.edu.sv.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Utils {

	public static String getMD5String(String texto) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] msjd = md.digest(texto.getBytes());
			BigInteger number = new BigInteger(1, msjd);
			return number.toString(16).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getAESString(String texto) {
		try {
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			KeyGenerator keyGenerator;
			keyGenerator = KeyGenerator.getInstance("AES");

			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrypText = cipher.doFinal(texto.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			
			System.out.println("Encrypted text: " + new String(encrypText));

			return new String(encrypText);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
