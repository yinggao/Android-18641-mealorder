package DBLayout;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DragonBroDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 11;

    // Database Name
    private static final String DATABASE_NAME = "DragonIsHungry";
 
    public DragonBroDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String CREATE_DISH_TABLE = "CREATE TABLE Dish (dish_id TEXT, rest_id TEXT, dish_name TEXT, description TEXT, audio_path TEXT, photo_path TEXT, PRIMARY KEY (dish_id, rest_id))";
    	String CREATE_FAVORITE_LIST_TABLE = "CREATE TABLE FavoriteList (rest_id TEXT, email TEXT, PRIMARY KEY (rest_id, email))";
    	String CREATE_HISHTORY_LIST_TABLE = "CREATE TABLE HistoryList (email TEXT, visit_date TEXT, rest_id TEXT, PRIMARY KEY (email, visit_date, rest_id))";
    	String CREATE_RESTAURANT_TABLE = "CREATE TABLE Restaurant (rest_id TEXT PRIMARY KEY, name TEXT, address TEXT, phone TEXT, businesss_hour TEXT, location TEXT, category TEXT, email TEXT, rate TEXT)";
    	String CREATE_STUDENT_TABLE = "CREATE TABLE Student (email TEXT PRIMARY KEY, password TEXT, first_name TEXT, last_name TEXT, address TEXT, phone TEXT, photo_path TEXT)";
    	String CREATE_CURRENT_USER_TABLE = "CREATE TABLE CurrentUser (id TEXT PRIMARY KEY, email TEXT)";
    	db.execSQL(CREATE_DISH_TABLE);
    	db.execSQL(CREATE_FAVORITE_LIST_TABLE);
    	db.execSQL(CREATE_HISHTORY_LIST_TABLE);
    	db.execSQL(CREATE_RESTAURANT_TABLE);
    	db.execSQL(CREATE_STUDENT_TABLE);
    	db.execSQL(CREATE_CURRENT_USER_TABLE);
    	initializeDatabase(db);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "Dish");
        db.execSQL("DROP TABLE IF EXISTS " + "FavoriteList");
        db.execSQL("DROP TABLE IF EXISTS " + "HistoryList");
        db.execSQL("DROP TABLE IF EXISTS " + "Restaurant");
        db.execSQL("DROP TABLE IF EXISTS " + "Student");
        db.execSQL("DROP TABLE IF EXISTS " + "CurrentUser");
 
        // Create tables again
        onCreate(db);
    }
    
    /*************************
     * Database initialization
     ************************/
    
    public void initializeDatabase(SQLiteDatabase db) {
    	initializeDish(db);
    	initializeFavoriteList(db);
    	initializeHistoryList(db);
    	initializeRestaurant(db);
    	initializeStudent(db);
    	initializeCurrentUser(db);
    }
    
    private void initializeDish(SQLiteDatabase db) {
    	String addData = "INSERT INTO Dish VALUES('1','1','Coffee','A kind of beverage',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('1','2','Coffee','A kind of beverage',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('1','3','Coffee','A kind of beverage',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('1','4','Coffee','A kind of beverage',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('1','5','Beef Skew Noodle Soup','A kind of traditional chinese food',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('1','6','Coffee','A kind of beverage',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('1','7','Curry Chicken','A kind of indian food',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('2','5','Yang Chou Fried Rice','A kind of traditional chinese food',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('3','5','Fried Chicken Over Rice','A kind of traditional chinese food',NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Dish VALUES('2','7','Indian Cake','A traditional indian cake',NULL,NULL)";
    	db.execSQL(addData);
    }
    
    private void initializeHistoryList(SQLiteDatabase db) {
    	String addData = "INSERT INTO HistoryList VALUES('muyangy@andrew.cmu.edu','2013.10.22','1')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('yinggao@andrew.cmu.edu','2013.10.21','6')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('xiaoc@andrew.cmu.edu','2013.10.19','5')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('muyangy@andrew.cmu.edu','2013.10.22','2')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('muyangy@andrew.cmu.edu','2013.10.23','1')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('muyangy@andrew.cmu.edu','2013.10.23','2')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('muyangy@andrew.cmu.edu','2013.10.24','2')";
    	db.execSQL(addData);
    	addData = "INSERT INTO HistoryList VALUES('xiaoc@andrew.cmu.edu','2013.10.24','4')";
    	db.execSQL(addData);
    }
    
    private void initializeFavoriteList(SQLiteDatabase db) {
    	String addData = "INSERT INTO FavoriteList VALUES('1','muyangy@andrew.cmu.edu')";
    	db.execSQL(addData);
    	addData = "INSERT INTO FavoriteList VALUES('2','muyangy@andrew.cmu.edu')";
    	db.execSQL(addData);
    	addData = "INSERT INTO FavoriteList VALUES('6','yinggao@andrew.cmu.edu')";
    	db.execSQL(addData);
    	addData = "INSERT INTO FavoriteList VALUES('5','xiaoc@andrew.cmu.edu')";
    	db.execSQL(addData);
    }
    
    private void initializeStudent(SQLiteDatabase db) {
    	
    	String addData = "INSERT INTO Student VALUES('muyangy@andrew.cmu.edu','123','muyang','yu','cmu','(412)888-9018',NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Student VALUES('yinggao@andrew.cmu.edu','123','ying','gao','cmu','(412)888-9015',NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Student VALUES('xiaoc@andrew.cmu.edu','123','xiao','chen','cmu','(626)315-7169',NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Student VALUES('guest@andrew.cmu.edu','guest',NULL,NULL,NULL,NULL,NULL)";
    	db.execSQL(addData);
    	addData = "INSERT INTO Student VALUES('admin','123',NULL,NULL,NULL,NULL,NULL)";
    	db.execSQL(addData);
    }
    
    private void initializeRestaurant(SQLiteDatabase db) {
    	String addData = "INSERT INTO Restaurant VALUES('1','Skibo Coffee House','5000 Forbes Avenue, Pittsburgh, PA 15213','(412)268-1803','',NULL,'Cafe', 'dragonishungrytest@gmail.com', '1')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('2','El Gallo de Oro','5000 Forbes Avenue, Pittsburgh, PA 15213',NULL,'Mon.-Fri. 10:30每22:00',NULL,'Cafe', 'dragonishungrytest@gmail.com', '2')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('3','Tartans Pavilion','5000 Forbes Avenue, Pittsburgh, PA 15213',NULL,'Mon.-Fri. 11:00每23:00',NULL,'Cafe', 'dragonishungrytest@gmail.com', '1')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('4','Carnegie Mellon Cafe','5000 Forbes Avenue, Pittsburgh, PA 15213','(412)268-2139',NULL,NULL,'Cafe', 'dragonishungrytest@gmail.com', '2')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('5','Orient Express','4609 Forbes Avenue, Pittsburgh, PA 15213','(412)622-7232',NULL,NULL,'Chinese', 'dragonishungrytest@gmail.com', '1')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('6','Starbucks','417 South Craig Street, Pittsburgh, PA 15213','(412)687-2494','Mon.-Sun. 05:30每21:00',NULL,'Cafe', 'dragonishungrytest@gmail.com', '1')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('7','Yuva India Restaurant','412 South Craig Street, Pittsburgh, PA 15213','(412)681-5700','Mon.-Fri. 11:30每14:30 17:00每22:30',NULL,'Indian', 'dragonishungrytest@gmail.com', '3')";
    	db.execSQL(addData);
    	addData = "INSERT INTO Restaurant VALUES('8','CMU Indian Food','5000 Forbes Avenue, Pittsburgh, PA 15213','(412)423-5719','Mon.-Fri. 11:30每14:30 17:00每22:30',NULL,'Indian', 'dragonishungrytest@gmail.com', '3')";
    	db.execSQL(addData);
    }
    
    private void initializeCurrentUser(SQLiteDatabase db) {
    	String addData = "INSERT INTO CurrentUser VALUES('1', null)";
    	db.execSQL(addData);
    }
    
    /***************************************************
     * All CRUD(Create, Read, Update, Delete) Operations
     **************************************************/
    
    /**
     * Authentication
     * @param email
     * @param password
     * @return true if authenticated, false otherwise
     */
    public boolean passwordCheck(String email, String password) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	boolean checkResult = StudentDatabaseHandler.passwordCheck(db, email, password);
    	db.close();
    	return checkResult;
    	
