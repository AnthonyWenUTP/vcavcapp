package org.clintonhealthaccess.vca;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.clintonhealthaccess.vca.activities.ListaLocalidadesActivity;
import org.clintonhealthaccess.vca.activities.MenuCensoActivity;
import org.clintonhealthaccess.vca.activities.irs.SeleccionarTemporadaActivity;
import org.clintonhealthaccess.vca.activities.mapeo.MenuMapeoActivity;
import org.clintonhealthaccess.vca.adapters.MainActivityAdapter;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.preferences.PreferencesActivity;
import org.clintonhealthaccess.vca.server.DownloadAllActivity;
import org.clintonhealthaccess.vca.server.DownloadCasasActivity;
import org.clintonhealthaccess.vca.server.DownloadCatalogosActivity;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AbstractAsyncListActivity {
	
	private static final int EXIT = 1;
	private static final int DOWNLOAD = 2;
	private static final int CATALOG = 3;
	private static final int VERIFY = 4;
	private static final int CASAS = 5;
	
	private static final int UPDATE_EQUIPO = 11;
	private static final int UPDATE_SERVER = 12;
	private static final int UPDATE_CATALOG = 13;
	private static final int UPDATE_CASAS = 14;
	
	private AlertDialog alertDialog;
	private VcaAdapter vcaAdapter;
	private SharedPreferences settings;
	private String mLocalidad;
	private String mLastCatUpdate;
    private Localidad localidad = null;
    private List<String> roles = null;
	private TextView mLabelHeader;
	private TextView mLabelVersion;
    private TextView mLabelFooter;
    private String[] menu_main;
    private Integer numCatalogos=0;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm");
    private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		menu_main = getResources().getStringArray(R.array.menu_main);
		
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		mLocalidad =
				settings.getString(PreferencesActivity.KEY_CODE_LOCALIDAD,
						null);
		mLastCatUpdate =
				settings.getString(PreferencesActivity.KEY_ULTSYNCCAT,
						null);
		username =
				settings.getString(PreferencesActivity.KEY_USERNAME,
						null);
		mLabelHeader = (TextView) findViewById(R.id.label_logo);
		mLabelHeader.setText(getString(R.string.main_header));
		mLabelVersion = (TextView) findViewById(R.id.main_label_version);
		mLabelVersion.setText(getString(R.string.version_app_preferences)+", "+getString(R.string.versiondate_app_preferences)+ "\n "+ 
        		getString(R.string.lastsync)+ " " + getString(R.string.catalog)+": "+ mLastCatUpdate);
		mLabelFooter = (TextView) findViewById(R.id.label_foot);
		if(mLocalidad==null) {
			mLabelFooter.setText(getString(R.string.default_localidad));
			Intent i = new Intent(getApplicationContext(),
					ListaLocalidadesActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
		else {
			new FetchDataLocalidadTask().execute(mLocalidad,username);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MENU_EXIT:
			createDialog(EXIT);
			return true;
		case R.id.MENU_PREFERENCES:
			Intent ig = new Intent(this, PreferencesActivity.class);
			startActivity(ig);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		// Opcion de menu seleccionada
		Intent i;
		switch(position){
		case 0: 
			if(localidad==null) {
				Toast.makeText(getApplicationContext(), getString(R.string.default_localidad),Toast.LENGTH_LONG).show();
				break;
			}
			else {
				if (roles.toString().contains("ROLE_CENSUS")||roles.toString().contains("ROLE_SUPER")) {
					Bundle arguments = new Bundle();
					arguments.putSerializable(Constants.LOCALIDAD , localidad);
					arguments.putString(Constants.ROLES, roles.toString());
					i = new Intent(getApplicationContext(),
						MenuCensoActivity.class);
					i.putExtras(arguments);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
				}
			}
			break;
		case 1:
			if(localidad==null) {
				Toast.makeText(getApplicationContext(), getString(R.string.default_localidad),Toast.LENGTH_LONG).show();
				break;
			}
			else {
				if (roles.toString().contains("ROLE_SPRAY")||roles.toString().contains("ROLE_SENTINEL")||roles.toString().contains("ROLE_SUPER")) {
					Bundle arguments = new Bundle();
					arguments.putSerializable(Constants.LOCALIDAD , localidad);
					arguments.putString(Constants.ROLES, roles.toString());
					i = new Intent(getApplicationContext(),
							SeleccionarTemporadaActivity.class);
					i.putExtras(arguments);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
				}
			}
			break;
		
		case 2: 
			/*if(localidad==null) {
				Toast.makeText(getApplicationContext(), getString(R.string.default_localidad),Toast.LENGTH_LONG).show();
				break;
			}
			else {
				if (roles.toString().contains("ROLE_CENSUS")||roles.toString().contains("ROLE_SUPER")) {
					Bundle arguments = new Bundle();
					arguments.putSerializable(Constants.LOCALIDAD , localidad);
					arguments.putString(Constants.ROLES, roles.toString());
					i = new Intent(getApplicationContext(),
							SeleccionarTemporadaActivity.class);
					i.putExtras(arguments);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
				}
			}*/
			break;
			
		case 3: 
			if(localidad==null) {
				Toast.makeText(getApplicationContext(), getString(R.string.default_localidad),Toast.LENGTH_LONG).show();
				break;
			}
			else {
				if (roles.toString().contains("ROLE_CENSUS")||roles.toString().contains("ROLE_SUPER")||roles.toString().contains("ROLE_MOVIL")) {
					Bundle arguments = new Bundle();
					arguments.putSerializable(Constants.LOCALIDAD , localidad);
					arguments.putString(Constants.ROLES, roles.toString());
					i = new Intent(getApplicationContext(),
							MenuMapeoActivity.class);
					i.putExtras(arguments);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
				}
				else{
					Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
				}
			}
			break;
		case 4:
			i = new Intent(getApplicationContext(),
					ListaLocalidadesActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			break;
		case 5: 
			createDialog(CATALOG);
			break;
		case 6: 
			createDialog(DOWNLOAD);
			break;
		default: 
			String s = (String) getListAdapter().getItem(position);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
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
	}

	@Override
	public void onBackPressed (){
		//createDialog(EXIT);
		Intent startMain = new Intent(Intent.ACTION_MAIN);
	    startMain.addCategory(Intent.CATEGORY_HOME);
	    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(startMain);
	}

	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case EXIT:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.exiting));
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// Finish app
					dialog.dismiss();
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
		case DOWNLOAD:
			
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					vcaAdapter.open();
					boolean hayDatos = vcaAdapter.verificarData();
					vcaAdapter.close();
					if(hayDatos){
						createDialog(VERIFY);
					}
					else{
						dialog.dismiss();
						Intent ie = new Intent(getApplicationContext(), DownloadAllActivity.class);
						startActivityForResult(ie, UPDATE_EQUIPO);
					}
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
			
		case VERIFY:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.data_not_sent));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), DownloadAllActivity.class);
					startActivityForResult(ie, UPDATE_EQUIPO);
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
		case CATALOG:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloadingcat));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), DownloadCatalogosActivity.class);
					startActivityForResult(ie, UPDATE_CATALOG);
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
			
		case CASAS:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloadingcasas));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), DownloadCasasActivity.class);
					startActivityForResult(ie, UPDATE_CASAS);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_CANCELED) {
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER||requestCode == UPDATE_CATALOG||requestCode == UPDATE_CASAS){
				String mensajeCancel = intent.getStringExtra("resultado");
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				if (mensajeCancel.equals("no hay datos")) {
					builder.setTitle(getApplicationContext().getString(R.string.process_canceled));
					builder.setMessage(getApplicationContext().getString(R.string.no_items_tosend));
					builder.setIcon(R.drawable.ic_menu_revert)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
				}
				else {
					builder.setTitle(getApplicationContext().getString(R.string.error));
					builder.setMessage(intent.getStringExtra("resultado"));
					builder.setIcon(R.drawable.ic_menu_close_clear_cancel)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
				}
				AlertDialog alert = builder.create();
				alert.show();
				return;
			}
		}
		else{
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER||requestCode == UPDATE_CATALOG||requestCode == UPDATE_CASAS){
				if (requestCode == UPDATE_CATALOG) {
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			        Editor editor = sharedPreferences.edit();
			        editor.putString(PreferencesActivity.KEY_ULTSYNCCAT, mDateFormat.format( new Date()));
			        editor.commit();
			        mLabelVersion.setText(getString(R.string.version_app_preferences)+", "+getString(R.string.versiondate_app_preferences)+ "\n "+ 
			        		getString(R.string.lastsync)+ " " + getString(R.string.catalog)+": "+ mDateFormat.format( new Date()));
				}
				new FetchDataLocalidadTask().execute(mLocalidad,username);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_mark);
				builder.setMessage(getApplicationContext().getString(R.string.success))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//do things
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			return;
		}
		
	}
	
	
	
	private class FetchDataLocalidadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
        	String strLocalidad = values[0];
        	String strUser = values[1];
            try {
            	localidad = null;
                vcaAdapter.open();
                localidad = vcaAdapter.getLocalidad(MainDBConstants.ident + " = '"+ strLocalidad + "'", null);
                numCatalogos = vcaAdapter.getNumeroRegistros(MainDBConstants.MESSAGES_TABLE, null);
                roles = vcaAdapter.obtenerRoles(strUser);

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return "error";
            }finally {
            	if (vcaAdapter != null)
            		vcaAdapter.close();
            }
            return "exito";
        }

        protected void onPostExecute(String resultado) {
            dismissProgressDialog();
            showResult(resultado);
        }
    }
	
	// ***************************************
	// Private methods
	// ***************************************
	private void showResult(String resultado) {
		if(resultado.equals("exito") && localidad==null) {
			mLabelFooter.setText(getString(R.string.default_localidad));
			Intent i = new Intent(getApplicationContext(),
					ListaLocalidadesActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}else if(resultado.equals("exito")){
			setListAdapter(new MainActivityAdapter(this, R.layout.menu_item, menu_main, numCatalogos));
			mLabelFooter.setTextColor(Color.BLUE);
			mLabelFooter.setText(getString(R.string.localidad)+":"+localidad.getName()+"\n"+
									getString(R.string.distrito)+":"+localidad.getDistrict().getName()+"\n"+
												getString(R.string.area)+":"+localidad.getDistrict().getArea().getName());
		}
		else {
			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
			if (vcaAdapter != null)
				vcaAdapter.close(); 
			finish();
		}
	}
	
}
