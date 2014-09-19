package com.flourix.ibikeyiwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Loading extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 4000;
	
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉标题
        //getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR, WindowManager.LayoutParams.TYPE_STATUS_BAR); //全屏
        setContentView(R.layout.loading);
         
        new Handler().postDelayed(new Runnable() {  
            public void run() {  
                Intent mainIntent = new Intent(Loading.this,  
                        MainActivity.class);  
                Loading.this.startActivity(mainIntent);  
                Loading.this.finish();  
            }  
   
        }, SPLASH_DISPLAY_LENGHT);  
    }
	
	
}
