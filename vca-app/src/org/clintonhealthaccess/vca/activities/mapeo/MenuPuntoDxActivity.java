package org.clintonhealthaccess.vca.activities.mapeo;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.mapeo.PuntoDxActivity;
import org.clintonhealthaccess.vca.adapters.MenuPuntoDxAdapter;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import org.clintonhealthaccess.vca.database.VcaAdapter;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MenuPuntoDxActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private static PuntoDiagnostico mPunto = new PuntoDiagnostico();
	private VcaAdapter vcaAdapter;
	
    private static final int EDIT_PUNTO = 2;
    private static final int DELETE_PUNTO = 3;

    
    private static final int OPEN_EDIT_PUNTO = 40;
    
    
	
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	private String roles;
	
	String[] menuPunto;


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_caso);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		if (savedInstanceState != null) {
			
		}
		
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);

		//Aca se recupera los datos de la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		//Aca se recupera los datos de la vivienda
		mPunto = (PuntoDiagnostico) getIntent().getExtras().getSerializable(Constants.PUNTO);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		
		textView = (TextView) findViewById(R.id.label);
		textView.setTextSize(22);
		textView.setText(getString(R.string.clave)+":"+mPunto.getClave()+ "\n" +
							getString(R.string.localidad)+": "+mPunto.getLocal().getName());

		
		
		menuPunto = getResources().getStringArray(R.array.menu_punto);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
	            Intent i;
				switch(position){ 
				case 0:
					//EDITAR PUNTO
					createDialog(EDIT_PUNTO);
					break;
				case 1:
					//UBICACION PUNTO
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mPunto!=null) arguments.putSerializable(Constants.PUNTO , mPunto);
					if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                    i = new Intent(getApplicationContext(), UbicacionPuntoDxActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 2:
					//VISITAS PUNTO
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mPunto!=null) arguments.putSerializable(Constants.PUNTO , mPunto);
					if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                    i = new Intent(getApplicationContext(), VisPuntosDxActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 3:
					//ELIMINAR PUNTO
					createDialog(DELETE_PUNTO);
					break;				
				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuPuntoDxAdapter(getApplicationContext(), R.layout.menu_item_3, menuPunto, mPunto));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case android.R.id.home:
			i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		case R.id.MENU_BACK:
			Bundle arguments = new Bundle();
			if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
			if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
			i = new Intent(getApplicationContext(),
                    PuntosDxActivity.class);
			i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
			return true;
		case R.id.MENU_HOME:
			i = new Intent(getApplicationContext(),
						MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
		if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
		Intent i = new Intent(getApplicationContext(),
                PuntosDxActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
        switch(dialog){
		case EDIT_PUNTO:
			builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.confirm_pdx_edit) + "\n" + mPunto.getClave());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_EDIT_PUNTO));
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
		case DELETE_PUNTO:
			builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage(getString(R.string.confirm_pdx_delete) + "\n" + mPunto.getClave());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new DeletePuntoTask().execute();
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
    // ***************************************
    private class OpenDataActivityTask extends AsyncTask<String, Void, String> {
        private int position = 0;
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            position = Integer.valueOf(values[0]);
            Bundle arguments = new Bundle();
            Intent i;
            try {
                switch (position) {
                    case OPEN_EDIT_PUNTO:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        arguments.putSerializable(Constants.PUNTO , mPunto);
        		        if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                        i = new Intent(getApplicationContext(), PuntoDxActivity.class);
                        break;
                    default:
                    	i = new Intent(getApplicationContext(), MainActivity.class);
                    	break;
                }
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            finally {
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            // after the request completes, hide the progress indicator
            dismissProgressDialog();
        }

    }
    
    
 // ***************************************
    // Private classes
    // ***************************************
    private class DeletePuntoTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
            	vcaAdapter.open();
            	vcaAdapter.eliminarPuntoDiagnostico(mPunto);
            	vcaAdapter.close();
   	            FileUtils.createFolder(FileUtils.BACKUP_PATH);
   	            File databaseFile = new File(FileUtils.DATABASE_PATH + "/" +MainDBConstants.DATABASE_NAME);
   	            File databaseFileBackup = new File(FileUtils.BACKUP_FILE);
   	            
   	            FileUtils.copy(databaseFile, databaseFileBackup);
            	
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }
            finally {
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
        	// after the request completes, hide the progress indicator
        	dismissProgressDialog();
        	if(resultado.equals("error")) {
     			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
     			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
     			if (vcaAdapter != null)
     				vcaAdapter.close();
     		}
      		Toast.makeText(getApplicationContext(), "Proceso finalizado...",Toast.LENGTH_LONG).show();
        	Bundle arguments = new Bundle();
            Intent i;
            
            if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
			if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
			i = new Intent(getApplicationContext(),
                    PuntosDxActivity.class);
			i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

    }
	
  	
  	 

}
	
