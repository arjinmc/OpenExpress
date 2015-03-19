package com.arjinmc.openexpress;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.arjinmc.openexpress.adapter.RecordAdapter;
import com.arjinmc.openexpress.http.ExpressDetailResponse;
import com.arjinmc.openexpress.http.ExpressDetailRunnable;
import com.arjinmc.openexpress.utils.ExpressUtil;
import com.arjinmc.openexpress.utils.HttpHelper;

public class ExpressDetailActivity extends Activity {
	
	private ImageButton ibBack;
	private TextView tvTitle;
	private ListView lvRecords;
	
	private String companyCode;
	private String billCode;
	
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HttpHelper.REQUEST_FINISH:
				showDetail((ExpressDetailResponse)msg.getData().get("response"));
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_express_detail);
		
		Intent intent = getIntent();
		companyCode = intent.getStringExtra("company");
		billCode = intent.getStringExtra("code");
		
		init();
		
	}
	
	
	private void init(){
		
		tvTitle = (TextView) findViewById(R.id.tv_title);
		
		ibBack = (ImageButton) findViewById(R.id.ib_back);
		lvRecords = (ListView) findViewById(R.id.lv_records);
		
		ibBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ExpressDetailActivity.this.finish();
			}
		});
		
		tvTitle.setText(ExpressUtil.getExpressName(
				ExpressDetailActivity.this, companyCode)+billCode);
		
		executor.execute(new ExpressDetailRunnable(companyCode
				, billCode, mHandler, HttpHelper.REQUEST_FINISH
				, ExpressDetailActivity.this));
	}
	
	
	/**
	 * show express bill detail
	 * 
	 * */
	private void showDetail(ExpressDetailResponse response){
		if(response.getErrCode()==0){
			RecordAdapter recordAdapter = new RecordAdapter(ExpressDetailActivity.this
					, response.getData());
			lvRecords.setAdapter(recordAdapter);
		}
	}

}
