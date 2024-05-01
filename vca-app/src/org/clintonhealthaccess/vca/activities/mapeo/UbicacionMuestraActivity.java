package org.clintonhealthaccess.vca.activities.mapeo;

import java.io.File;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.GeoPointActivity;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.DeviceInfo;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;


public class UbicacionMuestraActivity extends AbstractAsyncActivity {
	

	private VcaAdapter vcaAdapter;
	private static Muestra mMuestra = new Muestra();
	private static Localidad mLocalidad = new Localidad();
	private TextView textViewHeader;
	private TextView textViewMuestra;
	private TextView textViewLatitud;
	private TextView textViewLongitud;
	private TextView textViewExactitud;
	private TextView textViewAltitud;
	private Button mSaveButton;
	private Button mOpenButton;
	private DeviceInfo infoMovil;
	double latitud = 0, longitud = 0, altitud = 0;
	float proximidad = 0;
	private boolean cambio = false;
	private AlertDialog alertDialog;
	
	private static final int LOCATION_CAPTURE = 200;
	
	public static final String LOCATION_RESULT = "LOCATION_RESULT";
	
	private static final int GUARDAR = 1;
	private static final int SALIR = 9;
	private String roles;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ubicacion);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		infoMovil = new DeviceInfo(UbicacionMuestraActivity.this);
		
		//Aca se recupera los datos de la muestra y la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		mMuestra = (Muestra) getIntent().getExtras().getSerializable(Constants.MUESTRA);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		textViewHeader = (TextView) findViewById(R.id.label_header);
		textViewMuestra = (TextView) findViewById(R.id.label_encuesta);
		textViewLatitud = (TextView) findViewById(R.id.label_latitud);
		textViewLongitud = (TextView) findViewById(R.id.label_longitud);
		textViewExactitud = (TextView) findViewById(R.id.label_exactitud);
		textViewAltitud = (TextView) findViewById(R.id.label_altitud);
		
		textViewHeader.setText("Ubicación muestra");

		
		textViewMuestra.setText(getString(R.string.codCasa)+":"+mMuestra.getCasa()+"\n"+
				getString(R.string.localidad)+":"+mMuestra.getLocal().getName()
					+"\n\n"+
				" Datos actuales:\n");
		if(mMuestra.getLatitude()==null||mMuestra.getLatitude()==0) {
			textViewLatitud.setText("Latitud no establecida");
		}else{
			textViewLatitud.setText("Latitud: " + mMuestra.getLatitude().toString());
		}
		
		if(mMuestra.getLongitude()==null||mMuestra.getLongitude()==0) {
			textViewLongitud.setText("Longitud no establecida");
		}else{
			textViewLongitud.setText("Longitud: "+mMuestra.getLongitude().toString());
		}
		
		if(mMuestra.getExactitud()==null||mMuestra.getExactitud()==0) {
			textViewExactitud.setText("Proximidad no establecida");
		}else{
			textViewExactitud.setText("Proximidad: "+mMuestra.getExactitud().toString());
		}
		
		if(mMuestra.getAltitud()==null||mMuestra.getAltitud()==0) {
			textViewAltitud.setText("Altitud no establecida");
		}else{
			textViewAltitud.setText("Altitud: "+mMuestra.getAltitud().toString());
		}
		
		
		
		mSaveButton = (Button) findViewById(R.id.save_button);
		mSaveButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(GUARDAR);
			}
		});
		mSaveButton.setEnabled(false);
		
		
		mOpenButton = (Button) findViewById(R.id.open_button);
		mOpenButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				Intent launchIntent = new Intent(getApplicationContext(), GeoPointActivity.class);
				if (launchIntent != null) { 
				    startActivityForResult(launchIntent,LOCATION_CAPTURE);
				}
			}
		});
		
		

	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_CANCELED) {
        	Toast.makeText(this, "No se logró establecer ubicación!", Toast.LENGTH_LONG).show();
        	return;
        }
        else {
	        if (requestCode == LOCATION_CAPTURE) {
	        	Bundle params = intent.getExtras();
	            if  (params != null)
	            {
	            	latitud = params.getDouble("latitud");
	            	longitud = params.getDouble("longitud");
	            	altitud = params.getDouble("altitud");
	            	proximidad = params.getFloat("proximidad");
	            }
	        	textViewLatitud.setText("Latitud: " + latitud);
	        	textViewLongitud.setText("Longitud: "+longitud);
	        	textViewExactitud.setText("Proximidad: "+proximidad);
	        	textViewAltitud.setText("Altitud: "+altitud);
	        	cambio = true;
	        	mSaveButton.setEnabled(true);
	        }
        }
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	
	@Override
	public void onBackPressed (){
		if(cambio) {
			createDialog(SALIR);
		}
		else {
			Intent i = null;
			Bundle arguments = new Bundle();
    		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
            if (mMuestra!=null) arguments.putSerializable(Constants.MUESTRA , mMuestra);
            arguments.putString(Constants.ROLES, roles);
	    	i = new Intent(getApplicationContext(),
	                    MenuMuestraActivity.class);
    		i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
		}
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case GUARDAR:
			builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage("Desea guardar esta nueva ubicación?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new SaveDataTask().execute(String.valueOf(latitud),String.valueOf(longitud),String.valueOf(altitud),String.valueOf(proximidad));
                }
            });
            builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });
            break; 
		case SALIR:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage("Desea salir sin guardar la ubicación?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent i = null;
        			Bundle arguments = new Bundle();
            		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                    if (mMuestra!=null) arguments.putSerializable(Constants.MUESTRA , mMuestra);
                    arguments.putString(Constants.ROLES, roles);
                    
        	    		i = new Intent(getApplicationContext(),
        	                    MenuMuestraActivity.class);
            		i.putExtras(arguments);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            });
            builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });
            break; 
		default:
			break;
			
		}
		alertDialog = builder.create();
		alertDialog.show();
	}

	
	
	// ***************************************
	 	// Private classes
	 	// ************************************
	 	private class SaveDataTask extends AsyncTask<String, Void, String> {
	 		private double latitud = 0;
	 		private double longitud = 0;
	 		private double altitud = 0;
	 		private float exactitud = 0;
	 		@Override
	 		protected void onPreExecute() {
	 			// before the request begins, show a progress indicator
	 			showLoadingProgressDialog();
	 		}

	 		@Override
	 		protected String doInBackground(String... values) {
	 			latitud = Double.valueOf(values[0]);
	 			longitud = Double.valueOf(values[1]);
	 			altitud = Double.valueOf(values[2]);
	 			exactitud = Float.valueOf(values[3]);
	 			try {
	 				vcaAdapter.open();
	 				mMuestra.setDeviceid(infoMovil.getDeviceId());
	 				mMuestra.setLatitude(latitud);
	 				mMuestra.setLongitude(longitud);
	 				mMuestra.setAltitud(altitud);
	 				mMuestra.setExactitud(exactitud);
	 				mMuestra.setEstado(Constants.STATUS_NOT_SUBMITTED);
	 				vcaAdapter.editarMuestra(mMuestra);
	 				vcaAdapter.close();
	 	            FileUtils.createFolder(FileUtils.BACKUP_PATH);
	 	            File databaseFile = new File(FileUtils.DATABASE_PATH + "/" +MainDBConstants.DATABASE_NAME);
	 	            File databaseFileBackup = new File(FileUtils.BACKUP_FILE);
	 	            
	 	            FileUtils.copy(databaseFile, databaseFileBackup);
	 			} catch (Exception e) {
	 				Log.e(TAG, e.getLocalizedMessage(), e);
	 				return "error";
	 			}
	 			return "exito";
	 		}

	 		protected void onPostExecute(String resultado) {
	 			// after the network request completes, hide the progress indicator
	 			dismissProgressDialog();
	 			showResult(resultado);
	 		}

	 	}

	 	// ***************************************
	 	// Private methods
	 	// ***************************************
	 	private void showResult(String resultado) {
	 		if(resultado.equals("error")) {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
				if (vcaAdapter != null)
	                vcaAdapter.close();
				finish();
			}
	 		Intent i = null;
			Bundle arguments = new Bundle();
    		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
            if (mMuestra!=null) arguments.putSerializable(Constants.MUESTRA , mMuestra);
            arguments.putString(Constants.ROLES, roles);
	    		i = new Intent(getApplicationContext(),
	                    MenuMuestraActivity.class);
            
    		i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.success),Toast.LENGTH_LONG);
            toast.show();
            finish();
	 	}	
}
