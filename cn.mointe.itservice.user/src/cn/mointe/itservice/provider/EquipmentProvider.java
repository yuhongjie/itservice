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

public class EquipmentProvider extends ContentProvider {

	public static final String ITSERVICE_EQUIPMENT_TYPE = "vnd.android.cursor.dir/equipment";
	public static final String ITSERVICE_EQUIPMENT_ITEM_TYPE = "vnd.andoid.cursor.item/equipment";
	public static final String AUTHORITY = "cn.mointe.itservice.providers.equipmentprovider";

	public static final int EQUIPMENTS = 1;
	public static final int EQUIPMENT = 2;

	public static final Uri ITSERVICE_EQUIPMENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/equipment");

	private DBHelperEquipment mDBHelperEquipment = null;
	// private SQLiteDatabase mDatabase;

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "equipment", EQUIPMENTS);
		uriMatcher.addURI(AUTHORITY, "equipment/#", EQUIPMENT);
	}

	@Override
	public boolean onCreate() {
		mDBHelperEquipment = new DBHelperEquipment(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mDBHelperEquipment.getWritableDatabase();

		switch (uriMatcher.match(uri)) {
		case EQUIPMENTS:
			return db.query("EQUIPMENT", projection, selection, selectionArgs,
					null, null, sortOrder);
		case EQUIPMENT:
			long id = ContentUris.parseId(uri);
			String where = DBHelperEquipment.COLUMN_EQUIPMENT_ID + "=" + id;
			where += !TextUtils.isEmpty(selection) ? " and (" + selection + ")"
					: " ";
			return db.query(DBHelperEquipment.TABLE_EQUIPMENT_NAME, projection,
					where, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		// return null;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case EQUIPMENTS:
			return ITSERVICE_EQUIPMENT_TYPE;
		case EQUIPMENT:
			return ITSERVICE_EQUIPMENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Uriknown URI" + uri);
		}
		// return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDBHelperEquipment.getWritableDatabase();
		long id = 0;
		switch (uriMatcher.match(uri)) {
		case EQUIPMENTS:
			id = db.insert(DBHelperEquipment.TABLE_EQUIPMENT_NAME, null, values);
			// String path = uri.toString();
			return ContentUris.withAppendedId(uri, id);
		case EQUIPMENT:
			id = db.insert(DBHelperEquipment.TABLE_EQUIPMENT_NAME, null, values);
			String path = uri.toString();
			return Uri.parse(path.substring(0, path.lastIndexOf("/")) + id);
		default:

			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		// return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDBHelperEquipment.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case EQUIPMENTS:
			count = db.delete("EQUIPMENT", selection, selectionArgs);
			break;
		case EQUIPMENT:
			long EQUIPMENTid = ContentUris.parseId(uri);
			String where = "id = " + EQUIPMENTid;
			where += !TextUtils.isEmpty(selection) ? "and (" + selection + ")"
					: " ";
			count = db.delete("EQUIPMENT", where, selectionArgs);
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
		SQLiteDatabase db = mDBHelperEquipment.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case EQUIPMENTS:
			count = db.update("EQUIPMENT", values, selection, selectionArgs);
			break;
		case EQUIPMENT:
			long questionid = ContentUris.parseId(uri);
			String where = "id = " + questionid;
			where += !TextUtils.isEmpty(selection) ? " and(" + selection + ")"
					: " ";
			count = db.update("EQUIPMENT", values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		db.close();
		return count;
	}

}
