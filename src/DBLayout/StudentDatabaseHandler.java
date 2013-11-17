package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDatabaseHandler {
	public static boolean checkExist(SQLiteDatabase db, String email, String password) {
		Cursor cursor = db.query("Student", new String[] {"email"}, "email" + "=?"
				+ " AND " + "password" + "=?", new String[] { email, password}, null,
				null, null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		}
		return true;
	}
	
	public static void addStudent(SQLiteDatabase db, StudentContainer studentInfo) {
		ContentValues values = new ContentValues();
		values.put("email", studentInfo.getEmail());
		values.put("password", studentInfo.getPassword());
		values.put("first_name", studentInfo.getFirstName());
		values.put("last_name", studentInfo.getLastName());
		values.put("address", studentInfo.getAddress());
		values.put("phone", studentInfo.getPhone());
		values.put("photo_path", studentInfo.getPhotoPath());

		// Inserting Row
		db.insert("CurrentUser", null, values);
	}
	
	public static boolean existStudent(SQLiteDatabase db, String email) {
		return true;
	}
}
