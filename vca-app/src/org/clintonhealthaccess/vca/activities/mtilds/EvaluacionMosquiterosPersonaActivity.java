package org.clintonhealthaccess.vca.activities.mtilds;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionPersona;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.PersonActivity;
import org.clintonhealthaccess.vca.activities.enterdata.mtilds.EvaluacionPersonaActivity;
import org.clintonhealthaccess.vca.adapters.EvaluacionPersonaAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class EvaluacionMosquiterosPersonaActivity extends AbstractAsyncListActivity {
	

	private VcaAdapter vcaAdapter;
	private List<EvaluacionPersona> mEvPersons = new ArrayList<EvaluacionPersona>();
	private static Localidad mLocalidad = new Localidad();
	private static EntregaTarget mTarget = new EntregaTarget();
	private EvaluacionPersona evPersona= null;
    private TextView mHeaderTitle;
    
    private Button mAddButton;
    
    private AlertDialog alertDialog;
    
    private static final int ADD_PER = 1;
    private static final int EDIT_PER = 2;
    private static final int OPEN_ADD_PER = 11;
    private static final int OPEN_EDIT_PER = 12;
    
    private String roles;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_person_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		//Aca se recupera los datos de la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		//Aca se recupera los datos de la vivienda
		mTarget = (EntregaTarget) getIntent().getExtras().getSerializable(Constants.META);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		mHeaderTitle = (TextView) findViewById(R.id.label_header);
		
	
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_PER);
			}
		});
		
		mAddButton.setText(getString(R.string.add) + " persona");
		
		mEvPersons.add(new EvaluacionPersona("1",mTarget,new Person("1", mTarget.getHousehold(), "1", "PEDRO", "M", 23, null, ""),"Si",null,"Todas las noches",null));
		mEvPersons.add(new EvaluacionPersona("2",mTarget,new Person("2", mTarget.getHousehold(), "2", "JUAN", "M", 29, null, ""),"No","Mal olor","No lo usa del todo",null));
		mEvPersons.add(new EvaluacionPersona("3",mTarget,new Person("3", mTarget.getHousehold(), "3", "MARIA", "F", 43, null, "")));
		//TODO 
		new FetchPersonsHouseholdTask().execute(mTarget.getIdent(),"");
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		//TODO
		evPersona = (EvaluacionPersona) getListAdapter().getItem(position);
		createDialog(EDIT_PER);
	}
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
            case ADD_PER:
                builder.setTitle(this.getString(R.string.confirm) );
                builder.setMessage(getString(R.string.confirm_person_add)+ "\n" + mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_PER));
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
            case EDIT_PER:
                builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage("Registrar evaluación de esta persona?" + "\n" + evPersona.getPersona().getCode()+ "\n" + evPersona.getPersona().getName());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataActivityTask().execute(String.valueOf(OPEN_EDIT_PER));
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
		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
		if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
		Intent i = new Intent(getApplicationContext(),
				MenuMosquiterosCasaActivity.class);
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
			if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
			if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
			Intent i = new Intent(getApplicationContext(),
					MenuMosquiterosCasaActivity.class);
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
	
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchPersonsHouseholdTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			//String strHouse = values[0];
			try {
				//vcaAdapter.open();
				//mPersons = vcaAdapter.getPersonas(MainDBConstants.household + " = '"+ strHouse + "'", MainDBConstants.namePerson);
				//vcaAdapter.close();
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
				mHeaderTitle.setTextColor(Color.BLUE);
				mHeaderTitle.setText(getString(R.string.household)+":"+mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
			}
			
			EvaluacionPersonaAdapter adapter = new EvaluacionPersonaAdapter(this, R.layout.household_list_item, mEvPersons);
			setListAdapter(adapter);
			if (mEvPersons.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
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
            Intent i=null;
            try {
                switch (position) {
                    case OPEN_ADD_PER:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        if (mTarget!=null) arguments.putSerializable(Constants.VIVIENDA , mTarget.getHousehold());
        		        if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
        		        arguments.putString(Constants.FORM_NAME, Constants.EVMOSQPERSONA);
        		        arguments.putSerializable(Constants.PERSONA , new Person());
                        i = new Intent(getApplicationContext(), PersonActivity.class);
                        break;
                    case OPEN_EDIT_PER:
                    	if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                    	if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
                    	if (evPersona!=null) arguments.putSerializable(Constants.EVMOSQPERSONA , evPersona);
        		        arguments.putString(Constants.ROLES, roles);
                        i = new Intent(getApplicationContext(), EvaluacionPersonaActivity.class);
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
