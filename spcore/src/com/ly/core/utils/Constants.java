package com.ly.core.utils;

import java.nio.charset.Charset;


/**
 * This is the set of constants involved in configuring network connections
 * 
 * @author zhanjie
 *
 */
public class Constants {

	/**
	 * This is the default read buffer size used for creating a
	 * client or server.
	 */
	public final static int		READ_BUFFER_SIZE = 100000;
	
	/**
	 * This is the default packet sized used for creating a
	 * client or server.
	 */
	public final static int		PACKET_BUFFER_SIZE = 10000;
	
	public static final int IPV4_ADDR_SZ = 4;
	
	/**
	 * Transport Protocols
	 */	
	public final static String UDP = "udp";
	public final static String TCP = "tcp";

	/**
	 * IO Types
	 */	
	public final static String NIO = "nio";
	public final static String OIO = "oio";
	
	public static final long MILLI_IN_SECOND = 1000l;
	
	public static final String UTF_8 = "UTF-8";
	
	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
	
	public static final String DOT = ".";
	public static final String COLON = ":";

	public static final String IPSTART_COLUMN_NAME = "ip_start";
	public static final String LATITUDE_COLUMN_NAME = "latitude";
	public static final String LONGITUDE_COLUMN_NAME = "longitude";
	public static final String COUNTRY_COLUMN_NAME = "country_code";
	public static final String REGION_COLUMN_NAME = "region_name";
	
	public static final String ORIGIN = "origin";
	
}
