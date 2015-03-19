package com.arjinmc.openexpress.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @usage HTTP
 * @author arjinmc
 * @email arjinmc@hotmail.com
 * @website http://www.hicsg.com
 * @version 2014-1-16
 */
public class HttpHelper {
	
	public static final int TIMEOUT_DURATION=5000;
	public static final String TIMEOUT = "timeout";
	
	/**mark for request finish*/
	public static final int REQUEST_FINISH = 0xffffff;
	/**mark error code for timeout*/
	public static final int REQUEST_TIMEOUT = 0xeeeeee;
	
	public static Context myContext;
	
	/**
	 * http GET
	 * @param context
	 * @param url
	 * @return
	 */
	public static String getHttp(Context context,String url){
		String result = TIMEOUT;
		
		if(isNeworkWorking(context)){
	        
	        HttpParams httpParameters = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_DURATION);
		    HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_DURATION);
		    
		    HttpGet httpget = new HttpGet(url);
		    HttpClient client = new DefaultHttpClient(httpParameters);  
	        HttpResponse response = null;  
	        try {  
	            response = client.execute(httpget);  
	            int status = response.getStatusLine().getStatusCode();  
	          if (status == HttpStatus.SC_OK) {  
	        	  result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);  
	          }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return result;
	        }  		    
		}       
		return result;
	}
	
	/**
	 * check network is ok
	 * @param act
	 * @return
	 */
	public static  boolean isNeworkWorking(Context context) {  
		
	       ConnectivityManager manager = (ConnectivityManager) context  
	    		   .getSystemService(  
	                     Context.CONNECTIVITY_SERVICE);  
	        
	       if (manager == null) {  
	           return false;  
	       }  
	        
	       NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
	        
	       if (networkinfo == null || !networkinfo.isAvailable()) {  
	           return false;  
	       }  
	   
	       return true;  
	}  

}
