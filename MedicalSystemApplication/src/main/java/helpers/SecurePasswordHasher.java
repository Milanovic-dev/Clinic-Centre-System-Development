package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurePasswordHasher 
{
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	 
	public static String encode(String data) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		byte[] hash = digest.digest(data.getBytes());
		return bytesToStringHex(hash);
	}
	
	private static String bytesToStringHex(byte[] bytes)
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
	
		
}
