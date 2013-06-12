/**
 * 
 */
package com.ly.miner.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jiezhan
 * 
 */
public class Helper {

	public static String readFile(String path) throws IOException{
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedReader br = null;
		try {
			 fstream = new FileInputStream(path);
			 in = new DataInputStream(fstream);
			 br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			StringBuffer ret = new StringBuffer();
			while ((strLine = br.readLine()) != null) {
				ret.append(strLine);
			}
			return ret.toString();
		} catch (IOException e) {
			throw e;
		}finally{
			if(br!=null){
				br.close();
			}
			if(in!=null){
				in.close();
			}
			if(fstream!=null){
				fstream.close();
			}
		}

	}

}
