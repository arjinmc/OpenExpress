package com.arjinmc.openexpress.http;

import java.io.Serializable;
import java.util.List;

import com.arjinmc.openexpress.model.ExpressDetailBean;

/**
 * @desciption the response for get express bill detail
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class ExpressDetailResponse implements Serializable{
	
	private int status;
	private String message;
	private int errCode;
	
	private List<ExpressDetailBean> data;
	private String html;
	private String tel;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public List<ExpressDetailBean> getData() {
		return data;
	}
	public void setData(List<ExpressDetailBean> data) {
		this.data = data;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

}
