package mypackage.workspace;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.Connection;
//import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements MessageReceiver {

	protected static final int SHOW_TOAST = 0;

	TextView tv;
	ConnectionManager connection;
	private Stato statoCorrente;
	private EditText nomeProprio, nomeAvversario;
	public String TAG = "workspace.indovinailnumero.main";
	public Button B1;
	public Button B2;
	public Button B3;
	private String selectedNumber;

	enum Stato {
		WAIT_FOR_START, WAIT_FOR_START_ACK, USER_SELECTING, WAIT_FOR_NUMBER_SELECTION, WAIT_FOR_BET, USER_BETTING
	}

	Timer timer = new Timer();
	TimerTask sendStart = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (statoCorrente == Stato.WAIT_FOR_START_ACK) {
				connection.send("START");
			} else {
				Log.d(TAG, "Sending START but the state is " + statoCorrente);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tv = (TextView) findViewById(R.id.textView3);
		B1 = (Button) findViewById(R.id.button1);
		B2 = (Button) findViewById(R.id.button2);
		B3 = (Button) findViewById(R.id.button3);

		String nomeProprio = getIntent().getExtras().getString("NOMEPROP");
		String nomeAvversario = getIntent().getExtras().getString("NOMEAVV");

		String players = getIntent().getExtras().getString("players");
		tv.setText(players);
		connection = new ConnectionManager(nomeProprio, nomeAvversario, this);

		if (nomeAvversario.hashCode() < nomeProprio.hashCode()) {
			// Inizio io
			timer.schedule(sendStart, 1000, 5000);
			statoCorrente = Stato.WAIT_FOR_START_ACK;
		} else {
			// Inizia lui
			// Io aspetto il pacchetto;
			statoCorrente = Stato.WAIT_FOR_START;
		}

		B1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				numberSelected(v);

			}
		});

		B2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				numberSelected(v);
			}
		});

		B3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				numberSelected(v);

			}
		});

	}

	@Override
	public void receiveMessage(String body) {

		if (body.equals("START")) {
			if (statoCorrente == Stato.WAIT_FOR_START) {
				// Mando l'ack indietro
				connection.send("STARTACK");
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				b.putString("toast", "Scegli un numero");
				osmsg.setData(b);
				handler.sendMessage(osmsg);
				statoCorrente = Stato.USER_SELECTING;
			} else {
				Log.e(TAG, "Ricevuto START ma lo stato è " + statoCorrente);
			}
		} else if (body.equals("STARTACK")) {
			if (statoCorrente == Stato.WAIT_FOR_START_ACK) {
				statoCorrente = Stato.WAIT_FOR_NUMBER_SELECTION;
			} else {
				Log.e(TAG, "Ricevuto STARTACK ma lo stato è " + statoCorrente);
			}
		} else if (body.startsWith("SELECTED")) {
			if (statoCorrente == Stato.WAIT_FOR_NUMBER_SELECTION) {
				selectedNumber = body.split(":")[1];
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				b.putString("toast", "Indovina il numero");
				osmsg.setData(b);
				handler.sendMessage(osmsg);
				statoCorrente = Stato.USER_BETTING;
			} else {
				Log.e(TAG, "Ricevuto SELECTED ma lo stato è " + statoCorrente);
			}
		} else if (body.startsWith("BET")) {
			if (statoCorrente == Stato.WAIT_FOR_BET) {
				String result = body.split(":")[1];
				Log.e(TAG,"result " + result + "numeroselezionato" + selectedNumber);
				Message osmsg = handler.obtainMessage(Main.SHOW_TOAST);
				Bundle b = new Bundle();
				if (result.equals(selectedNumber))
					b.putString("toast",
							"Hai perso, il tuo avversario ha indovinato");
				else
					b.putString("toast",
							"Hai vinto, il tuo avversario ha sbagliato");
				osmsg.setData(b);
				handler.sendMessage(osmsg);
				statoCorrente = Stato.WAIT_FOR_NUMBER_SELECTION;
			} else {
				Log.e(TAG, "Ricevuto SELECTED ma lo stato è " + statoCorrente);
			}
		}

	}

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Main.SHOW_TOAST:
				Toast.makeText(Main.this, msg.getData().getString("toast"),
						Toast.LENGTH_LONG).show();
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};

	public void numberSelected(View v) {
		Button b = (Button) v;
		
		if (statoCorrente == Stato.USER_SELECTING) {
			connection.send("SELECTED:" + b.getText().toString());
			selectedNumber = b.getText().toString();
			
			statoCorrente = Stato.WAIT_FOR_BET;
		} else if (statoCorrente == Stato.USER_BETTING) {
			String bet = b.getText().toString();
			connection.send("BET:" + bet);
			if (bet.equals(selectedNumber)) {
				Toast.makeText(Main.this, "Bravo hai indovinato, ora tocca te",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(Main.this,
						"Peccato non hai indovinato, ora tocca te",
						Toast.LENGTH_LONG).show();
			}
			statoCorrente = Stato.USER_SELECTING;
		}
	}

}
