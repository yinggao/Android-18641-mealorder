package DBLayout;


import java.util.ArrayList;

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


public class SQLiteUsageExample extends Activity {
	
    private DragonBroDatabaseHandler dbdb;
    
    /*
     * The onCreate function demonstrate how to use
     * SQLite database in our app.
     */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		dbdb = new DragonBroDatabaseHandler(this);
		Log.d("GetData: ", dbdb.getStudentData("muyangy@andrew.cmu.edu"));
		Log.d("getRestaurantCount: ", dbdb.getRestaurantCount());
		Log.d("getStudentCount: ", dbdb.getStudentCount());
		Log.d("getFavoriteListCount: ", dbdb.getFavoriteListCount());
		Log.d("getHistoryListCount: ", dbdb.getHistoryListCount());
		Log.d("getDish: ", dbdb.getDish());
		Log.d("getRestaurant: ", dbdb.getRestaurant());
		Log.d("getStudent: ", dbdb.getStudent());
		Log.d("getFavoriteList: ", dbdb.getFavoriteList());
		Log.d("getHistoryList: ", dbdb.getHistoryList());
		dbdb.passwordCheck("muyangy@andrew.cmu.edu", "123");
		dbdb.login("muyangy@andrew.cmu.edu", "123");
		Log.d("getCurrentUser: ", dbdb.getCurrentUser());
		Log.d("GetData: ", dbdb.getRestaurantData("1"));
		StudentContainer std = new StudentContainer("apple@aa.com", "123", null, null, null, null, null);
		dbdb.addStudent(std);
		dbdb.passwordCheck("apple@aa.com", "123");
		StudentContainer std1 = new StudentContainer("apple@aa.com", "123", "jack", "jone", null, null, null);
		dbdb.updateStudentInfo(std);
		StudentContainer std2 = dbdb.getStudentInfo("muyangy@andrew.cmu.edu");
		Log.d("getStudentCount: ", dbdb.getStudentCount());
		Log.d("getStudent: ", dbdb.getStudent());
		FavoriteListContainer favList1 = new FavoriteListContainer("5", "yinggao@andrew.cmu.edu");
		dbdb.addToFavoriteList(favList1);
		Log.d("getFavoriteList: ", dbdb.getFavoriteList());
		FavoriteListContainer favList2 = new FavoriteListContainer("5", "yinggao@andrew.cmu.edu");
		dbdb.deleteFromFavoriteList(favList2);
		Log.d("getFavoriteList: ", dbdb.getFavoriteList());
		ArrayList<String> al = dbdb.getFavoriteList("yinggao@andrew.cmu.edu");
		HistoryListContainer historyList1 = new HistoryListContainer("muyangy@andrew.cmu.edu", "2013.11.22", "5");
		dbdb.addToHistoryList(historyList1);
		Log.d("getHistoryList: ", dbdb.getHistoryList());
		HistoryListContainer historyList2 = new HistoryListContainer("muyangy@andrew.cmu.edu", "2013.11.22", "5");
		dbdb.deleteFromHistoryList(historyList2);
		Log.d("getHistoryList: ", dbdb.getHistoryList());
		ArrayList<HistoryListContainer> hl = dbdb.getHistoryList("muyangy@andrew.cmu.edu");
		RestaurantContainer rest = new RestaurantContainer("10", "ChuanXiangHui", "some place, pittsburgh, PA, 15213",
				null, null, null, "chinese", "waht@gmail.com");
		dbdb.addRestaurant(rest);
		Log.d("getRestaurant: ", dbdb.getRestaurant());
		dbdb.deleteRestaurant("10");
		Log.d("getRestaurant: ", dbdb.getRestaurant());
		Integer intval = dbdb.getRestaurantNum();
		Log.d("getRestaurantNum: ", intval.toString());
		RestaurantContainer rc = dbdb.getRestaurantInfo("1");
		DishContainer dish = new DishContainer("5", "1", "Pig", "Not so bad", null, null);
		dbdb.addDish(dish);
		Log.d("getDish: ", dbdb.getDish());
		dbdb.deleteDish("5", "1");
		Log.d("getDish: ", dbdb.getDish());
		dbdb.getDishNum("1");
		DishContainer dc = dbdb.getDishInfo("1", "1");
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    TextWatcher watcher0to100 = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			if (s.toString().equals("")) {
				return;
			}
			int tempNum=Integer.parseInt(s.toString());  
            if (tempNum>100) {
            	s.replace(0, s.length(), "100");  
            }
		}
	};
}
