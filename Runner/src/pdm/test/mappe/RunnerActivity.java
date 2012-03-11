package pdm.test.mappe;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import android.R.color;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RunnerActivity extends MapActivity {
	/** Called when the activity is first created. */
	private static final String TAG = null;

	MapView mapView;
	MyLocationOverlay myLocationOverlay;
	GeoPoint stazioneTermini, piazzaDellaRepubblica, colosseo, casaDiRomolo,
			frascati, roccapriora;
	RadiusOverlay overlayTermini, overlayRepubblica, overlayColosseo,
			overlayRomolo, overlayFrascati, overlayRoccapriora;
	PendingIntent mPendingTermini;
	PendingIntent mPendingRepubblica;
	PendingIntent mPendingRomolo;
	PendingIntent mPendingColosseo;
	PendingIntent mPendingFrascati;
	PendingIntent mPendingRoccaPriora;

	ProximityBroadcast mProximityBroadcast;
	LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mProximityBroadcast = new ProximityBroadcast();
		mapView = (MapView) findViewById(R.id.MapView);
		stazioneTermini = new GeoPoint(41901222, 12500882);
		piazzaDellaRepubblica = new GeoPoint(41902622, 12495482);
		colosseo = new GeoPoint(41890310, 12492410);
		casaDiRomolo = new GeoPoint(41890492, 12484823);
		frascati = new GeoPoint(41806204, 12680429);
		roccapriora = new GeoPoint(41791780, 12760655);

		overlayTermini = new RadiusOverlay(stazioneTermini, 400, Color.BLUE);
		overlayRepubblica = new RadiusOverlay(piazzaDellaRepubblica, 300,Color.RED);
		overlayColosseo = new RadiusOverlay(colosseo, 500, Color.CYAN);
		overlayRomolo = new RadiusOverlay(casaDiRomolo, 450, Color.WHITE);
		overlayFrascati = new RadiusOverlay(frascati, 150, Color.MAGENTA);
		overlayRoccapriora = new RadiusOverlay(roccapriora, 200, Color.YELLOW);

		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						myLocationOverlay.getMyLocation());
			}
		});

		mapView.getOverlays().add(myLocationOverlay);
		mapView.getOverlays().add(overlayTermini);
		mapView.getOverlays().add(overlayRepubblica);
		mapView.getOverlays().add(overlayColosseo);
		mapView.getOverlays().add(overlayRomolo);
		mapView.getOverlays().add(overlayFrascati);
		mapView.getOverlays().add(overlayRoccapriora);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();

		Intent intentTermini = new Intent("pdm.test.mappe");
		intentTermini.putExtra("overlay", 1);
		mPendingTermini = PendingIntent.getBroadcast(this, 1, intentTermini,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Intent intentRepubblica = new Intent("pdm.test.mappe");
		intentRepubblica.putExtra("overlay", 2);
		mPendingRepubblica = PendingIntent.getBroadcast(this, 2,
				intentRepubblica, PendingIntent.FLAG_CANCEL_CURRENT);

		Intent intentColosseo = new Intent("pdm.test.mappe");
		intentColosseo.putExtra("overlay", 3);
		mPendingColosseo = PendingIntent.getBroadcast(this, 3, intentColosseo,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Intent intentRomolo = new Intent("pdm.test.mappe");
		intentRomolo.putExtra("overlay", 4);
		mPendingRomolo = PendingIntent.getBroadcast(this, 4, intentRomolo,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Intent intentFrascati = new Intent("pdm.test.mappe");
		intentFrascati.putExtra("overlay", 5);
		mPendingFrascati = PendingIntent.getBroadcast(this, 5, intentFrascati,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Intent intentRoccapriora = new Intent("pdm.test.mappe");
		intentRoccapriora.putExtra("overlay", 6);
		mPendingRoccaPriora = PendingIntent.getBroadcast(this, 6,
				intentRoccapriora, PendingIntent.FLAG_CANCEL_CURRENT);

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.addProximityAlert(
				stazioneTermini.getLatitudeE6() * 0.000001,
				stazioneTermini.getLongitudeE6() * 0.000001, 400, -1,mPendingTermini);

		locationManager
				.addProximityAlert(colosseo.getLatitudeE6() * 0.000001,
						colosseo.getLongitudeE6() * 0.000001, 500, -1,mPendingColosseo);

		locationManager.addProximityAlert(
				piazzaDellaRepubblica.getLatitudeE6() * 0.000001,
				piazzaDellaRepubblica.getLongitudeE6() * 0.000001, 300, -1,mPendingRepubblica);

		locationManager.addProximityAlert(
				casaDiRomolo.getLatitudeE6() * 0.000001,
				casaDiRomolo.getLongitudeE6() * 0.000001, 450, -1,mPendingRomolo);

		locationManager
				.addProximityAlert(frascati.getLatitudeE6() * 0.000001,
						frascati.getLongitudeE6() * 0.000001, 150, -1,mPendingFrascati);

		locationManager.addProximityAlert(
				roccapriora.getLatitudeE6() * 0.000001,
				roccapriora.getLongitudeE6() * 0.000001, 200, -1,mPendingRoccaPriora);

		registerReceiver(mProximityBroadcast,
				new IntentFilter("pdm.test.mappe"));

	}

	public void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		locationManager.removeProximityAlert(mPendingTermini);
		locationManager.removeProximityAlert(mPendingColosseo);
		locationManager.removeProximityAlert(mPendingRepubblica);
		locationManager.removeProximityAlert(mPendingRomolo);
		locationManager.removeProximityAlert(mPendingFrascati);
		locationManager.removeProximityAlert(mPendingRoccaPriora);
		unregisterReceiver(mProximityBroadcast);
	}

	class ProximityBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			
			int area = intent.getIntExtra("overlay", -1);
			//RadiusOverlay selected = intent;
			
			Log.d(TAG, "Proximity Alert");
			//Toast.makeText(getApplicationContext(), "Alert di prossimità",Toast.LENGTH_LONG).show();
			boolean stoEntrando = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
			if (stoEntrando) {
				Toast.makeText(getApplicationContext(), "Benvenuto nell'area "+area,Toast.LENGTH_SHORT).show();
			
				
				if(area==1){
					overlayTermini.setColor(Color.GREEN);
				}
				if(area==2){
					overlayRepubblica.setColor(Color.GREEN);
				}
				if(area==3){
					overlayColosseo.setColor(Color.GREEN);
				}
				if(area==4){
					overlayRomolo.setColor(Color.GREEN);
				}
				if(area==5){
					overlayFrascati.setColor(Color.GREEN);
				}
				if(area==6){
					overlayRoccapriora.setColor(Color.GREEN);
				}			
			} else {
				Toast.makeText(getApplicationContext(), "Arrivederci", Toast.LENGTH_SHORT).show();
				overlayTermini.setColor(Color.LTGRAY);
			}

		}
	}
}