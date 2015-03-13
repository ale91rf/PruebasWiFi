package com.alejandroFernandez.pruebaswifi.Utils;

import com.alejandroFernandez.pruebaswifi.R;
import com.alejandroFernandez.pruebaswifi.Actividades.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ContrasennaDialogFragment extends DialogFragment{
	
	private String SSID = "ssid";
	private String PASSWIFI = "passWifi";
	private EditText edPass;
	private positiveDialogListener listener = null;
	
	// Interfaz pública para comunicación con la actividad.
	public interface positiveDialogListener {
	public void onPositiveButtonClick(DialogFragment dialog);
	 
	}

	@Override
	public Dialog onCreateDialog(Bundle bundle) {
		Bundle args = new Bundle();
		args = getArguments();
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_pass, null);
		
		edPass = (EditText) view.findViewById(R.id.editTextPass);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    
	    
	    builder.setView(view)
	    
	    		.setTitle(args.getString(SSID))
	    
	           .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
	        	   
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	 MainActivity.pass = edPass.getText().toString();
	            	 
	            	 
	            	 listener.onPositiveButtonClick(ContrasennaDialogFragment.this);
	            	 
	               }

	           })
	           .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   return;
	               }
	           });      
	    return builder.create();

	}
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			listener = (positiveDialogListener) activity;
		} catch (ClassCastException e) {
			// La actividad no implementa la interfaz, se lanza excepción.
			throw new ClassCastException(activity.toString()
					+ " debe implementar positiveDialogListener");
		}
	}

}
