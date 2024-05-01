package org.clintonhealthaccess.vca.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.activities.irs.MenuRociadoCasaActivity;
import org.clintonhealthaccess.vca.adapters.DatosAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.Dato;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;


public class VerCasaActivity extends AbstractAsyncListActivity {
	

	private static Localidad mLocalidad = new Localidad();
	private static Household mVivienda = new Household();
	private List<Dato> mDatos = new ArrayList<Dato>();
	private TextView mLabelHeader;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	private static Target mTarget = new Target();
	private String formulario;
	private String roles;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_loc_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		
		//Aca se recupera los datos de la localidad
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		//Aca se recupera los datos de la vivienda
		mVivienda = (Household) getIntent().getExtras().getSerializable(Constants.VIVIENDA);
		mTarget = (Target) getIntent().getExtras().getSerializable(Constants.META);
		formulario = getIntent().getExtras().getString(Constants.FORM_NAME);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		mLabelHeader = (TextView) findViewById(R.id.label_header);
		mLabelHeader.setText(getString(R.string.codeHouse)+ ": "+mVivienda.getCode());
		
		mDatos.add(new Dato("Localidad", mVivienda.getLocal().getName()));
		mDatos.add(new Dato("Censador", mVivienda.getCensusTaker().getName()));
		mDatos.add(new Dato("Fecha censo", mDateFormat.format(mVivienda.getCensusDate())));
		mDatos.add(new Dato("Jefe Familia", mVivienda.getOwnerName()));
		mDatos.add(new Dato("Habitada", mVivienda.getInhabited()));
		mDatos.add(new Dato("Material", mVivienda.getMaterial()));
		if (mVivienda.getHabitants()!=null) mDatos.add(new Dato("Habitantes", mVivienda.getHabitants().toString()));
		if (mVivienda.getRooms()!=null) mDatos.add(new Dato("Cuartos totales", mVivienda.getRooms().toString()));
		if (mVivienda.getSprRooms()!=null) mDatos.add(new Dato("Cuartos rociables", mVivienda.getSprRooms().toString()));
		if (mVivienda.getSleep()!=null) mDatos.add(new Dato("Espacios de dormir", mVivienda.getSleep().toString()));
		if (mVivienda.getNumNets()!=null) mDatos.add(new Dato("Mosquiteros", mVivienda.getNumNets().toString()));
		mDatos.add(new Dato("Razones no rociable", mVivienda.getNoSproomsReasons()));
		mDatos.add(new Dato("Observaciones", mVivienda.getObs()));
		
		
		DatosAdapter adapter = new DatosAdapter(this, R.layout.complex_list_item, mDatos);
		setListAdapter(adapter);
		if (mDatos.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.general, menu);
		return true;
	}
	
	@Override
	public void onBackPressed (){
		salirFormulario();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==android.R.id.home){
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			startActivity(i);
			finish();
			return true;
		}
		else if(item.getItemId()==R.id.MENU_BACK){
			salirFormulario();
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
	
	private void salirFormulario() {
		Bundle arguments = new Bundle();
		Intent i = null;
		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
		if (mVivienda!=null) arguments.putSerializable(Constants.VIVIENDA , mVivienda);
		if (mTarget!=null) arguments.putSerializable(Constants.META , mTarget);
		if(formulario.equals(Constants.VIVIENDA)) {
		i = new Intent(getApplicationContext(),
				MenuCensoCasaActivity.class);
		}
		else if(formulario.equals(Constants.VISITA)) {
			arguments.putString(Constants.ROLES, roles);
        	i = new Intent(getApplicationContext(),
                    MenuRociadoCasaActivity.class);
		}
		i.putExtras(arguments);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
		
	}
}
