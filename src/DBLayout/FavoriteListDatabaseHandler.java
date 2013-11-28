package DBLayout;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FavoriteListDatabaseHandler {

	public static boolean isExist(SQLiteDatabase db, FavoriteListContainer favoriteList) {
		Cursor cursor = db.query("FavoriteList", new String[] {"rest_id", "email"}, "rest_id" + "=?"
				+ " AND " + "email" + "=?", new String[] { favoriteList.getRestId(),
				favoriteList.getEmail() }, null, null,
				null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addToFavoriteList(SQLiteDatabase db, FavoriteListContainer favoriteList) {
		ContentValues values = new ContentValues();
		values.put("email", favoriteList.getEmail());
		values.put("rest_id", favoriteList.getRestId());
		
		// Inserting Row
		db.insert("FavoriteList", null, values);
	}
	
	public static void deleteFromFavoriteList(SQLiteDatabase db, FavoriteListContainer favoriteList) {
		db.delete("FavoriteList", "email" + " = ?" + " AND " + "rest_id" + " = ?", 
				new String[] { favoriteList.getEmail(), favoriteList.getRestId() });
	}
	
	public static ArrayList<String> getFavoriteList(SQLiteDatabase db, String email) {
		Cursor cursor = db.query("FavoriteList", new String[] {"rest_id"}, "email" + " = ?", new String[] { email }, null, null, null);
		ArrayList<String> favoriteList = new ArrayList<String>();
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				favoriteList.add(cursor.getString(0));
			}
		}
		return favoriteList;
	}
}
