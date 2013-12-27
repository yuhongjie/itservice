package cn.mointe.itservice.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class EquipmentProviderTest extends AndroidTestCase {

	private static final String TAG = "EquipmentContentProviderTest";
	
	public void testSave() throws Throwable {
		ContentResolver contentResolver = this.getContext().getContentResolver();
		Uri insertUri = Uri.parse("content://cn.mointe.itservice.providers.equipmentprovider");
		ContentValues contentvalues = new ContentValues();
		
		contentvalues.put("","" );
		contentvalues.put(" "," ");
		contentvalues.put(" "," ");
		contentvalues.put(" "," ");
		contentvalues.put(" "," ");
		contentvalues.put(" "," ");
		contentvalues.put(" "," ");
		Uri uri = contentResolver.insert(insertUri,contentvalues);
		Log.i(TAG,uri.toString());
	}
	public void testUpdate() throws Throwable {
		ContentResolver contentResolver = this.getContext().getContentResolver();
		Uri updateUri = Uri.parse("content://cn.mointe.itservice.providers.equipmentprovider");
		ContentValues contentValues = new ContentValues();
		contentValues.put("equipment ", "");
		contentResolver.update(updateUri, contentValues, null, null);
	}
	public void testFind() throws Throwable {
		ContentResolver contentResolver = this.getContext().getContentResolver();
		Uri uri = Uri.parse("content://cn.mointe.itservice.providers.equipmentprovider");
		Cursor cursor = contentResolver.query(uri, null, null, null, "_id");
		while(cursor.moveToNext()) {
			int equipmentid = cursor.getInt(cursor.getColumnIndex("_id"));
			String equipmentname = cursor.getString(cursor.getColumnIndex("equipmentname"));
			String equipmentparameter = cursor.getString(cursor.getColumnIndex("equipmentparameter"));
			String equipmentprice = cursor.getString(cursor.getColumnIndex("equipmentprice"));
			String equipmentstate = cursor.getString(cursor.getColumnIndex("equipmentstate"));
			String equipmentpurchasetime = cursor.getString(cursor.getColumnIndex("equipmentpurchasetime"));
			String equipmentcategory = cursor.getString(cursor.getColumnIndex("equipmentcategory"));
			
			Log.i(TAG,"_id  = " + equipmentid + "equipmentname = " + equipmentname + ",equipmentparameter = " 
			+ equipmentparameter + "equipmentprice = " + equipmentprice + "equipmentstate = " + equipmentstate 
			+  "equipmentpurchasetime = " + equipmentpurchasetime + "machinecategory = " + equipmentcategory);
		}
		cursor.close();
	}
	public void testDelete() throws Throwable {
		ContentResolver contentResolver = this.getContext().getContentResolver();
		Uri uri = Uri.parse("content://cn.mointe.itservice.providers.equipmentprovider/1");
		contentResolver.delete(uri,null,null);
	}
}
