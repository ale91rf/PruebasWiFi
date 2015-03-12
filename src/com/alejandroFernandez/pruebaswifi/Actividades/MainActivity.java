package com.alejandroFernandez.pruebaswifi.Actividades;

import java.util.ArrayList;
import java.util.List;

import com.alejandroFernandez.pruebaswifi.R;
import com.alejandroFernandez.pruebaswifi.Utils.WifiAdapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private ListView lstWifi;
	private Button btnBuscar;
	private TextView txtEstado;
	private WifiManager wifi;
	private WifiInfo info;
	private WifiAdapter adapter;
	
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
		List<ScanResult> lista = wifi.getScanResults();
		
		adapter = new WifiAdapter(this, lista);
		lstWifi.setAdapter(adapter);
		
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
}
