package cn.mointe.itservice.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class QuestionProviderTest extends AndroidTestCase {

	private static final String TAG = "QuestionProviderTest";
	
	public void TestSave() throws Throwable {
		ContentResolver contentResolver = this.getContext().getContentResolver();
		Uri insertUri = Uri.parse("content://cn.mointe.itservice.providers.questionprovider");
		ContentValues contentValues = new ContentValues();
		
		contentValues.put("", "");
		contentValues.put("", "");
		contentValues.put("", "");
		contentValues.put("", "");
		contentValues.put("", "");
		contentValues.put("", "");
		
		Uri uri = contentResolver.insert(insertUri, contentValues);
		Log.i(TAG, uri.toString());
	}
	public void testUpdate() throws 
}
