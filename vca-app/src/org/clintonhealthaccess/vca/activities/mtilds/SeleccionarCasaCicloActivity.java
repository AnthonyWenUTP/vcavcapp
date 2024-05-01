package org.clintonhealthaccess.vca.activities.mtilds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.adapters.EntregaTargetAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class SeleccionarCasaCicloActivity extends AbstractAsyncListActivity {
	

	private VcaAdapter vcaAdapter;
	private List<EntregaTarget> mTargets = new ArrayList<EntregaTarget>();
    private EntregaTarget target = null;
    private TextView mLabelTitle;
    private TextView mLabelHeader;
    private static Localidad mLocalidad = new Localidad();
    private static Ciclo mTemporada = new Ciclo();
    
	private EditText mParametroView;
    private ImageButton mFindButton;
    private Button mAddButton;
    
    private AlertDialog alertDialog;
    private static final int SELECT_HOUSE = 1;
    
    private String roles;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_house_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		
		
		//Aca se recupera los datos de la localidad y la temporada
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		mTemporada = (Ciclo) getIntent().getExtras().getSerializable(Constants.CICLO);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mLabelHeader = (TextView) findViewById(R.id.label_header);
		
		
		mLabelHeader.setText("Seleccionar vivienda");
		mLabelHeader.setVisibility(View.GONE);
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.VISIBLE);
		mParametroView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		mParametroView.setHint(getString(R.string.parametro));
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setVisibility(View.GONE);
		
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mTargets.clear();
				buscarMetas(mParametroView.getText().toString());
			}
		});

		mFindButton.setVisibility(View.VISIBLE);
		
		new FetchTargetsTask().execute(mLocalidad.getIdent(),"");
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		target = (EntregaTarget) getListAdapter().getItem(position);
		createDialog(SELECT_HOUSE);
	}
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
            case SELECT_HOUSE:
            	builder.setTitle(this.getString(R.string.confirm)+ " " + target.getHousehold().getLocal().getName());
                builder.setMessage("Seleccionar esta vivienda? \n" + target.getHousehold().getCode()+ "\n" + target.getHousehold().getOwnerName());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                		if (target!=null) arguments.putSerializable(Constants.META , target);
                		arguments.putString(Constants.ROLES, roles);
                		Intent i = new Intent(getApplicationContext(),
                				MenuMosquiterosCasaActivity.class);
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
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}
	
	@Override
	public void onBackPressed (){
		Bundle arguments = new Bundle();
		arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
		arguments.putSerializable(Constants.CICLO , mTemporada);
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				MenuMosquiterosActivity.class);
		i.putExtras(arguments);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_BACK){
			Bundle arguments = new Bundle();
			arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
			arguments.putSerializable(Constants.CICLO , mTemporada);
			arguments.putString(Constants.ROLES, roles);
			Intent i = new Intent(getApplicationContext(),
					MenuMosquiterosActivity.class);
			i.putExtras(arguments);
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
		else{
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	public void buscarMetas(String parametro){
		new FetchTargetsTask().execute(mLocalidad.getIdent(),parametro);
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchTargetsTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			//String strLocalidad = values[0];
			//String filtro = values[1];
			
			try {
				vcaAdapter.open();
				//TODO conectar a base de datos
				//mTargets = vcaAdapter.getHouseholds(MainDBConstants.local + " = '"+ strLocalidad + "' and ("+ MainDBConstants.code +" like '%" + filtro + "%'" + " or "+ MainDBConstants.ownerName +" like '%" + filtro + "%')", MainDBConstants.ownerName);
				String filtro=MainDBConstants.ident +"='00000000-70a7-23f2-0000-00003b1c24b6'";
				Household casa = vcaAdapter.getHousehold(filtro, MainDBConstants.ownerName);
				mTargets.add(new EntregaTarget("1",mTemporada,casa,new Date(),6,5,4,3,2,1,"Pendiente","Observaciones en la vivienda",15));
				
				vcaAdapter.close();
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return "error";
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
		if(resultado.equals("exito")) {
			if(mLocalidad==null) {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			}else {
				mLabelTitle.setTextColor(Color.BLUE);
				mLabelTitle.setText("Seleccionar vivienda\nEntrega de Mosquiteros\n"+ getString(R.string.localidad)+":"+mLocalidad.getName());
			}
			EntregaTargetAdapter adapter = new EntregaTargetAdapter(this, R.layout.household_list_item, mTargets);
			setListAdapter(adapter);
			if (mTargets.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
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
