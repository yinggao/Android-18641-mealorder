package DBLayout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CurrentUserDatabaseHandler {
	
	public static void setCurrentUser(SQLiteDatabase db, String email) {

      ContentValues values = new ContentValues();
      values.put("id", "1");
      values.put("email", email);

      // updating row
      db.update("CurrentUser", values, "id" + "=?",	new String[] { "1" });
	}
	
	public static String getCurrentUser(SQLiteDatabase db) {
		Cursor cursor = db.query("CurrentUser", new String[] {"email"}, "id" + "=?", 
				new String[] { "1" }, null,	null, null, null);
		if (cursor == null) {
			return null;
		}
		if (cursor.getCount() <= 0) {
			return null;
		}
		cursor.moveToFirst();
		return cursor.getString(0);
	}
}
