package com.arjinmc.openexpress;

import java.sql.SQLException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arjinmc.openexpress.model.DeliverBean;
import com.arjinmc.openexpress.utils.DataHelperUtil;
import com.arjinmc.openexpress.utils.DeliverUtil;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * @desciption this activity is for add or modify deliver contacts
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class EditDeliverActivity extends Activity implements OnClickListener{
	
	private String mAction;
	private DeliverBean mDeliverBean;
	
	private ImageButton ibBack;
	private TextView tvTitle;
	
	private EditText etName;
	private EditText etPhone;
	
	private TextView tvError;
	private Button btnDone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_deliver);
		
		Intent intent = getIntent();
		mAction = intent.getAction();
		mDeliverBean = (DeliverBean) intent.getSerializableExtra("deliver");
		
		init();
	}
	
	private void init(){
		
		ibBack = (ImageButton) findViewById(R.id.ib_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		
		etName = (EditText) findViewById(R.id.et_name);
		etPhone = (EditText) findViewById(R.id.et_phone);
		
		//when the action is edit,show the oraginal info
		if(mAction.equals(DeliverUtil.ACTION_EDIT)){
			etName.setText(mDeliverBean.getName());
			etPhone.setText(mDeliverBean.getPhone());
		}
		
		btnDone = (Button) findViewById(R.id.btn_done);
		tvError = (TextView) findViewById(R.id.tv_error);
		
		ibBack.setOnClickListener(this);
		btnDone.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.ib_back:
			EditDeliverActivity.this.finish();
			break;

		case R.id.btn_done:
			save();
			break;
		}
		
	}
	
	/**
	 * @desciption save the info have been entered
	 * @author eminem
	 * @email eminem@hicsg.com
	 * @website arjinmc.com
	 */
	private void save(){
		String name = etName.getText().toString();
		String phone = etPhone.getText().toString();
		if(TextUtils.isEmpty(name)){
			tvError.setText("Name can not be null");
			tvError.setVisibility(View.VISIBLE);
		}else if(TextUtils.isEmpty(phone)){
			tvError.setText("Phone can not be null");
			tvError.setVisibility(View.VISIBLE);
		}else{
			tvError.setVisibility(View.INVISIBLE);
			
			RuntimeExceptionDao<DeliverBean, Integer> simpleDao 
				= DataHelperUtil.getHelper(EditDeliverActivity.this)
					.getDeliverDao();
			if(mAction.equals(DeliverUtil.ACTION_ADD)){
				DeliverBean tempBean = new DeliverBean();
				tempBean.setName(name);
				tempBean.setPhone(phone);
				try {
					if(simpleDao.queryBuilder()
							.where().eq("name", name)
							.and().eq("phone", phone)
							.query().size()==0){
						simpleDao.create(tempBean);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			//when the action is edit,update the info
			}else{
				mDeliverBean.setName(name);
				mDeliverBean.setPhone(phone);
				simpleDao.update(mDeliverBean);
			}
			
			setResult(RESULT_OK);
			EditDeliverActivity.this.finish();
		}
	}
	
	

}
