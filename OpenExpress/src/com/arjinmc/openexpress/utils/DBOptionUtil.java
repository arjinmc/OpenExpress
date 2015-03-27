package com.arjinmc.openexpress.utils;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.arjinmc.openexpress.model.DeliverBean;
import com.arjinmc.openexpress.model.ExpressRecordBean;
import com.j256.ormlite.dao.RuntimeExceptionDao;

/**
 * @desciption database options
 * @author Eminem Lu
 * @email arjinmc@hicsg.com
 * @create 2015/3/27
 */
public class DBOptionUtil {
	
	/**
	 * @desciption save what has been search effective
	 * @author Eminem Lu
	 * @param context
	 * @param record
	 */
	public static void saveExpressBill(Context context,ExpressRecordBean record){
		RuntimeExceptionDao<ExpressRecordBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getExpressBillDao();
		try {
			if(simpleDao.queryBuilder()
					.where().eq("companyCode", record.getCompanyCode())
					.and().eq("billCode", record.getBillCode())
					.query().size()==0){
				simpleDao.create(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @desciption delete a bill from history
	 * @author Eminem Lu
	 * @param context
	 * @param record
	 */
	public static void deleteExpressBill(Context context,ExpressRecordBean record){
		RuntimeExceptionDao<ExpressRecordBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getExpressBillDao();
		simpleDao.delete(record);
	}
	
	/**
	 * @desciption get all history
	 * @author Eminem Lu
	 * @param context
	 * @return
	 */
	public static List<ExpressRecordBean> getAllExpressBill(Context context){
		RuntimeExceptionDao<ExpressRecordBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getExpressBillDao();
		return simpleDao.queryForAll();
	}
	
	/**
	 * @desciption clear all records for history
	 * @author Eminem Lu
	 * @param context
	 */
	public static void clearHistory(Context context){
		RuntimeExceptionDao<ExpressRecordBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getExpressBillDao();
		try {
			simpleDao.deleteBuilder().delete();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @desciption save deliver
	 * @author Eminem Lu
	 * @param context
	 * @param deliver
	 */
	public static void saveDeliver(Context context,DeliverBean deliver){
		RuntimeExceptionDao<DeliverBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getDeliverDao();
		try {
			if(simpleDao.queryBuilder()
					.where().eq("name", deliver.getName())
					.and().eq("phone", deliver.getPhone())
					.query().size()==0){
				simpleDao.create(deliver);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @desciption updateDeliver
	 * @author Eminem Lu
	 * @param context
	 * @param deliver
	 */
	public static void updateDeliver(Context context,DeliverBean deliver){
		RuntimeExceptionDao<DeliverBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getDeliverDao();
		simpleDao.update(deliver);
	}
	
	/**
	 * @desciption query all delivers
	 * @author Eminem Lu
	 * @param context
	 * @return
	 */
	public static List<DeliverBean> getAllDelivers(Context context){
		RuntimeExceptionDao<DeliverBean, Integer> simpleDao 
			= DataHelperUtil.getHelper(context).getDeliverDao();
		return simpleDao.queryForAll();
	}

}
