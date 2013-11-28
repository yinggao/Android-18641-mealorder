package DBLayout;

import com.example.mealdelivery.R;

import DBLayout.DishContainer;
import DBLayout.DragonBroDatabaseHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class SomeActivity extends Activity {
//
//	public final static String PURCHASE_PRICE = "com.example.myfirstapp.PURCHASE_PRICE";
//	public final static String MORTGAGE_TERM  = "com.example.myfirstapp.MORTGATE_TERM";
//	public final static String INTEREST_RATE  = "com.example.myfirstapp.INTEREST_RATE";
//	public final static String FIRST_PAYMENT_DATE  = "com.example.myfirstapp.FIRST_PAYMENT_DATE";
//    private DragonBroDatabaseHandler dbdb;
//    
//	@Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        EditText mInterestRate = (EditText)findViewById(R.id.interest_rate);
//        mInterestRate.addTextChangedListener(watcher0to100);
//        
//		dbdb = new DragonBroDatabaseHandler(this);
//		dbdb.initializeDatabase();
//		Log.d("GetData: ", dbdb.getStudentData("muyangy@andrew.cmu.edu"));
//		Log.d("getRestaurantCount: ", dbdb.getRestaurantCount());
//		Log.d("getStudentCount: ", dbdb.getStudentCount());
//		Log.d("getFavoriteListCount: ", dbdb.getFavoriteListCount());
//		Log.d("getHistoryListCount: ", dbdb.getHistoryListCount());
//		Log.d("getDish: ", dbdb.getDish());
//		Log.d("getRestaurant: ", dbdb.getRestaurant());
//		Log.d("getStudent: ", dbdb.getStudent());
//		Log.d("getFavoriteList: ", dbdb.getFavoriteList());
//		Log.d("getHistoryList: ", dbdb.getHistoryList());
//		dbdb.passwordCheck("muyangy@andrew.cmu.edu", "123");
//		dbdb.login("muyangy@andrew.cmu.edu", "123");
//		Log.d("getCurrentUser: ", dbdb.getCurrentUser());
//		Log.d("GetData: ", dbdb.getRestaurantData("1"));
//		StudentContainer std = new StudentContainer("apple@aa.com", "123", null, null, null, null, null);
//		dbdb.addStudent(std);
//		dbdb.passwordCheck("apple@aa.com", "123");
//		StudentContainer std = new StudentContainer("apple@aa.com", "123", "jack", "jone", null, null, null);
//		dbdb.updateStudentInfo(std);
//		StudentContainer std = dbdb.getStudentInfo("muyangy@andrew.cmu.edu");
//		Log.d("getStudentCount: ", dbdb.getStudentCount());
//		Log.d("getStudent: ", dbdb.getStudent());
//		FavoriteListContainer favList = new FavoriteListContainer("5", "yinggao@andrew.cmu.edu");
//		dbdb.addToFavoriteList(favList);
//		Log.d("getFavoriteList: ", dbdb.getFavoriteList());
//		FavoriteListContainer favList = new FavoriteListContainer("5", "yinggao@andrew.cmu.edu");
//		dbdb.deleteFromFavoriteList(favList);
//		Log.d("getFavoriteList: ", dbdb.getFavoriteList());
//		ArrayList<String> al = dbdb.getFavoriteList("yinggao@andrew.cmu.edu");
//		HistoryListContainer historyList = new HistoryListContainer("muyangy@andrew.cmu.edu", "2013.11.22", "5");
//		dbdb.addToHistoryList(historyList);
//		Log.d("getHistoryList: ", dbdb.getHistoryList());
//		HistoryListContainer historyList = new HistoryListContainer("muyangy@andrew.cmu.edu", "2013.11.22", "5");
//		dbdb.deleteFromHistoryList(historyList);
//		Log.d("getHistoryList: ", dbdb.getHistoryList());
//		ArrayList<HistoryListContainer> hl = dbdb.getHistoryList("muyangy@andrew.cmu.edu");
//		RestaurantContainer rest = new RestaurantContainer("10", "ChuanXiangHui", "some place, pittsburgh, PA, 15213",
//				null, null, null, "chinese", "waht@gmail.com");
//		dbdb.addRestaurant(rest);
//		Log.d("getRestaurant: ", dbdb.getRestaurant());
//		dbdb.deleteRestaurant("10");
//		Log.d("getRestaurant: ", dbdb.getRestaurant());
//		Integer intval = dbdb.getRestaurantNum();
//		Log.d("getRestaurantNum: ", intval.toString());
//		RestaurantContainer rc = dbdb.getRestaurantInfo("1");
//		DishContainer dish = new DishContainer("5", "1", "Pig", "Not so bad", null, null);
//		dbdb.addDish(dish);
//		Log.d("getDish: ", dbdb.getDish());
//		dbdb.deleteDish("5", "1");
//		Log.d("getDish: ", dbdb.getDish());
//		dbdb.getDishNum("1");
//		DishContainer dc = dbdb.getDishInfo("1", "1");
//	}
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    
//    /** Called when the user clicks the calculate button */
//    public void calculate(View view) {
//    	Intent intent = new Intent(this, DisplayResult.class);
//    	
//    	EditText purchase_price = (EditText) findViewById(R.id.purchase_price);
//    	String message_purchase_price = purchase_price.getText().toString();
//    	intent.putExtra(PURCHASE_PRICE, message_purchase_price);
//
//    	EditText mortgage_term = (EditText) findViewById(R.id.mortgage_term);
//    	String message_mortgage_term = mortgage_term.getText().toString();
//    	intent.putExtra(MORTGAGE_TERM, message_mortgage_term);
//    	
//    	EditText interest_rate = (EditText) findViewById(R.id.interest_rate);
//    	String message_interest_rate = interest_rate.getText().toString();
//    	intent.putExtra(INTEREST_RATE, message_interest_rate);
//
//    	DatePicker datePicker = (DatePicker) findViewById(R.id.dp);
//    	Integer day = datePicker.getDayOfMonth();
//    	Integer month = datePicker.getMonth();
//    	Integer year = datePicker.getYear();
//    	String message_first_payment_date = day.toString() + " " + month.toString()
//    			+ " " + year.toString(); 
//    	intent.putExtra(FIRST_PAYMENT_DATE, message_first_payment_date);
//    	    	
//        db.addInputData(new InputContainer(1, message_purchase_price,
//        		message_mortgage_term, message_interest_rate)); 
//		  Log.d("Reading: ", "Reading a data from DB.."); 
//		  InputContainer data = db.getData(1);       
//		  String log = "Id: "+data.getId()+" ,Loan: " + data.getLoanAmount() +
//		" ,Term: " + data.getTermInYear() + " ,Interest: " +
//				  data.getInterestRate();
//		  Log.d("Data: ", log);
//    	
//
//    	startActivity(intent);
//    }
//    
//    TextWatcher watcher0to100 = new TextWatcher() {
//		
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//		}
//		
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count,
//				int after) {			
//		}
//		
//		@Override
//		public void afterTextChanged(Editable s) {
//			if (s.toString().equals("")) {
//				return;
//			}
//			int tempNum=Integer.parseInt(s.toString());  
//            if (tempNum>100) {
//            	s.replace(0, s.length(), "100");  
//            }
//		}
//	};
//	
}
