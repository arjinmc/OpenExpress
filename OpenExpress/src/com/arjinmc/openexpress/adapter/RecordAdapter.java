package com.arjinmc.openexpress.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arjinmc.openexpress.R;
import com.arjinmc.openexpress.R.id;
import com.arjinmc.openexpress.R.layout;
import com.arjinmc.openexpress.model.ExpressDetailBean;
import com.arjinmc.openexpress.utils.ViewHolder;


/**
 * @desciption express detail adapter
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class RecordAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<ExpressDetailBean> mRecords;
	
	public RecordAdapter(Context context,List<ExpressDetailBean> records) {
		mContext = context;
		mRecords = records;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRecords.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.item_express_detial, null);
		}
		
		TextView tvTime = ViewHolder.get(convertView, R.id.item_tv_time);
		TextView tvContext = ViewHolder.get(convertView, R.id.item_tv_context);
		tvTime.setText(mRecords.get(position).getTime());
		tvContext.setText(mRecords.get(position).getContent());
		
		return convertView;
	}

}
