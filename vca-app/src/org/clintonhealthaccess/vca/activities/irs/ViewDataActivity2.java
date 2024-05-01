package org.clintonhealthaccess.vca.activities.irs;


import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.activities.ViewReportActivity;
import org.clintonhealthaccess.vca.adapters.ViewDataActivityAdapter;
import org.clintonhealthaccess.vca.utils.Constants;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class ViewDataActivity2 extends AbstractAsyncListActivity {

    private String[] menu_datos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte_datos);
		menu_datos = getResources().getStringArray(R.array.menu_datos2);
		setListAdapter(new ViewDataActivityAdapter(this, R.layout.menu_item, menu_datos));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		// Opcion de menu seleccionada
		Intent i = new Intent(getApplicationContext(),
				ViewReportActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		switch(position){
		case 0: 
			i.putExtra(Constants.REPORTE_NAME,Constants.REPORTE_4);
			break;	
		case 1: 
			i.putExtra(Constants.REPORTE_NAME,Constants.REPORTE_5);
			break;
		case 2: 
			i.putExtra(Constants.REPORTE_NAME,Constants.REPORTE_6);
			break;
		case 3: 
			i.putExtra(Constants.REPORTE_NAME,Constants.REPORTE_7);
			break;
		default: 
			String s = (String) getListAdapter().getItem(position);
			Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
		startActivity(i);
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
	}	
}
