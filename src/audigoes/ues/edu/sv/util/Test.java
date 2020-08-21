package audigoes.ues.edu.sv.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Test {

	public static void main(String[] args) {
		try {
			String texto = "JRAMOSJramos93";
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			KeyGenerator keyGenerator;
			keyGenerator = KeyGenerator.getInstance("AES");

			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrypText = cipher.doFinal(texto.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decrypText = cipher.doFinal(encrypText);
			String encodedEncText = javax.xml.bind.DatatypeConverter.printBase64Binary(encrypText);
			
			System.out.println("Encrypted text: " + new String(encrypText));
			System.out.println("Encrypted and encoded text: " + encodedEncText);
			System.out.println("Decrypted text: " + new String(decrypText));
			//return encodedEncText;
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] msjd = md.digest( new String(encrypText).getBytes());
			BigInteger number = new BigInteger(1, msjd);
			System.out.println("Decrypted text MD5: " + number.toString(16).toUpperCase());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
