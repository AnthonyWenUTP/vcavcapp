package org.clintonhealthaccess.vca.activities.mapeo;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.mapeo.CriaderoActivity;
import org.clintonhealthaccess.vca.adapters.CriaderoAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

public class CriaderosActivity extends AbstractAsyncListActivity implements AdapterView.OnItemSelectedListener{
	

	private VcaAdapter vcaAdapter;
	private List<Criadero> mCriaderos = new ArrayList<Criadero>();
    private Criadero criadero = new Criadero();
    private TextView mLabelTitle;
    private TextView mLabelHeader;
    private static Localidad mLocalidad = new Localidad();
    
	private EditText mParametroView;
    private ImageButton mFindButton;
    private Button mAddButton;
    private String roles;
    private String strFiltro = "";
    private int tipoSeleccionado=0;
    
    private AlertDialog alertDialog;
    private static final int ADD_CRIAD = 1;
    private static final int EDIT_CRIAD = 2;
    private static final int OPEN_ADD_CRIAD = 3;
    private static final int OPEN_EDIT_CRIAD = 4;
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_case_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		
		
		//Aca se recupera los datos de la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mLabelHeader = (TextView) findViewById(R.id.label_header);
		

		mLabelHeader.setVisibility(View.GONE);
		
		Spinner spinner = (Spinner) findViewById(R.id.opciones_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.opciones_tiposc, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(optionsListener);
		
		Spinner spinner2 = (Spinner) findViewById(R.id.opciones_spinner_2);
		spinner2.setVisibility(View.GONE);
		
		
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.VISIBLE);
		mParametroView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		mParametroView.setHint(getString(R.string.parametro));
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.criad_add));
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_CRIAD);
			}
		});
		
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mCriaderos.clear();
				buscarCriaderos(mParametroView.getText().toString(),tipoSeleccionado);
			}
		});

		mFindButton.setVisibility(View.VISIBLE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		criadero = (Criadero) getListAdapter().getItem(position);
		createDialog(EDIT_CRIAD);
	}
	
	AdapterView.OnItemSelectedListener optionsListener = 
		    new AdapterView.OnItemSelectedListener() {
		        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		            switch(parent.getId()){
		                case R.id.opciones_spinner:
		                    // estado related code
		                	tipoSeleccionado=position;
		                    break;
		            }
		            // common code
		            buscarCriaderos(mParametroView.getText().toString(),tipoSeleccionado);
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> arg0) {
		            // TODO Auto-generated method stub.
		        }
		    };
	
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
        	case ADD_CRIAD:
        		builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_criad_add));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_CRIAD));
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
            case EDIT_CRIAD:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_criad_edit) + "\n" + criadero.getInfo());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                		if (criadero!=null) arguments.putSerializable(Constants.CRIADERO , criadero);
                		if (roles!=null) arguments.putString(Constants.ROLES, roles);
                		Intent i = new Intent(getApplicationContext(),
                				MenuCriaderoActivity.class);
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
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				MenuMapeoActivity.class);
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
			arguments.putString(Constants.ROLES, roles);
			Intent i = new Intent(getApplicationContext(),
					MenuMapeoActivity.class);
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
	
	
	public void buscarCriaderos(String parametro, int tipo){
		new FetchCriaderosTask().execute(parametro, String.valueOf(tipo));
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchCriaderosTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String filtro = values[0];
			String tipo = values[1];
			
			try {
				vcaAdapter.open();
				
				strFiltro = MainDBConstants.info +" like '%" + filtro + "%'";
				
				if(!tipo.equals("0")) {
					if(tipo.equals("1")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'PR'";
					}
					else if(tipo.equals("2")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'PT'";
					}
				}
				
				
				mCriaderos = vcaAdapter.getCriaderos(strFiltro, MainDBConstants.info);
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
				mLabelTitle.setText(getString(R.string.breedingsites));
			}
			CriaderoAdapter adapter = new CriaderoAdapter(this, R.layout.household_list_item, mCriaderos);
			setListAdapter(adapter);
			if (mCriaderos.isEmpty()) {
				Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), String.valueOf(mCriaderos.size()) + " criaderos",Toast.LENGTH_SHORT).show();
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


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
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
                    case OPEN_ADD_CRIAD:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
        		        arguments.putSerializable(Constants.CRIADERO , new Criadero());
                        i = new Intent(getApplicationContext(), CriaderoActivity.class);
                        break;
                    case OPEN_EDIT_CRIAD:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        arguments.putSerializable(Constants.CRIADERO , criadero);
                        i = new Intent(getApplicationContext(), CriaderoActivity.class);
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
}
