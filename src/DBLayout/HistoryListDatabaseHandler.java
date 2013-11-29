package DBLayout;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HistoryListDatabaseHandler {
 
	public static boolean isExist(SQLiteDatabase db, HistoryListContainer historyList) {
		Cursor cursor = db.query("HistoryList", new String[] {"email", "visit_date", "rest_id"},
				"email" + "=?" + " AND " + "visit_date" + "=?" + " AND " + "rest_id" + "=?",
				new String[] { historyList.getEmail(),	historyList.getVisitDate(),
				historyList.getRestId() }, null, null, null, null);
		if (cursor == null) {
			return false;
		}
		if (cursor.getCount() <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void addToHistoryList(SQLiteDatabase db, HistoryListContainer historyList) {
		ContentValues values = new ContentValues();
		values.put("email", historyList.getEmail());
		values.put("visit_date", historyList.getVisitDate());
		values.put("rest_id", historyList.getRestId());
		
		// Inserting Row
		db.insert("HistoryList", null, values);
	}
	
	public static void deleteFromFavoriteList(SQLiteDatabase db, HistoryListContainer historyList) {
		db.delete("HistoryList", "email" + "=?" + " AND " + "visit_date" + "=?" + " AND " + "rest_id" + "=?",
				new String[] { historyList.getEmail(), historyList.getVisitDate(), historyList.getRestId() });
	}
	
	public static ArrayList<HistoryListContainer> getHistoryList(SQLiteDatabase db, String email) {
		Cursor cursor = db.query("HistoryList", new String[] { "email", "visit_date", "rest_id" }, 
				"email" + "=?", new String[] { email }, null, null, null);
		ArrayList<HistoryListContainer> historyList = new ArrayList<HistoryListContainer>();
		if (cursor != null) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				HistoryListContainer historyContainer = new HistoryListContainer(cursor.getString(0), cursor.getString(1), cursor.getString(2));
				historyList.add(historyContainer);
			}
		}
		return historyList;
	}
}
