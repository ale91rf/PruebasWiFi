package com.alejandroFernandez.pruebaswifi.Utils;

import java.util.List;

import com.alejandroFernandez.pruebaswifi.R;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WifiAdapter extends ArrayAdapter<ScanResult> {
	
	private List<ScanResult> listaWifi;
	private ScanResult escaneado;
	private Activity activity;
	private TextView txtItem;

	public WifiAdapter(Activity activity, List<ScanResult> listaWifi) {
		super(activity, R.layout.activity_wifi_item ,listaWifi);
		
		this.listaWifi = listaWifi;
		this.activity = activity;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inf.inflate(R.layout.activity_wifi_item, null);
        txtItem = (TextView) v.findViewById(R.id.txtItem);
        
        escaneado = listaWifi.get(position);
        
        txtItem.setText(escaneado.toString());
        
        return v;
	}

}
