package org.clintonhealthaccess.vca.activities;


import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.adapters.DatoAdapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ViewReportActivity extends AbstractAsyncListActivity {

    private String reportName;
    private VcaAdapter vcaAdapter;
    private TextView mLabelTitle;
    private List<Object[]> mResultados = new ArrayList<Object[]>();
    private Integer totalRegistros;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reporte_datos);
		mLabelTitle = (TextView) findViewById(R.id.label_header);
		reportName = getIntent().getStringExtra(Constants.REPORTE_NAME);
		mLabelTitle.setText(reportName);
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		new FetchDataTask().execute(reportName);
	}
	
	
	// ***************************************
	// Private classes
	// ***************************************
	private class FetchDataTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected String doInBackground(String... values) {
			String strReporte = values[0];
			try {
				vcaAdapter.open();
				if(strReporte.equals(Constants.REPORTE_1)) {
					totalRegistros = vcaAdapter.getNumeroRegistros(MainDBConstants.VIVIENDAS_TABLE,null);
					mResultados = vcaAdapter.getNumeroRegistrosFecha();
				}
				else if(strReporte.equals(Constants.REPORTE_2)) {
					totalRegistros = vcaAdapter.getNumeroRegistros(MainDBConstants.VIVIENDAS_TABLE,null);
					mResultados = vcaAdapter.getNumeroRegistrosLocalidad();
				}
				else if(strReporte.equals(Constants.REPORTE_3)) {
					totalRegistros = vcaAdapter.getNumeroRegistros(MainDBConstants.VIVIENDAS_TABLE,null);
					mResultados = vcaAdapter.getNumeroRegistrosEstado();
				}
				else if(strReporte.equals(Constants.REPORTE_4)) {
					totalRegistros = vcaAdapter.getNumeroRegistros(MainDBConstants.VISITAS_TABLE,null);
					mResultados = vcaAdapter.getNumeroRegistrosFechaVisitas();
				}
				else if(strReporte.equals(Constants.REPORTE_5)) {
					totalRegistros = vcaAdapter.getNumeroRegistros(MainDBConstants.METAS_TABLE,null);
					mResultados = vcaAdapter.getNumeroRegistrosEstadoMetas();
				}
				else if(strReporte.equals(Constants.REPORTE_6)) {
					totalRegistros = vcaAdapter.getNumeroRegistros(MainDBConstants.VISITAS_TABLE,null);
					mResultados = vcaAdapter.getNumeroRegistrosVisitasEstado();
				}
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
			DatoAdapter adapter = new DatoAdapter(this, R.layout.dato_list_item, mResultados);
			setListAdapter(adapter);
			mLabelTitle.setText(reportName+"\nTotal: "+totalRegistros);
			if (mResultados.isEmpty()) Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
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
