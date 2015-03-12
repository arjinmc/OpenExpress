package com.arjinmc.openexpress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.arjinmc.openexpress.fragments.AboutFragment;
import com.arjinmc.openexpress.fragments.DeliverFragment;
import com.arjinmc.openexpress.fragments.HistoryFragment;
import com.arjinmc.openexpress.fragments.HomeFragment;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemHistory;
    private ResideMenuItem itemDeliver;
    private ResideMenuItem itemAbout;
    
    private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		setUpMenu();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
	}	
	
	private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackgroundColor(Color.parseColor(getString(R.color.menu_bg)));
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		//valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
		resideMenu.setScaleValue(0.6f);
		
		// create menu items;
		itemHome     = new ResideMenuItem(this, R.drawable.ic_home, "Home");
		itemHistory  = new ResideMenuItem(this, R.drawable.ic_history,"History");
		itemDeliver = new ResideMenuItem(this, R.drawable.ic_deliver, "Delivers");
		itemAbout = new ResideMenuItem(this, R.drawable.ic_about, "About");
		
		itemHome.setOnClickListener(this);
		itemHistory.setOnClickListener(this);
		itemDeliver.setOnClickListener(this);
		itemAbout.setOnClickListener(this);
		
		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemHistory, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemDeliver, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
		
		// You can disable a direction by setting ->
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
		
		    findViewById(R.id.ib_menu).setOnClickListener(new View.OnClickListener() {
		        @Override
		        public void onClick(View view) {
		            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
		        }
		    });
		}
		
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
		    return resideMenu.dispatchTouchEvent(ev);
		}
		
		@Override
		public void onClick(View view) {
		
		    if (view == itemHome){
		        changeFragment(new HomeFragment());
		        tvTitle.setText("Home");
		    }else if (view == itemHistory){
		        changeFragment(new HistoryFragment());
		        tvTitle.setText("History");
		    }else if (view == itemDeliver){
		        changeFragment(new DeliverFragment());
		        tvTitle.setText("Delivers");
		    }else if (view == itemAbout){
		        changeFragment(new AboutFragment());
		        tvTitle.setText("About");
		    }
		
		    resideMenu.closeMenu();
		}
		
		private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		    @Override
		    public void openMenu() {
		        //Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
		    }
		
			@Override
			public void closeMenu() {
			    //Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
		    }
		};
			
		
		private void changeFragment(Fragment targetFragment){
		    resideMenu.clearIgnoredViewList();
		    getSupportFragmentManager()
		            .beginTransaction()
		            .replace(R.id.fragements, targetFragment, "fragment")
		            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		            .commit();
		}
		
		public ResideMenu getResideMenu(){
		    return resideMenu;
		}

}