//    	if (StudentDatabaseHandler.checkExist(db, email, password)) {
//    		Log.d("Debug:", "CheckExistSucceess");
//    		CurrentUserDatabaseHandler.setCurrentUser(db, email);
//    		return true;
//    	}
//    	db.close();
//    	return false;
    }
    
    /**
     * Login
     * @param email
     * @param password
     * @return
     */
    public boolean login(String email, String password) {
    	if (passwordCheck(email, password)) {
    		SQLiteDatabase db = this.getReadableDatabase();
    		CurrentUserDatabaseHandler.setCurrentUser(db, email);
    		db.close();
    		return true;
    	}
    	return false;
    }
    
    /**
     * Log out
     */
    public void logout() {
		SQLiteDatabase db = this.getReadableDatabase();
		CurrentUserDatabaseHandler.setCurrentUser(db, null);
		db.close();
    }
    
    /**
     * Get email of current user. If no user, return null
     * @return
     */
    public String getCurrentUser() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	return CurrentUserDatabaseHandler.getCurrentUser(db);
    }

    /**
     * Create new student
     * @param studentInfo
     * @return true if add is successful false otherwise - if student already exists in database, then return false
     */
    public boolean addStudent(StudentContainer studentInfo) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	boolean addResult = false;
    	if (!StudentDatabaseHandler.existStudent(db, studentInfo.getEmail())) {
    		StudentDatabaseHandler.addStudent(db, studentInfo);
    		addResult = true;
    	}
    	db.close();
		return addResult;
    }
    
    /**
     * Update student information
     * @param studentInfo
     * @return true if add is successful false otherwise - if student does not exists in database, then return false
     */
    public boolean updateStudentInfo(StudentContainer studentInfo) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	boolean updateResult = false;
    	if (StudentDatabaseHandler.existStudent(db, studentInfo.getEmail())) {
    		StudentDatabaseHandler.updateStudentInfo(db, studentInfo);
    		updateResult = true;
    	}
    	db.close();
		return updateResult;
    }
    
    /**
     * Check whether one student account exists
     * @param email
     * @return
     */
    public boolean existStudent(String email) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	boolean retval = false;
    	retval = StudentDatabaseHandler.existStudent(db, email);
    	db.close();
		return retval;
    }
    
    /**
     * Get student's information
     * @param email
     * @return
     */
    public StudentContainer getStudentInfo(String email) {
    	SQLiteDatabase db = this.getReadableDatabase();
   		return StudentDatabaseHandler.getStudentInfo(db, email);
    }
    
    /**
     * Add restaurant to favorite list
     * @param favoriteList
     */
    public void addToFavoriteList(FavoriteListContainer favoriteList) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (!FavoriteListDatabaseHandler.isExist(db, favoriteList)) {
    		FavoriteListDatabaseHandler.addToFavoriteList(db, favoriteList);
    		
    	}
    	db.close();
    }
    
    /**
     * Delete restaurant from favorite list
     * @param favoriteList
     */
    public void deleteFromFavoriteList(FavoriteListContainer favoriteList) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (FavoriteListDatabaseHandler.isExist(db, favoriteList)) {
    		FavoriteListDatabaseHandler.deleteFromFavoriteList(db, favoriteList);
    	}
    	db.close();
    }
    
    /**
     * Get all favorite restaurant for one student
     * @param email
     * @return
     */
    public ArrayList<String> getFavoriteList(String email) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<String> retval = FavoriteListDatabaseHandler.getFavoriteList(db, email);
    	db.close();
    	return retval;
    }
    
    /**
     * Add restaurant and visit date to favorite list
     * @param historyList
     */
    public void addToHistoryList(HistoryListContainer historyList) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (!HistoryListDatabaseHandler.isExist(db, historyList)) {
    		HistoryListDatabaseHandler.addToHistoryList(db, historyList);
    	}
    	db.close();
    }
    
    /**
     * Delete restaurant and visit date from favorite list
     * @param historyList
     */
    public void deleteFromHistoryList(HistoryListContainer historyList) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (HistoryListDatabaseHandler.isExist(db, historyList)) {
    		HistoryListDatabaseHandler.deleteFromFavoriteList(db, historyList);
    	}
    	db.close();
    }
    
    /**
     * Get all visited restaurant for one student
     * @param email
     */
    public ArrayList<HistoryListContainer> getHistoryList(String email) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<HistoryListContainer> retval = HistoryListDatabaseHandler.getHistoryList(db, email);
    	db.close();
    	return retval;
    }
    
    /**
     * Add restaurant
     * @param restaurant
     */
    public void addRestaurant(RestaurantContainer restaurant) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (!RestaurantDatabaseHandler.isExist(db, restaurant.getRestId())) {
    		RestaurantDatabaseHandler.addRestaurant(db, restaurant);
    	}
    	db.close();
    }
    
    /**
     * Delete restaurant
     * @param restaurant
     */
    public void deleteRestaurant(String restId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (RestaurantDatabaseHandler.isExist(db, restId)) {
    		RestaurantDatabaseHandler.deleteRestaurant(db, restId);
    	}
    	db.close();
    }
    
    /**
     * Get total number of restaurants
     * @return
     */
    public int getRestaurantNum() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int retval = RestaurantDatabaseHandler.getRestaurantNum(db);
    	db.close();
    	return retval;
    	
    }
    
    /**
     * get restaurant information
     * @param restId
     * @return
     */
    public RestaurantContainer getRestaurantInfo(String restId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	RestaurantContainer retval = RestaurantDatabaseHandler.getRestaurantInfo(db, restId);
    	db.close();
    	return retval;
    }
    
    /**
     * get all restaurants information
     * @return
     */
    public ArrayList<RestaurantContainer> getAllRestaurantsInfo() {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<RestaurantContainer> retval = RestaurantDatabaseHandler.getAllRestaurantsInfo(db);
    	db.close();
    	return retval;
    }
    
    /**
     * Get restaurants whose name like name parameter
     * @param name
     * @return
     */
    public ArrayList<RestaurantContainer> getRestaurantsFromName(String name) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<RestaurantContainer> retval = RestaurantDatabaseHandler.getRestaurantsFromName(db, name);
    	db.close();
    	return retval;
    }
    
    /**
     * Get restaurants whose category match parameter
     * @param category
     * @return
     */
    public ArrayList<RestaurantContainer> getRestaurantsFromCategory(String category) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<RestaurantContainer> retval = RestaurantDatabaseHandler.getRestaurantsFromCategory(db, category);
    	db.close();
    	return retval;
    }
    
    /**
     * Add dish to a restaurant
     * @param dish
     */
    public void addDish(DishContainer dish) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (!DishDatabaseHandler.isExist(db, dish.getRestId(), dish.getDishId())) {
    		DishDatabaseHandler.addDish(db, dish);
    	}
    	db.close();
    }
    
    /**
     * Delete dish from restaurant
     * @param dish
     */
    public void deleteDish(String dishId, String restId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	if (DishDatabaseHandler.isExist(db, restId, dishId)) {
    		DishDatabaseHandler.deleteDish(db, restId, dishId);
    	}
    	db.close();
    }
    
    /**
     * Get total number of dishes in a restaurant
     * @param restId
     * @return
     */
    public int getDishNum(String restId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	int retval = DishDatabaseHandler.getDishNum(db, restId);
    	db.close();
    	return retval;
    }
    
    public ArrayList<DishContainer> getDishesInfo(String restId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<DishContainer> retval = DishDatabaseHandler.getDishesInfo(db, restId);
    	db.close();
    	return retval;
    }
    
    /**
     * Get dish information
     * @param restId
     * @param dishId
     * @return
     */
    public DishContainer getDishInfo(String dishId, String restId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	return DishDatabaseHandler.getDishInfo(db, restId, dishId);
    }
    
    /************************************************
     * Test function to check database initialization
     ***********************************************/
    
    public String getStudentData(String email) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("Student", new String[] { "email",
		"password", "first_name", "last_name", "address",
		"phone", "photo_path"}, "email" + "=?",
		new String[] { email },	null, null, null, null);
    	if (cursor != null)
    		cursor.moveToFirst();
    	
    	String retval = cursor.getString(0) + cursor.getString(1) + cursor.getString(2)
    			+ cursor.getString(3) + cursor.getString(4) + cursor.getString(5)
    			+ cursor.getString(6);
    	
    	return retval;
    }
    
    public String getRestaurantData(String id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("Restaurant", new String[] { "rest_id",
		"name", "address", "phone", "businesss_hour",
		"location", "category"}, "rest_id" + "=?",
		new String[] { id },	null, null, null, null);
    	if (cursor != null)
    		cursor.moveToFirst();
    	
    	String retval = cursor.getString(0)
    			+ cursor.getString(1) + cursor.getString(2)
    			+ cursor.getString(3) + cursor.getString(4) + cursor.getString(5)
    			+ cursor.getString(6)
    			;
    	
    	return retval;
    }
    
    public String getRestaurantCount() {
		String countQuery = "SELECT  * FROM " + "Restaurant";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		Integer val = cursor.getCount();
		cursor.close();
		// return count
		return val.toString();
    }

    public String getStudentCount() {
		String countQuery = "SELECT  * FROM " + "Student";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		Integer val = cursor.getCount();
		cursor.close();
		// return count
		return val.toString();
    }
    
    public String getFavoriteListCount() {
		String countQuery = "SELECT  * FROM " + "FavoriteList";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		Integer val = cursor.getCount();
		cursor.close();
		// return count
		return val.toString();
    }
    
    public String getHistoryListCount() {
		String countQuery = "SELECT  * FROM " + "HistoryList";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		Integer val = cursor.getCount();
		cursor.close();
		// return count
		return val.toString();
    }
    
    public String getDishCount() {
		String countQuery = "SELECT  * FROM " + "Dish";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		Integer val = cursor.getCount();
		cursor.close();
		// return count
		return val.toString();
    }
    
    public String getRestaurant() {
    	String result = "";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("Restaurant", null, null, null, null, null, null);
    	for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
    		result += cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2)
        			+ " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5)
        			+ " " + cursor.getString(6) + " " + cursor.getString(7) + " \n";
    	}
    	return result;
    }
    
    public String getStudent() {
    	String result = "";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("Student", null, null, null, null, null, null);
    	for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
    		result += cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2)
        			+ " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5)
        			+ " " + cursor.getString(6) + " \n";
    	}
    	return result;
    }
    
    public String getFavoriteList() {
    	String result = "";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("FavoriteList", null, null, null, null, null, null);
    	for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
    		result += cursor.getString(0) + " " + cursor.getString(1) + " \n";
    	}
    	return result;
    }
    
    public String getHistoryList() {
    	String result = "";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("HistoryList", null, null, null, null, null, null);
    	for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
    		result += cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " \n";
    	}
    	return result;
    }
    
    public String getDish() {
    	String result = "";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.query("Dish", null, null, null, null, null, null);
    	for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
    		result += cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2)
        			+ " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5)
        			+ " \n";
    	}
    	return result;
    }

 
