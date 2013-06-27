/**
 * 
 */
package com.ly.core;

import java.util.List;

import com.ly.core.utils.Constants;


/**
 * @author jiezhan
 *
 */
public class SocketClientConfig implements IClientConfig {
	
	public 	String 	_endPointHostname = "";
	public 	int		_endPointPort = 80;
	public 	boolean	_synchronousConnection = true;
	public 	String 	_protocolType = Constants.TCP;
	public 	String 	_ioType = Constants.NIO;
	public 	int		_readBufferSize = 1000000;
	public 	int		_packetBufferSize = 10000;
	public 	boolean	_bUseStaticThreadPool = true;
	public 	String 	_id = "";
	public  String  _trafficRecordId = "";
	public boolean _isPrefetch = false;
	public	Object 	_initialObject = null;
	public boolean  _bEnableTLS = false;
	public 	List<String> 	_sslEnabledProtocols;
	public String toString(){
		return _endPointHostname + ":" + _endPointPort;
	}
	

}
