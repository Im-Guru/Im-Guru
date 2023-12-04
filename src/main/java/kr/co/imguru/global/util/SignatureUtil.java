package kr.co.imguru.global.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SignatureUtil {
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
	
}
