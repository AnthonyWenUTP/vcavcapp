package org.clintonhealthaccess.vca.activities.mapeo;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.AbstractAsyncActivity;
import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Localidad;
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

public class MapaCasosActivity extends AbstractAsyncActivity {
    MapView map = null;
    IMapController mapController = null;
    AlertDialog.Builder builder;
    MyLocationNewOverlay mLocationOverlay;
    
    private VcaAdapter vcaAdapter;
	
	private List<Caso> mCasos = new ArrayList<Caso>();
	private Caso mCaso = null;
    private static Localidad mLocalidad = new Localidad();
    private TextView mLabelTitle;
    private TextView mHeaderTitle;
    private AlertDialog alertDialog;
    private String strFiltro;
   
    
    
    
        
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
		
		

		//Aca se recupera los datos de la localidad y la temporada
		mLocalidad = (Localidad) getIntent().getExtras().getSerializable(Constants.LOCALIDAD);
		roles = getIntent().getExtras().getString(Constants.ROLES);
		strFiltro = getIntent().getExtras().getString(Constants.FILTRO);
		
		mLabelTitle = (TextView) findViewById(R.id.label_localidad);
		mHeaderTitle = (TextView) findViewById(R.id.label_header);
		
		mHeaderTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                	Bundle arguments = new Bundle();
                	Intent i = new Intent(getApplicationContext(),
                			CasosActivity.class);
                	if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
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
                	 if(mLocationOverlay!=null) {
                		 mapController.setCenter(mLocationOverlay.getMyLocation());                        
                		 mapController.animateTo(mLocationOverlay.getMyLocation());
                	 }
                 }
             });*/
        
        new FetchCasesTask().execute(strFiltro);
        

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
		Intent i = new Intent(getApplicationContext(), CasosActivity.class);
		if (mLocalidad != null) arguments.putSerializable(Constants.LOCALIDAD, mLocalidad);
		arguments.putString(Constants.ROLES, roles);
		i.putExtras(arguments);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	
		// ***************************************
		// Private classes
		// ***************************************
		private class FetchCasesTask extends AsyncTask<String, Void, String> {
			@Override
			protected void onPreExecute() {
				// before the request begins, show a progress indicator
				showLoadingProgressDialog();
			}

			@Override
			protected String doInBackground(String... values) {
				String filtro = values[0];

				try {
					vcaAdapter.open();
					mCasos = vcaAdapter.getCasos(filtro, MainDBConstants.mxDate);
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
		private class FetchCaseTask extends AsyncTask<String, Void, String> {
			@Override
			protected void onPreExecute() {
				// before the request begins, show a progress indicator
				showLoadingProgressDialog();
			}

			@Override
			protected String doInBackground(String... values) {
				String strCaso = values[0];
				String filtro = MainDBConstants.codigo + " = '"+ strCaso + "'";
				
				
				try {
					vcaAdapter.open();
					mCaso = vcaAdapter.getCaso(filtro, null);
					vcaAdapter.close();
				} catch (Exception e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
					return "error";
				}
				return "exito";
			}

			protected void onPostExecute(String resultado) {
				dismissProgressDialog();
				showPreguntaAbrirCaso(resultado);
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
					mLabelTitle.setText(getString(R.string.cases));
					
					
			        if(mLocalidad.getLatitude()!=null && mLocalidad.getLongitude()!=null) {
						mapController.setCenter(new GeoPoint(mLocalidad.getLatitude(),mLocalidad.getLongitude()));
					}
			        
			       
			        
			        List<IGeoPoint> points = new ArrayList<>();
					for(Caso caso:mCasos) {
			        	if(caso.getLatitude()!=null && caso.getLongitude()!= null) {
			        		points.add(new LabelledGeoPoint(caso.getLatitude(),caso.getLongitude()
			        			, caso.getCodigo()));
			        	}
			        }

			        // wrap them in a theme
			        SimplePointTheme pt = new SimplePointTheme(points, true);

			        // create label style
			        Paint textStyle = new Paint();
			        textStyle.setStyle(Paint.Style.FILL);
			        textStyle.setColor(Color.parseColor("#000000"));
			        textStyle.setTextAlign(Paint.Align.CENTER);
			        textStyle.setTextSize(28);

			        // set some visual options for the overlay
			        // we use here MAXIMUM_OPTIMIZATION algorithm, which works well with >100k points
			        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
			        		.setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
			        		.setRadius(15).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);

			        // create the overlay with the theme
			        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

			        // onClick callback
			        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
			        	@Override
			        	public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
			        		new FetchCaseTask().execute(((LabelledGeoPoint) points.get(point)).getLabel());
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
		
		
		private void showPreguntaAbrirCaso(String resultado) {
			if(resultado.equals("exito")) {			
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(this.getString(R.string.confirm)+ " " + mLocalidad.getName());
                builder.setMessage(getString(R.string.confirm_case_edit) + "\n" + mCaso.getCodigo()+ "\n" + mCaso.getNombre());
                builder.setPositiveButton(this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                        if (mLocalidad!=null) arguments.putSerializable(Constants.LOCALIDAD , mLocalidad);
                		if (mCaso!=null) arguments.putSerializable(Constants.CASO , mCaso);
                		arguments.putString(Constants.ROLES, roles);
                		Intent i = new Intent(getApplicationContext(),
                				MenuCasoActivity.class);
                		i.putExtras(arguments);
                		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                		startActivity(i);
                		finish();
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
		
	
}
