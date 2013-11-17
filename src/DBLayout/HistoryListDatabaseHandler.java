package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryListDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    // Contacts table name
    private static final String TABLE_NAME = "HistoryList";
 
    // Contacts Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_VISIT_DATE = "visit_date";
    private static final String KEY_REST_ID = "rest_id";
 
    public HistoryListDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_EMAIL + " TEXT," + KEY_VISIT_DATE + " TEXT,"
                + KEY_REST_ID + "INTEGER," + "PRIMARY KEY (KEY_VISIT_DATE, "
                + "KEY_REST_ID, KEY_EMAIL)" + ")";
        db.execSQL(CREATE_INPUTDATA_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    void addData(HistoryListContainer historyListData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, historyListData.getEmail());
        values.put(KEY_VISIT_DATE, historyListData.getVisitDate());
        values.put(KEY_REST_ID, historyListData.getRestId());
 
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
 
    HistoryListContainer getData(String email, String restId) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_EMAIL,
        		KEY_VISIT_DATE, KEY_REST_ID}, KEY_EMAIL + "=?" + "AND" + 
        		KEY_REST_ID + "=?", new String[] { email, restId },
        		null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        HistoryListContainer historyListData = new HistoryListContainer(
        		cursor.getString(0), cursor.getString(1),
        		cursor.getString(2));

        return historyListData;
    }
     
    public int updateData(HistoryListContainer historyListData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, historyListData.getEmail());
        values.put(KEY_VISIT_DATE, historyListData.getVisitDate());
        values.put(KEY_REST_ID, historyListData.getRestId());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_EMAIL + "=?" + "AND" + 
        		KEY_REST_ID + "=?", new String[] { historyListData.getEmail(),
        		historyListData.getRestId()});
    }

    public void deleteData(String email, String restId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_EMAIL + "=?" + "AND" + KEY_REST_ID + "=?",
                new String[] { email, restId });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}
