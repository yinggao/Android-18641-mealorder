package DBLayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantDatabaseHandler extends SQLiteOpenHelper {

	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    // Contacts table name
    private static final String TABLE_NAME = "Restaurant";
 
    // Contacts Table Columns names
    private static final String KEY_REST_ID = "rest_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_BUSINESS_HOUR = "businesss_hour";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_CATEGORY = "category";
 
    public RestaurantDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        
        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_REST_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_PHONE + " TEXT,"
                + KEY_BUSINESS_HOUR + " TEXT," + KEY_LOCATION + "TEXT,"
                + KEY_CATEGORY + "TEXT" + ")";
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
 
    void addData(RestaurantContainer restaurantData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_REST_ID, restaurantData.getRestId());
        values.put(KEY_NAME, restaurantData.getName());
        values.put(KEY_ADDRESS, restaurantData.getAddress());
        values.put(KEY_PHONE, restaurantData.getPhone());
        values.put(KEY_BUSINESS_HOUR, restaurantData.getBusinessHour());
        values.put(KEY_LOCATION, restaurantData.getLocation());
        values.put(KEY_CATEGORY, restaurantData.getCategory());
 
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }
 
    RestaurantContainer getData(String restId) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        // How to use two column to find a row?
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_REST_ID,
        		KEY_NAME, KEY_ADDRESS, KEY_PHONE, KEY_BUSINESS_HOUR,
        		KEY_LOCATION, KEY_CATEGORY}, KEY_REST_ID + "=?", 
        		new String[] { restId }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        RestaurantContainer restaurantData = new RestaurantContainer(
        		cursor.getString(0), cursor.getString(1),
        		cursor.getString(2), cursor.getString(3),
        		cursor.getString(4), cursor.getString(5),
        		cursor.getString(6));

        return restaurantData;
    }
     
    public int updateData(RestaurantContainer restaurantData) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_REST_ID, restaurantData.getRestId());
        values.put(KEY_NAME, restaurantData.getName());
        values.put(KEY_ADDRESS, restaurantData.getAddress());
        values.put(KEY_PHONE, restaurantData.getPhone());
        values.put(KEY_BUSINESS_HOUR, restaurantData.getBusinessHour());
        values.put(KEY_LOCATION, restaurantData.getLocation());
        values.put(KEY_CATEGORY, restaurantData.getCategory());
 
        // updating row
        return db.update(TABLE_NAME, values, KEY_REST_ID + " = ?",
                new String[] { restaurantData.getRestId() });
    }

    public void deleteData(String restId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_REST_ID + " = ?",
                new String[] { restId });
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
