package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteListDatabaseHandler extends SQLiteOpenHelper {

	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    // Contacts table name
    private static final String TABLE_NAME = "FavoriteList";
 
    // Contacts Table Columns names
    private static final String KEY_REST_ID = "rest_id";
    private static final String KEY_RANK = "rank";
    private static final String KEY_ADD_DATE = "add_date";
 
    public FavoriteListDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_REST_ID + " TEXT PRIMARY KEY," + KEY_RANK + " TEXT,"
                + KEY_ADD_DATE + "TEXT" + ")";
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
 
    void addData(FavoriteListContainer favoriteListData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_REST_ID, favoriteListData.getRestId());
        values.put(KEY_RANK, favoriteListData.getRank());
        values.put(KEY_ADD_DATE, favoriteListData.getAddDate());
 
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
 
    FavoriteListContainer getData(String restId) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_REST_ID,
        		KEY_RANK, KEY_ADD_DATE}, KEY_REST_ID + "=?", new String[] { restId },
        		null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        FavoriteListContainer favoriteListData = new FavoriteListContainer(
        		cursor.getString(0), cursor.getString(1),
        		cursor.getString(2));

        return favoriteListData;
    }
     
    public int updateData(FavoriteListContainer favoriteListData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_REST_ID, favoriteListData.getRestId());
        values.put(KEY_RANK, favoriteListData.getRank());
        values.put(KEY_ADD_DATE, favoriteListData.getAddDate());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_REST_ID + " = ?",
                new String[] { favoriteListData.getRestId() });
    }

    public void deleteData(FavoriteListContainer favoriteListData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_REST_ID + " = ?",
                new String[] { favoriteListData.getRestId() });
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