package org.clintonhealthaccess.vca.activities.mtilds;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.activities.enterdata.mtilds.EntregaTargetActivity;
import org.clintonhealthaccess.vca.activities.enterdata.mtilds.EvaluacionActivity;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.Evaluacion;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.adapters.MenuMosquiterosCasaAdapter;
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


public class MenuMosquiterosCasaActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private static Ciclo mCiclo = new Ciclo();
	private static EntregaTarget mTarget = new EntregaTarget();
	private static Evaluacion mEval = new Evaluacion();
	
    private static final int EDIT_META = 1;
    private static final int EDIT_EVAL = 2;
   
    private static final int OPEN_EDIT_META = 11;
    private static final int OPEN_EDIT_EVAL = 12;
    
    private String roles;
    
    
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	
	String[] menumosquiteros;

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

		//Aca se recupera los datos de la meta
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		mTarget = (EntregaTarget) getIntent().getExtras().getSerializable(Constants.META);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		mEval.setIdent("1");
		mEval.setTarget(mTarget);
		mEval.setVisitDate(new Date());
		
		textView = (TextView) findViewById(R.id.label);
		Integer espaciosDormir = mTarget.getSitiosDormirCama() + mTarget.getSitiosDormirHamaca() + mTarget.getSitiosDormirOtro() + mTarget.getSitiosDormirSuelo();
		textView.setText(getString(R.string.codeHouse)+":"+mTarget.getHousehold().getCode()+"\n"+
							getString(R.string.ownerName)+":"+mTarget.getHousehold().getOwnerName()+"\n"+
							"Total habitantes: " + mTarget.getHabitantes() + "\n" +
							"Total espacios de dormir: " + espaciosDormir + "\n" +
							getString(R.string.localidad)+":"+mTarget.getHousehold().getLocal().getName());
		
		
		menumosquiteros = getResources().getStringArray(R.array.menu_mosquiteros_casa);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
	            Intent i;
				switch(position){ 
				case 0: // GENERAL
					createDialog(EDIT_META);
					break;
				case 1:
					//VISITAS
					if (mTarget!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
					arguments.putString(Constants.ROLES, roles);
                    i = new Intent(getApplicationContext(), EntregaVisitasActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 2: // GENERAL
					createDialog(EDIT_EVAL);
					break;
				case 3:
					//PERSONAS
					if (mTarget!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
					arguments.putString(Constants.ROLES, roles);
                    i = new Intent(getApplicationContext(), EvaluacionMosquiterosPersonaActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 4://Mosquiteros
					if (mTarget!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
					arguments.putString(Constants.ROLES, roles);
                    i = new Intent(getApplicationContext(), EvaluacionMosquiteroIndividualActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 5: // REGRESAR MENU ANTERIOR
					
					arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					arguments.putSerializable(Constants.CICLO , mCiclo);
					arguments.putString(Constants.ROLES, roles);
					i = new Intent(getApplicationContext(),
							SeleccionarCasaCicloActivity.class);
					i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuMosquiterosCasaAdapter(getApplicationContext(), R.layout.menu_item_2, menumosquiteros));
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
			arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
			arguments.putSerializable(Constants.CICLO , mCiclo);
			arguments.putString(Constants.ROLES, roles);
			i = new Intent(getApplicationContext(),
					SeleccionarCasaCicloActivity.class);
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
		arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
		arguments.putSerializable(Constants.CICLO , mCiclo);
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				SeleccionarCasaCicloActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		
		case EDIT_META:
			builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage("Editar las metas en esta casa? \n" + mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_EDIT_META));
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
            
		case EDIT_EVAL:
			builder.setTitle(this.getString(R.string.confirm));
            builder.setMessage("Editar la evaluación en esta casa? \n" + mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_EDIT_EVAL));
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
	                case OPEN_EDIT_META:
	    		        if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
	    		        arguments.putString(Constants.ROLES, roles);
	                    i = new Intent(getApplicationContext(), EntregaTargetActivity.class);
	                    break;
                    case OPEN_EDIT_EVAL:
                    	if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
        		        if (mEval!=null) arguments.putSerializable(Constants.EVALUACION , mEval);
        		        arguments.putString(Constants.ROLES, roles);
                        i = new Intent(getApplicationContext(), EvaluacionActivity.class);
                        break;
                    
                    default:
                    	i = new Intent(getApplicationContext(), MainActivity.class);
                    	break;
                }
                i.putExtras(arguments);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
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
	
