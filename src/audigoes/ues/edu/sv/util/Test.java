package audigoes.ues.edu.sv.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Test {

	public static void main(String[] args) {
		try {
			String texto = "JRAMOSJramos93";
			MessageDigest md = MessageDigest.getInstance("SHA-384");
			byte[] msjd = md.digest(texto.getBytes(StandardCharsets.UTF_8));
			BigInteger number = new BigInteger(1, msjd);
		    
		    String tex= number.toString(16);
		    while (tex.length() < 32) { 
		    	tex = "0" + tex; 
            } 
		    
		    System.out.println("tex "+tex.toUpperCase());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
