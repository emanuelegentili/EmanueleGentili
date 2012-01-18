package mypackage.workspace;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Second extends Activity {
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        
    	TextView label = (TextView) findViewById(R.id.textView2);
		String iltestoricevuto = getIntent().getExtras().getString("iltestonelbox");
		label.setText(iltestoricevuto);
	}
	
	public void onStart(){
		super.onStart();
		Log.d("Second","onStart");
	}
	public void onRestart(){
		super.onRestart();
		Log.d("Second","onRestart");
	}
	public void onResume(){
		super.onResume();
		Log.d("Second","onResume");
	}
	public void onPause(){
		super.onPause();
		Log.d("Second","onPause");
	}
	public void onStop(){
		super.onStop();
		Log.d("Second","onStop");
	}
	public void onDestroy(){
		super.onDestroy();
		Log.d("Second","onDestroy");
	}
}

