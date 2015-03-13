package com.arjinmc.openexpress.utils;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.arjinmc.openexpress.model.ExpressBean;
import com.arjinmc.openexpress.model.ExpressRecordBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @desciption database connection util
 * @author Eminem Lu
 * @email arjinmc@hicsg.com
 * @create 2015/2/26
 */
public class DBOpenHeleper extends OrmLiteSqliteOpenHelper{
	
	private final static int DB_VERSION = 1;
	private final static String DB_NAME = "demo.db";
	
	private RuntimeExceptionDao<ExpressRecordBean, Integer> runExpressBill= null;
	
	public DBOpenHeleper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBOpenHeleper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, DB_NAME, factory, DB_VERSION);
	}
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		 try {
			TableUtils.createTableIfNotExists(connectionSource, ExpressRecordBean.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @desciption get Dao for person
	 * @author Eminem Lu
	 * @email arjinmc@hicsg.com
	 * @create 2015/2/26
	 * @return
	 */
	public RuntimeExceptionDao<ExpressRecordBean, Integer> getExpressBillDao() {
		if (runExpressBill == null) {
			runExpressBill = getRuntimeExceptionDao(ExpressRecordBean.class);
		}
		return runExpressBill;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
}
