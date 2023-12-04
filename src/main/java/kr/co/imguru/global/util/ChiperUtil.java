package kr.co.imguru.global.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * 시스템 유틸리티 기능 제공
 * </pre>
 * @Class설명 : 암복호화 및 인코딩 기능 등 시스템 운영에필요한 유틸성기능 제공
 * @Author : 
 * @Date : 
 * @Version : 1.0
 */
public class ChiperUtil {
	
	/**
	 * <pre>
	 * 문자열 인코딩 (SHA-256)
	 * </pre>
	 * @Method설명 : sha256으로 문자열을 인코딩한다. 
	 * @Method Name : sha256
	 * @Author : 
	 * @Date : 
	 * @Version : 1.0
	 * @param str
	 * @return SHA-256 으로 인코딩한 문자열
	 */
	public static String sha256(String str){
		StringBuffer sb = new StringBuffer();
		try {
			  MessageDigest sh = MessageDigest.getInstance("SHA-256");

			  byte[] toDigest = sh.digest(str.getBytes());
			  for(int i =0; i<toDigest.length; i++){
				  int c = toDigest[i] & 0xff;
				  if (c <= 15)
				        sb.append("0");
				  sb.append(Integer.toHexString(c));
			  }
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
        }
		return sb.toString();
	}
	
	/**
	 * <pre>
	 * 		AES/ECB/PKCS5Padding 방식으로 암호화함
	 * 		AES 256
	 * 		ECB 암호화 키를 이용해서 각 블록과 일대일로 대응되는 암호화 블록 생성, 입력값이 같을 경우, 암호화된 값도 같아서 암호문 해독이 쉬움
	 * 		hex 인코딩
	 * </pre>
	 * @param key		암호화Key 
	 * @param plainText	암호화 할  메세지
	 * @return
	 */
	public static String aesEncryptEcb(String key, String plainText) {
		Cipher cipher;
		byte[] keys;
		byte[] encryptes;
		String returnMessage = null;
		
		try {
			keys = key.getBytes("UTF-8");
			
			SecretKeySpec skeySpec = new SecretKeySpec(keys ,"AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			
			encryptes = cipher.doFinal(plainText.getBytes("UTF-8"));
			
			returnMessage = toHexString(encryptes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException |
				 IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		return returnMessage;
	}
	
	/**
	 * <pre>
	 * 		AES/ECB/PKCS5Padding 방식으로 복호화 함
	 * 		ECB 암호화 키를 이용해서 각 블록과 일대일로 대응되는 암호화 블록 생성, 입력값이 같을 경우, 암호화된 값도 같아서 암호문 해독이 쉬움
	 * 		AES 256
	 * 		hex 디코딩
	 * </pre>
	 * @param key		암호화Key 
	 * @param plainText	암호화 할  메세지
	 * @return
	 */
	public static String aesDecryptEcb(String key, String chiperText) {
		Cipher cipher;
		byte[] keys;
		byte[] decryptes;
		String returnMessage = null; 
		
		try {
			keys = key.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(keys ,"AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec );
			
			decryptes = hexToByteArray(chiperText);
			returnMessage = new String(cipher.doFinal(decryptes), "UTF-8");
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |UnsupportedEncodingException |
				 IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnMessage;
	}
	
  public static String toHexString(byte[] bytesArray) {
        StringBuffer sb = new StringBuffer();
        
        if (bytesArray == null || bytesArray.length == 0)
        	throw new RuntimeException("toHexString target bytes array is empty");
 
        for (int i = 0; i < bytesArray.length; i++) {
            sb.append(String.format("%02X", bytesArray[i]));
        }
 
        return sb.toString();
    }
  
  public static byte[] hexToByteArray(String hexStr) {
	  byte[] b = new byte[hexStr.length() / 2];
	  
	  for(int i = 0 ; i < b.length; i++){
		  b[i] = (byte)Integer.parseInt(hexStr.substring(2*i, 2*i+2),16);
	  }
	  
	  return b;
  }
	
}
