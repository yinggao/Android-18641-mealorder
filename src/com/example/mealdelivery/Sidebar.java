package com.example.mealdelivery;


import java.util.ArrayList;
import android.content.Intent;
import java.util.Stack;

import com.example.mealdelivery.SidebarView.SizeCallback;

import utilities.Config;
import adapter.MenuAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Sidebar extends Activity {
	SidebarView scrollView;
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

    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy); 
        }
        
        LayoutInflater inflater = LayoutInflater.from(this);
        scrollView = (SidebarView) inflater.inflate(R.layout.sidebar_list_menu, null);
        setContentView(scrollView);

        final Stack stack=new Stack();
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
                Context context = view.getContext();
                menuOut = true;
                scrollSideBarVeiw(scrollView, menu);
        		menuOut = false;
        	    stack.push(options.get(position));
        	    Log.d("The contents of Stack is" , stack.toString());
        	    switch (position) {
        	    case 0:
        	    	Intent myIntent = new Intent(Sidebar.this, SearchByName.class);
        	    	startActivity(myIntent);
        	    	break;
        	    }
        	    TextView title = (TextView)findViewById(R.id.txtTitle);
        	    title.setText(options.get(position));
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
            Context context = menu.getContext();
            
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
    	 Context context = menu.getContext();
         
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
    protected void onResume() {
    	super.onResume();
    	
    }
}

