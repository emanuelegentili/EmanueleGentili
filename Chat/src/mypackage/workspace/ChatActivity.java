package mypackage.workspace;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity {

	public String TAG = "ppl.connection.manager";

	String destin;
	EditText et;
	// TextView tv;
	Button button;
	ListView listView;
	Connection connection;
	ArrayAdapter<String> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		et = (EditText) findViewById(R.id.editText);
		button = (Button) findViewById(R.id.bSend);

		listView = (ListView) findViewById(R.id.listView);
		
		adapter = new ArrayAdapter<String>(this, R.layout.row,R.id.rowText);

		// tv = (TextView) findViewById(R.id.textView);
		// tv.setMovementMethod(new ScrollingMovementMethod());

		try {
			ConnectionConfiguration config = new ConnectionConfiguration(
					"ppl.eln.uniroma2.it", 5222);
			config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
			connection = new XMPPConnection(config);
			Log.d(TAG, "connessione creata");
			connection.connect();
			String user = getIntent().getExtras().getString("username");
			String pass = getIntent().getExtras().getString("password");
			destin = getIntent().getExtras().getString("destinatario");
			connection.login(user, pass);
			Log.d(TAG, "XMPP Connection Started");
		} catch (XMPPException e) {
			e.printStackTrace();
		}

		connection.addPacketListener(new PacketListener() {
			@Override
			public void processPacket(Packet pkt) {
				Message msg = (Message) pkt;
				String from = msg.getFrom();
				String body = msg.getBody();
				Log.d(TAG, "msg arrivato");
	

				adapter.add(from + " : " + body + "\n");
				

				// tv.append(from+" : "+body+"\n");
				Log.d(TAG, "msg visualizzato");
			}
		}, new MessageTypeFilter(Message.Type.normal));

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// tv.append("ME:" + et.getText().toString() + "\n");

				listView.setAdapter(adapter);
				adapter.add("ME: "+et.getText().toString());
				listView.setSelection(adapter.getCount()-1);
				

				listView.setSelection(adapter.getCount() - 1);
				Message msg = new Message();
				msg.setTo(destin+"@ppl.eln.uniroma2.it");
				msg.setBody(et.getText().toString());
				connection.sendPacket(msg);
				Log.d(TAG, "msg inviato");

			}
		});
	}
}