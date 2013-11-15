package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AudioDatabaseHandler extends SQLiteOpenHelper {

	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    // Contacts table name
    private static final String TABLE_NAME = "Audio";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "dish_id";
    private static final String KEY_CONTENT_PATH = "content_path";
 
    public AudioDatabaseHandler(Context context) {
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
 
    void addData(AudioContainer audioData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, audioData.getId());
        values.put(KEY_CONTENT_PATH, audioData.getPath());
 
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
 
    AudioContainer getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
        		KEY_CONTENT_PATH}, KEY_ID + "=?", new String[] { id },
        		null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        AudioContainer audioData = new AudioContainer(
        		cursor.getString(0), cursor.getString(1));

        return audioData;
    }
     
    public int updateData(AudioContainer audioData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, audioData.getId());
        values.put(KEY_CONTENT_PATH, audioData.getPath());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { audioData.getId() });
    }

    public void deleteData(AudioContainer audioData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { audioData.getId() });
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
