package org.clintonhealthaccess.vca.activities.irs;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.adapters.TargetAdapter;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class RociadoActivity extends AbstractAsyncListActivity implements AdapterView.OnItemSelectedListener{
	

	private VcaAdapter vcaAdapter;
	private List<Target> mTargets = new ArrayList<Target>();
    private Target target = null;
    private TextView mLabelTitle;
    private TextView mLabelHeader;
    private static Localidad mLocalidad = new Localidad();
	private static IrsSeason mTemporada = new IrsSeason();
    
	private EditText mParametroView;
    private ImageButton mFindButton;
    private Button mAddButton;
    private String roles;
    private int opcionSeleccionada=0;
    
    private AlertDialog alertDialog;
    private static final int EDIT_TARGET = 2;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_house_list2);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		
		
		//Aca se recupera los datos de la localidad y la temporada
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		mTemporada = (IrsSeason) getIntent().getExtras().getSerializable(Constants.TEMPORADA);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mLabelHeader = (TextView) findViewById(R.id.label_header);
		

		mLabelHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                	Bundle arguments = new Bundle();
                	Intent i = new Intent(getApplicationContext(),
                			MapaRociadoActivity.class);
                	if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
					if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
					arguments.putString(Constants.ROLES, roles);
					arguments.putInt("Cuales casas", opcionSeleccionada);
					i.putExtras(arguments);
                	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                	startActivity(i);
                	finish();
                } catch (Exception except) {
                    Log.e(TAG,"Ooops map couldn´t load "+except.getMessage());
                }
            }
        });
		
		Spinner spinner = (Spinner) findViewById(R.id.opciones_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.opciones_casas, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		
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
				buscarMetas(mParametroView.getText().toString(),opcionSeleccionada);
			}
		});

		mFindButton.setVisibility(View.VISIBLE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		target = (Target) getListAdapter().getItem(position);
		createDialog(EDIT_TARGET);
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view,
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		switch (pos){
			case 0:
				buscarMetas(mParametroView.getText().toString(),0);
				opcionSeleccionada = 0;
				break;
			case 1:
				buscarMetas(mParametroView.getText().toString(),1);
				opcionSeleccionada = 1;
				break;
			case 2:
				buscarMetas(mParametroView.getText().toString(),2);
				opcionSeleccionada = 2;
				break;
			case 3:
				buscarMetas(mParametroView.getText().toString(),3);
				opcionSeleccionada = 3;
				break;
			case 4:
				buscarMetas(mParametroView.getText().toString(),4);
				opcionSeleccionada = 4;
				break;
			case 5:
				buscarMetas(mParametroView.getText().toString(),5);
				opcionSeleccionada = 5;
				break;
			case 6:
				buscarMetas(mParametroView.getText().toString(),6);
				opcionSeleccionada = 6;
				break;
			case 7:
				buscarMetas(mParametroView.getText().toString(),7);
				opcionSeleccionada = 7;
				break;
			default:
				break;
				
		}
		
    }
	
	@Override
    public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
	
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
            case EDIT_TARGET:
                builder.setTitle(this.getString(R.string.confirm)+ " " + mLocalidad.getName());
                builder.setMessage(getString(R.string.confirm_house_edit) + "\n" + target.getHousehold().getCode()+ "\n" + target.getHousehold().getOwnerName());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                		if (target!=null) arguments.putSerializable(Constants.META , target);
                		arguments.putString(Constants.ROLES, roles);
                		Intent i = new Intent(getApplicationContext(),
                				MenuRociadoCasaActivity.class);
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
		arguments.putSerializable(Constants.TEMPORADA , mTemporada);
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				MenuRociadoActivity.class);
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
			arguments.putSerializable(Constants.TEMPORADA , mTemporada);
			arguments.putString(Constants.ROLES, roles);
			Intent i = new Intent(getApplicationContext(),
					MenuRociadoActivity.class);
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
	
	
	public void buscarMetas(String parametro, int opcion){
		new FetchTargetsTask().execute(mLocalidad.getIdent(),mTemporada.getIdent(),parametro, String.valueOf(opcion));
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
			String strLocalidad = values[0];
			String strTemporada = values[1];
			String filtro = values[2];
			String opcion = values[3];
			MessageResource mr;
			try {
				vcaAdapter.open();
				if(opcion.equals("0")) {
					mTargets = vcaAdapter.getTargets2(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("1")) {
					mTargets = vcaAdapter.getNovisitadas(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("2")) {
					mTargets = vcaAdapter.getAsignadas(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("3")) {
					mTargets = vcaAdapter.getCerradas(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("4")) {
					mTargets = vcaAdapter.getRenuentes(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("5")) {
					mTargets = vcaAdapter.getDestruidas(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("6")) {
					mTargets = vcaAdapter.getRociadas(strTemporada,strLocalidad,filtro);
				}
				else if(opcion.equals("7")) {
					mTargets = vcaAdapter.getParciales(strTemporada,strLocalidad,filtro);
				}
				for(Target target:mTargets) {
					mr =vcaAdapter.getMessageResource(MainDBConstants.catKey + "='"+target.getSprayStatus() +"' and " + MainDBConstants.catRoot + "='CAT_STATUS'", null);
					if(mr!=null) target.setSprayStatus(mr.getSpanish());
				}
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
				mLabelTitle.setText(getString(R.string.localidad)+":"+mLocalidad.getName()+"\n"+
										getString(R.string.distrito)+":"+mLocalidad.getDistrict().getName()+"\n"+
											getString(R.string.area)+":"+mLocalidad.getDistrict().getArea().getName());
			}
			TargetAdapter adapter = new TargetAdapter(this, R.layout.household_list_item, mTargets);
			setListAdapter(adapter);
			if (mTargets.isEmpty()) {
				Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), String.valueOf(mTargets.size()) + " viviendas",Toast.LENGTH_LONG).show();
			}
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
