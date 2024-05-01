package org.clintonhealthaccess.vca.server.tasks;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.listeners.UploadListener;
import org.clintonhealthaccess.vca.utils.Constants;
import org.clintonhealthaccess.vca.utils.MainDBConstants;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.util.Log;

public class UploadVisitasTask extends UploadTask {
	
	private final Context mContext;

	public UploadVisitasTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadVisitasTask.class.getSimpleName();
    private static final String TOTAL_TASK = "1";
	private VcaAdapter vcaAdapter = null;
	private List<Household> mHouseholds = new ArrayList<Household>();
	private List<Visit> mVisitas = new ArrayList<Visit>();
	private List<Target> mMetas = new ArrayList<Target>();
	private List<Supervision> mSupervisiones = new ArrayList<Supervision>();
	


	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;
	public static final int VISITAS = 0;
	public static final int METAS = 1;
	public static final int SUPERVISIONES = 2;
	public static final int VIVIENDAS = 3;
	
	
	

	@Override
	protected String doInBackground(String... values) {
		url = values[0];
		username = values[1];
		password = values[2];

		try {
			publishProgress("Obteniendo registros de la base de datos", "1", "2");
			vcaAdapter = new VcaAdapter(mContext, password, false,false);
			vcaAdapter.open();
			String filtro = MainDBConstants.estado + "='" + Constants.STATUS_NOT_SUBMITTED + "'";
			mVisitas = vcaAdapter.getVisits(filtro, null);
			mMetas = vcaAdapter.getTargets(filtro, null);
			mSupervisiones = vcaAdapter.getSupervisiones(filtro, null);
			mHouseholds = vcaAdapter.getHouseholds(filtro, null);
			publishProgress("Datos completos!", "2", "2");
			if(mVisitas.size()==0 && mMetas.size()==0 && mSupervisiones.size()==0 && mHouseholds.size()==0) {
				vcaAdapter.close();
				return "no hay datos";
			}
			if(mVisitas.size()>0) {
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, VISITAS);
				error = cargarVisitas(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VISITAS);
					return error;
				}
			}
			if(mMetas.size()>0) {
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, METAS);
				error = cargarMetas(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, METAS);
					return error;
				}
			}
			if(mSupervisiones.size()>0) {
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, SUPERVISIONES);
				error = cargarSupervisiones(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, SUPERVISIONES);
					return error;
				}
			}
			if(mHouseholds.size()>0) {
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, VIVIENDAS);
				error = cargarHouseholds(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VIVIENDAS);
					return error;
				}
			}
			vcaAdapter.close();
		} catch (Exception e1) {
			vcaAdapter.close();
			e1.printStackTrace();
			return e1.getLocalizedMessage();
		}
		return error;
	}
	
	private void actualizarBaseDatos(char estado, int opcion) {
		int c;
		if(opcion==VISITAS){
			c = mVisitas.size();
			if(c>0){
				for (Visit visita : mVisitas) {
					visita.setEstado(estado);
					vcaAdapter.editarVisit(visita);
					publishProgress("Actualizando visitas base de datos local", Integer.valueOf(mVisitas.indexOf(visita)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==METAS){
			c = mMetas.size();
			if(c>0){
				for (Target meta : mMetas) {
					meta.setEstado(estado);
					vcaAdapter.editarTarget(meta);
					publishProgress("Actualizando metas base de datos local", Integer.valueOf(mMetas.indexOf(meta)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		
		if(opcion==SUPERVISIONES){
			c = mSupervisiones.size();
			if(c>0){
				for (Supervision supervision : mSupervisiones) {
					supervision.setEstado(estado);
					vcaAdapter.editarSupervision(supervision);
					publishProgress("Actualizando supervisiones base de datos local", Integer.valueOf(mSupervisiones.indexOf(supervision)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		
		if(opcion==VIVIENDAS){
			c = mHouseholds.size();
			if(c>0){
				for (Household vivienda : mHouseholds) {
					vivienda.setEstado(estado);
					vcaAdapter.editarHousehold(vivienda);
					publishProgress("Actualizando viviendas base de datos local", Integer.valueOf(mHouseholds.indexOf(vivienda)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
	}

	
	/***************************************************/
	/********************* Visita ********************/
	/***************************************************/
    // url, username, password
    protected String cargarVisitas(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mVisitas.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando visitas!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/visitas";
    			Visit[] envio = mVisitas.toArray(new Visit[mVisitas.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Visit[]> requestEntity = 
    					new HttpEntity<Visit[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la visita y espera un mensaje de respuesta del servidor
    					ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
    							String.class);
    					return response.getBody();
    		}
    		else{
    			return "Datos recibidos!";
    		}
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
    		return e.getMessage();
    	}
    }
    
    
    /***************************************************/
	/********************* Meta ********************/
	/***************************************************/
    // url, username, password
    protected String cargarMetas(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mMetas.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando metas!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/metas";
    			Target[] envio = mMetas.toArray(new Target[mMetas.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Target[]> requestEntity = 
    					new HttpEntity<Target[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la visita y espera un mensaje de respuesta del servidor
    					ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
    							String.class);
    					return response.getBody();
    		}
    		else{
    			return "Datos recibidos!";
    		}
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
    		return e.getMessage();
    	}
    }
    
    
    /***************************************************/
	/********************* Supervision ********************/
	/***************************************************/
    // url, username, password
    protected String cargarSupervisiones(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mSupervisiones.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando supervisiones!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/supervisiones";
    			Supervision[] envio = mSupervisiones.toArray(new Supervision[mSupervisiones.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Supervision[]> requestEntity = 
    					new HttpEntity<Supervision[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la visita y espera un mensaje de respuesta del servidor
    					ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
    							String.class);
    					return response.getBody();
    		}
    		else{
    			return "Datos recibidos!";
    		}
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
    		return e.getMessage();
    	}
    }
    
    /***************************************************/
	/********************* Household ********************/
	/***************************************************/
    // url, username, password
    protected String cargarHouseholds(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mHouseholds.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando viviendas!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/viviendas";
    			Household[] envio = mHouseholds.toArray(new Household[mHouseholds.size()]);
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Household[]> requestEntity = 
    					new HttpEntity<Household[]>(envio, requestHeaders);
    					RestTemplate restTemplate = new RestTemplate();
    					restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    					// Hace la solicitud a la red, pone la vivienda y espera un mensaje de respuesta del servidor
    					ResponseEntity<String> response = restTemplate.exchange(urlRequest, HttpMethod.POST, requestEntity,
    							String.class);
    					return response.getBody();
    		}
    		else{
    			return "Datos recibidos!";
    		}
    	} catch (Exception e) {
    		Log.e(TAG, e.getMessage(), e);
    		return e.getMessage();
    	}
    }
}