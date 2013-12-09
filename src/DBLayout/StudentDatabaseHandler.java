package DBLayout;

import entities.StudentContainer;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentDatabaseHandler {
	public static boolean passwordCheck(SQLiteDatabase db, String email, String password) {
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
		db.insert("Student", null, values);
	}
	
	public static boolean existStudent(SQLiteDatabase db, String email) {
		Cursor cursor = db.query("Student", new String[] {"email"}, "email" + "=?", 
				new String[] { email }, null,	null, null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void updateStudentInfo(SQLiteDatabase db, StudentContainer studentInfo) {
		ContentValues values = new ContentValues();
		values.put("email", studentInfo.getEmail());
		values.put("password", studentInfo.getPassword());
		values.put("first_name", studentInfo.getFirstName());
		values.put("last_name", studentInfo.getLastName());
		values.put("address", studentInfo.getAddress());
		values.put("phone", studentInfo.getPhone());
		values.put("photo_path", studentInfo.getPhotoPath());
	    
		// updating row
	    db.update("Student", values, "email" + "=?", new String[] { studentInfo.getEmail() });
	}
	
	public static StudentContainer getStudentInfo(SQLiteDatabase db, String email) {
		Cursor cursor = db.query("Student", new String[] {"email", "password", "first_name",
				"last_name", "address", "phone", "photo_path"}, "email" + "=?", 
				new String[] { email }, null, null, null, null);
		if (cursor == null) {
			return null;
		}
		if (cursor.getCount() <= 0) {
			return null;
		}
		cursor.moveToFirst();
		return new StudentContainer(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6));
	}
}
