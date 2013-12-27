package cn.mointe.itservice.provider;

import cn.mointe.itservice.sqlite.DBHelperEquipment;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class QuestionTypeProvider extends ContentProvider {

	public static final String ITSERVICE_QUESTIONTYPE_TYPE = "vnd.android.cursor.dir/questiontype";
	public static final String ITSERVICE_QUESTIONTYPE_ITEM_TYPE = "vnd.android.cursor.item/questiontype";
	public static final String AUTHORITY = "cn.mointe.itservice.providers.questiontypeprovider";

	public static final int QUESTIONTYPES = 1;
	public static final int QUESTIONTYPE = 2;

	public static final Uri ITSERVICE_QUESTIONTYPE_URI = Uri.parse("content://"
			+ AUTHORITY + "/questiontype");

	private DBHelperEquipment mDBHelperMachine;
	public static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "questiontype", QUESTIONTYPES);
		uriMatcher.addURI(AUTHORITY, "questiontype/#", QUESTIONTYPE);
	}

	@Override
	public boolean onCreate() {
		mDBHelperMachine = new DBHelperEquipment(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mDBHelperMachine.getWritableDatabase();

		switch (uriMatcher.match(uri)) {
		case QUESTIONTYPES:
			return db.query("questiontype", projection, selection,
					selectionArgs, null, null, sortOrder);
		case QUESTIONTYPE:
			long id = ContentUris.parseId(uri);
			String where = DBHelperEquipment.COLUMN_TYPE_ID + "=" + id;
			where += !TextUtils.isEmpty(selection) ? "and(" + selection + ")" :"";
			return db.query(DBHelperEquipment.COLUMN_BIG_NAME, projection, where, selectionArgs, null, null, sortOrder);
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		//return null;
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)) {
		case QUESTIONTYPES:
			return ITSERVICE_QUESTIONTYPE_TYPE;
		case QUESTIONTYPE:
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		//return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDBHelperMachine.getWritableDatabase();
		long id = 0;
		switch (uriMatcher.match(uri)) {
		case QUESTIONTYPES:
			id = db.insert(DBHelperEquipment.TABLE_TYPE_NAME, null, values);
			return ContentUris.withAppendedId(uri, id);
		case QUESTIONTYPE:
			id = db.insert(DBHelperEquipment.TABLE_TYPE_NAME, null, values);
			String path = uri.toString();
			return Uri.parse(path.substring(0,path.lastIndexOf("/")) + id);
			default:
				throw new IllegalArgumentException("Unknown URI" +uri);
		}
		//return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDBHelperMachine.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case QUESTIONTYPES:
			count = db.delete("questiontype", selection, selectionArgs);
			break;
		case QUESTIONTYPE:
			long questiontypeid = ContentUris.parseId(uri);
			String where = "id = " + questiontypeid;
			where += !TextUtils.isEmpty(selection) ? "and (" + selection + " )" :" ";
			count = db.delete("questiontype", where, selectionArgs);
			break;
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		db.close();
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SQLiteDatabase db = mDBHelperMachine.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case QUESTIONTYPES:
			count = db.update("questiontypes",values, selection,selectionArgs);
			break;
		case QUESTIONTYPE:
			long questionid = ContentUris.parseId(uri);
			String where = "id = " + questionid;
			where += !TextUtils.isEmpty(selection) ? "and (" + selection + ")" :" ";
			count = db.update("questiontype", values, where, selectionArgs);
			break;
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		db.close();
		return count;
	}

}
