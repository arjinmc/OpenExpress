package com.arjinmc.openexpress.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.arjinmc.openexpress.EditDeliverActivity;
import com.arjinmc.openexpress.R;
import com.arjinmc.openexpress.model.DeliverBean;
import com.arjinmc.openexpress.utils.DataHelperUtil;
import com.arjinmc.openexpress.utils.DeliverUtil;
import com.arjinmc.openexpress.utils.ViewHolder;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * @desciption Deliver the contacts for delivers
 * @author eminem
 * @email eminem@hicsg.com
 * @website arjinmc.com
 * @create 2015/3/12
 */
public class DeliverFragment extends Fragment{
	
	private TextView tvAdd;
	private ListView lvDelivers;
	private ListAdapter mAdapter;
	
	
	private List<DeliverBean> deliverBeans; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_deliver, null);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		tvAdd = (TextView) view.findViewById(R.id.tv_add);
		lvDelivers = (ListView) view.findViewById(R.id.lv_records);
		deliverBeans = new ArrayList<DeliverBean>();
		
		tvAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent addIntent = new Intent(getActivity(),EditDeliverActivity.class);
				addIntent.setAction(DeliverUtil.ACTION_ADD);
				startActivityForResult(addIntent,1);
			}
		});
		
		showData();
	}
	
	private class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return deliverBeans.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return deliverBeans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final int currentPosition = position;

			if(convertView==null){
				convertView = LayoutInflater.from(getActivity())
						.inflate(R.layout.item_deliver, null);
			}
			
			TextView tvName = ViewHolder.get(convertView, R.id.item_tv_name);
			TextView tvPhone = ViewHolder.get(convertView, R.id.item_tv_phone);
			tvName.setText(deliverBeans.get(position).getName());
			tvPhone.setText(deliverBeans.get(position).getPhone());
			ImageButton ibCall = ViewHolder.get(convertView, R.id.item_ib_call);
			ibCall.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent phoneIntent = new Intent();
					phoneIntent.setAction("android.intent.action.CALL");
					phoneIntent.setData(Uri.parse("tel:"
							+deliverBeans.get(currentPosition).getPhone()));
					startActivity(phoneIntent);
				}
			});
			ImageButton ibDelete = ViewHolder.get(convertView, R.id.item_ib_delete);
			ibDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					RuntimeExceptionDao<DeliverBean, Integer> simpleDao 
						= DataHelperUtil.getHelper(getActivity())
							.getDeliverDao();
					simpleDao.deleteById(deliverBeans.get(currentPosition).getId());
					deliverBeans.remove(currentPosition);
					mAdapter.notifyDataSetChanged();
				}
			});
			
			return convertView;
		}
		
	}
	
	
	private void showData(){
		RuntimeExceptionDao<DeliverBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(getActivity())
				.getDeliverDao();
		deliverBeans = simpleDao.queryForAll();
		mAdapter = new ListAdapter();
		lvDelivers.setAdapter(mAdapter);
		
		lvDelivers.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int postion, long id) {
				Intent editInent = new Intent(getActivity(),EditDeliverActivity.class);
				editInent.setAction(DeliverUtil.ACTION_EDIT);
				editInent.putExtra("deliver", deliverBeans.get(postion));
				startActivity(editInent);
				return false;
			}
		});
	}

}
