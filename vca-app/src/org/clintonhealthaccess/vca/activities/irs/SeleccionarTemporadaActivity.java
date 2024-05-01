package org.clintonhealthaccess.vca.activities.irs;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.adapters.TemporadaAdapter;
import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


public class SeleccionarTemporadaActivity extends AbstractAsyncListActivity {
	

	private VcaAdapter vcaAdapter;
	private List<IrsSeason> mTemporadas = new ArrayList<IrsSeason>();
	private static Localidad mLocalidad = new Localidad();
	private static final int EXIT = 1;
	private TextView mLabelHeader;
	private String roles;
	
	private AlertDialog alertDialog;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_loc_list);
		mLabelHeader = (TextView) findViewById(R.id.label_header);
		mLabelHeader.setText(getString(R.string.choose_temporada));
		
		//Aca se recupera los datos de la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		roles = getIntent().getExtras().getString(Constants.ROLES);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataTask().execute("");

	}

	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		// Obtener la temporada seleccionada
		IrsSeason temporada = (IrsSeason) getListAdapter().getItem(position);
		
		Bundle arguments = new Bundle();
		arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
		arguments.putSerializable(Constants.TEMPORADA , temporada);
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				MenuRociadoActivity.class);
		i.putExtras(arguments);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}
	
	@Override
	public void onBackPressed (){
		finish();
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_BACK){
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_HOME){
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_EXIT){
			createDialog(EXIT);
			return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
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
		default:
			break;
		}
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchDataTask extends AsyncTask<String, Void, String> {
		private String filtro = null;
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			filtro = values[0];
			try {
				vcaAdapter.open();
				mTemporadas = vcaAdapter.getTemporadas(filtro, MainDBConstants.name);
				vcaAdapter.close();
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
		TemporadaAdapter adapter = new TemporadaAdapter(this, R.layout.complex_list_item, mTemporadas);
		setListAdapter(adapter);
		if (mTemporadas.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
	}	
	
}