//    void addData(StudentContainer studentData) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_EMAIL, studentData.getEmail());
//        values.put(KEY_PASSWORD, studentData.getPassword());
//        values.put(KEY_FIRST_NAME, studentData.getFirstName());
//        values.put(KEY_LAST_NAME, studentData.getLastName());
//        values.put(KEY_ADDRESS, studentData.getAddress());
//        values.put(KEY_PHONE, studentData.getPhone());
//        values.put(KEY_PHOTO_PATH, studentData.getPhone());
//        
//        // Inserting Row
//        db.insert(TABLE_NAME, null, values);
//        db.close(); // Closing database connection
//    }
// 
//    StudentContainer getData(String email) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        
//        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_EMAIL,
//        		KEY_PASSWORD, KEY_FIRST_NAME, KEY_LAST_NAME, KEY_ADDRESS,
//        		KEY_PHONE, KEY_PHOTO_PATH}, KEY_EMAIL + "=?",
//        		new String[] { email },	null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
// 
//        StudentContainer studentData = new StudentContainer(
//        		cursor.getString(0), cursor.getString(1),
//        		cursor.getString(2), cursor.getString(3),
//        		cursor.getString(4), cursor.getString(5)
//        		,cursor.getString(6));
//
//        return studentData;
//    }
     
//    public int updateData(StudentContainer studentData) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_EMAIL, studentData.getEmail());
//        values.put(KEY_PASSWORD, studentData.getPassword());
//        values.put(KEY_FIRST_NAME, studentData.getFirstName());
//        values.put(KEY_LAST_NAME, studentData.getLastName());
//        values.put(KEY_ADDRESS, studentData.getAddress());
//        values.put(KEY_PHONE, studentData.getPhone());
//        values.put(KEY_PHOTO_PATH, studentData.getPhone());
// 
//        // updating row
//        return db.update(TABLE_NAME, values, KEY_EMAIL + " = ?",
//                new String[] { studentData.getEmail() });
//    }

//    public void deleteData(String email) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, KEY_EMAIL + " = ?",
//                new String[] { email });
//        db.close();
//    }
 
 
    // Getting contacts Count
//    public int getDataCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
// 
//        // return count
//        return cursor.getCount();
//    }
}
