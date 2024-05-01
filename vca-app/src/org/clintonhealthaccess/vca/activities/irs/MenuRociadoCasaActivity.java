package org.clintonhealthaccess.vca.activities.irs;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.activities.UbicacionActivity;
import org.clintonhealthaccess.vca.activities.VerCasaActivity;
import org.clintonhealthaccess.vca.activities.enterdata.irs.SprayActivity;
import org.clintonhealthaccess.vca.activities.enterdata.irs.SprayActivityRociado;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.adapters.MenuRociadoCasaAdapter;
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
import android.widget.Toast;


public class MenuRociadoCasaActivity extends AbstractAsyncActivity {
	private static Target mTarget = new Target();
	
    private static final int ADD_PREAVISO = 1;
    private static final int ADD_ROCIADO = 2;
    private static final int DONE_PREAVISO = 3;
    private static final int DONE_ROCIADO = 4;
   
    private static final int OPEN_ADD_PREAVISO = 11;
    private static final int OPEN_ADD_ROCIADO = 12;
    
    private String roles;
    
    
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	
	String[] menuRociado;

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
		mTarget = (Target) getIntent().getExtras().getSerializable(Constants.META);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		String estado=mTarget.getSprayStatus();
		if(mTarget.getSprayStatus().equals("Asignada")) {
			estado= estado + " a "+ mTarget.getAssignedTo().getName();
		}
		
		textView = (TextView) findViewById(R.id.label);
		textView.setText(getString(R.string.codeHouse)+":"+mTarget.getHousehold().getCode()+"\n"+
							getString(R.string.ownerName)+":"+mTarget.getHousehold().getOwnerName()+"\n"+
							getString(R.string.localidad)+":"+mTarget.getHousehold().getLocal().getName()+"\n"+
							"Temporada: "+mTarget.getIrsSeason().getName() +"\n"+
							"Estado: "+estado);
		
		
		menuRociado = getResources().getStringArray(R.array.menu_rociado_casa);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
	            Intent i;
				switch(position){ 
				case 0: // PREAVISO
					if (roles.contains("ROLE_SENTINEL")||roles.contains("ROLE_SUPER")) {
						if(mTarget.getSprayStatus().equals("Descartada")||mTarget.getSprayStatus().equals("Nunca visitada")||mTarget.getSprayStatus().equals("Cerrada")||mTarget.getSprayStatus().equals("Renuente")||mTarget.getSprayStatus().equals("Rociada parcial")) {
							createDialog(ADD_PREAVISO);
						}
						else {
							createDialog(DONE_PREAVISO);
						}
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 1:
					//ROCIADO
					if (roles.contains("ROLE_SPRAY")||roles.contains("ROLE_SUPER")) {
						if(mTarget.getSprayStatus().equals("Asignada")||mTarget.getSprayStatus().equals("Rociada parcial")) {
							createDialog(ADD_ROCIADO);
						}
						else {
							createDialog(DONE_ROCIADO);
						}
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 2:
					//UBICACION VIVIENDA
					if (mTarget!=null) arguments.putSerializable(Constants.LOCALIDAD , mTarget.getHousehold().getLocal());
					if (mTarget!=null) arguments.putSerializable(Constants.VIVIENDA , mTarget.getHousehold());
					if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
					arguments.putString(Constants.ROLES, roles);
					arguments.putString(Constants.FORM_NAME, Constants.VISITA);
                    i = new Intent(getApplicationContext(), UbicacionActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 3:
					if (mTarget!=null) arguments.putSerializable(Constants.LOCALIDAD , mTarget.getHousehold().getLocal());
					if (mTarget!=null) arguments.putSerializable(Constants.VIVIENDA , mTarget.getHousehold());
					if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
					arguments.putString(Constants.ROLES, roles);
					arguments.putString(Constants.FORM_NAME, Constants.VISITA);
                    i = new Intent(getApplicationContext(), VerCasaActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 4: // REGRESAR MENU ANTERIOR
					
					if (mTarget != null) arguments.putSerializable(Constants.LOCALIDAD, mTarget.getHousehold().getLocal());
					if (mTarget != null) arguments.putSerializable(Constants.TEMPORADA, mTarget.getIrsSeason());
					arguments.putString(Constants.ROLES, roles);
					i = new Intent(getApplicationContext(),
							MenuRociadoActivity.class);
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
		gridView.setAdapter(new MenuRociadoCasaAdapter(getApplicationContext(), R.layout.menu_item_2, menuRociado, mTarget));
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
			if (mTarget != null) arguments.putSerializable(Constants.LOCALIDAD, mTarget.getHousehold().getLocal());
			if (mTarget != null) arguments.putSerializable(Constants.TEMPORADA, mTarget.getIrsSeason());
			arguments.putString(Constants.ROLES, roles);
			i = new Intent(getApplicationContext(),
					MenuRociadoActivity.class);
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
		if (mTarget != null) arguments.putSerializable(Constants.LOCALIDAD, mTarget.getHousehold().getLocal());
		if (mTarget != null) arguments.putSerializable(Constants.TEMPORADA, mTarget.getIrsSeason());
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				MenuRociadoActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case ADD_PREAVISO:
			builder.setTitle(this.getString(R.string.confirm)+ " " + mTarget.getHousehold().getLocal().getName());
            builder.setMessage("Agregar visita de preaviso a la vivienda? \n" + mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_PREAVISO));
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
		case ADD_ROCIADO:
			builder.setTitle(this.getString(R.string.confirm)+ " " + mTarget.getHousehold().getLocal().getName());
            builder.setMessage("Agregar visita de rociado a la vivienda? \n" + mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_ROCIADO));
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
		case DONE_PREAVISO:
			builder.setTitle("No se puede agregar preaviso");
            builder.setMessage("La vivienda está en estado " + mTarget.getSprayStatus());
            builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            break;
		case DONE_ROCIADO:
			builder.setTitle("No se puede agregar rociado");
            builder.setMessage("La vivienda está en estado " + mTarget.getSprayStatus());
            builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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
                    case OPEN_ADD_PREAVISO:
        		        if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
        		        arguments.putString(Constants.ROLES, roles);
                        i = new Intent(getApplicationContext(), SprayActivity.class);
                        break;
                    case OPEN_ADD_ROCIADO:
        		        if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
        		        arguments.putString(Constants.ROLES, roles);
                        i = new Intent(getApplicationContext(), SprayActivityRociado.class);
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
	
