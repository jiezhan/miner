
package com.ly.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


/**
 * This is the routers general utility class.
 * 
 * @author bbuffone
 * 
 */
public class Utils {
	
    private static final DateFormat format = new SimpleDateFormat("MMdd HHmm sss");
    
    
	
	
	
	/**
	 * Get the String type date
	 * @param millis
	 * @return
	 */
	public static String getTime(long millis){
		
		return format.format(new Date(millis));
		
	}
	/**
	 * This will turn an input stream into a String Buffer.
	 * 
	 * @param inputStream
	 * @return
	 */
	public static StringBuffer getInputStream2StringBuffer(
			InputStream inputStream) {
		StringBuffer stringBuffer = new StringBuffer();
		char[] cbuf = new char[1024];
		int len=0;
		InputStreamReader utf8= null;
		try {
			utf8 = new InputStreamReader(inputStream, "UTF8");
			while ((len = utf8.read(cbuf)) > 0) {
				stringBuffer.append(cbuf, 0, len);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(utf8!=null){
				try {
					utf8.close();
				} catch (IOException e) {}
			}
		}
		return stringBuffer;
	}

	/**
	 * Converts the input bytes to hex.
	 */
	public static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * Use the standard java GZip functionality.
	 * 
	 * returns a byte array of the compress data.
	 * 
	 * @param inputBytes
	 * @return
	 */
	public static byte[] compress(byte[] inputBytes) {
		ByteArrayOutputStream bytesOutputStream = null;
		GZIPOutputStream gZIPOutputStream = null;
		try {
			bytesOutputStream = new ByteArrayOutputStream();
			gZIPOutputStream = new GZIPOutputStream(bytesOutputStream);

			gZIPOutputStream.write(inputBytes, 0, inputBytes.length);

			bytesOutputStream.close();
			gZIPOutputStream.close();

			return bytesOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Uncompress a byte array of data
	 * 
	 * @param inputBytes
	 * @return an uncompressed byte array.
	 */
	public static byte[] uncompress(byte[] inputBytes) {
		try {
			ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(
					inputBytes);
			GZIPInputStream gzipInputStream = new GZIPInputStream(
					bytesInputStream);
			ByteArrayOutputStream bytesOutputStream = new ByteArrayOutputStream();

			byte[] bytes = new byte[1024];
			int len = 0;

			while ((len = gzipInputStream.read(bytes)) > 0) {
				bytesOutputStream.write(bytes, 0, len);
			}

			gzipInputStream.close();
			bytesInputStream.close();
			bytesOutputStream.close();
			return bytesOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String md5String(String content)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] md5hash = new byte[32];
		md.update(content.getBytes("iso-8859-1"), 0, content.length());
		md5hash = md.digest();
		return convertToHex(md5hash);
	}

	
	/**
	 * ip address translation(byte array to int)
	 * @param addr  byte array format address
	 * @return 
	 */
	public static int byteArrayToIntIP(byte addr[]) {

		return IpTranslationUtils.translate(addr);
	}

	
	/**
	 * ip address translation(int to byte array)
	 * @param ipAddr
	 * @return
	 */
	public static byte[] intIPToByteArray(int ipAddr){
		
		return IpTranslationUtils.toArray(ipAddr);
	}
	/**
	 * ip address translation(int to string)
	 * 
	 * @param ipAddr
	 * @return ip address(dot seperated string)
	 */
	public static String numericToTextFormat(int ipAddr) {

		return IpTranslationUtils.toString(ipAddr);
	}

	/**
	 * ip address translation(byte array to string)
	 * @param addr
	 * @return ip address(dot seperated string)
	 */
	public static String numericToTextFormat(byte[] addr) {
		
		return IpTranslationUtils.toString(addr);
	}
	
	/**
	 * ip address translation(String to byte array)
	 * @param ipv4Addr
	 * @return ip address(byte array format)
	 */
	public static byte[] textIPToByteArray(String ipv4Addr) {

		return IpTranslationUtils.toArray(ipv4Addr);

	}
	
	/**
	 * ip address translation(String to int)
	 * @param ipv4Addr
	 * @return ip address(int format)
	 */
	public static int textIPToInt(String ipv4Addr) {
		
		return IpTranslationUtils.translate(ipv4Addr);
		
	}
	
	/**
	 * Tests whether or not the url has a http or https prefix,
	 * if it does returns true else returns false.
	 * 
	 * @param url
	 * @return
	 */
	public static boolean testHttpPrefix(String url){
		return url.toLowerCase().startsWith("http://") == true || url.toLowerCase().startsWith("https://") == true;
	}
	
	/**
	 * subtract the pat from the source
	 * @param source
	 * @param pat
	 * @return 
	 */
	public static String subtract(String source, String pat) {

		int index = source.indexOf(pat);
		if (index == -1) {
			// not found
			return source;
		}

		StringBuffer sb = new StringBuffer();
		if (index != 0) {
			sb.append(source.substring(0, index));
		}

		if (index + pat.length() != source.length()) {
			sb.append(source.substring(index + pat.length()));
		}

		return sb.toString();
	}
	
	/**
	 * @return remove the port in hostName.
	 */
	public static String removePortInHostName(String hostNameInRequest){
		
		int portPosition = hostNameInRequest.indexOf(Constants.COLON);
		if(portPosition != -1){
			hostNameInRequest = hostNameInRequest.substring(0,portPosition);
    	}
		
		return hostNameInRequest;
	}
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getShortClassName(Class<?> clazz) {	
		return getShortClassName(clazz.getName());
	}
	
	/**
	 * Get the Short class name.
	 * @param className
	 * @return
	 */
	public static String getShortClassName(String className) {		
		int lastDotIndex = className.lastIndexOf(".");
		int nameEndIndex = className.length();
		String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
		return shortName;
	}
	
	
	/**
	 * Get the last HOP in xForwardedFor header.
	 * @param xForwardedFor
	 */
	public static String lastHop(String xForwardedFor){
		
		int lastIndex = xForwardedFor.lastIndexOf(", ");
		if(lastIndex != -1){
			return xForwardedFor.substring(lastIndex+2);
		}
		
		return xForwardedFor;
	}
	
	
	/**
	 * append a trailing dot if name isn't end with "."
	 * @param name
	 * @return
	 */
	public static String applyTrailingDot(String name){
		
		if(name.endsWith(Constants.DOT)){
			return name;
		}
		
		return name + Constants.DOT;
	}
	
	/**
	 * remove the trailing dot if name has one
	 * @param name
	 * @return
	 */
	public static String removeTrailingDot(String name){
		if(name == null) return null;
		if(!name.endsWith(Constants.DOT)){
			return name;
		}
		
		return name.substring(0, name.lastIndexOf(Constants.DOT));
	}
	
	public static Charset getDefaultUTF8Charset(String charsetName){
		if(charsetName == null){
			return Constants.CHARSET_UTF_8;
		}
		Charset charset = Charset.forName(charsetName);
		if(charset == null){
			return Constants.CHARSET_UTF_8;
		}
		return charset;
	}
}
