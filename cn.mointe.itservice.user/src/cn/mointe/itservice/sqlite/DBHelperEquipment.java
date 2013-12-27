package cn.mointe.itservice.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperEquipment extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "itservice.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_EQUIPMENT_NAME = "equipment";
	public static final String COLUMN_EQUIPMENT_ID = "_id";
	public static final String COLUMN_EQUIPMENT_NAME = "equipmentname";
	public static final String COLUMN_EQUIPMENT_PARAMETER = "equipmentparameter";
	public static final String COLUMN_EQUIPMENT_PRICE = "equipmentprice";
	public static final String COLUMN_EQUIPMENT_STATE = "equipmentstate";
	public static final String COLUMN_EQUIPMENT_PURCHASE_TIME = "equipmentpurchasetime";
	public static final String COLUMN_EQUIPMENT_CATEGORY = "equipmentcategory";
	
	public static final String TABLE_CATEGORY_NAME = "category";
	public static final String COLUMN_CATEGORY_ID = "_id";
	public static final String COLUMN_CATEGORYB = "categoryb";
	public static final String COLUMN_CATEGORYS = "categorys";
	
	public static final String QUESTION_TABLE_NAME = "question";// table_name
	public static final String QUESTION_COLUMN_ID = "_id";
	public static final String QUESTION_COLUMN_TYPE_ID = "typeid";
	public static final String QUESTION_COLUMN_DISPOSE_PEOPLE = "disposepeople";
	
	public static final String QUESTION_COLUMN_CREATE_TIME = "createtime";
	public static final String QUESTION_COLUMN_STATE = "state";
	public static final String QUESTION_COLUMN_COMMENTS = "comments";
	
	public static final String QUESTION_COLUMN_USER_ID = "userid";
	public static final String QUESTION_COLUMN_FINISH_TIME = "finishtime";
	public static final String QUESTION_COLUMN_GRADE_ID = "gradeid";			
	public static final String QUESTION_COLUMN_MACHINE_ID = "machineid";		//machine_id
	
	public static final String TABLE_TYPE_NAME = "questiontype";
	public static final String COLUMN_TYPE_ID = "_id";
	public static final String COLUMN_BIG_NAME = "big";
	
	public static final String COLUMN_SMALL_NAME = "small";
	
	public static final String TABLE_TYPE_NAME_SQL = "CREATE TABLE" + TABLE_TYPE_NAME
			+"(" + COLUMN_TYPE_ID + " integer primary key autoincrement," + COLUMN_BIG_NAME 
			+ " text not null," + COLUMN_SMALL_NAME + " text not null";
	
	public static final String TABLE_CATEGORY_SQL = "CREATE TABLE" 
			+ TABLE_CATEGORY_NAME + "(" + COLUMN_CATEGORY_ID
			+ " integer primary key autoincrement,"
			+ COLUMN_CATEGORY_ID + " text not null," 
			+ COLUMN_CATEGORYB + " text not null," 
			+COLUMN_CATEGORYS + " text not null)" ;
	
	public static final String TABLE_EQUIPMENT_SQL = "CREATE TABLE" + TABLE_EQUIPMENT_NAME 
			+"(" + COLUMN_EQUIPMENT_ID + " integer primary key autoincrement,"
			+COLUMN_EQUIPMENT_NAME + " text not null," + COLUMN_EQUIPMENT_PARAMETER 
			+ " text not null, " + COLUMN_EQUIPMENT_PRICE + " real not null, "
			+ COLUMN_EQUIPMENT_STATE + " text not null,"
			+ COLUMN_EQUIPMENT_PURCHASE_TIME + " text not null, "
			+ COLUMN_EQUIPMENT_CATEGORY + " text not null) ";
	
	public static final String QUESTION_TABLE_NAME_SQL = "CREATE TABLE" + QUESTION_TABLE_NAME 
			+ "(" + QUESTION_COLUMN_ID  +  " integer primary key autoincrement," + QUESTION_COLUMN_TYPE_ID + " text not null,"
			+ QUESTION_COLUMN_DISPOSE_PEOPLE + " text not null," + QUESTION_COLUMN_CREATE_TIME + " real not null,"
			+ QUESTION_COLUMN_STATE + " text not null," + QUESTION_COLUMN_COMMENTS + "text not null,"
			+QUESTION_COLUMN_USER_ID + " text not null," + QUESTION_COLUMN_FINISH_TIME + " real not null," 
			+QUESTION_COLUMN_GRADE_ID + " text not null," + QUESTION_COLUMN_MACHINE_ID +" text not null";
	
	public DBHelperEquipment(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_EQUIPMENT_SQL);
		db.execSQL(TABLE_CATEGORY_SQL);
		db.execSQL(QUESTION_TABLE_NAME_SQL);
		db.execSQL(TABLE_TYPE_NAME_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w("DBHelperMahcine", "Upgrading from version"
				+ oldVersion + "to" + newVersion
				+"all old data will be destroyed.");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT_NAME + TABLE_CATEGORY_NAME
				+ TABLE_CATEGORY_NAME + TABLE_TYPE_NAME_SQL);
		onCreate(db);
	}
}
