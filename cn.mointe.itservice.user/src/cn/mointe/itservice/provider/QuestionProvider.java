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

public class QuestionProvider extends ContentProvider {

	public static final String ITSERVICE_QUESTION_TYPE = "vnd.android.cursor.dir/question";
	public static final String ITSERVICE_QUESTION_ITEM_TYPE = "vnd.android.cursor.item/question";
	public static final String AUTHORITY = "cn.mointe.itservice.providers.questionprovider";

	public static final int QUESTIONS = 1;
	public static final int QUESTION = 2;

	public static final Uri ITSERVICE_QUESTION_URI = Uri.parse("content://"
			+ AUTHORITY + "/machine");

	private DBHelperEquipment mDBHelperMachine = null;
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "question", QUESTIONS);
		uriMatcher.addURI(AUTHORITY, "question/#", QUESTION);
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
		case QUESTIONS:
			return db.query("question", projection, selection, selectionArgs,
					null, null, sortOrder);
		case QUESTION:
			long id = ContentUris.parseId(uri);
			String where = DBHelperEquipment.QUESTION_COLUMN_ID + " =" +id;
			where += !TextUtils.isEmpty(selection) ? "and(" +selection + ")" : " ";
			return db.query(DBHelperEquipment.QUESTION_TABLE_NAME, projection, where, selectionArgs, null, null, sortOrder);
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
	//	return null;
	}

	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)) {
		case QUESTIONS:
			return ITSERVICE_QUESTION_TYPE;
		case QUESTION:
			return ITSERVICE_QUESTION_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Uriknown URI" + uri);
		}
		//return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDBHelperMachine.getWritableDatabase();
		long id = 0;
		switch(uriMatcher.match(uri)) {
		case QUESTIONS:
			id = db.insert(DBHelperEquipment.QUESTION_TABLE_NAME, null, values);
			return ContentUris.withAppendedId(uri, id);
		case QUESTION:
			id = db.insert(DBHelperEquipment.QUESTION_TABLE_NAME, null, values);
			String path = uri.toString();
			return Uri.parse(path.substring(0,path.lastIndexOf("/")) + id);
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		//return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDBHelperMachine.getWritableDatabase();
		int count = 0;
		switch(uriMatcher.match(uri)) {
		case QUESTIONS:
			 count = db.delete("question", selection, selectionArgs);
			 break;
		case QUESTION:
			long questionid = ContentUris.parseId(uri);
			String where = "id =" + questionid;
			where += !TextUtils.isEmpty(selection) ? "and(" + selection +"(": "";
			count = db.delete("question", where, selectionArgs);
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
		switch(uriMatcher.match(uri)) {
		case QUESTIONS:
			count = db.update("question",values,selection,selectionArgs);
			break;
		case QUESTION:
			long questionid = ContentUris.parseId(uri);
			String where = "id =" +questionid;
			where += !TextUtils.isEmpty(selection) ? "and(" + selection + ")" : "";
			count = db.update("question", values, where, selectionArgs);
			default:
				throw new IllegalArgumentException("Unknown URI" + uri);
		}
		db.close();
		return count;
	}

}
