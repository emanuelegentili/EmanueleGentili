package mypackage.workspace;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TextDialerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        Button bChiama = (Button)findViewById(R.id.button1);
        bChiama.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
        
        EditText etext = (EditText)findViewById(R.id.editText1);
        etext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        String telString = etext.getText().toString();
        
        String telUriString = "tel" + telString;
        Uri telURI = Uri.parse(telUriString);
        
        Intent intent_call = new Intent(Intent.ACTION_DIAL);
        intent_call.setData(telURI);
        startActivity(intent_call);
        }});
        
        
        Button bChiamaDir = (Button)findViewById(R.id.button1);
        bChiamaDir.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
        
        EditText etext2 = (EditText)findViewById(R.id.editText1);
        etext2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        String telString2 = etext2.getText().toString();
        
        String telUriString = "tel" + telString2;
        Uri telURI2 = Uri.parse(telUriString);
        
        Intent intent_call_dir = new Intent(Intent.ACTION_CALL);
        intent_call_dir.setData(telURI2);
        startActivity(intent_call_dir);
        
        }
        });
        
    }
}