package org.clintonhealthaccess.vca.server.tasks;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.listeners.UploadListener;
import org.clintonhealthaccess.vca.server.Datos;
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

public class UploadViviendasTask extends UploadTask {
	
	private final Context mContext;

	public UploadViviendasTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadViviendasTask.class.getSimpleName();
    private static final String TOTAL_TASK = "1";
	private VcaAdapter vcaAdapter = null;
	private Datos datos = new Datos();
	private List<Household> mHouseholds = new ArrayList<Household>();
	private List<Person> mPersons = new ArrayList<Person>();
	


	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;
	public static final int VIVIENDAS = 0;
	public static final int PERSONAS = 1;
	
	
	

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
			mHouseholds = vcaAdapter.getHouseholds(filtro, null);
			mPersons = vcaAdapter.getPersonas(filtro, null);
			publishProgress("Datos completos!", "2", "2");
			if(mHouseholds.size()>0||mPersons.size()>0) {
				datos.setViviendas(mHouseholds);
				datos.setPersonas(mPersons);
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, VIVIENDAS);
				actualizarBaseDatos(Constants.STATUS_SUBMITTED, PERSONAS);
				error = cargarCenso(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, VIVIENDAS);
					actualizarBaseDatos(Constants.STATUS_NOT_SUBMITTED, PERSONAS);
					return error;
				}
			}
			else {
				return "no hay datos";
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
		if(opcion==PERSONAS){
			c = mPersons.size();
			if(c>0){
				for (Person persona : mPersons) {
					persona.setEstado(estado);
					vcaAdapter.editarPersona(persona);
					publishProgress("Actualizando personas base de datos local", Integer.valueOf(mPersons.indexOf(persona)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
	}

	
	/***************************************************/
	/********************* Household ********************/
	/***************************************************/
    // url, username, password
    protected String cargarCenso(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mHouseholds.size()>0||mPersons.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando viviendas!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/datoscenso";
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<Datos> requestEntity = 
    					new HttpEntity<Datos>(datos, requestHeaders);
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