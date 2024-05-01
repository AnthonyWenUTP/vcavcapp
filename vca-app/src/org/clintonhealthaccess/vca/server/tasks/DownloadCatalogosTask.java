package org.clintonhealthaccess.vca.server.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.MessageResource;
import org.clintonhealthaccess.vca.server.Catalogos;
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



public class DownloadCatalogosTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadCatalogosTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadCatalogosTask.class.getSimpleName();
	private VcaAdapter vcaAdapter = null;
	private List<MessageResource> mCatalogos = null;
	private List<Censador> mCensadores = null;
	private List<Personal> mPersonal = null;
	private List<Brigada> mBrigadas = null;
	
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
			error = descargarCatalogos();
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
		vcaAdapter.borrarMessageResource();
		vcaAdapter.borrarCensadores();
		vcaAdapter.borrarPersonal();
		vcaAdapter.borrarBrigadaes();
        
		try {
			if (mCatalogos != null){
				v = mCatalogos.size();
				ListIterator<MessageResource> iter = mCatalogos.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearMessageResource(iter.next());
					publishProgress("Insertando catalogos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			if (mCensadores != null){
				v = mCensadores.size();
				ListIterator<Censador> iter = mCensadores.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearCensador(iter.next());
					publishProgress("Insertando censadores en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			if (mPersonal != null){
				v = mPersonal.size();
				ListIterator<Personal> iter = mPersonal.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearPersonal(iter.next());
					publishProgress("Insertando personal en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			
			if (mBrigadas != null){
				v = mBrigadas.size();
				ListIterator<Brigada> iter = mBrigadas.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearBrigada(iter.next());
					publishProgress("Insertando brigadas en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
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
    protected String descargarCatalogos() throws Exception {
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
            urlRequest = url + "/movil/catalogos";
            publishProgress("Solicitando catalogos",DESCARGAR,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<Catalogos> responseEntityCatalogos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		Catalogos.class);
            
            mCatalogos = responseEntityCatalogos.getBody().getCatalogos();
            mCensadores = responseEntityCatalogos.getBody().getCensadores();
            mPersonal = responseEntityCatalogos.getBody().getPersonalIrs();
            mBrigadas = responseEntityCatalogos.getBody().getBrigadas();
            
            responseEntityCatalogos = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
}
