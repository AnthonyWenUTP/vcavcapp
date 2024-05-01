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
import org.clintonhealthaccess.vca.activities.enterdata.mapeo.CaseActivity;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.joda.time.DateMidnight;
import org.clintonhealthaccess.vca.adapters.MenuCasoAdapter;
import org.clintonhealthaccess.vca.database.VcaAdapter;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MenuCasoActivity extends AbstractAsyncActivity {
	private static Localidad mLocalidad = new Localidad();
	private static Caso mCaso = new Caso();
	private VcaAdapter vcaAdapter;
	
    private static final int EDIT_CASO = 2;
    private static final int INV_CASO = 3;
    private static final int TRAT_CASO = 4;
    private static final int TRATC_CASO = 5;
    private static final int SEG2_CASO = 6;
    private static final int SEG4_CASO = 7;
    private static final int LOST_CASO = 8;
    private static final int INV_CASO_COMP = 9;
    private static final int TRATSUSP_CASO = 10;
    private static final int ELIMINAR_CASO = 11;
    
    private static final int OPEN_EDIT_CASO = 40;
    
    
	
	private GridView gridView;
	private TextView textView;
	private AlertDialog alertDialog;
	private String roles;
	private Date resCalendar=null;
	private String resultado="";
	private String pruebausada="";
	private String razonsusp="";
	private String orazonsusp="";
	private String omotivolost="";
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	
	String[] menuCaso;


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
		
		
		
		menuCaso = getResources().getStringArray(R.array.menu_caso);
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Bundle arguments = new Bundle();
	            Intent i;
				switch(position){ 
				case 0:
					//EDITAR CASO
					createDialog(EDIT_CASO);
					break;
				case 1:
					//UBICACION DETECCION CASO
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mCaso!=null) arguments.putSerializable(Constants.CASO , mCaso);
					if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                    i = new Intent(getApplicationContext(), UbicacionCasoActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 2:
					//UBICACION INFECCION CASO
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mCaso!=null) arguments.putSerializable(Constants.CASO , mCaso);
					if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                    i = new Intent(getApplicationContext(), UbicacionInfeccionCasoActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 3:
					//INVESTIGACION CASO
					createDialog(INV_CASO);
					break;
				case 4:
					//INVESTIGACION CASO FINAL
					createDialog(INV_CASO_COMP);
					break;
				case 5:
					//TRATAMIENTO CASO
					createDialog(TRAT_CASO);
					break;
				case 6:
					//TRATAMIENTO SUPERVISADO
					if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
					if (mCaso!=null) arguments.putSerializable(Constants.CASO , mCaso);
					if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                    i = new Intent(getApplicationContext(), MenuCasoTxSupActivity.class);
                    i.putExtras(arguments);
	                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                startActivity(i);
	                finish();
					break;
				case 7:
					//SUSPENDE TRATAMIENTO
					createDialog(TRATSUSP_CASO);
					break;						
				case 8:
					//TRATAMIENTO COMPLETO CASO
					createDialog(TRATC_CASO);
					break;							
				case 9:
					//PRIMER SEGUIMIENTO CASO
					createDialog(SEG2_CASO);
					break;	
				case 10:
					//SEGUNDO SEGUIMIENTO CASO
					createDialog(SEG4_CASO);
					break;	
				case 11:
					//PERDIDA SEGUIMIENTO CASO
					createDialog(LOST_CASO);
					break;	
				case 12:
					//ELIMINAR CASO
					createDialog(ELIMINAR_CASO);
					break;					

				default:					
					break;
				}
			}
		});
		gridView.setAdapter(new MenuCasoAdapter(getApplicationContext(), R.layout.menu_item_3, menuCaso, mCaso));
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
			if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
			if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
			i = new Intent(getApplicationContext(),
                    CasosActivity.class);
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
		if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
		if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
		Intent i = new Intent(getApplicationContext(),
                CasosActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
	}
	
	
	private void createDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final DatePicker picker = new DatePicker(this);
		LayoutInflater inflater = this.getLayoutInflater();
        
		View dialogViewResultados = inflater.inflate(R.layout.result_layout, null);
        final DatePicker pickerFechaControl = (DatePicker) dialogViewResultados.findViewById(R.id.control_date_picker);
        final Spinner spinner = (Spinner) dialogViewResultados.findViewById(R.id.resultados_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.opciones_resultados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        View dialogViewRazones = inflater.inflate(R.layout.lost_layout, null);
        final Spinner spinnerRazones = (Spinner) dialogViewRazones.findViewById(R.id.reasons_spinner);
        ArrayAdapter<CharSequence> adapterRazones = ArrayAdapter.createFromResource(this,
		        R.array.opciones_razones, android.R.layout.simple_spinner_item);
        adapterRazones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRazones.setAdapter(adapterRazones);
        final TextView mMotivoLabel = (TextView) dialogViewRazones.findViewById(R.id.label_omotivo_header);
        final EditText mMotivoView = (EditText) dialogViewRazones.findViewById(R.id.omotivo_text);
        mMotivoLabel.setVisibility(View.GONE);
        mMotivoView.setVisibility(View.GONE);
        
        spinnerRazones.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            	if (position ==4 ) {
            		mMotivoLabel.setVisibility(View.VISIBLE);
            		mMotivoView.setVisibility(View.VISIBLE);
            	}
            	else {
            		mMotivoLabel.setVisibility(View.GONE);
            		mMotivoView.setVisibility(View.GONE);
                    mMotivoView.setText("");
            	}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        
        
        View dialogViewResultTx = inflater.inflate(R.layout.tx_layout, null);
        final DatePicker pickerFechaTx = (DatePicker) dialogViewResultTx.findViewById(R.id.control_date_picker);
        final Spinner spinnerTipoRes = (Spinner) dialogViewResultTx.findViewById(R.id.pruebas_spinner);
        ArrayAdapter<CharSequence> adapterTipos = ArrayAdapter.createFromResource(this,
		        R.array.opciones_pruebas, android.R.layout.simple_spinner_item);
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoRes.setAdapter(adapterTipos);
        
        View dialogViewRazonSuspTx = inflater.inflate(R.layout.txsusp_layout, null);
        final DatePicker pickerFechaSuspTx = (DatePicker) dialogViewRazonSuspTx.findViewById(R.id.control_date_picker);
        final TextView mRazonLabel = (TextView) dialogViewRazonSuspTx.findViewById(R.id.label_oreason_header);
        final EditText mRazonView = (EditText) dialogViewRazonSuspTx.findViewById(R.id.orazon_text);
        mRazonLabel.setVisibility(View.GONE);
        mRazonView.setVisibility(View.GONE);
        final Spinner spinnerRazonesSusp = (Spinner) dialogViewRazonSuspTx.findViewById(R.id.razones_spinner);
        
        spinnerRazonesSusp.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            	if (position ==3 ) {
            		mRazonLabel.setVisibility(View.VISIBLE);
                    mRazonView.setVisibility(View.VISIBLE);
            	}
            	else {
            		mRazonLabel.setVisibility(View.GONE);
                    mRazonView.setVisibility(View.GONE);
                    mRazonView.setText("");
            	}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ArrayAdapter<CharSequence> adapterRazonesSusp = ArrayAdapter.createFromResource(this,
		        R.array.opciones_razones_txsusp, android.R.layout.simple_spinner_item);
        adapterRazonesSusp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRazonesSusp.setAdapter(adapterRazonesSusp);
		
        switch(dialog){
		case EDIT_CASO:
			builder.setTitle(this.getString(R.string.confirm)+ " " + mCaso.getLocal().getName());
            builder.setMessage(getString(R.string.confirm_case_edit) + "\n" + mCaso.getCodigo());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new OpenDataActivityTask().execute(String.valueOf(OPEN_EDIT_CASO));
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
		case INV_CASO:
			if(mCaso.getInvDate()==null) {
				builder.setTitle("Ingresar inicio de investigación " + mCaso.getCodigo() + "?");
	            builder.setMessage("Ingresar fecha de inicio de investigación: ");
	            picker.setCalendarViewShown(false);
	            DateMidnight newDate = new DateMidnight(mCaso.getMxDate().getTime());
	            picker.setMinDate(mCaso.getMxDate().getTime());
	            picker.setMaxDate((new Date()).getTime());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            builder.setView(picker);
	            
			}
			else {
				builder.setTitle("Quitar investigación " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(picker);
                    new SaveInvTask().execute();
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
            
		case INV_CASO_COMP:
			if(mCaso.getInvCompDate()==null) {
				builder.setTitle("Ingresar fin de investigación " + mCaso.getCodigo() + "?");
	            builder.setMessage("Ingresar fecha de fin de investigación: ");
	            picker.setCalendarViewShown(false);
	            DateMidnight newDate = new DateMidnight(mCaso.getInvDate().getTime());
	            picker.setMinDate(mCaso.getInvDate().getTime());
	            picker.setMaxDate((new Date()).getTime());
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            builder.setView(picker);
	            
			}
			else {
				builder.setTitle("Quitar investigación " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(picker);
                    new SaveInvCompTask().execute();
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
		case TRAT_CASO:
			if(mCaso.getTx().equals("0")) {
				builder.setTitle("Marcar inicio de tratamiento " + mCaso.getCodigo() + "?");
	            pickerFechaTx.setCalendarViewShown(false);
	            DateMidnight newDate = new DateMidnight(mCaso.getMxDate().getTime());
	            pickerFechaTx.setMinDate(mCaso.getMxDate().getTime());
	            pickerFechaTx.setMaxDate((new Date()).getTime());
	            pickerFechaTx.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            builder.setView(dialogViewResultTx);
	            
			}
			else {
				builder.setTitle("Quitar inicio de tratamiento " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(pickerFechaTx);
                    if(spinnerTipoRes.getSelectedItemId()==0) {
                    	pruebausada="PDR";
                    }
                    else {
                    	pruebausada="GG";
                    }
                    new SaveTratTask().execute();
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
		case TRATC_CASO:
			if(mCaso.getTxComp().equals("0")) {
				builder.setTitle("Marcar fin de tratamiento " + mCaso.getCodigo() + "?");
	            builder.setMessage("Ingresar fecha de fin de tratamiento: ");
	            picker.setCalendarViewShown(false);
	            DateMidnight newDate = new DateMidnight(mCaso.getTxDate().getTime());
	            newDate = newDate.plusDays(13);
	            picker.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            picker.setMinDate(newDate.getMillis());
	            picker.setMaxDate((new Date()).getTime());
	            builder.setView(picker);
	            
			}
			else {
				builder.setTitle("Quitar fin de tratamiento " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(picker);
                    new SaveTratCTask().execute();
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
		case TRATSUSP_CASO:
			if(mCaso.getTxSusp().equals("0")) {
				builder.setTitle("Suspender tratamiento del caso " + mCaso.getCodigo() + "?");
	            pickerFechaSuspTx.setCalendarViewShown(false);
	            DateMidnight newDate = new DateMidnight(mCaso.getTxDate().getTime());
	            pickerFechaSuspTx.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
	            pickerFechaSuspTx.setMinDate(mCaso.getTxDate().getTime());
	            pickerFechaSuspTx.setMaxDate((new Date()).getTime());
	            builder.setView(dialogViewRazonSuspTx);
	            
			}
			else {
				builder.setTitle("Quitar suspensión de tratamiento " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(pickerFechaSuspTx);
                    if(spinnerRazonesSusp.getSelectedItemId()==0) {
                    	razonsusp="SUSPCLO1";
                    }
                    else if(spinnerRazonesSusp.getSelectedItemId()==1) {
                    	razonsusp="SUSPPRI1";
                    }
                    else if(spinnerRazonesSusp.getSelectedItemId()==2) {
                    	razonsusp="SUSPPRI2";
                    }
                    else {
                    	razonsusp="SUSPOTRO";
                    }
                    orazonsusp = mRazonView.getText().toString();
                    new SaveTratSuspTask().execute();
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
		case SEG2_CASO:
			if(mCaso.getSx().equals("0")) {
				
				builder.setTitle("Marcar primer control " + mCaso.getCodigo() + "?");
				pickerFechaControl.setCalendarViewShown(false);
				DateMidnight newDate = new DateMidnight(mCaso.getTxCompDate().getTime());
				newDate = newDate.plusDays(1);
				pickerFechaControl.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
				pickerFechaControl.setMinDate(newDate.getMillis());
	            pickerFechaControl.setMaxDate((new Date()).getTime());
	            builder.setView(dialogViewResultados);
	            
			}
			else {
				builder.setTitle("Quitar primer control " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(pickerFechaControl);
                    if(spinner.getSelectedItemId()==0) {
                    	resultado="NEG";
                    }
                    else {
                    	resultado="POS";
                    }
                    new SaveSeg2Task().execute();
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
		case SEG4_CASO:
			if(mCaso.getSxComp().equals("0")) {
				builder.setTitle("Marcar segundo control " + mCaso.getCodigo() + "?");
				pickerFechaControl.setCalendarViewShown(false);
				DateMidnight newDate = new DateMidnight(mCaso.getTxCompDate().getTime());
				newDate = newDate.plusDays(10);
				pickerFechaControl.updateDate( newDate.getYear(), newDate.getMonthOfYear()-1, newDate.getDayOfMonth());
				pickerFechaControl.setMinDate(newDate.getMillis());
	            pickerFechaControl.setMaxDate((new Date()).getTime());
	            builder.setView(dialogViewResultados);
	            
			}
			else {
				builder.setTitle("Quitar segundo control " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    resCalendar = getDateFromDatePicker(pickerFechaControl);
                    if(spinner.getSelectedItemId()==0) {
                    	resultado="NEG";
                    }
                    else {
                    	resultado="POS";
                    }
                    new SaveSeg4Task().execute();
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
		case LOST_CASO:
			if(mCaso.getLostFollowUp().equals("0")) {
				builder.setTitle("Marcar como perdida de seguimiento " + mCaso.getCodigo() + "?");
				builder.setView(dialogViewRazones);
	            
			}
			else {
				builder.setTitle("Habilitar caso " + mCaso.getCodigo() + "?");
			}
			builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if(spinnerRazones.getSelectedItemId()==0) {
                    	resultado="CD";
                    }
                    else if(spinnerRazones.getSelectedItemId()==1)  {
                    	resultado="WORK";
                    }
                    else if(spinnerRazones.getSelectedItemId()==2)  {
                    	resultado="REL";
                    }
                    else if(spinnerRazones.getSelectedItemId()==3)  {
                    	resultado="ADV";
                    }
                    else if(spinnerRazones.getSelectedItemId()==4)  {
                    	resultado="OTHER";
                    }
                    omotivolost = mMotivoView.getText().toString();
                    new SaveLostTask().execute();
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
		case ELIMINAR_CASO:
			builder.setTitle(this.getString(R.string.confirm)+ " " + mCaso.getLocal().getName());
            builder.setMessage(getString(R.string.confirm_case_delete) + "\n" + mCaso.getCodigo());
            builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new DeleteCasoTask().execute(String.valueOf(OPEN_EDIT_CASO));
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
                    case OPEN_EDIT_CASO:
        		        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
        		        arguments.putSerializable(Constants.CASO , mCaso);
        		        if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
                        i = new Intent(getApplicationContext(), CaseActivity.class);
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
  	// ************************************
  	private class SaveInvTask extends AsyncTask<String, Void, String> {
  		@Override
  		protected void onPreExecute() {
  			// before the request begins, show a progress indicator
  			showLoadingProgressDialog();
  		}

  		@Override
  		protected String doInBackground(String... values) {
  			try {
  				vcaAdapter.open();
  				if(mCaso.getInvDate()==null) {
  	  				mCaso.setInvDate(resCalendar);
  				}
  				else {
  					mCaso.setInv("0");
  	  				mCaso.setInvDate(null);
  	  				mCaso.setInvCompDate(null);
  				}
  				String estado = obtenerEstado(mCaso);
  				mCaso.setEstadocaso(estado);
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
 	// Private classes
 	// ************************************
 	private class SaveInvCompTask extends AsyncTask<String, Void, String> {
 		@Override
 		protected void onPreExecute() {
 			// before the request begins, show a progress indicator
 			showLoadingProgressDialog();
 		}

 		@Override
 		protected String doInBackground(String... values) {
 			try {
 				vcaAdapter.open();
 				if(mCaso.getInvCompDate()==null) {
 					mCaso.setInv("1");
 	  				mCaso.setInvCompDate(resCalendar);
 				}
 				else {
 					mCaso.setInv("0");
 	  				mCaso.setInvCompDate(null);
 				}
 				String estado = obtenerEstado(mCaso);
 				mCaso.setEstadocaso(estado);
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
  	// Private classes
  	// ************************************
  	private class SaveTratTask extends AsyncTask<String, Void, String> {
  		@Override
  		protected void onPreExecute() {
  			// before the request begins, show a progress indicator
  			showLoadingProgressDialog();
  		}

  		@Override
  		protected String doInBackground(String... values) {
  			try {
  				vcaAdapter.open();
  				if(mCaso.getTx().equals("0")) {
  					mCaso.setTx("1");
  	  				mCaso.setTxDate(resCalendar);
  	  				mCaso.setTxResultType(pruebausada);
  				}
  				else {
  					mCaso.setTx("0");
  	  				mCaso.setTxDate(null);
  	  				mCaso.setTxResultType(null);
  				}
  				String estado = obtenerEstado(mCaso);
  				mCaso.setEstadocaso(estado);
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
   	// Private classes
   	// ************************************
   	private class SaveTratSuspTask extends AsyncTask<String, Void, String> {
   		@Override
   		protected void onPreExecute() {
   			// before the request begins, show a progress indicator
   			showLoadingProgressDialog();
   		}

   		@Override
   		protected String doInBackground(String... values) {
   			try {
   				vcaAdapter.open();
   				if(mCaso.getTxSusp().equals("0")) {
   					mCaso.setTxSusp("1");
   	  				mCaso.setTxSuspDate(resCalendar);
   	  				mCaso.setTxSuspReason(razonsusp);
   	  				mCaso.setTxSuspOtherReason(orazonsusp);
   				}
   				else {
   					mCaso.setTxSusp("0");
   	  				mCaso.setTxSuspDate(null);
   	  				mCaso.setTxSuspReason(null);
   	  				mCaso.setTxSuspOtherReason(null);
   				}
   				String estado = obtenerEstado(mCaso);
   				mCaso.setEstadocaso(estado);
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
  	// Private classes
  	// ************************************
  	private class SaveTratCTask extends AsyncTask<String, Void, String> {
  		@Override
  		protected void onPreExecute() {
  			// before the request begins, show a progress indicator
  			showLoadingProgressDialog();
  		}

  		@Override
  		protected String doInBackground(String... values) {
  			try {
  				vcaAdapter.open();
  				if(mCaso.getTxComp().equals("0")) {
  					mCaso.setTxComp("1");
  	  				mCaso.setTxCompDate(resCalendar);
  				}
  				else {
  					mCaso.setTxComp("0");
  	  				mCaso.setTxCompDate(null);
  				}
  				String estado = obtenerEstado(mCaso);
  				mCaso.setEstadocaso(estado);
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
   	// Private classes
   	// ************************************
   	private class SaveSeg2Task extends AsyncTask<String, Void, String> {
   		@Override
   		protected void onPreExecute() {
   			// before the request begins, show a progress indicator
   			showLoadingProgressDialog();
   		}

   		@Override
   		protected String doInBackground(String... values) {
   			try {
   				vcaAdapter.open();
   				if(mCaso.getSx().equals("0")) {
   					mCaso.setSx("1");
   					mCaso.setSxResult(resultado);
   	  				mCaso.setSxDate(resCalendar);
   				}
   				else {
   					mCaso.setSx("0");
   					mCaso.setSxResult(null);
   	  				mCaso.setSxDate(null);
   				}
   				String estado = obtenerEstado(mCaso);
   				mCaso.setEstadocaso(estado);
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
   	// Private classes
   	// ************************************
   	private class SaveSeg4Task extends AsyncTask<String, Void, String> {
   		@Override
   		protected void onPreExecute() {
   			// before the request begins, show a progress indicator
   			showLoadingProgressDialog();
   		}

   		@Override
   		protected String doInBackground(String... values) {
   			try {
   				vcaAdapter.open();
   				if(mCaso.getSxComp().equals("0")) {
   					mCaso.setSxComp("1");
   					mCaso.setSxCompResult(resultado);
   	  				mCaso.setSxCompDate(resCalendar);
   				}
   				else {
   					mCaso.setSxComp("0");
   					mCaso.setSxCompResult(null);
   	  				mCaso.setSxCompDate(null);
   				}
   				String estado = obtenerEstado(mCaso);
   				mCaso.setEstadocaso(estado);
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
   	// Private classes
   	// ************************************
   	private class SaveLostTask extends AsyncTask<String, Void, String> {
   		@Override
   		protected void onPreExecute() {
   			// before the request begins, show a progress indicator
   			showLoadingProgressDialog();
   		}

   		@Override
   		protected String doInBackground(String... values) {
   			try {
   				vcaAdapter.open();
   				if(mCaso.getLostFollowUp().equals("0")) {
   					mCaso.setLostFollowUp("1");
   					mCaso.setLostFollowUpReason(resultado);
   					mCaso.setLostFollowUpOtherReason(omotivolost);
   				}
   				else {
   					mCaso.setLostFollowUp("0");
   					mCaso.setLostFollowUpReason(null);
   					mCaso.setLostFollowUpOtherReason(null);
   				}
   				String estado = obtenerEstado(mCaso);
   				mCaso.setEstadocaso(estado);
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
                MenuCasoActivity.class);
		i.putExtras(arguments);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
  	}
  	
 // ***************************************
    // Private classes
    // ***************************************
    private class DeleteCasoTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
            	vcaAdapter.open();
            	vcaAdapter.eliminarCaso(mCaso);
            	vcaAdapter.close();
   	            FileUtils.createFolder(FileUtils.BACKUP_PATH);
   	            File databaseFile = new File(FileUtils.DATABASE_PATH + "/" +MainDBConstants.DATABASE_NAME);
   	            File databaseFileBackup = new File(FileUtils.BACKUP_FILE);
   	            
   	            FileUtils.copy(databaseFile, databaseFileBackup);
            	
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
        	if(resultado.equals("error")) {
     			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
     			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
     			if (vcaAdapter != null)
     				vcaAdapter.close();
     		}
      		Toast.makeText(getApplicationContext(), "Proceso finalizado...",Toast.LENGTH_LONG).show();
        	Bundle arguments = new Bundle();
            Intent i;
            
			if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
			if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
			i = new Intent(getApplicationContext(),
                    CasosActivity.class);
			i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

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
  	
  	public String obtenerEstado (Caso caso) {
    	
    	if(caso.getLostFollowUp().equals("1")) {
    		return "SEGINC";
    	}
    	else if (caso.getSxCompResult()!= null) {
    		if (caso.getSxCompResult().equals("NEG")) {
    			return "SEG4";
    		}
    		else {
    			return "SEGPOS";
    		}
    	}
    	else if (caso.getSxResult()!= null) {
    		if (caso.getSxResult().equals("NEG")) {
    			return "SEG2";
    		}
    		else {
    			return "SEGPOS";
    		}
    	}
    	else if (caso.getTxComp().equals("1")) {
    		return "TRATC";
    	}
    	else if (caso.getTx().equals("1")) {
    		return "TRAT";
    	}
    	return "CONF";
    }

}
	
