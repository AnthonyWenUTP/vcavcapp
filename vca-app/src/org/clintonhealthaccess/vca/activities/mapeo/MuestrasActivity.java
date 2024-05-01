package org.clintonhealthaccess.vca.activities.mapeo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.joda.time.DateMidnight;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.mapeo.MuestraActivity;
import org.clintonhealthaccess.vca.adapters.MuestraAdapter;

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

public class MuestrasActivity extends AbstractAsyncListActivity implements AdapterView.OnItemSelectedListener{
	

	private VcaAdapter vcaAdapter;
	private List<Muestra> mMuestras = new ArrayList<Muestra>();
    private Muestra muestra = new Muestra();
    private TextView mLabelTitle;
    private TextView mLabelHeader;
    private static Localidad mLocalidad = new Localidad();
    
	private EditText mParametroView;
    private ImageButton mFindButton;
    private Button mAddButton;
    private String roles;
    private int opcionSeleccionada=0;
    private int periodoSeleccionado=0;
    private String strFiltro = "";
    
    private AlertDialog alertDialog;
    private static final int ADD_MUESTRA = 1;
    private static final int EDIT_MUESTRA = 2;
    private static final int OPEN_ADD_MUESTRA = 3;
    private static final int OPEN_EDIT_MUESTRA = 4;
	

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
                			MapaMuestrasActivity.class);
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
		        R.array.opciones_muestras, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(optionsListener);
		
		Spinner spinner2 = (Spinner) findViewById(R.id.opciones_spinner_2);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.opciones_casos_2, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner2.setAdapter(adapter2);
		spinner2.setOnItemSelectedListener(optionsListener);
		spinner.setVisibility(View.GONE);
		
		
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.VISIBLE);
		mParametroView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		mParametroView.setHint(getString(R.string.parametro));
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.test_add));
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_MUESTRA);
			}
		});
		
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mMuestras.clear();
				buscarMuestras(mParametroView.getText().toString(),opcionSeleccionada,periodoSeleccionado);
			}
		});

		mFindButton.setVisibility(View.VISIBLE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		muestra = (Muestra) getListAdapter().getItem(position);
		createDialog(EDIT_MUESTRA);
	}
	
	AdapterView.OnItemSelectedListener optionsListener = 
		    new AdapterView.OnItemSelectedListener() {
		        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		            switch(parent.getId()){
		                case R.id.opciones_spinner:
		                    // estado related code
		                	opcionSeleccionada=position;
		                    break;
		                case R.id.opciones_spinner_2:
		                    // periodo related code
		                	periodoSeleccionado=position;
		                    break;
		            }
		            // common code
		            buscarMuestras(mParametroView.getText().toString(),opcionSeleccionada,periodoSeleccionado);
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> arg0) {
		            // TODO Auto-generated method stub.
		        }
		    };
	
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
        	case ADD_MUESTRA:
        		builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_test_add));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_MUESTRA));
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
            case EDIT_MUESTRA:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_test_edit) + "\n" + muestra.getCasa());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                		if (muestra!=null) arguments.putSerializable(Constants.MUESTRA , muestra);
                		if (roles!=null) arguments.putString(Constants.ROLES, roles);
                		Intent i = new Intent(getApplicationContext(),
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
	
	
	public void buscarMuestras(String parametro, int estado, int periodo){
		new FetchTestsTask().execute(parametro, String.valueOf(estado), String.valueOf(periodo));
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchTestsTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String filtro = values[0];
			//String busqueda = values[2];
			String periodo = values[2];
			DateMidnight dmDesde = null;
			
			
			try {
				vcaAdapter.open();
				
				strFiltro = MainDBConstants.casa +" like '%" + filtro + "%'";
				
				/*if(!busqueda.equals("0")) {
					if(busqueda.equals("1")) {
						strFiltro = strFiltro + " and " + MainDBConstants.busqueda + "= 'Activa'";
					}
					else if(busqueda.equals("2")) {
						strFiltro = strFiltro + " and " + MainDBConstants.busqueda + "= 'Pasiva'";
					}
				}*/
				
				if(!periodo.equals("0")) {
					if(periodo.equals("1")) {
						dmDesde = new DateMidnight(new Date().getTime()).minusDays(7);
					}
					else if(periodo.equals("2")) {
						dmDesde = new DateMidnight(new Date().getTime()).minusDays(30);
					}
					else if(periodo.equals("3")) {
						dmDesde = new DateMidnight(new Date().getTime()).minusDays(60);
					}
					strFiltro = strFiltro + " and " + MainDBConstants.mxDate +">= " + dmDesde.getMillis() ;
				}
				
				mMuestras = vcaAdapter.getMuestras(strFiltro, MainDBConstants.mxDate);
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
				mLabelTitle.setText(getString(R.string.tests));
			}
			MuestraAdapter adapter = new MuestraAdapter(this, R.layout.household_list_item, mMuestras);
			setListAdapter(adapter);
			if (mMuestras.isEmpty()) {
				Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), String.valueOf(mMuestras.size()) + " muestras",Toast.LENGTH_SHORT).show();
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
                    case OPEN_ADD_MUESTRA:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
        		        arguments.putSerializable(Constants.MUESTRA , new Muestra());
                        i = new Intent(getApplicationContext(), MuestraActivity.class);
                        break;
                    case OPEN_EDIT_MUESTRA:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        arguments.putSerializable(Constants.MUESTRA , muestra);
                        i = new Intent(getApplicationContext(), MuestraActivity.class);
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
