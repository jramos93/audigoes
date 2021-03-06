package audigoes.ues.edu.sv.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Utils {
	
	public static final String claveEstandar = "Cambiame1";
	
	public static final String rolAuditor = "AUDITOR";
	public static final String rolCoordinador = "COORDINADOR";
	public static final String rolJefe = "JEFATURA";
	public static final String rolAuditado = "AUDITADO";
	public static final String rolGeneral = "GENERAL";
	
	
	public static final String perCreate = "AGREGAR";
	public static final String perRead = "CONSULTAR";
	public static final String perUpdate = "MODIFICAR";
	public static final String perDelete = "ELIMINAR";
	public static final String perGeneral = "GENERAL";
	public static final String perLogin = "LOGIN";
	

	public static String getShaString(String texto) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-384");
			byte[] msjd = md.digest(texto.getBytes(StandardCharsets.UTF_8));
			BigInteger number = new BigInteger(1, msjd);
		    
		    String tex= number.toString(16);
		    while (tex.length() < 32) { 
		    	tex = "0" + tex; 
            } 
			
			return number.toString(16).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getAESString(String texto) {
		try {
			System.out.println("p-texto "+texto);
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			KeyGenerator keyGenerator;
			keyGenerator = KeyGenerator.getInstance("AES");

			keyGenerator.init(256);
			SecretKey secretKey = keyGenerator.generateKey();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encrypText = cipher.doFinal(texto.getBytes());
			
			System.out.println("Encrypted text: " + new String(encrypText));
			System.out.println("Encrypted text: " + new String(encrypText));

			return new String(encrypText);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
