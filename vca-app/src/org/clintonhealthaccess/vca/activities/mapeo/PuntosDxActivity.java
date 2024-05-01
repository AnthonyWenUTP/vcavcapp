package org.clintonhealthaccess.vca.activities.mapeo;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.mapeo.PuntoDxActivity;
import org.clintonhealthaccess.vca.adapters.PuntoDiagnosticoAdapter;
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

public class PuntosDxActivity extends AbstractAsyncListActivity implements AdapterView.OnItemSelectedListener{
	

	private VcaAdapter vcaAdapter;
	private List<PuntoDiagnostico> mPuntoDiagnosticos = new ArrayList<PuntoDiagnostico>();
    private PuntoDiagnostico puntodx = new PuntoDiagnostico();
    private TextView mLabelTitle;
    private TextView mLabelHeader;
    private static Localidad mLocalidad = new Localidad();
    
	private EditText mParametroView;
    private ImageButton mFindButton;
    private Button mAddButton;
    private String roles;
    private int tipoSeleccionada=0;
    private int estadoSeleccionado=0;
    private String strFiltro = "";
    
    private AlertDialog alertDialog;
    private static final int ADD_PUNTO = 1;
    private static final int EDIT_PUNTO = 2;
    private static final int OPEN_ADD_PUNTO = 3;
    private static final int OPEN_EDIT_PUNTO = 4;
	

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
		

		mLabelHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                	Bundle arguments = new Bundle();
                	Intent i = new Intent(getApplicationContext(),
                			MapaPuntosDxActivity.class);
                	if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
					arguments.putString(Constants.ROLES, roles);
					arguments.putString(Constants.FILTRO, strFiltro);
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
		        R.array.opciones_tipos, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(optionsListener);
		
		Spinner spinner2 = (Spinner) findViewById(R.id.opciones_spinner_2);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.opciones_estados, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner2.setAdapter(adapter2);
		spinner2.setOnItemSelectedListener(optionsListener);
		
		
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.VISIBLE);
		mParametroView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		mParametroView.setHint(getString(R.string.parametro));
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.pdx_add));
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_PUNTO);
			}
		});
		
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mPuntoDiagnosticos.clear();
				buscarPuntoDiagnosticos(mParametroView.getText().toString(),tipoSeleccionada,estadoSeleccionado);
			}
		});

		mFindButton.setVisibility(View.VISIBLE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		puntodx = (PuntoDiagnostico) getListAdapter().getItem(position);
		createDialog(EDIT_PUNTO);
	}
	
	AdapterView.OnItemSelectedListener optionsListener = 
		    new AdapterView.OnItemSelectedListener() {
		        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		            switch(parent.getId()){
		                case R.id.opciones_spinner:
		                    // estado related code
		                	tipoSeleccionada=position;
		                    break;
		                case R.id.opciones_spinner_2:
		                    // periodo related code
		                	estadoSeleccionado=position;
		                    break;
		            }
		            // common code
		            buscarPuntoDiagnosticos(mParametroView.getText().toString(),tipoSeleccionada,estadoSeleccionado);
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> arg0) {
		            // TODO Auto-generated method stub.
		        }
		    };
	
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
        	case ADD_PUNTO:
        		builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_pdx_add));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_PUNTO));
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
            case EDIT_PUNTO:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_pdx_edit) + "\n" + puntodx.getClave());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                		if (puntodx!=null) arguments.putSerializable(Constants.PUNTO , puntodx);
                		if (roles!=null) arguments.putString(Constants.ROLES, roles);
                		Intent i = new Intent(getApplicationContext(),
                				MenuPuntoDxActivity.class);
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
	
	
	public void buscarPuntoDiagnosticos(String parametro, int tipo, int estado){
		new FetchPointsTask().execute(parametro, String.valueOf(tipo), String.valueOf(estado));
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchPointsTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String filtro = values[0];
			String tipo = values[1];
			String estado = values[2];
			
			try {
				vcaAdapter.open();
				
				strFiltro = MainDBConstants.clave +" like '%" + filtro + "%'";
				
				if(!tipo.equals("0")) {
					if(tipo.equals("1")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'CV'";
					}
					else if(tipo.equals("2")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'CM'";
					}
					else if(tipo.equals("3")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'POC'";
					}
					else if(tipo.equals("4")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'PS'";
					}
					else if(tipo.equals("5")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'CS'";
					}
					else if(tipo.equals("6")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'HOSP'";
					}
					else if(tipo.equals("7")) {
						strFiltro = strFiltro + " and " + MainDBConstants.tipo + "= 'OTRO'";
					}
				}
				
				if(!estado.equals("0")) {
					if(estado.equals("1")) {
						strFiltro = strFiltro + " and " + MainDBConstants.status + "= 'ACT'";
					}
					else if(estado.equals("2")) {
						strFiltro = strFiltro + " and " + MainDBConstants.status + "= 'INACT'";
					}
				}
				
				mPuntoDiagnosticos = vcaAdapter.getPuntosDiagnosticos(strFiltro, MainDBConstants.clave);
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
				mLabelTitle.setText(getString(R.string.dxpoints));
			}
			PuntoDiagnosticoAdapter adapter = new PuntoDiagnosticoAdapter(this, R.layout.household_list_item, mPuntoDiagnosticos);
			setListAdapter(adapter);
			if (mPuntoDiagnosticos.isEmpty()) {
				Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), String.valueOf(mPuntoDiagnosticos.size()) + " puntos",Toast.LENGTH_SHORT).show();
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
                    case OPEN_ADD_PUNTO:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
        		        arguments.putSerializable(Constants.PUNTO , new PuntoDiagnostico());
                        i = new Intent(getApplicationContext(), PuntoDxActivity.class);
                        break;
                    case OPEN_EDIT_PUNTO:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        arguments.putSerializable(Constants.PUNTO , puntodx);
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
}
