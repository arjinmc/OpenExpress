package com.arjinmc.openexpress.fragments;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.arjinmc.openexpress.R;
import com.arjinmc.openexpress.adapter.RecordAdapter;
import com.arjinmc.openexpress.http.ExpressDetailResponse;
import com.arjinmc.openexpress.http.ExpressDetailRunnable;
import com.arjinmc.openexpress.model.ExpressBean;
import com.arjinmc.openexpress.model.ExpressRecordBean;
import com.arjinmc.openexpress.utils.DBOptionUtil;
import com.arjinmc.openexpress.utils.DataHelperUtil;
import com.arjinmc.openexpress.utils.ExpressUtil;
import com.arjinmc.openexpress.utils.HttpHelper;
import com.arjinmc.openexpress.utils.ViewHolder;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * @desciption Home get the express info
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class HomeFragment extends Fragment implements OnClickListener{
	
	private Spinner spCompany;
	private EditText etBill;
	private Button btnSearch;
	private TextView tvError;
	private ListView lvRecords;
	
	/**express company info*/
	private List<ExpressBean> mExpressBeans;
	/**for save search record*/
	private ExpressRecordBean recordBean;
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);
		
		spCompany = (Spinner) view.findViewById(R.id.sp_company);
		etBill = (EditText) view.findViewById(R.id.et_bill_code);
		btnSearch = (Button) view.findViewById(R.id.btn_search);
		tvError = (TextView) view.findViewById(R.id.tv_error);
		lvRecords = (ListView) view.findViewById(R.id.lv_records);
		
		btnSearch.setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initData();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btn_search:
			searchExpress();
			break;
		}
		
	}
	
	/**
	 * search and check empty
	 * 
	 * */
	private void searchExpress(){
		String billCode = etBill.getText().toString();
		//for testing,just select the first express company
		billCode = "411424930730";
		if(TextUtils.isEmpty(billCode)){
			tvError.setText(getActivity().getString(R.string.express_bill_empty));
			tvError.setVisibility(View.VISIBLE);
		}else{
			
			String companyCode = ((ExpressBean)spCompany.getSelectedItem()).getNumber();
			recordBean.setCompanyCode(companyCode);
			recordBean.setBillCode(billCode);
			
			tvError.setVisibility(View.GONE);
			executor.execute(new ExpressDetailRunnable(companyCode
					, billCode, mHandler, HttpHelper.REQUEST_FINISH
					, getActivity()));
		}
	}
	
	private void initData(){
		mExpressBeans = ExpressUtil.getExpressCode(getActivity());
		ExpressCompanyAdapter lAdapter = new ExpressCompanyAdapter();
		spCompany.setAdapter(lAdapter);
		
		recordBean = new ExpressRecordBean();
	}
	
	
	/**
	 * the adapter for spinner express company
	 * */
	private class ExpressCompanyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mExpressBeans.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mExpressBeans.get(position);
		}

		@Override
		public long getItemId(int id) {
			// TODO Auto-generated method stub
			return id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			
			if(convertView == null){
				convertView = LayoutInflater.from(getActivity())
						.inflate(R.layout.item_home_express, null);
			}
			
			TextView itemCompanyName = ViewHolder.get(convertView, R.id.item_company_name);
			itemCompanyName.setText(mExpressBeans.get(position).getName());
			return convertView;
		}
	}
	
	/**
	 * show express bill detail
	 * 
	 * */
	private void showDetail(ExpressDetailResponse response){
		if(response.getErrCode()==0){
			RecordAdapter recordAdapter = new RecordAdapter(getActivity(), response.getData());
			lvRecords.setAdapter(recordAdapter);
			
			
			//save search record
			DBOptionUtil.saveExpressBill(getActivity(), recordBean);
			
			lvRecords.setVisibility(View.VISIBLE);
			tvError.setVisibility(View.GONE);
		}else{
			tvError.setText(response.getMessage());
			
			lvRecords.setVisibility(View.GONE);
			tvError.setVisibility(View.VISIBLE);
		}
	}

}
