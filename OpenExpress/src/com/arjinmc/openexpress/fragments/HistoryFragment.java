package com.arjinmc.openexpress.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.arjinmc.openexpress.ExpressDetailActivity;
import com.arjinmc.openexpress.R;
import com.arjinmc.openexpress.adapter.HistoryAdapter;
import com.arjinmc.openexpress.model.ExpressRecordBean;
import com.arjinmc.openexpress.utils.DBOptionUtil;
import com.arjinmc.openexpress.utils.DataHelperUtil;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * @desciption History records for searching
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class HistoryFragment extends Fragment {
	
	private List<ExpressRecordBean> recordBeans;
	private ListView lvRecords;
	private HistoryAdapter mAdapter;
	
	private Button btnClear;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_history, null);
		lvRecords = (ListView) view.findViewById(R.id.lv_records);
		btnClear = (Button) view.findViewById(R.id.btn_clear);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		init();
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(){
		
		recordBeans = DBOptionUtil.getAllExpressBill(getActivity());
		mAdapter = new HistoryAdapter(getActivity(), recordBeans);
		lvRecords.setAdapter(mAdapter);
		lvRecords.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent itemIntent = new Intent(getActivity(),ExpressDetailActivity.class);
				itemIntent.putExtra("company", recordBeans.get(position).getCompanyCode());
				itemIntent.putExtra("code", recordBeans.get(position).getBillCode());
				startActivity(itemIntent);
				
			}
		});
		
		btnClear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DBOptionUtil.clearHistory(getActivity());
				recordBeans = DBOptionUtil.getAllExpressBill(getActivity());
				mAdapter = new HistoryAdapter(getActivity(), recordBeans);
				lvRecords.setAdapter(mAdapter);
			}
		});
	}

}
