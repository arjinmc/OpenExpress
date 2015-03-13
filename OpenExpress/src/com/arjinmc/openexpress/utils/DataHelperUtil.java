package com.arjinmc.openexpress.utils;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DataHelperUtil {
	
	private static DBOpenHeleper databaseHelper = null;
	
	/**
	 * @desciption get database connection
	 * @author Eminem Lu
	 * @email arjinmc@hicsg.com
	 * @create 2015/2/26
	 * @param context
	 * @return
	 */
	public static DBOpenHeleper getHelper(Context context) {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager
					.getHelper(context, DBOpenHeleper.class);
		}
		return databaseHelper;
	}

	/**
	 * @desciption release database connection
	 * @author Eminem Lu
	 * @email arjinmc@hicsg.com
	 * @create 2015/2/26
	 */
	public static void releaseHelper(){
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}
}
