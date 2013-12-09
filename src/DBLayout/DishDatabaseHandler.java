package DBLayout;

import java.util.ArrayList;

import entities.DishContainer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DishDatabaseHandler {
//
//	 
//    // All Static variables
//    // Database Version
//    private static final int DATABASE_VERSION = 1;
// 
//    // Database Name
//    private static final String DATABASE_NAME = "DragonIsHungry";
// 
//    // Contacts table name
//    private static final String TABLE_NAME = "Dish";
// 
//    // Contacts Table Columns names
//    private static final String KEY_DISH_ID = "dish_id";
//    private static final String KEY_REST_ID = "rest_id";
//    private static final String KEY_NAME = "dish_name";
//    private static final String KEY_DESCRIPTION = "description";
//    private static final String KEY_AUDIO_PATH = "audio_path";
//    private static final String KEY_PHOTO_PATH = "photo_path";
//    
//    public DishDatabaseHandler(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
// 
//    // Creating Tables
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        
//        String CREATE_INPUTDATA_TABLE = "CREATE TABLE " + TABLE_NAME + "("
//                + KEY_DISH_ID + " INTEGER," +  KEY_REST_ID + " INTEGER,"
//                + KEY_NAME + " TEXT," + KEY_DESCRIPTION + " TEXT,"
//                + KEY_AUDIO_PATH + " TEXT," + KEY_PHOTO_PATH + " TEXT,"
//                + "PRIMARY KEY (KEY_DISH_ID, KEY_REST_ID)" + ")";
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
//    void addData(DishContainer dishData) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_DISH_ID, dishData.getDishId());
//        values.put(KEY_REST_ID, dishData.getRestId());
//        values.put(KEY_NAME, dishData.getName());
//        values.put(KEY_DESCRIPTION, dishData.getDescription());
//        values.put(KEY_AUDIO_PATH, dishData.getAudioPath());
//        values.put(KEY_PHOTO_PATH, dishData.getPhotoPath());
// 
//        // Inserting Row
//        db.insert(TABLE_NAME, null, values);
//        db.close(); // Closing database connection
//    }
// 
//    DishContainer getData(String dishId, String restId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        
//        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_DISH_ID,
//        		KEY_REST_ID, KEY_NAME, KEY_DESCRIPTION, KEY_AUDIO_PATH,
//        		KEY_PHOTO_PATH}, KEY_DISH_ID + "=?" + 
//        		" AND " + KEY_REST_ID +"=?", new String[] { dishId, restId },
//        		null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
// 
//        DishContainer dishData = new DishContainer(
//        		cursor.getString(0), cursor.getString(1), cursor.getString(2),
//        		cursor.getString(3), cursor.getString(4), cursor.getString(5));
//
//        return dishData;
//    }
//     
//    public int updateData(DishContainer dishData) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_DISH_ID, dishData.getDishId());
//        values.put(KEY_REST_ID, dishData.getRestId());
//        values.put(KEY_NAME, dishData.getName());
//        values.put(KEY_DESCRIPTION, dishData.getDescription());
//        values.put(KEY_AUDIO_PATH, dishData.getAudioPath());
//        values.put(KEY_PHOTO_PATH, dishData.getPhotoPath());
// 
//        // updating row
//        return db.update(TABLE_NAME, values, KEY_DISH_ID + "=?" + " AND "
//        + KEY_REST_ID +"=?", new String[] { dishData.getDishId(),
//        		dishData.getRestId() });
//    }
//
//    public void deleteData(String dishId, String restId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, KEY_DISH_ID + "=?" + " AND " + KEY_REST_ID +"=?",
//        		new String[] { dishId, restId });
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
 
	public static boolean isExist(SQLiteDatabase db, String restId, String dishId) {
		Cursor cursor = db.query("Dish", new String[] { "rest_id", "dish_id" }, "rest_id" + "=?" + " AND " + "dish_id" + "=?", 
				new String[] { restId, dishId }, null,	null, null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addDish(SQLiteDatabase db, DishContainer dish) {
		ContentValues values = new ContentValues();
		values.put("dish_id", dish.getDishId());
		values.put("rest_id", dish.getRestId());
		values.put("dish_name", dish.getName());
		values.put("description", dish.getDescription());
		values.put("audio_path", dish.getAudioPath());
		values.put("photo_path", dish.getPhotoPath());
		
		// Inserting Row
		db.insert("Dish", null, values);
	}
	
	public static void deleteDish(SQLiteDatabase db, String restId, String dishId) {
		db.delete("Dish", "rest_id" + " = ?" + " AND " + "dish_id" + "=?", new String[] { restId, dishId });
	}
	
	public static int getDishNum(SQLiteDatabase db, String restId) {
		Cursor cursor = db.query("Restaurant", new String[] { "rest_id" }, "rest_id" + " = ?", new String[] { restId }, null, null, null);
		if (cursor == null) {
			return -1;
		}
		return cursor.getCount();
	}
	
	public static DishContainer getDishInfo(SQLiteDatabase db, String restId, String dishId) {
		Cursor cursor = db.query("Dish", new String[] { "dish_id", "rest_id", "dish_name", "description", "audio_path", "photo_path"},
				"rest_id" + "=?" + " AND " + "dish_id" + "=?", new String[] { restId, dishId }, null, null, null);
		if (cursor == null) {
			return null;
		}
		cursor.moveToFirst();
		return new DishContainer(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
	}
	
	public static ArrayList<DishContainer> getDishesInfo(SQLiteDatabase db, String restId) {
		Cursor cursor = db.query("Dish", new String[] { "dish_id", "rest_id", "dish_name", "description", "audio_path", "photo_path"},
				"rest_id" + "=?", new String[] { restId }, null, null, null);
		ArrayList<DishContainer> dishesList = new ArrayList<DishContainer>();
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				dishesList.add(new DishContainer(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
			}
		}
		return dishesList;
	}
}
