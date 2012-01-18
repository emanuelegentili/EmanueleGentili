package mypackage.workspace;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyPlayerActivity extends Activity {
    /** Called when the activity is first created. */
	 MediaPlayer mp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mp = MediaPlayer.create(MyPlayerActivity.this, R.raw.dst);
        
        //START
        Button start = (Button)findViewById(R.id.button1);
        start.setOnClickListener(new OnClickListener(){
        	@Override
            public void onClick(View v){
            	mp.start();
            }
        });
        
      //PAUSE
        Button pause = (Button)findViewById(R.id.button2);
        pause.setOnClickListener(new OnClickListener(){
        	@Override
            public void onClick(View v){
            	mp.pause();
            }
        }); 
        
      
       
    }
    
    public void onDestroy(){
    	mp.release();
    	 
    }
    
}