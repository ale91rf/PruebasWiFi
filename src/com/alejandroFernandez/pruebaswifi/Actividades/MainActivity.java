package com.alejandroFernandez.pruebaswifi.Actividades;

import java.util.List;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandroFernandez.pruebaswifi.R;
import com.alejandroFernandez.pruebaswifi.Utils.ContrasennaDialogFragment;
import com.alejandroFernandez.pruebaswifi.Utils.ContrasennaDialogFragment.positiveDialogListener;
import com.alejandroFernandez.pruebaswifi.Utils.WifiAdapter;

public class MainActivity extends Activity implements positiveDialogListener{
	
	private ListView lstWifi;
	private Button btnBuscar;
	private TextView txtEstado;
	private ScanResult result;
	private WifiManager wifi;
	private WifiInfo info;
	private WifiAdapter adapter;
	private List<ScanResult> lista;
	private String SSID = "ssid";
	private String ssidResul;
	private WifiConfiguration conf;
	public static String pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getVistas();
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		pintarEstado();
		btnBuscar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buscarWifi();
				
			}
		});
	}
	
	public void getVistas(){
		lstWifi = (ListView) findViewById(R.id.lstWifi);
		btnBuscar = (Button) findViewById(R.id.btnBuscar);
		txtEstado = (TextView) findViewById(R.id.txtEstadoWifi);
	}
		
	
	
	public void pintarEstado(){
		
		info = wifi.getConnectionInfo();
		txtEstado.setText("Estado: "+info);
	}
	
	public void buscarWifi(){
		
		//Lista de wifi's disponibles
		lista = wifi.getScanResults();
		
		adapter = new WifiAdapter(this, lista);
		lstWifi.setAdapter(adapter);
		
		//Cuando clicamos en un item nos intentamos conectar
		lstWifi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				result = lista.get(position);
				

				//Sacamos un dialog para meter contrasenna
				ContrasennaDialogFragment dialog = new ContrasennaDialogFragment();
				Bundle bundle = new Bundle();
				bundle.putString(SSID, result.SSID);
				dialog.setArguments(bundle);
				dialog.show(getFragmentManager(), "Dialog");

			}
		});
	}
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	//Metodo llamado al pulsar aceptar en el dialogo fragment
	@Override
	public void onPositiveButtonClick(DialogFragment dialog) {
		//conectamos pasandole el SSID y la key
		
		conf = new WifiConfiguration();
		
		ssidResul = "\""+result.SSID+"\"";
		
		conf.SSID = ssidResul;
		
		conf.status = WifiConfiguration.Status.ENABLED;
		conf.priority = 40;
		
		conf.allowedAuthAlgorithms.clear();
		
		
		
		
		//para WPA network
		
		conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		
		
		conf.preSharedKey = "\"" + pass + "\"";
		
		
		
		//lo annadimos a WifiManager
		
		int networId = wifi.addNetwork(conf);
		if(networId != -1){
			wifi.enableNetwork(networId, true);
		}
		
		
		
	}
}
