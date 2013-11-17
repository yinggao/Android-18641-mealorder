package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CurrentUserDatabaseHandler {
//
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 1;
// 
//    // Database Name
//    private static final String DATABASE_NAME = "DragonIsHungry";
// 
//    // Contacts table name
//    private static final String TABLE_NAME = "CurrentUser";
// 
//    // Contacts Table Columns names
//    private static final String KEY_ID = "id";
//    private static final String KEY_EMAIL = "email";
// 
//    public CurrentUserDatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
// 
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        
//        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + KEY_ID + " TEXT PRIMARY KEY," + KEY_EMAIL + " TEXT," + ")";
//        db.execSQL(CREATE_INPUTDATA_TABLE);
//        
//        setCurrentUser(null);
//    }
// 
//    // Upgrading database
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
// 
//        // Create tables again
//        onCreate(db);
//    }
// 
//    /**
//     * All CRUD(Create, Read, Update, Delete) Operations
//     */
// 
//    void setCurrentUser(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_ID, "1");
//        values.put(KEY_EMAIL, email);
// 
//        // Inserting Row
//        db.insert(TABLE_NAME, null, values);
//        db.close(); // Closing database connection
//    }
// 
//    public String getCurrentUser() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        
//        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_EMAIL }, 
//        		KEY_ID + "=?", new String[] { "1" },
//        		null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        return cursor.getString(0);
//    }
//     
//    public int updateCurrentUser(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
////        values.put(KEY_ID, "1");
//        values.put(KEY_EMAIL, email);
// 
//        // updating row
//        return db.update(TABLE_NAME, values, KEY_ID + "=?",
//        		new String[] { "1" });
//    }
//
//    public void deleteCurrentUser() {
////        SQLiteDatabase db = this.getWritableDatabase();
////        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] { "1" });
////        db.close();
//    	updateCurrentUser(null);
//    } 
	
	public static void setCurrentUser(SQLiteDatabase db, String email) {
//		ContentValues values = new ContentValues();
//		values.put("id", "1");
//		values.put("email", email);
//
//		// Inserting Row
//		db.insert("CurrentUser", null, values);
		
		
      ContentValues values = new ContentValues();
      values.put("id", "1");
      values.put("email", email);

    // updating row
    db.update("CurrentUser", values, "id" + "=?",
    		new String[] { "1" });
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
