package org.clintonhealthaccess.vca.activities;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.OldHousehold;
import org.clintonhealthaccess.vca.preferences.PreferencesActivity;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.HouseholdActivity;
import org.clintonhealthaccess.vca.adapters.HouseholdAdapter;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.content.SharedPreferences;
import android.graphics.Color;

public class CensoActivity extends AbstractAsyncListActivity {
	

	private VcaAdapter vcaAdapter;
	private List<Household> mHouseholds = new ArrayList<Household>();
	private SharedPreferences settings;
	private String mLocalidad;
    private Localidad localidad = null;
    private Household vivienda = null;
    private OldHousehold viviendavieja = null;
    private Household viviendaDup = null;
    private TextView mLabelTitle;
    private TextView mHeaderTitle;
    
	private EditText mParametroView;
    private Button mAddButton;
    private ImageButton mFindButton;
    
    private AlertDialog alertDialog;
    private static final int ADD_VIV = 1;
    private static final int ADD_VIV_2 = 5;
    private static final int EDIT_VIV = 2;
    private static final int OPEN_ADD_VIV = 3;
    private static final int OPEN_EDIT_VIV = 4;
    private static final int OPEN_USE_VIV_SI = 6;
    
    
    private String m_Text = "";
	

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
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		mLocalidad =
				settings.getString(PreferencesActivity.KEY_CODE_LOCALIDAD,
						null);
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mHeaderTitle = (TextView) findViewById(R.id.label_header);
		
		mHeaderTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                	Intent i = new Intent(getApplicationContext(),
                			MapaActivity.class);
                	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                	startActivity(i);
                	finish();
                } catch (Exception except) {
                    Log.e(TAG,"Ooops map couldn´t load "+except.getMessage());
                }
            }
        });
				
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.VISIBLE);
		mParametroView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		mParametroView.setHint(getString(R.string.parametro));
		
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_VIV_2);
			}
		});
		
		
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mFindButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				mHouseholds.clear();
				buscarCasas(mParametroView.getText().toString());
			}
		});

		mFindButton.setVisibility(View.VISIBLE);
		
		new FetchHouseholdsLocalidadTask().execute(mLocalidad,"");
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		vivienda = (Household) getListAdapter().getItem(position);
		createDialog(EDIT_VIV);
	}
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
            case ADD_VIV:
            	if(viviendaDup!=null) {
            		builder.setTitle("Vivienda duplicada");
                    builder.setMessage(m_Text + "\n Este código ya existe en " + localidad.getName());
                    builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            	}
            	else if(viviendavieja!=null) {
            		builder.setTitle("Vivienda encontrada");
                    builder.setMessage("La vivienda " + m_Text +" fue encontrada en la base de datos anterior.\n Quiere usar estos datos? \n"+ viviendavieja.getCode() 
                    + "\n" +viviendavieja.getOwnerName()+ "\n" +viviendavieja.getObs()+ "\n" +viviendavieja.getRecordUser()+ "\n" +viviendavieja.getRecordDate());
                    builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new OpenDataActivityTask().execute(String.valueOf(OPEN_USE_VIV_SI));
                        }
                    });
                    builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                            new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_VIV));
                        }
                    });
            	}
            	else {
            		builder.setTitle(this.getString(R.string.confirm));
                    builder.setMessage(getString(R.string.confirm_house_add)+ "\n" + m_Text + "\n" + localidad.getName() );
                    builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_VIV));
                        }
                    });
                    builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });
            	}
            	
                
                break;
                
            case ADD_VIV_2:
            	final EditText txtCode = new EditText(this);
            	txtCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            	txtCode.setSingleLine();
            	txtCode.setHint("Ingrese el identificador ");
            	builder.setTitle(this.getString(R.string.confirm) );
            	builder.setView(txtCode);
                builder.setMessage(getString(R.string.confirm_house_add)+ "\n" + localidad.getName());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        m_Text = txtCode.getText().toString();
                        if(m_Text.equals("")) {
                        	Toast.makeText(getApplicationContext(), "No ha ingresado un identificador válido",Toast.LENGTH_LONG).show();
                        }
                        else {
                        	new FetchHouseViejaLocalidadTask().execute(mLocalidad,m_Text);
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
                 
            case EDIT_VIV:
                builder.setTitle(this.getString(R.string.confirm)+ " " + localidad.getName());
                builder.setMessage(getString(R.string.confirm_house_edit) + "\n" + vivienda.getCode()+ "\n" + vivienda.getOwnerName());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                		if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
                		if (vivienda!=null) arguments.putSerializable(Constants.VIVIENDA , vivienda);
                		Intent i = new Intent(getApplicationContext(),
                				MenuCensoCasaActivity.class);
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
		arguments.putSerializable(Constants.LOCALIDAD , localidad);
		Intent i = new Intent(getApplicationContext(),
				MenuCensoActivity.class);
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
			arguments.putSerializable(Constants.LOCALIDAD , localidad);
			Intent i = new Intent(getApplicationContext(),
					MenuCensoActivity.class);
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
	
	
	public void buscarCasas(String parametro){
		new FetchHouseholdsLocalidadTask().execute(mLocalidad,parametro);
	}
	

	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchHouseholdsLocalidadTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String strLocalidad = values[0];
			String filtro = values[1];
			try {
				vcaAdapter.open();
				localidad = vcaAdapter.getLocalidad(MainDBConstants.ident + " = '"+ strLocalidad + "'", null);
				mHouseholds = vcaAdapter.getHouseholds(MainDBConstants.local + " = '"+ strLocalidad + "' and ("+ MainDBConstants.code +" like '%" + filtro + "%'" + " or "+ MainDBConstants.ownerName +" like '%" + filtro + "%')", MainDBConstants.ownerName);
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
			if(localidad==null) {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			}else {
				mLabelTitle.setTextColor(Color.BLUE);
				mLabelTitle.setText(getString(R.string.localidad)+":"+localidad.getName());
			}
			
			HouseholdAdapter adapter = new HouseholdAdapter(this, R.layout.household_list_item, mHouseholds);
			setListAdapter(adapter);
			if (mHouseholds.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
			if (vcaAdapter != null)
                vcaAdapter.close(); 
			finish();
		}
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
            Household viv;
            try {
                switch (position) {
                    case OPEN_ADD_VIV:
        		        if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
        		        viv =  new Household();
        		        viv.setCode(m_Text);
        		        arguments.putSerializable(Constants.VIVIENDA , viv);
                        i = new Intent(getApplicationContext(), HouseholdActivity.class);
                        break;
                    case OPEN_EDIT_VIV:
        		        if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
        		        arguments.putSerializable(Constants.VIVIENDA , vivienda);
                        i = new Intent(getApplicationContext(), HouseholdActivity.class);
                        break;
                    case OPEN_USE_VIV_SI:
        		        if (localidad!=null) arguments.putSerializable(Constants.LOCALIDAD , localidad);
        		        viv =  new Household();
        		        viv.setCode(viviendavieja.getCode());
        		        viv.setInhabited(viviendavieja.getInhabited());
        		        viv.setOwnerName(viviendavieja.getOwnerName());
        		        viv.setMaterial(viviendavieja.getMaterial());
        		        viv.setHabitants(viviendavieja.getHabitants());
        		        viv.setSprRooms(viviendavieja.getSprRooms());
        		        viv.setNoSprooms(viviendavieja.getNoSprooms());
        		        viv.setRooms(viviendavieja.getRooms());
        		        viv.setNoSproomsReasons(viviendavieja.getNoSproomsReasons());
        		        viv.setSleep(viviendavieja.getSleep());
        		        viv.setNumNets(viviendavieja.getNumNets());
        		        viv.setPersonasCharlas(1);
        		        viv.setObs(viviendavieja.getObs());
        		        arguments.putSerializable(Constants.VIVIENDA , viv);
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
 	// ***************************************
 	private class FetchHouseViejaLocalidadTask extends AsyncTask<String, Void, String> {
 		@Override
 		protected void onPreExecute() {
 			// before the request begins, show a progress indicator
 			showLoadingProgressDialog();
 		}

 		@Override
 		protected String doInBackground(String... values) {
 			String strLocalidad = values[0];
 			String filtro = values[1];
 			try {
 				vcaAdapter.open();
 				localidad = vcaAdapter.getLocalidad(MainDBConstants.ident + " = '"+ strLocalidad + "'", null);
 				viviendavieja = vcaAdapter.getOldHousehold(MainDBConstants.local + " = '"+ strLocalidad + "' and "+ MainDBConstants.code +" = '" + filtro + "'", MainDBConstants.code);
 				viviendaDup = vcaAdapter.getHousehold(MainDBConstants.local + " = '"+ strLocalidad + "' and "+ MainDBConstants.code +" = '" + filtro + "'", MainDBConstants.code);
 				vcaAdapter.close();
 			} catch (Exception e) {
 				Log.e(TAG, e.getLocalizedMessage(), e);
 				return "error";
 			}
 			return "exito";
 		}

 		protected void onPostExecute(String resultado) {
 			dismissProgressDialog();
 			showResultCasa(resultado);
 		}

 	}

 	// ***************************************
 	// Private methods
 	// ***************************************
 	private void showResultCasa(String resultado) {
 		if(resultado.equals("exito")) {			
 			createDialog(ADD_VIV);
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
