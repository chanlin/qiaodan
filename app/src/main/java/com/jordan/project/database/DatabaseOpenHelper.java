package com.jordan.project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/***
 * @author qx
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
	private static DatabaseOpenHelper sInstance = null;
	public static final String DATABASE_NAME = "jordan_database.db";
	public static final int DATABASE_VERSION = 1;
	public static DatabaseOpenHelper getInstance(Context context) {
		if (null == sInstance) {
			sInstance = new DatabaseOpenHelper(context);
		}
		return sInstance;
	}

	private DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createUserInfoTable(db);
		createMotionDetailTable(db);
		createReachDetailTable(db);
		createBluetoothListTable(db);
		createMotionBluetoothDataTable(db);
		createMotionListTable(db);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
	public void createMotionListTable(SQLiteDatabase db) {
		db.execSQL(DatabaseObject.MotionListTable.DROP_SQL);
		db.execSQL(DatabaseObject.MotionListTable.CREATE_SQL);
	}
	public void createMotionBluetoothDataTable(SQLiteDatabase db) {
		db.execSQL(DatabaseObject.MotionBluetoothDataTable.DROP_SQL);
		db.execSQL(DatabaseObject.MotionBluetoothDataTable.CREATE_SQL);
	}
	public void createUserInfoTable(SQLiteDatabase db) {
		db.execSQL(DatabaseObject.UserInfoTable.DROP_SQL);
		db.execSQL(DatabaseObject.UserInfoTable.CREATE_SQL);
	}
	public void createMotionDetailTable(SQLiteDatabase db) {
		db.execSQL(DatabaseObject.MotionDetailTable.DROP_SQL);
		db.execSQL(DatabaseObject.MotionDetailTable.CREATE_SQL);
	}
	public void createReachDetailTable(SQLiteDatabase db) {
		db.execSQL(DatabaseObject.ReachDetailTable.DROP_SQL);
		db.execSQL(DatabaseObject.ReachDetailTable.CREATE_SQL);
	}
	public void createBluetoothListTable(SQLiteDatabase db) {
		db.execSQL(DatabaseObject.BluetoothListTable.DROP_SQL);
		db.execSQL(DatabaseObject.BluetoothListTable.CREATE_SQL);
	}
}