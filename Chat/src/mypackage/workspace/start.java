package mypackage.workspace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class start extends Activity {
	EditText user, pass, destinatario;
	Button login;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		user = (EditText) findViewById(R.id.User);
		pass = (EditText) findViewById(R.id.Pass);
		destinatario = (EditText) findViewById(R.id.Destinatario);
		
		
		login = (Button) findViewById(R.id.bLogin);
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//startActivity(new Intent(start.this, ChatActivity.class));
				Intent intent = new Intent(start.this, ChatActivity.class);
				String username = user.getText().toString();
				String password = pass.getText().toString();
				String destin = destinatario.getText().toString();
				
				intent.putExtra("username", username);
				intent.putExtra("password", password);
				intent.putExtra("destinatario", destin);
				startActivity(intent);
			}
		});
	}
}