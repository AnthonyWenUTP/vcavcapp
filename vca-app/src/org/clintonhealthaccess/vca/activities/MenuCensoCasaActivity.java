package org.clintonhealthaccess.vca.activities;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.HouseholdActivity;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.adapters.MenuCensoCasaAdapter;
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


public class MenuCensoCasaActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private static Household mVivienda = new Household();
	private VcaAdapter vcaAdapter;
	
    private static final int EDIT_VIV = 2;
    
    private static final int OPEN_EDIT_VIV = 4;
    
    private static final int FINALIZAR = 9;
    private static final int VERIFICAR = 92;
	
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	
	String[] menuCasa;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_censo);

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
		mVivienda = (Household) getIntent().getExtras().getSerializable(Constants.VIVIENDA);
		
		String jefeFamilia;
		
		if(mVivienda.getInhabited().equals(Constants.CERRADA_ID)){
			jefeFamilia = "Casa Cerrada";
		} else {
			jefeFamilia = mVivienda.getOwnerName();
		}
		
		textView = (TextView) findViewById(R.id.label);
		textView.setText(getString(R.string.codeHouse)+":"+mVivienda.getCode()+"\n"+
							getString(R.string.ownerName)+":"+jefeFamilia+"\n"+
							getString(R.string.localidad)+":"+mLocalidad.getName()+"\n"+
							getString(R.string.distrito)+":"+mLocalidad.getDistrict().getName()+"\n"+
								getString(R.string.area)+":"+mLocalidad.getDistrict().getArea().getName());
		
		
		menuCasa = getResources().getStringArray(R.array.menu_casa);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
	            Intent i;
				switch(position){ 
				case 0: // VER VIVIENDA
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mVivienda!=null) arguments.putSerializable(Constants.VIVIENDA , mVivienda);
					arguments.putString(Constants.FORM_NAME, Constants.VIVIENDA);
                    i = new Intent(getApplicationContext(), VerCasaActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 1:
					//EDITAR VIVIENDA
					createDialog(EDIT_VIV);
					break;
				case 2: // VER FAMILIA
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mVivienda!=null) arguments.putSerializable(Constants.VIVIENDA , mVivienda);
					arguments.putString(Constants.FORM_NAME, Constants.VIVIENDA);
                    i = new Intent(getApplicationContext(), VerFamiliaActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
					
				case 3:
					//UBICACION VIVIENDA
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mVivienda!=null) arguments.putSerializable(Constants.VIVIENDA , mVivienda);
					arguments.putString(Constants.FORM_NAME, Constants.VIVIENDA);
                    i = new Intent(getApplicationContext(), UbicacionActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;	

				case 4:
					//FINALIZAR VIVIENDA
					createDialog(FINALIZAR);
					break;

				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuCensoCasaAdapter(getApplicationContext(), R.layout.menu_item_2, menuCasa, mVivienda));
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
			i = new Intent(getApplicationContext(),
                    CensoActivity.class);
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
		Intent i = new Intent(getApplicationContext(),
                CensoActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EDIT_VIV:
			builder.setTitle(this.getString(R.string.confirm)+ " " + mLocalidad.getName());
            builder.setMessage(getString(R.string.confirm_house_edit) + "\n" + mVivienda.getCode()+ "\n" + mVivienda.getOwnerName());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_EDIT_VIV));
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
		case FINALIZAR:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage("Desea terminar la entrada de datos de esta vivienda?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new SaveDataTask().execute();
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
		case VERIFICAR:
            builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage("Desea verificar la existencia de esta vivienda?");
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new SaveVerifTask().execute();
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
                    case OPEN_EDIT_VIV:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        arguments.putSerializable(Constants.VIVIENDA , mVivienda);
                        i = new Intent(getApplicationContext(), HouseholdActivity.class);
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
  	// ************************************
  	private class SaveDataTask extends AsyncTask<String, Void, String> {
  		@Override
  		protected void onPreExecute() {
  			// before the request begins, show a progress indicator
  			showLoadingProgressDialog();
  		}

  		@Override
  		protected String doInBackground(String... values) {
  			try {
  				vcaAdapter.open();
  				mVivienda.setEstado(Constants.STATUS_NOT_SUBMITTED);
  				vcaAdapter.editarHousehold(mVivienda);
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
  	// Private classes
  	// ************************************
  	private class SaveVerifTask extends AsyncTask<String, Void, String> {
  		@Override
  		protected void onPreExecute() {
  			// before the request begins, show a progress indicator
  			showLoadingProgressDialog();
  		}

  		@Override
  		protected String doInBackground(String... values) {
  			try {
  				vcaAdapter.open();
  				mVivienda.setVerified("Si");
  				if(mVivienda.getEstado()==Constants.STATUS_SUBMITTED) mVivienda.setEstado(Constants.STATUS_NOT_SUBMITTED);
  				vcaAdapter.editarHousehold(mVivienda);
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
  		Toast.makeText(getApplicationContext(), "Proceso finalizado...",Toast.LENGTH_LONG).show();
  		Bundle arguments = new Bundle();
		if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
		if (mVivienda != null) arguments.putSerializable(Constants.VIVIENDA, mVivienda);
		Intent i = new Intent(getApplicationContext(),
                MenuCensoCasaActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
  	}	

}
	
