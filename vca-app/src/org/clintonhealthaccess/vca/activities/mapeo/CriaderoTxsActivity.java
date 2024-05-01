package org.clintonhealthaccess.vca.activities.mapeo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncListActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.FileUtils;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.mapeo.CriaderoTxActivity;
import org.clintonhealthaccess.vca.adapters.CriaderoTxAdapter;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class CriaderoTxsActivity extends AbstractAsyncListActivity implements AdapterView.OnItemSelectedListener{
	

	private VcaAdapter vcaAdapter;
	private List<CriaderoTx> mCriadroTx = new ArrayList<CriaderoTx>();
    private CriaderoTx txcriadero = new CriaderoTx();
    private TextView mLabelTitle;
    private TextView mLabelHeader;
    private static Criadero mCriadero = new Criadero();
    
	private EditText mParametroView;
    private ImageButton mFindButton;
    private Button mAddButton;
    private String roles;
    
    private AlertDialog alertDialog;
    private static final int ADD_TRATCRIADERO = 1;
    private static final int DELETE_TRATCRIADERO = 2;
    private static final int OPEN_ADD_TRATCRIADERO = 3;
    
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selec_case_list);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		
		
		//Aca se recupera los datos de la localidad
		mCriadero = (Criadero) getIntent().getExtras().getSerializable(Constants.CRIADERO);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mLabelHeader = (TextView) findViewById(R.id.label_header);
		
		mLabelHeader.setVisibility(View.GONE);


		
		Spinner spinner = (Spinner) findViewById(R.id.opciones_spinner);
		spinner.setVisibility(View.GONE);
		
		Spinner spinner2 = (Spinner) findViewById(R.id.opciones_spinner_2);
		spinner2.setVisibility(View.GONE);
		
		
		
		mParametroView = (EditText) findViewById(R.id.parametro);
		mParametroView.setVisibility(View.GONE);
		
		
		mAddButton = (Button) findViewById(R.id.add_button);
		mAddButton.setText(getString(R.string.txcriad_add));
		mAddButton.setOnClickListener(new View.OnClickListener()  {
			@Override
			public void onClick(View v) {
				createDialog(ADD_TRATCRIADERO);
			}
		});
		
		mFindButton = (ImageButton) findViewById(R.id.find_button);
		mFindButton.setVisibility(View.GONE);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		new FetchPointVisitsTask().execute(mCriadero.getIdent());
		
	}
	
	
	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		txcriadero = (CriaderoTx) getListAdapter().getItem(position);
		createDialog(DELETE_TRATCRIADERO);
	}
	
	
	private void createDialog(final int accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(accion){
        	case ADD_TRATCRIADERO:
        		builder.setTitle(this.getString(R.string.confirm));
                builder.setMessage(getString(R.string.confirm_txcriad_add)+ "\n" + mCriadero.getInfo());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new OpenDataActivityTask().execute(String.valueOf(OPEN_ADD_TRATCRIADERO));
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
            case DELETE_TRATCRIADERO:
                builder.setTitle(this.getString(R.string.confirm)+ " " + mCriadero.getInfo());
                builder.setMessage(getString(R.string.confirm_txcriad_delete));
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new DeleteCriaderoTxTask().execute();
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
		arguments.putSerializable(Constants.LOCALIDAD , mCriadero.getLocal());
		arguments.putSerializable(Constants.CRIADERO , mCriadero);
		arguments.putString(Constants.ROLES, roles);
		Intent i = new Intent(getApplicationContext(),
				MenuCriaderoActivity.class);
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
			arguments.putSerializable(Constants.LOCALIDAD , mCriadero.getLocal());
			arguments.putSerializable(Constants.CRIADERO , mCriadero);
			arguments.putString(Constants.ROLES, roles);
			Intent i = new Intent(getApplicationContext(),
					MenuCriaderoActivity.class);
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
	private class FetchPointVisitsTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// before the request begins, show a progress indicator
			showLoadingProgressDialog();
		}	

		@Override
		protected String doInBackground(String... values) {
			String criadero = values[0];
			
			try {
				vcaAdapter.open();		
				String strFriltro= MainDBConstants.criadero + " = '"+ criadero +"'";
				mCriadroTx = vcaAdapter.getCriaderoTxs(strFriltro, MainDBConstants.txDate);
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
			if(mCriadero==null) {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			}else {
				mLabelTitle.setTextColor(Color.BLUE);
				mLabelTitle.setText(getString(R.string.visitsPx)+": "+mCriadero.getInfo());
			}
			CriaderoTxAdapter adapter = new CriaderoTxAdapter(this, R.layout.household_list_item, mCriadroTx);
			setListAdapter(adapter);
			if (mCriadroTx.isEmpty()) {
				Toast.makeText(getApplicationContext(), getString(R.string.no_items),Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), String.valueOf(mCriadroTx.size()) + " puntos",Toast.LENGTH_SHORT).show();
			}
		}
		else {
			Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
			Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
			if (vcaAdapter != null)
                vcaAdapter.close(); 
			finish();
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
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
                    case OPEN_ADD_TRATCRIADERO:
        		        if (mCriadero!=null) arguments.putSerializable(Constants.CRIADERO , mCriadero);
        		        if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
        		        arguments.putSerializable(Constants.TRATCRIADERO , new CriaderoTx());
                        i = new Intent(getApplicationContext(), CriaderoTxActivity.class);
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
    // ***************************************
    private class DeleteCriaderoTxTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // before the request begins, show a progress indicator
            showLoadingProgressDialog();
        }

        @Override
        protected String doInBackground(String... values) {
            try {
            	vcaAdapter.open();
            	vcaAdapter.eliminarCriaderoTx(txcriadero);
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
            if (mCriadero!=null) arguments.putSerializable(Constants.CRIADERO , mCriadero);
            if (mCriadero != null) arguments.putSerializable(Constants.LOCALIDAD, mCriadero.getLocal());
			if (roles!=null) arguments.putSerializable(Constants.ROLES , roles);
			i = new Intent(getApplicationContext(),
                    CriaderoTxsActivity.class);
			i.putExtras(arguments);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

    }
	

}
