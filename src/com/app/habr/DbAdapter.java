package com.app.habr;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DbAdapter {

	private static final String DATABASE_NAME = "pressure.db";
	private static final String DATABASE_TABLE = "pTable";
	private static final String TABLE_CREATE =
	"create table IF NOT EXISTS  " + DATABASE_TABLE + " ( _id integer primary key	autoincrement," +
	"_time NUMERIC not null, pressure text not null);";
	private static final String TABLE_DROP =
			"drop table IF EXISTS " + DATABASE_TABLE+";";
	SQLiteDatabase myDatabase;
	private Context ctx;
	
	public void createDatabase(Context ctx) {
	this.ctx=ctx;	
	Log.i(DbAdapter.class.getName(), "Creating database ***********************");
	myDatabase = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null);
	try{
	//myDatabase.execSQL(TABLE_DROP);
	
	myDatabase.execSQL(TABLE_CREATE);
	}catch (SQLiteException ex){}
	Log.i(DbAdapter.class.getName(), "Created database ***********************");
	
	}
	
	public void insValue(int press){
		
		
	// �������� ����� ������ �� ���������� ��� �������.
	ContentValues newValues = new ContentValues();
	// ������� �������� ��� ������ ������.
	newValues.put("_time", System.currentTimeMillis());
	newValues.put("pressure", press);
		// �������� ������ � ���� ���� ������.
	myDatabase.insert(DATABASE_TABLE, null, newValues);
	}

	public List<Integer> getValue() {
		// ���������� ��� ������ ��� ������� � �������� �������, ��� ����������
		String[] result_columns = new String[] {"_id","_time", "pressure"};
		Cursor allRows = myDatabase.query(true, DATABASE_TABLE, result_columns,	null, null, null, null, null, null);
		ArrayList<Integer> l = new ArrayList<Integer>();
		if (allRows.moveToFirst()) {
			// ���������� �� ������ ������.
			do {
				Integer s0 = allRows.getInt(0);
				Long s1 = allRows.getLong(1);
				String s2 = allRows.getString(2);
				Log.i(DbAdapter.class.getName(), "readed "+s0+" "+s1+" "+s2);
				l.add(746);
			} while(allRows.moveToNext());
			}
		return l;
	}
	
}
