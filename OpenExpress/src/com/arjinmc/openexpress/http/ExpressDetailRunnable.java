package com.arjinmc.openexpress.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arjinmc.openexpress.model.ExpressDetailBean;
import com.arjinmc.openexpress.utils.ExpressUtil;
import com.arjinmc.openexpress.utils.HttpHelper;

/**
 * @desciption a thread for get express detail
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class ExpressDetailRunnable implements Runnable {
	
	
	private String mCompanyCode;
	private String mBillCode;
	private Handler mHandler;
	private int mMsgCode;
	private Context mContext;

	public ExpressDetailRunnable(String companyCode,String billCode
			,Handler handler,int msgCode,Context context) {
		mCompanyCode = companyCode;
		mBillCode = billCode;
		mHandler = handler;
		mMsgCode = msgCode;
		mContext = context;
	}
	@Override
	public void run() {
	
		String result = HttpHelper.getHttp(mContext, 
				ExpressUtil.getExpressURL(mCompanyCode, mBillCode));
		ExpressDetailResponse response = new ExpressDetailResponse();
		if(result.equals(HttpHelper.TIMEOUT)){
			response.setErrCode(HttpHelper.REQUEST_TIMEOUT);
			response.setMessage(HttpHelper.TIMEOUT);
		}else{
			JSONObject jObject;
			try {
				jObject = new JSONObject(result);
				response.setStatus(jObject.getInt("status"));
				response.setMessage(jObject.getString("message"));
				response.setErrCode(jObject.getInt("errCode"));
				JSONArray jArray = jObject.getJSONArray("data");
				if(jArray!=null && jArray.length()!=0){
					List<ExpressDetailBean> data = new ArrayList<ExpressDetailBean>();
					for(int i=0;i<jArray.length();i++){
						JSONObject jData = (JSONObject) jArray.get(i);
						ExpressDetailBean record = new ExpressDetailBean();
						record.setTime(jData.getString("time"));
						record.setContent(jData.getString("context"));
						data.add(record);
					}
					response.setData(data);
				}
				response.setHtml(jObject.getString("html"));
				response.setTel(jObject.getString("tel"));
			} catch (JSONException e) {
				response.setErrCode(HttpHelper.REQUEST_TIMEOUT);
				response.setMessage("json parse error");
				e.printStackTrace();
			}
		}
		
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putSerializable("response", response);
		msg.setData(data);
		msg.what = mMsgCode;
		mHandler.sendMessage(msg);

	}

}
