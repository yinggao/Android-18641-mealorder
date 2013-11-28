package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantDatabaseHandler {

	public static boolean isExist(SQLiteDatabase db, String restId) {
		Cursor cursor = db.query("Restaurant", new String[] { "rest_id" }, "rest_id" + "=?", 
				new String[] { restId }, null,	null, null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addRestaurant(SQLiteDatabase db, RestaurantContainer restaurant) {
		ContentValues values = new ContentValues();
		values.put("rest_id", restaurant.getRestId());
		values.put("name", restaurant.getName());
		values.put("address", restaurant.getAddress());
		values.put("phone", restaurant.getPhone());
		values.put("businesss_hour", restaurant.getBusinessHour());
		values.put("location", restaurant.getLocation());
		values.put("category", restaurant.getCategory());
		values.put("email", restaurant.getEmail());
		
		// Inserting Row
		db.insert("Restaurant", null, values);
	}
	
	public static void deleteRestaurant(SQLiteDatabase db, String restId) {
		db.delete("Restaurant", "rest_id" + " = ?", new String[] { restId });
	}
	
	public static int getRestaurantNum(SQLiteDatabase db) {
		Cursor cursor = db.query("Restaurant", new String[] { "rest_id" }, null, null, null, null, null);
		if (cursor == null) {
			return -1;
		}
		return cursor.getCount();
	}
	
	public static RestaurantContainer getRestaurantInfo(SQLiteDatabase db, String restId) {
		Cursor cursor = db.query("Restaurant", new String[] { "rest_id", "name", "address", "phone", "businesss_hour",
				"location", "category", "email"}, "rest_id" + "=?", new String[] { restId }, null, null, null);
		if (cursor == null) {
			return null;
		}
		cursor.moveToFirst();
		return new RestaurantContainer(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
				cursor.getString(6), cursor.getString(7));
		
	}
}
