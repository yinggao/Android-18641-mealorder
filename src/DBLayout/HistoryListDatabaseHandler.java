package DBLayout;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryListDatabaseHandler {
//
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 1;
// 
//    // Database Name
//    private static final String DATABASE_NAME = "DragonIsHungry";
// 
//    // Contacts table name
//    private static final String TABLE_NAME = "HistoryList";
// 
//    // Contacts Table Columns names
//    private static final String KEY_EMAIL = "email";
//    private static final String KEY_VISIT_DATE = "visit_date";
//    private static final String KEY_REST_ID = "rest_id";
// 
//    public HistoryListDatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
// 
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        
//        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + KEY_EMAIL + " TEXT," + KEY_VISIT_DATE + " TEXT,"
//                + KEY_REST_ID + "INTEGER," + "PRIMARY KEY (KEY_VISIT_DATE, "
//                + "KEY_REST_ID, KEY_EMAIL)" + ")";
//        db.execSQL(CREATE_INPUTDATA_TABLE);
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
//    void addData(HistoryListContainer historyListData) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_EMAIL, historyListData.getEmail());
//        values.put(KEY_VISIT_DATE, historyListData.getVisitDate());
//        values.put(KEY_REST_ID, historyListData.getRestId());
// 
//        // Inserting Row
//        db.insert(TABLE_NAME, null, values);
//        db.close(); // Closing database connection
//    }
// 
//    HistoryListContainer getData(String email, String restId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        
//        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_EMAIL,
//        		KEY_VISIT_DATE, KEY_REST_ID}, KEY_EMAIL + "=?" + "AND" + 
//        		KEY_REST_ID + "=?", new String[] { email, restId },
//        		null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
// 
//        HistoryListContainer historyListData = new HistoryListContainer(
//        		cursor.getString(0), cursor.getString(1),
//        		cursor.getString(2));
//
//        return historyListData;
//    }
//     
//    public int updateData(HistoryListContainer historyListData) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_EMAIL, historyListData.getEmail());
//        values.put(KEY_VISIT_DATE, historyListData.getVisitDate());
//        values.put(KEY_REST_ID, historyListData.getRestId());
// 
//        // updating row
//        return db.update(TABLE_NAME, values, KEY_EMAIL + "=?" + "AND" + 
//        		KEY_REST_ID + "=?", new String[] { historyListData.getEmail(),
//        		historyListData.getRestId()});
//    }
//
//    public void deleteData(String email, String restId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, KEY_EMAIL + "=?" + "AND" + KEY_REST_ID + "=?",
//                new String[] { email, restId });
//        db.close();
//    }
// 
// 
//    // Getting contacts Count
//    public int getDataCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
// 
//        // return count
//        return cursor.getCount();
//    }
 
	public static boolean isExist(SQLiteDatabase db, HistoryListContainer historyList) {
		Cursor cursor = db.query("HistoryList", new String[] {"email", "visit_date", "rest_id"},
				"email" + "=?" + " AND " + "visit_date" + "=?" + " AND " + "rest_id" + "=?",
				new String[] { historyList.getEmail(),	historyList.getVisitDate(),
				historyList.getRestId() }, null, null, null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addToHistoryList(SQLiteDatabase db, HistoryListContainer historyList) {
		ContentValues values = new ContentValues();
		values.put("email", historyList.getEmail());
		values.put("visit_date", historyList.getVisitDate());
		values.put("rest_id", historyList.getRestId());
		
		// Inserting Row
		db.insert("HistoryList", null, values);
	}
	
	public static void deleteFromFavoriteList(SQLiteDatabase db, HistoryListContainer historyList) {
		db.delete("HistoryList", "email" + "=?" + " AND " + "visit_date" + "=?" + " AND " + "rest_id" + "=?",
				new String[] { historyList.getEmail(), historyList.getVisitDate(), historyList.getRestId() });
	}
	
	public static ArrayList<HistoryListContainer> getHistoryList(SQLiteDatabase db, String email) {
		Cursor cursor = db.query("HistoryList", new String[] { "email", "visit_date", "rest_id" }, 
				"email" + "=?", new String[] { email }, null, null, null);
		ArrayList<HistoryListContainer> historyList = new ArrayList<HistoryListContainer>();
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				HistoryListContainer historyContainer = new HistoryListContainer(cursor.getString(0), cursor.getString(1), cursor.getString(2));
				historyList.add(historyContainer);
			}
		}
		return historyList;
	}
}
