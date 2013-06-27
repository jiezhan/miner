package com.ly.core.utils;

import java.util.StringTokenizer;


/**
 * @author zhanjie
 */
public class IpTranslationUtils {

	
	/**
	 * ip address translation(byte array to int)
	 * @param addr  byte array format address
	 * @return 
	 */
	public static int translate(byte addr[]) {

		int address = 0;

		if (addr != null && addr.length == Constants.IPV4_ADDR_SZ) {
			address =  addr[3] & 0xFF;
			address |= (addr[2]&0xFF) << 8;
			address |= (addr[1]&0xFF) << 16;
			address |= (addr[0]&0xFF) << 24;
		}

		return address;
	}
	
	/**
	 * ip address translation(String to int)
	 * @param addr
	 * @return ip address(int format)
	 */
	public static int translate(String addr) {
		return translate(toArray(addr));
	}

	/**
	 * ip address translation(byte array to string)
	 * @param addr
	 * @return ip address(dot seperated string)
	 */
	public static String toString(byte[] addr) {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append(addr[0] & 0xff);
		stringBuffer.append(Constants.DOT).append(addr[1] & 0xff);
		stringBuffer.append(Constants.DOT).append(addr[2] & 0xff);
		stringBuffer.append(Constants.DOT).append(addr[3] & 0xff);
		
		return stringBuffer.toString();
	}
	
	/**
	 * ip address translation(int to byte array)
	 * @param addr
	 * @return
	 */
	public static byte[] toArray(int addr){
		
		byte[] result = new byte[Constants.IPV4_ADDR_SZ];

		result[0] = (byte) ((addr >>> 24) & 0xFF);
		result[1] = (byte) ((addr >>> 16) & 0xFF);
		result[2] = (byte) ((addr >>> 8) & 0xFF);
		result[3] = (byte) (addr & 0xFF);
		
		return result;
	}
	/**
	 * ip address translation(int to string)
	 * 
	 * @param addr
	 * @return ip address(dot seperated string)
	 */
	public static String toString(int addr) {
		return toString(toArray(addr));
	}
	
	/**
	 * ip address translation(String to byte array)
	 * @param addr
	 * @return ip address(byte array format)
	 */
	public static byte[] toArray(String addr) {

		byte[] result = null;
		StringTokenizer st = new StringTokenizer(addr, Constants.DOT);
		try {
			byte part0 = (byte)(Integer.parseInt(st.nextToken().trim()) & 0xFF);
			byte part1 = (byte) (Integer.parseInt(st.nextToken().trim()) & 0xFF);
			byte part2 = (byte) (Integer.parseInt(st.nextToken().trim()) & 0xFF);
			byte part3 = (byte) (Integer.parseInt(st.nextToken().trim()) & 0xFF);
			
			result = new byte[Constants.IPV4_ADDR_SZ];
			result[0] = part0;
			result[1] = part1;
			result[2] = part2;
			result[3] = part3;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
