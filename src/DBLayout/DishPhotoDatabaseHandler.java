package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DishPhotoDatabaseHandler extends SQLiteOpenHelper {

	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    // Contacts table name
    private static final String TABLE_NAME = "DishPhoto";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "dish_id";
    private static final String KEY_CONTENT_PATH = "content_path";
 
    public DishPhotoDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_CONTENT_PATH + " BLOB" + ")";
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
 
    void addData(DishPhotoContainer dishPhotoData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, dishPhotoData.getId());
        values.put(KEY_CONTENT_PATH, dishPhotoData.getContentPath());
 
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
 
    DishPhotoContainer getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
        		KEY_CONTENT_PATH}, KEY_ID + "=?", new String[] { id },
        		null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        DishPhotoContainer dishPhotoData = new DishPhotoContainer(
        		cursor.getString(0), cursor.getString(1));

        return dishPhotoData;
    }
     
    public int updateData(DishPhotoContainer dishPhotoData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, dishPhotoData.getId());
        values.put(KEY_CONTENT_PATH, dishPhotoData.getContentPath());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { dishPhotoData.getId() });
    }

    public void deleteData(DishPhotoContainer dishPhotoData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { dishPhotoData.getId() });
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
