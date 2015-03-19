package com.arjinmc.openexpress.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.arjinmc.openexpress.ExpressDetailActivity;
import com.arjinmc.openexpress.R;
import com.arjinmc.openexpress.adapter.HistoryAdapter;
import com.arjinmc.openexpress.model.ExpressRecordBean;
import com.arjinmc.openexpress.utils.DataHelperUtil;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * @desciption History records for searching
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2015/3/12
 */
public class HistoryFragment extends Fragment {
	
	private List<ExpressRecordBean> recordBeans;
	private ListView lvRecords;
	private HistoryAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_history, null);
		lvRecords = (ListView) view.findViewById(R.id.lv_records);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		init();
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(){
		RuntimeExceptionDao<ExpressRecordBean, Integer> simpleDao = DataHelperUtil.getHelper(getActivity())
				.getExpressBillDao();
		recordBeans = simpleDao.queryForAll();
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
	}

}
