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

public class EquipmentCategoryProvider extends ContentProvider {

	private DBHelperEquipment mDBHelperEquipment;

	public static final String ITSERVICE_CATEGORY_TYPE = "vnd.android.cursor.dir/category";
	public static final String ITSERVICE_CATEGORY_ITEM_TYPE = "vnd.android.cursor.item/category";
	public static final String AUTHORITY = "cn.mointe.itservice.providers.equipmentcatagoryprovider";

	public static final int CATEGORYS = 1;
	public static final int CATEGORY = 2;

	public static final Uri ITSERVICE_CATEGORY_URI = Uri.parse("content:// "
			+ AUTHORITY + "/category");

	public static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "category", CATEGORYS);
		uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY);
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
		case CATEGORYS:
			return db.query("category", projection, selection, selectionArgs,
					null, null, sortOrder);
		case CATEGORY:
			long id = ContentUris.parseId(uri);
			String where = DBHelperEquipment.COLUMN_CATEGORY_ID + "=" + id;
			where += !TextUtils.isEmpty(selection) ? " and (" + selection
					+ " ) " : " ";
			return db.query(DBHelperEquipment.TABLE_CATEGORY_NAME, projection,
					where, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		// return null;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case CATEGORYS:
			return ITSERVICE_CATEGORY_TYPE;
		case CATEGORY:
			return ITSERVICE_CATEGORY_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("UriKnown URI" + uri);
		}
		// return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mDBHelperEquipment.getWritableDatabase();
		long id = 0;
		switch (uriMatcher.match(uri)) {
		case CATEGORYS:
			id = db.insert(DBHelperEquipment.COLUMN_CATEGORY_ID, null, values);
			return ContentUris.withAppendedId(uri, id);
		case CATEGORY:
			id = db.insert(DBHelperEquipment.TABLE_CATEGORY_NAME, null, values);
			String path = uri.toString();
			return Uri.parse(path.substring(0, path.lastIndexOf("/")) + id);
		default:
			throw new IllegalArgumentException("Unknown URI" + uri);
		}
		// return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = mDBHelperEquipment.getWritableDatabase();
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case CATEGORYS:
			count = db.delete("category", selection, selectionArgs);
			break;
		case CATEGORY:
			long categoryid = ContentUris.parseId(uri);
			String where = "id = " + categoryid;
			where += !TextUtils.isEmpty(selection) ? " and (" + selection
					+ " )" : " ";
			count = db.delete("category", where, selectionArgs);
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
		case CATEGORYS:
			count = db.update("category", values, selection, selectionArgs);
			break;
		case CATEGORY:
			long categoryid = ContentUris.parseId(uri);
			String where = "id = " + categoryid;
			where += !TextUtils.isEmpty(selection) ? "and(" + selection + ")"
					: " ";
			count = db.update("category", values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("UnKnown URI" + uri);
		}
		db.close();
		return count;
	}

}
