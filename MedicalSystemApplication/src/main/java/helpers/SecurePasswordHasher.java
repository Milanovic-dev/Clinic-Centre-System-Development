package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurePasswordHasher 
{
	private static SecurePasswordHasher instance;
	
	private final char[] hexArray = "0123456789ABCDEF".toCharArray();
	 
	public String encode(String data) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] hash = digest.digest(data.getBytes());
		return bytesToStringHex(hash);
	}
	
	private String bytesToStringHex(byte[] bytes)
	{
		char[] hexChars = new char[bytes.length * 2];
		for(int j = 0 ; j < bytes.length ; j++)
		{
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		
		return new String(hexChars);
	}
	
	public static SecurePasswordHasher getInstance()
	{
		if(instance == null)
		{
			instance = new SecurePasswordHasher();			
		}
		
		return instance;
	}
		
}
