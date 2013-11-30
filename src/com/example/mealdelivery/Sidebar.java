package com.example.mealdelivery;


import java.util.ArrayList;

import DBLayout.DragonBroDatabaseHandler;
import android.content.Intent;

import com.example.mealdelivery.SidebarView.SizeCallback;

import utilities.Config;
import adapter.MenuAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Sidebar extends Activity implements OnGestureListener {
	SidebarView scrollView;
	View scrollView1;
    MenuAdapter menuAdapter;
    View menu;
    View app;
    Button btnSlide;
    static boolean menuOut = false;
    boolean isScan = false;
    Handler handler = new Handler();
    int btnWidth;
    ArrayList<String> options = new ArrayList<String>();
    boolean loadingFinished = true;
    boolean redirect = false;
    AlertDialog.Builder alert;
    private DragonBroDatabaseHandler dbdb;
    private GestureDetector detector; 

    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbdb = new DragonBroDatabaseHandler(this);
        if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy); 
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (SidebarView) inflater.inflate(R.layout.sidebar_list_menu, null);
        setContentView(scrollView);
        detector = new GestureDetector(this); // used in onTouchEvent functio to capture event

        menu = inflater.inflate(R.layout.sidebar_scroll_menu, null);
        app = inflater.inflate(R.layout.sidebar, null);
        //webView =(WebView) app.findViewById(R.id.webView);
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
        
        options = Config.createOptions();
        menuAdapter = new MenuAdapter(this,R.layout.link, options);
        ListView listView = (ListView) menu.findViewById(R.id.list);
        //ViewUtils.initListView(this, listView, "Menu ", 8, android.R.layout.simple_list_item_1);
        listView.setAdapter(menuAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuOut = true;
                scrollSideBarVeiw(scrollView, menu);
        		menuOut = false;
        	    Intent myIntent;
        	    TextView title = (TextView)findViewById(R.id.txtTitle);
        	    title.setText(options.get(position));
        	    switch (position) {
	        	    case 0:
	        	    	break;
	        	    case 1:
	        	    	myIntent = new Intent(Sidebar.this, SearchByName.class);
	        	    	startActivity(myIntent);
	        	    	break;
	        	    case 2:
	        	    	myIntent = new Intent(Sidebar.this, Nearby.class);
	        	    	startActivity(myIntent);
	        	    	break;
	        	    case 3:
		    	    	myIntent = new Intent(Sidebar.this, Shake.class);
		    	    	startActivity(myIntent);
		    	    	break;
	        	    case 4:
	        	    	myIntent = new Intent(Sidebar.this, Mine.class);
	        	    	startActivity(myIntent);
	        	    	break;
	        	    case 5:
			        	dbdb.logout();
				    	myIntent = new Intent(Sidebar.this, Signin.class);
				    	startActivity(myIntent);
				    	break;
			    }
            }
        });
        
        btnSlide = (Button) tabBar.findViewById(R.id.BtnSlide);
       
        btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView, menu));

        final View[] children = new View[] { menu, app };

        // Scroll to app (view[1]) when layout finished.

        int scrollToViewIdx = 1;
        
        scrollView.initViews(children, scrollToViewIdx, new SizeCallbackForMenu(btnSlide));
    }
    
    

     /**
     * Helper for examples with a HSV that should be scrolled by a menu View's width.
     */
    static class ClickListenerForScrolling implements OnClickListener {
        SidebarView scrollView;
        View menu;
        /**
         * Menu must NOT be out/shown to start with.
         */
        //boolean menuOut = false;

        public ClickListenerForScrolling(SidebarView scrollView, View menu) {
            super();
            this.scrollView = scrollView;
            this.menu = menu;
        }

        @Override
        public void onClick(View v) {            
            int menuWidth = menu.getMeasuredWidth();

            // Ensure menu is visible
            menu.setVisibility(View.VISIBLE);

            if (!menuOut) {
                // Scroll to 0 to reveal menu
            	Log.d("===slide==","Scroll to right");
            	Log.d("===clicked==","clicked");
                int left =20;
                scrollView.smoothScrollTo(left, 0);
            } else {
                // Scroll to menuWidth so menu isn't on screen.
            	Log.d("===slide==","Scroll to left");
            	Log.d("===clicked==","clicked");
                int left = menuWidth;
                scrollView.smoothScrollTo(left, 0);
            }
            menuOut = !menuOut;
        }
    }

    /**
     * Helper that remembers the width of the 'slide' button, so that the 'slide' button remains in view, even when the menu is
     * showing.
     */
    static class SizeCallbackForMenu implements SizeCallback {
        int btnWidth;
        View btnSlide;

        public SizeCallbackForMenu(View btnSlide) {
            super();
            this.btnSlide = btnSlide;
        }

        @Override
        public void onGlobalLayout() {
            btnWidth = btnSlide.getMeasuredWidth();
            System.out.println("btnWidth=" + btnWidth);
        }

        @Override
        public void getViewSize(int idx, int w, int h, int[] dims) {
            dims[0] = w;
            dims[1] = h;
            final int menuIdx = 0;
            if (idx == menuIdx) {
                dims[0] = w - btnWidth;
            }
        }
    }
    
    
    //scroll the page
    private void scrollSideBarVeiw(SidebarView scrollView, View menu) {
         
         int menuWidth = menu.getMeasuredWidth();

         // Ensure menu is visible
         menu.setVisibility(View.VISIBLE);

         if (!menuOut) {
             // Scroll to 0 to reveal menu
         	Log.d("===slide==","Scroll to right");
             int left = 0;
             scrollView.smoothScrollTo(left, 0);
         } else {
             // Scroll to menuWidth so menu isn't on screen.
         	Log.d("===slide==","Scroll to left");
             int left = menuWidth;
             scrollView.smoothScrollTo(left, 0);
            
         }
         menuOut = false;
    }
    
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
    	// catch touch event and trigger corresponding gesture function 
        return this.detector.onTouchEvent(event);  
    }  
        
    @Override
    protected void onResume() {
    	super.onResume();
    }


	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
        if (e1.getX() - e2.getX() > 120) { // left fling, hide menu
        	scrollView.smoothScrollTo(menu.getMeasuredWidth(), 0); 
            return true;  
        } else if (e1.getX() - e2.getX() < -120) { // right fling, show menu
        	scrollView.smoothScrollTo(0, 0); 
            return true;  
        }  
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {}


	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}

