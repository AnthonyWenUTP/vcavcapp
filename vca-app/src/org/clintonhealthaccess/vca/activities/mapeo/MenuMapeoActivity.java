package org.clintonhealthaccess.vca.activities.mapeo;

import android.os.Build;
import android.os.Bundle;
import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.server.DownloadDatosMapeoActivity;
import org.clintonhealthaccess.vca.server.UploadDatosMapeoActivity;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.adapters.MenuMapeoAdapter;
import org.clintonhealthaccess.vca.database.VcaAdapter;

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

public class MenuMapeoActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private String roles;
	
	private static final int DOWNLOAD = 2;
	private static final int UPLOAD = 3;
	private static final int VERIFY = 4;
	private static final int UPDATE_EQUIPO = 11;

	private static final int UPDATE_SERVER = 12;
	
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	
	private VcaAdapter vcaAdapter;
	
	String[] menuMapeo;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_caso);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		if (savedInstanceState != null) {
			
		}
		
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);

		//Aca se recupera los datos de la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		
		textView = (TextView) findViewById(R.id.label);
		textView.setText(getString(R.string.main_5));
		
		textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_mylocation, 0, 0, 0);
		menuMapeo = getResources().getStringArray(R.array.menu_mapeo);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
	            Intent i;
				switch(position){ 
				case 0: // CASOS DE MALARIA
					if (roles.contains("ROLE_SENTINEL")||roles.contains("ROLE_SUPER")||roles.contains("ROLE_MOVIL")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (roles != null) arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
	                            CasosActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
	                else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 1: // MUESTRAS DE MALARIA
					if (roles.contains("ROLE_SENTINEL")||roles.contains("ROLE_SUPER")||roles.contains("ROLE_MOVIL")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (roles != null) arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
	                            MuestrasActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
	                else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;
				case 2: // PUNTOS DE DIAGNOSTICO
					if (roles.contains("ROLE_SENTINEL")||roles.contains("ROLE_SUPER")||roles.contains("ROLE_MOVIL")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (roles != null) arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
								PuntosDxActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
	                else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;	
				case 3: // CRIADEROS
					if (roles.contains("ROLE_SENTINEL")||roles.contains("ROLE_SUPER")||roles.contains("ROLE_MOVIL")) {
						if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
						if (roles != null) arguments.putString(Constants.ROLES, roles);
						i = new Intent(getApplicationContext(),
								CriaderosActivity.class);
						i.putExtras(arguments);
		                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		                startActivity(i);
		                finish();
					}
	                else {
						Toast.makeText(getApplicationContext(), getString(R.string.nopermiso),Toast.LENGTH_LONG).show();
					}
					break;					
				case 4:
					createDialog(UPLOAD);
					break;
				case 5:
					createDialog(DOWNLOAD);
					break;
				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuMapeoAdapter(getApplicationContext(), R.layout.menu_item_2, menuMapeo));
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
					Intent ie = new Intent(getApplicationContext(), UploadDatosMapeoActivity.class);
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
			
		case DOWNLOAD:
			
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.downloading));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					vcaAdapter.open();
					boolean hayDatos = vcaAdapter.verificarDataMapeo();
					vcaAdapter.close();
					if(hayDatos){
						createDialog(VERIFY);
					}
					else{
						dialog.dismiss();
						Intent ie = new Intent(getApplicationContext(), DownloadDatosMapeoActivity.class);
						startActivityForResult(ie, UPDATE_EQUIPO);
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
			
		case VERIFY:
			builder.setTitle(this.getString(R.string.confirm));
			builder.setMessage(this.getString(R.string.data_not_sent));
			builder.setIcon(android.R.drawable.ic_menu_help);
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent ie = new Intent(getApplicationContext(), DownloadDatosMapeoActivity.class);
					startActivityForResult(ie, UPDATE_EQUIPO);
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
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
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
			if (requestCode == UPDATE_EQUIPO||requestCode == UPDATE_SERVER){
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
	
