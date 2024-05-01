package org.clintonhealthaccess.vca.server.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.server.Datos;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;



public class DownloadAllTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadAllTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadAllTask.class.getSimpleName();
	private VcaAdapter vcaAdapter = null;
	List<Household> mViviendas = null;
	List<Person> mPersonas = null;
	List<IrsSeason> mTemporadas = null;
	List<Target> mMetas = null;
	List<Visit> mVisitas = null;
	List<Supervision> mSupervisiones = null;
	
	public static final String CONECTAR = "1";
	public static final String DESCARGAR = "2";
	public static final String ABRIRBD = "3";
	public static final String CERRARBD = "4";
	private static final String TOTAL_TASK = "4";

	private String error = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private int v =0;

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];
		
		publishProgress("Conectando al servidor...",CONECTAR,TOTAL_TASK);
		
		try {
			error = descargarDatos();
			if (error!=null) return error;
		} catch (Exception e) {
			// Regresa error al descargar
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
		
		publishProgress("Abriendo base de datos...",ABRIRBD,TOTAL_TASK);
		vcaAdapter = new VcaAdapter(mContext, password, false,false);
		vcaAdapter.open();
		//Borrar los datos de la base de datos
		vcaAdapter.borrarHouseholds();
		vcaAdapter.borrarPersonas();
		vcaAdapter.borrarTemporadas();
		vcaAdapter.borrarTargets();
		vcaAdapter.borrarVisits();
		vcaAdapter.borrarSupervisiones();
		
		try {
			if (mViviendas != null){
				v = mViviendas.size();
				ListIterator<Household> iter = mViviendas.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearHousehold(iter.next());
					publishProgress("Insertando viviendas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mPersonas != null){
				v = mPersonas.size();
				ListIterator<Person> iter = mPersonas.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearPersona(iter.next());
					publishProgress("Insertando personas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mTemporadas != null){
				v = mTemporadas.size();
				ListIterator<IrsSeason> iter = mTemporadas.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearTemporada(iter.next());
					publishProgress("Insertando temporadas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			if (mMetas != null){
				v = mMetas.size();
				ListIterator<Target> iter = mMetas.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearTarget(iter.next());
					publishProgress("Insertando metas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			if (mVisitas != null){
				v = mVisitas.size();
				ListIterator<Visit> iter = mVisitas.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearVisit(iter.next());
					publishProgress("Insertando visitas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			if (mSupervisiones != null){
				v = mSupervisiones.size();
				ListIterator<Supervision> iter = mSupervisiones.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearSupervision(iter.next());
					publishProgress("Insertando supervisiones en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			

		} catch (Exception e) {
			// Regresa error al insertar
			e.printStackTrace();
			vcaAdapter.close();
			return e.getLocalizedMessage();
		}
		
		publishProgress("Cerrando base de datos...",CERRARBD,TOTAL_TASK);
		vcaAdapter.close();
		return error;
	}

    // url, username, password
    protected String descargarDatos() throws Exception {
        try {
            // The URL for making the GET request
            String urlRequest;
            // Set the Accept header for "application/json"
            HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
            acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(acceptableMediaTypes);
            requestHeaders.setAuthorization(authHeader);
            // Populate the headers in an HttpEntity object to use for the request
            HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
            //Descargar catalogos
            urlRequest = url + "/movil/datos";
            publishProgress("Solicitando datos",DESCARGAR,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Datos> responseEntityCatalogos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Datos.class);
            
            mViviendas = responseEntityCatalogos.getBody().getViviendas();
            mPersonas = responseEntityCatalogos.getBody().getPersonas();
            mTemporadas = responseEntityCatalogos.getBody().getTemporadas();
            mMetas = responseEntityCatalogos.getBody().getMetas();
            mVisitas = responseEntityCatalogos.getBody().getVisitas();
            mSupervisiones = responseEntityCatalogos.getBody().getSupervisiones();
            
            responseEntityCatalogos = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
}
