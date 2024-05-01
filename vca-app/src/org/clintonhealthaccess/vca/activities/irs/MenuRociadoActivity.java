package org.clintonhealthaccess.vca.activities.irs;

import android.os.Build;
import android.os.Bundle;
import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.server.UploadVisitasActivity;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.adapters.MenuRociadoAdapter;

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

public class MenuRociadoActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private static IrsSeason mTemporada = new IrsSeason();
	private String roles;
	
	
	private static final int UPLOAD = 3;
	private static final int UPDATE_SERVER = 12;
	
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

		//Aca se recupera los datos de la localidad y la temporada
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		mTemporada = (IrsSeason) getIntent().getExtras().getSerializable(Constants.TEMPORADA);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		
		textView = (TextView) findViewById(R.id.label);
		textView.setText("Temporada: "+mTemporada.getName()+"\n"+
							getString(R.string.localidad)+": "+mLocalidad.getName()+"\n"+
								getString(R.string.distrito)+": "+mLocalidad.getDistrict().getName()+"\n"+
								getString(R.string.area)+": "+mLocalidad.getDistrict().getArea().getName());
		
		
		menuRociado = getResources().getStringArray(R.array.menu_rociado);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
				Intent i;
				switch(position){ 
				case 0: // INGRESAR PREAVISO
					if (roles.contains("ROLE_SENTINEL")||roles.contains("ROLE_SUPER")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
						arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
	                            RociadoPreAvisoActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 1: // INGRESAR ROCIADO
					if (roles.contains("ROLE_SPRAY")||roles.contains("ROLE_SUPER")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
						arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
	                            RociadoRociarActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 2: // INGRESAR SUPERVISION
					if (roles.contains("ROLE_SUPER")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
						arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
	                            RociadoSupervisionActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 3: // INGRESAR BUSCAR VIVIENDA
					if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
					if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
					arguments.putString(Constants.ROLES, roles);
					i = new Intent(getApplicationContext(),
                            RociadoActivity.class);
					i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 4:
					createDialog(UPLOAD);
					break;	
				case 5:
					i = new Intent(getApplicationContext(),
							ViewDataActivity2.class);
					startActivity(i);
					break;		
				case 6: // REGRESAR MENU PRINCIPAL
					i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					finish();
					break;
				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuRociadoAdapter(getApplicationContext(), R.layout.menu_item_2, menuRociado));
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
			i = new Intent(getApplicationContext(),
						MainActivity.class);
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
		Intent i = new Intent(getApplicationContext(),
				MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch(dialog){
		case UPLOAD:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.uploading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), UploadVisitasActivity.class);
					startActivityForResult(ie, UPDATE_SERVER);
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
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {	
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_CANCELED) {
			if (requestCode == UPDATE_SERVER){
				String mensajeCancel = intent.getStringExtra("resultado");
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				if (mensajeCancel.equals("no hay datos")) {
					builder.setTitle(getApplicationContext().getString(R.string.process_canceled));
					builder.setMessage(getApplicationContext().getString(R.string.no_items_tosend));
					builder.setIcon(R.drawable.ic_menu_revert)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
				}
				else {
					builder.setTitle(getApplicationContext().getString(R.string.error));
					builder.setMessage(intent.getStringExtra("resultado"));
					builder.setIcon(R.drawable.ic_menu_close_clear_cancel)
					.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//do things
						}
					});
				}
				AlertDialog alert = builder.create();
				alert.show();
				return;
			}
		}
		else{
			if (requestCode == UPDATE_SERVER){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getApplicationContext().getString(R.string.confirm));
				builder.setIcon(R.drawable.ic_menu_mark);
				builder.setMessage(getApplicationContext().getString(R.string.success))
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//do things
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
			return;
		}
	}
	
	

}
	
