package audigoes.ues.edu.sv.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.faces.context.FacesContext;
import javax.xml.bind.DatatypeConverter;

public class Utils {
	
	public static final String claveEstandar = "Tesis2020";
	
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
	
	public static String NUMEROS = "0123456789";
	public static String MAYUSCULAS="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String MINUSCULAS="abcdefghijklmnopqrstuvwxyz";
	

	public static String getPassword(int length) {
		return getPassword(NUMEROS+MAYUSCULAS+MINUSCULAS, length);
	}
	
	public static String getPassword(String key, int length) {
		String pswd = "";
		
		for (int i=0; i<length; ++i) {
			pswd = pswd+key.charAt((int) (Math.random()*(double) key.length()));
		}
		return pswd;
	}
	
	public static String getHashPassword(String texto) {
		MessageDigest md;
		String tex="";
		try {
			md = MessageDigest.getInstance("SHA-384");
			byte[] msjd = md.digest(texto.getBytes(StandardCharsets.UTF_8));
			BigInteger number = new BigInteger(1, msjd);
		    
		    tex= number.toString(16);
		    while (tex.length() < 32) { 
		    	tex = "0" + tex; 
	        }
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		
	    
	    return tex.toUpperCase();
	}
	
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
	
	public static String encode(String input) throws UnsupportedEncodingException{
		return DatatypeConverter.printBase64Binary(input.getBytes());
	}
	
	public static String decode(String text) {
		String retorno = null;
		
		try {
			retorno = new String(DatatypeConverter.parseBase64Binary(text), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	

}
