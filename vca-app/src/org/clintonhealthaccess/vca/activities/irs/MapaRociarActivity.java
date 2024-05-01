package org.clintonhealthaccess.vca.activities.irs;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.MainActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.activities.enterdata.irs.SprayActivityRociado;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.preferences.PreferencesActivity;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MapaRociarActivity extends AbstractAsyncActivity {
    MapView map = null;
    IMapController mapController = null;
    AlertDialog.Builder builder;
    MyLocationNewOverlay mLocationOverlay;
    
    private VcaAdapter vcaAdapter;
	
	private List<Target> mTargets = new ArrayList<Target>();
	private Target mTarget = null;
    private static Localidad mLocalidad = new Localidad();
	private static IrsSeason mTemporada = new IrsSeason();
    private TextView mLabelTitle;
    private TextView mHeaderTitle;
    private AlertDialog alertDialog;
    private int opcionSeleccionada;
    
    private String defaultRociador;
    private SharedPreferences settings;
    
    private static final int OPEN_ADD_PREAVISO = 11;
    
    private String roles;
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //handle permissions first, before map is created. not depicted here

        //load/initialize the osmdroid configuration, this can be done 
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        setContentView(R.layout.mapa);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		String mPass = ((VcaApplication) this.getApplication()).getPassApp();
		vcaAdapter = new VcaAdapter(this.getApplicationContext(),mPass,false,false);
		
		
		settings =
				PreferenceManager.getDefaultSharedPreferences(this);
		
		defaultRociador =
				settings.getString(PreferencesActivity.KEY_CODE_ROCIADOR,
						null);

		//Aca se recupera los datos de la localidad y la temporada
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		mTemporada = (IrsSeason) getIntent().getExtras().getSerializable(Constants.TEMPORADA);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		opcionSeleccionada = getIntent().getExtras().getInt("Cuales casas");
		
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mHeaderTitle = (TextView) findViewById(R.id.label_header);
		
		mHeaderTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                	Bundle arguments = new Bundle();
                	Intent i = new Intent(getApplicationContext(),
                			RociadoRociarActivity.class);
                	if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
					if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
					arguments.putString(Constants.ROLES, roles);
					i.putExtras(arguments);
                	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                	startActivity(i);
                	finish();
                } catch (Exception except) {
                    
                }
            }
        });
		
		

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        
        mapController = map.getController();
        mapController.setZoom(18.0);
        
        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx),map);
        mLocationOverlay.enableMyLocation();
        map.getOverlays().add(mLocationOverlay);
        
        /*mLocationOverlay.runOnFirstFix(new Runnable() {
                 public void run() {

                	 mapController.setCenter(mLocationOverlay.getMyLocation());                        
                     mapController.animateTo(mLocationOverlay.getMyLocation());
                 }
             });*/
        
        new FetchHouseholdsLocalidadTask().execute(mLocalidad.getIdent(),mTemporada.getIdent(),defaultRociador,"",String.valueOf(opcionSeleccionada));
        

    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use 
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use 
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }
    
	@Override
	public void onBackPressed (){
		finish();
		Bundle arguments = new Bundle();
		Intent i = new Intent(getApplicationContext(), RociadoRociarActivity.class);
		if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
		if (mTemporada != null) arguments.putSerializable(Constants.TEMPORADA, mTemporada);
		arguments.putString(Constants.ROLES, roles);
		i.putExtras(arguments);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	
		// ***************************************
		// Private classes
		// ***************************************
		private class FetchHouseholdsLocalidadTask extends AsyncTask<String, Void, String> {
			@Override
			protected void onPreExecute() {
				// before the request begins, show a progress indicator
				showLoadingProgressDialog();
			}

			@Override
			protected String doInBackground(String... values) {
				String strLocalidad = values[0];
				String strTemporada = values[1];
				String rociador = values[2];
				String filtro = values[3];
				String opcion = values[4];
				try {
					vcaAdapter.open();
					if(opcion.equals("0")) {
						mTargets = vcaAdapter.getTargetsRociar(strTemporada,strLocalidad,rociador,filtro);
					}
					else if(opcion.equals("1")) {
						mTargets = vcaAdapter.getTargetsRociar2(strTemporada,strLocalidad,filtro);
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
		// Private classes
		// ***************************************
		private class FetchHouseTask extends AsyncTask<String, Void, String> {
			@Override
			protected void onPreExecute() {
				// before the request begins, show a progress indicator
				showLoadingProgressDialog();
			}

			@Override
			protected String doInBackground(String... values) {
				Household mCasa = null;
				String strVivienda = values[0];
				String codigo[] = strVivienda.split("&");
				String filtro = MainDBConstants.code + " = '"+ codigo[0] + "' and " + MainDBConstants.ownerName + " = '"+ codigo[1]+ "'";
				
				
				try {
					vcaAdapter.open();
					mCasa = vcaAdapter.getHousehold(filtro, null);
					String filtroMeta = MainDBConstants.irsSeason + " = '"+ mTemporada.getIdent() + "' and " + MainDBConstants.household + " = '"+ mCasa.getIdent() + "'";
					mTarget = vcaAdapter.getTarget(filtroMeta, null);
					vcaAdapter.close();
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					return "error";
				}
				return "exito";
			}

			protected void onPostExecute(String resultado) {
				dismissProgressDialog();
				showPreguntaAbrirCasa(resultado);
			}

		}

		// ***************************************
		// Private methods
		// ***************************************
		private void showResult(String resultado) {
			if(resultado.equals("exito")) {			
				if(mLocalidad==null) {
					Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
				}else {
					
					mLabelTitle.setTextColor(Color.BLUE);
					mLabelTitle.setText(getString(R.string.localidad)+":"+mLocalidad.getName());
					
					
			        if(mLocalidad.getLatitude()!=null && mLocalidad.getLongitude()!=null) {
						mapController.setCenter(new GeoPoint(mLocalidad.getLatitude(),mLocalidad.getLongitude()));
					}
			        
			       
			        
			        List<IGeoPoint> points = new ArrayList<>();
					for(Target casa:mTargets) {
			        	if(casa.getHousehold().getLatitude()!=null && casa.getHousehold().getLongitude()!= null) {
			        		points.add(new LabelledGeoPoint(casa.getHousehold().getLatitude(),casa.getHousehold().getLongitude()
			        			, casa.getHousehold().getCode() + "&" + casa.getHousehold().getOwnerName()));
			        	}
			        }

			        // wrap them in a theme
			        SimplePointTheme pt = new SimplePointTheme(points, true);

			        // create label style
			        Paint textStyle = new Paint();
			        textStyle.setStyle(Paint.Style.FILL);
			        textStyle.setColor(Color.parseColor("#0000ff"));
			        textStyle.setTextAlign(Paint.Align.CENTER);
			        textStyle.setTextSize(28);

			        // set some visual options for the overlay
			        // we use here MAXIMUM_OPTIMIZATION algorithm, which works well with >100k points
			        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
			        		.setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
			        		.setRadius(10).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);

			        // create the overlay with the theme
			        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

			        // onClick callback
			        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
			        	@Override
			        	public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
			        		new FetchHouseTask().execute(((LabelledGeoPoint) points.get(point)).getLabel());
			        	}
			        });

			        // add overlay
			        map.getOverlays().add(sfpo);
					
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
		
		
		private void showPreguntaAbrirCasa(String resultado) {
			if(resultado.equals("exito")) {			
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(this.getString(R.string.confirm)+ " " + mTarget.getHousehold().getLocal().getName());
                builder.setMessage("Agregar visita de rociado a la vivienda? \n" + mTarget.getHousehold().getCode()+ "\n" + mTarget.getHousehold().getOwnerName());
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
                alertDialog = builder.create();
                alertDialog.show();
			}
			else {
				Toast.makeText(getApplicationContext(), resultado,Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), getString(R.string.bd_error),Toast.LENGTH_LONG).show();
				if (vcaAdapter != null)
	                vcaAdapter.close(); 
				finish();
			}
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
