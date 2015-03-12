package com.arjinmc.openexpress.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.arjinmc.openexpress.model.ExpressBean;


/**
 * @desciption util for express options
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2015/3/12
 */
public class ExpressUtil {
	
	/**this keys is use for ickd service only for these app or apps of csg
	 * u can use it for test but u can't use it for business
	 * if u want to use it for business please check their web site
	 * */
	public static final String EXPRESS_ID="104058";
	public static final String EXPRESS_KEY="ae0e4263dbdf21313f1ee61ff639a736";
	
	public static List<ExpressBean> getExpressCode(Context context) {
		List<ExpressBean> expressBeans= new ArrayList<ExpressBean>();
		InputStream inputStream = null;
		try {

			inputStream = context.getAssets().open("company.xml");
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");

			expressBeans = new ArrayList<ExpressBean>();
			ExpressBean expressBean = null;
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();
					if ("express".equals(name)) {
						expressBean = new ExpressBean();
					}
					if (expressBean != null) {
						if ("name".equals(name)) {
							String expressName = parser.nextText();
							expressBean.setName(expressName); 
						}
						if ("code".equals(name)) {
							String expressCode = parser.nextText();
							expressBean.setNumber(expressCode);
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if ("express".equals(parser.getName())) {
						expressBeans.add(expressBean);
					}
					break;
				}
				event = parser.next();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return expressBeans;
	}
	
	/**
	 * get express company name
	 * @param expressBeans
	 * @param expressCode
	 * @return
	 */
	public static String getExpressName(List<ExpressBean> expressBeans,String expressCode){
		String result = "";
		if(expressBeans!=null && expressBeans.size()!=0){
			for(int i=0;i<expressBeans.size();i++){
				if(expressBeans.get(i).getNumber().equals(expressCode)){
					result = expressBeans.get(i).getName();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * make up the url for search express bill
	 * @param expressCode
	 * @param expressNo
	 * @return
	 */
	public static String getExpressURL(String expressCode,String expressNo){
		return "http://api.ickd.cn/?id="+EXPRESS_ID+"&secret="
				+EXPRESS_KEY+"&com="+expressCode+"&nu="
				+expressNo+"&type=json&encode=utf8&ord=desc";
	}
	
	
	/***
	 * ickd's error code
	 * @param errorCode
	 * @return
	 */
	public static String getExpressError(int errorCode){
		String result ="";
		switch(errorCode){
			case 0:
				result = "no promblem";
				break;
			case 1:
				result = "bill number is not existed";
				break;
			case 2:
				result = "captcha code error";
				break;
			case 3:
				result = "fail to connect with server";
				break;
			case 4:
				result = "server error";
				break;
			case 5:
				result = "server execute error";
				break;
			case 6:
				result = "bill number format error";
				break;
			case 7:
				result = "express company error";
				break;
			case 10:
				result = "unknown error";
				break;
			case 20:
				result = "api error";
				break;
			case 21:
				result = "api forbbiden";
				break;
			case 22:
				result = "api has been used over 2000 times today";
				break;
				
		}
		return result;
	}
	
	/**
	 * get ickd's searching status
	 * @param statusCode
	 * @return
	 */
	public static String getExpressStatus(int statusCode){
		String result = "";
		switch(statusCode){
			case 0:
				result = "search error";
				break;
			case 1:
				result = "normal";
				break;
			case 2:
				result = "expressing";
				break;
			case 3:
				result = "accepted";
				break;
			case 4:
				result = "return";
				break;
			case 5:
				result = "other question";
				break;
		}
		return result;
	}
	
}
