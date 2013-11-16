package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDatabaseHandler extends SQLiteOpenHelper {

	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    // Contacts table name
    private static final String TABLE_NAME = "Student";
 
    // Contacts Table Columns names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PHOTO_PATH = "photo_path";
 
    public StudentDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY," + KEY_PASSWORD + " TEXT,"
                + KEY_FIRST_NAME + " TEXT," + KEY_LAST_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_PHONE + "TEXT," + KEY_PHOTO_PATH
                + "TEXT" + ")";
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
 
    void addData(StudentContainer studentData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, studentData.getEmail());
        values.put(KEY_PASSWORD, studentData.getPassword());
        values.put(KEY_FIRST_NAME, studentData.getFirstName());
        values.put(KEY_LAST_NAME, studentData.getLastName());
        values.put(KEY_ADDRESS, studentData.getAddress());
        values.put(KEY_PHONE, studentData.getPhone());
        values.put(KEY_PHOTO_PATH, studentData.getPhone());
        
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
 
    StudentContainer getData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_EMAIL,
        		KEY_PASSWORD, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_ADDRESS,
        		KEY_PHONE, KEY_PHOTO_PATH}, KEY_EMAIL + "=?",
        		new String[] { email },	null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        StudentContainer studentData = new StudentContainer(
        		cursor.getString(0), cursor.getString(1),
        		cursor.getString(2), cursor.getString(3),
        		cursor.getString(4), cursor.getString(5)
        		,cursor.getString(6));

        return studentData;
    }
     
    public int updateData(StudentContainer studentData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, studentData.getEmail());
        values.put(KEY_PASSWORD, studentData.getPassword());
        values.put(KEY_FIRST_NAME, studentData.getFirstName());
        values.put(KEY_LAST_NAME, studentData.getLastName());
        values.put(KEY_ADDRESS, studentData.getAddress());
        values.put(KEY_PHONE, studentData.getPhone());
        values.put(KEY_PHOTO_PATH, studentData.getPhone());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_EMAIL + " = ?",
                new String[] { studentData.getEmail() });
    }

    public void deleteData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_EMAIL + " = ?",
                new String[] { email });
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
