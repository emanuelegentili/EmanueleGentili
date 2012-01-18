package mypackage.workspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText text = (EditText)findViewById(R.id.editText1);
        
        Button bottone = (Button)findViewById(R.id.button1);
        bottone.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		Intent intent = new Intent(Main.this,Second.class);
        		String iltesto = text.getText().toString();
        		intent.putExtra("iltestonelbox", iltesto);
        		startActivity(intent);
     
        	}
        	 	
        });
        
    }
}