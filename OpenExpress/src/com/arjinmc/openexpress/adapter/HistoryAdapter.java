package com.arjinmc.openexpress.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arjinmc.openexpress.R;
import com.arjinmc.openexpress.model.ExpressRecordBean;
import com.arjinmc.openexpress.utils.DataHelperUtil;
import com.arjinmc.openexpress.utils.ExpressUtil;
import com.arjinmc.openexpress.utils.ViewHolder;
import com.j256.ormlite.dao.RuntimeExceptionDao;


/**
 * @desciption search express history adapter
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 */
public class HistoryAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<ExpressRecordBean> mRecords;
	
	public HistoryAdapter(Context context,List<ExpressRecordBean> records) {
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
		
		final int currentPostion = position;
		
		if(convertView == null){
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.item_history, null);
		}
		
		TextView tvTime = ViewHolder.get(convertView, R.id.item_tv_company);
		TextView tvContext = ViewHolder.get(convertView, R.id.item_tv_code);
		tvTime.setText(ExpressUtil.getExpressName(mContext, mRecords.get(position)
				.getCompanyCode()));
		tvContext.setText(mRecords.get(position).getBillCode());
		ImageButton ibDelete = ViewHolder.get(convertView, R.id.item_ib_delete);
		ibDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RuntimeExceptionDao<ExpressRecordBean, Integer> simpleDao 
					= DataHelperUtil.getHelper(mContext).getExpressBillDao();
				simpleDao.delete(mRecords.get(currentPostion));
				mRecords.remove(currentPostion);
				notifyDataSetChanged();
			}
		});
		
		return convertView;
	}

}
