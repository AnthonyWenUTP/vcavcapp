package org.clintonhealthaccess.vca.activities.mapeo;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.joda.time.DateMidnight;
import org.clintonhealthaccess.vca.adapters.MenuCasoTxSupAdapter;
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
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class MenuCasoTxSupActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private static Caso mCaso = new Caso();
	private VcaAdapter vcaAdapter;
	
    
    
    
    private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	private String roles;
	private Date resCalendar=null;
	private String dia;
	private String accion;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	String[] menuCasoTxSup;

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
		//Aca se recupera los datos de la vivienda
		mCaso = (Caso) getIntent().getExtras().getSerializable(Constants.CASO);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		
		
		textView = (TextView) findViewById(R.id.label);
		textView.setTextSize(22);
		textView.setText(getString(R.string.codeCase)+":"+mCaso.getCodigo()+"\n"+
							getString(R.string.localidad)+":"+mCaso.getLocal().getName()+"\n"+mDateFormat.format(mCaso.getMxDate()));
		
		if (mCaso.getEstadocaso().equals("CONF")) {
			textView.setText(textView.getText() + "\n" + "Confirmado");
		} else if (mCaso.getEstadocaso().equals("TRAT")) {
			textView.setText(textView.getText() + "\n" + "En tratamiento");
		} else if (mCaso.getEstadocaso().equals("TRATC")) {
			textView.setText(textView.getText() + "\n" + "Tratamiento completo");
			
		} else if (mCaso.getEstadocaso().equals("SEG2")) {
			textView.setText(textView.getText() + "\n" + "Primer control negativo");
			
		} else if (mCaso.getEstadocaso().equals("SEG4")) {
			textView.setText(textView.getText() + "\n" + "Segundo control negativo");
			
		} else if (mCaso.getEstadocaso().equals("SEGPOS")) {
			textView.setText(textView.getText() + "\n" + "No curado");
			
		} else if (mCaso.getEstadocaso().equals("SEGINC")) {
			textView.setText(textView.getText() + "\n" + "Pérdida de seguimiento");
			
		}
		
		
		
		menuCasoTxSup = getResources().getStringArray(R.array.tx_sup);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				switch(position){ 
				default:
					createDialog(position);
					break;
				}
			}
		});
		gridView.setAdapter(new MenuCasoTxSupAdapter(getApplicationContext(), R.layout.menu_item_3, menuCasoTxSup, mCaso));
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
			if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
	        if (mCaso!=null) arguments.putSerializable(Constants.CASO , mCaso);
	        arguments.putString(Constants.ROLES, roles);
	    	i = new Intent(getApplicationContext(),
	                    MenuCasoActivity.class);
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
		Intent i = null;
		Bundle arguments = new Bundle();
		if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        if (mCaso!=null) arguments.putSerializable(Constants.CASO , mCaso);
        arguments.putString(Constants.ROLES, roles);
    	i = new Intent(getApplicationContext(),
                    MenuCasoActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final DatePicker picker = new DatePicker(this);
		picker.setCalendarViewShown(false);
		
		switch(dialog){
		case 0:
			dia = "1";
			if(mCaso.getDayTx01()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getTxDate().getTime());
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            //newDate = newDate.plusDays(1);
	            picker.setMaxDate(newDate.getMillis());
	            
			}
			else {				
				accion ="2";
			}
            break;
		case 1:
			dia = "2";
			if(mCaso.getDayTx02()==null) {
				accion ="1";
	            DateMidnight newDate = new DateMidnight(mCaso.getDayTx01().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            //newDate = newDate.plusDays(1);
	            picker.setMaxDate(newDate.getMillis());
			}
			else {				
				accion ="2";
			}
            break;
		case 2:
			dia = "3";
			if(mCaso.getDayTx03()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx02().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            //newDate = newDate.plusDays(1);
	            picker.setMaxDate(newDate.getMillis());
			}
			else {				
				accion ="2";
			}
            break;
		case 3:
			dia = "4";
			if(mCaso.getDayTx04()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx03().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 4:
			dia = "5";
			if(mCaso.getDayTx05()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx04().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 5:
			dia = "6";
			if(mCaso.getDayTx06()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx05().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 6:
			dia = "7";
			if(mCaso.getDayTx07()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx06().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 7:
			dia = "8";
			if(mCaso.getDayTx08()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx07().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 8:
			dia = "9";
			if(mCaso.getDayTx09()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx08().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 9:
			dia = "10";
			if(mCaso.getDayTx10()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx09().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 10:
			dia = "11";
			if(mCaso.getDayTx11()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx10().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 11:
			dia = "12";
			if(mCaso.getDayTx12()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx11().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 12:
			dia = "13";
			if(mCaso.getDayTx13()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx12().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		case 13:
			dia = "14";
			if(mCaso.getDayTx14()==null) {
				accion ="1";
				DateMidnight newDate = new DateMidnight(mCaso.getDayTx13().getTime());
	            newDate = newDate.plusDays(1);
	            picker.setMinDate(newDate.getMillis());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            newDate = newDate.plusDays(1);
	            DateMidnight todayDate = new DateMidnight(new Date().getTime());
	            if(todayDate.isBefore(newDate)) {
	            	picker.setMaxDate(new Date().getTime());
	            }
	            else {
	            	picker.setMaxDate(newDate.getMillis());
	            }
			}
			else {				
				accion ="2";
			}
            break;
		default:
			break;
		}
		if(accion.matches("1")) {
			builder.setTitle("Registrar tratamiento supervisado? ");
            builder.setMessage("Ingresar fecha de tratamiento: ");
            builder.setView(picker);
		}
		else {
			builder.setTitle("Quitar tratamiento supervisado?");
		}
		builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                resCalendar = getDateFromDatePicker(picker);
                new SaveTxSupTask().execute(dia,accion);
            }
        });
        builder.setNegativeButton(this.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	
	
	
  	
  	 // ***************************************
  	// Private classes
  	// ************************************
  	private class SaveTxSupTask extends AsyncTask<String, Void, String> {
  		@Override
  		protected void onPreExecute() {
  			// before the request begins, show a progress indicator
  			showLoadingProgressDialog();
  		}

  		@Override
  		protected String doInBackground(String... values) {
  			String strDia = values[0];
			String strAccion = values[1];
  			try {
  				vcaAdapter.open();
  				if(strAccion.matches("1")) {
  					switch(strDia){
  						case "1":
  							mCaso.setDayTx01(resCalendar);
  							break;
  						case "2":
  							mCaso.setDayTx02(resCalendar);
  							break;
  						case "3":
  							mCaso.setDayTx03(resCalendar);
  							break;
  						case "4":
  							mCaso.setDayTx04(resCalendar);
  							break;
  						case "5":
  							mCaso.setDayTx05(resCalendar);
  							break;
  						case "6":
  							mCaso.setDayTx06(resCalendar);
  							break;
  						case "7":
  							mCaso.setDayTx07(resCalendar);
  							break;
  						case "8":
  							mCaso.setDayTx08(resCalendar);
  							break;
  						case "9":
  							mCaso.setDayTx09(resCalendar);
  							break;
  						case "10":
  							mCaso.setDayTx10(resCalendar);
  							break;
  						case "11":
  							mCaso.setDayTx11(resCalendar);
  							break;
  						case "12":
  							mCaso.setDayTx12(resCalendar);
  							break;
  						case "13":
  							mCaso.setDayTx13(resCalendar);
  							break;
  						case "14":
  							mCaso.setDayTx14(resCalendar);
  							mCaso.setTxComp("1");
  							mCaso.setTxCompDate(resCalendar);
  							break;
  						default:
  							break;
  					}
  					mCaso.setTxSup("Si");
  				}
  				else {
  					switch(strDia){
						case "1":
							mCaso.setDayTx01(null);
							break;
						case "2":
							mCaso.setDayTx02(null);
							break;
						case "3":
							mCaso.setDayTx03(null);
							break;
						case "4":
							mCaso.setDayTx04(null);
							break;
						case "5":
							mCaso.setDayTx05(null);
							break;
						case "6":
							mCaso.setDayTx06(null);
							break;
						case "7":
							mCaso.setDayTx07(null);
							break;
						case "8":
							mCaso.setDayTx08(null);
							break;
						case "9":
							mCaso.setDayTx09(null);
							break;
						case "10":
							mCaso.setDayTx10(null);
							break;
						case "11":
							mCaso.setDayTx11(null);
							break;
						case "12":
							mCaso.setDayTx12(null);
							break;
						case "13":
							mCaso.setDayTx13(null);
							break;
						case "14":
							mCaso.setDayTx14(null);
							break;
						default:
							break;
					}
  					if(mCaso.getDayTx01()==null && mCaso.getDayTx02()==null && mCaso.getDayTx03()==null
  							&& mCaso.getDayTx04()==null && mCaso.getDayTx05()==null && mCaso.getDayTx06()==null
  							&& mCaso.getDayTx07()==null && mCaso.getDayTx08()==null && mCaso.getDayTx09()==null
  							&& mCaso.getDayTx10()==null && mCaso.getDayTx11()==null && mCaso.getDayTx12()==null
  							&& mCaso.getDayTx13()==null && mCaso.getDayTx14()==null) {
  						mCaso.setTxSup("No");
  					}
  				}
  				if(mCaso.getEstado()==Constants.STATUS_SUBMITTED) mCaso.setEstado(Constants.STATUS_NOT_SUBMITTED);
  				vcaAdapter.editarCaso(mCaso);
  				vcaAdapter.close();
  	            FileUtils.createFolder(FileUtils.BACKUP_PATH);
  	            File databaseFile = new File(FileUtils.DATABASE_PATH + "/" +MainDBConstants.DATABASE_NAME);
  	            File databaseFileBackup = new File(FileUtils.BACKUP_FILE);
  	            
  	            FileUtils.copy(databaseFile, databaseFileBackup);
  			} catch (Exception e) {
  				Log.e(TAG, e.getLocalizedMessage(), e);
  				return "error";
  			}
  			return "exito";
  		}

  		protected void onPostExecute(String resultado) {
  			// after the network request completes, hide the progress indicator
  			dismissProgressDialog();
  			showResult(resultado);
  		}

  	}
  	
  	


  	// ***************************************
  	// Private methods
  	// ***************************************
  	private void showResult(String resultado) {
  		if(resultado.equals("error")) {
 			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
 			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
 			if (vcaAdapter != null)
 				vcaAdapter.close();
 			finish();
 		}
  		Toast.makeText(getApplicationContext(), "Proceso finalizado...",Toast.LENGTH_LONG).show();
  		Bundle arguments = new Bundle();
		if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
		if (mCaso != null) arguments.putSerializable(Constants.CASO, mCaso);
		if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
		Intent i = new Intent(getApplicationContext(),
                MenuCasoTxSupActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
  	}	
  	
  	
    /**
	 * 
	 * @param datePicker
	 * @return a java.util.Date
	 */
  	public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
	    int day = datePicker.getDayOfMonth();
	    int month = datePicker.getMonth();
	    int year =  datePicker.getYear();
	
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day);
	
	    return calendar.getTime();
  	}
  	

}
	
