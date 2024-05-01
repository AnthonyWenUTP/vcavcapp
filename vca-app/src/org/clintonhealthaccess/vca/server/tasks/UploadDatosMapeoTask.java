package org.clintonhealthaccess.vca.server.tasks;

import java.util.ArrayList;
import java.util.List;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.clintonhealthaccess.vca.listeners.UploadListener;
import org.clintonhealthaccess.vca.server.DatosMapeo;
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

public class UploadDatosMapeoTask extends UploadTask {
	
	private final Context mContext;

	public UploadDatosMapeoTask(Context context) {
		mContext = context;
	}

	protected static final String TAG = UploadDatosMapeoTask.class.getSimpleName();
    private static final String TOTAL_TASK = "1";
	private VcaAdapter vcaAdapter = null;
	private DatosMapeo datos = new DatosMapeo();
	private List<Caso> mCasos = new ArrayList<Caso>();
	private List<Muestra> mMuestras = new ArrayList<Muestra>();
	private List<PuntoDiagnostico> mPuntosDx = new ArrayList<PuntoDiagnostico>();
	private List<Criadero> mCriaderos = new ArrayList<Criadero>();
	private List<PtoDxVisit> mPtoDxVisits = new ArrayList<PtoDxVisit>();
	private List<CriaderoTx> mCriaderoTxs = new ArrayList<CriaderoTx>();
	private List<PuntosCriadero> mPuntosCriaderos = new ArrayList<PuntosCriadero>();
	
	


	private String url = null;
	private String username = null;
	private String password = null;
	private String error = null;
	protected UploadListener mStateListener;
	public static final int CASOS = 0;
	public static final int MUESTRAS = 1;
	public static final int PUNTOS = 2;
	public static final int CRIADEROS = 3;
	public static final int VISITAS_PTOS = 4;
	public static final int TRAT_CRIADEROS = 5;
	public static final int PUNTOS_CRIADEROS = 6;
	
	

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
			mCasos = vcaAdapter.getCasos(filtro, null);
			mMuestras = vcaAdapter.getMuestras(filtro, null);
			mPuntosDx = vcaAdapter.getPuntosDiagnosticos(filtro, null);
			mCriaderos = vcaAdapter.getCriaderos(filtro, null);
			mPtoDxVisits = vcaAdapter.getVisPuntosDiagnosticos(filtro, null);
			mCriaderoTxs = vcaAdapter.getCriaderoTxs(filtro, null);
			mPuntosCriaderos = vcaAdapter.getPuntosCriaderos(filtro, null);
			publishProgress("Datos completos!", "2", "2");
			if(mCasos.size()>0||mMuestras.size()>0||mPuntosDx.size()>0||mCriaderos.size()>0||mPtoDxVisits.size()>0||mCriaderoTxs.size()>0||mPuntosCriaderos.size()>0) {
				datos.setCasos(mCasos);
				datos.setMuestras(mMuestras);
				datos.setPuntosdx(mPuntosDx);
				datos.setCriaderos(mCriaderos);
				datos.setVisitaspdx(mPtoDxVisits);
				datos.setTxscriadero(mCriaderoTxs);
				datos.setPuntoscriadero(mPuntosCriaderos);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, CASOS);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, MUESTRAS);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, PUNTOS);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, CRIADEROS);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, VISITAS_PTOS);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, TRAT_CRIADEROS);
				actualizarBaseDatosMapeo(Constants.STATUS_SUBMITTED, PUNTOS_CRIADEROS);
				error = cargarDatos(url, username, password);
				if (!error.matches("Datos recibidos!")){
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, CASOS);
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, MUESTRAS);
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, PUNTOS);
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, CRIADEROS);
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, VISITAS_PTOS);
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, TRAT_CRIADEROS);
					actualizarBaseDatosMapeo(Constants.STATUS_NOT_SUBMITTED, PUNTOS_CRIADEROS);
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
	
	private void actualizarBaseDatosMapeo(char estado, int opcion) {
		int c;
		if(opcion==CASOS){
			c = mCasos.size();
			if(c>0){
				for (Caso caso : mCasos) {
					caso.setEstado(estado);
					vcaAdapter.editarCaso(caso);
					publishProgress("Actualizando casos base de datos local", Integer.valueOf(mCasos.indexOf(caso)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==MUESTRAS){
			c = mMuestras.size();
			if(c>0){
				for (Muestra muestra : mMuestras) {
					muestra.setEstado(estado);
					vcaAdapter.editarMuestra(muestra);
					publishProgress("Actualizando muestras base de datos local", Integer.valueOf(mMuestras.indexOf(muestra)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==PUNTOS){
			c = mPuntosDx.size();
			if(c>0){
				for (PuntoDiagnostico punto : mPuntosDx) {
					punto.setEstado(estado);
					vcaAdapter.editarPuntoDiagnostico(punto);
					publishProgress("Actualizando puntos base de datos local", Integer.valueOf(mPuntosDx.indexOf(punto)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==VISITAS_PTOS){
			c = mPtoDxVisits.size();
			if(c>0){
				for (PtoDxVisit visitapto : mPtoDxVisits) {
					visitapto.setEstado(estado);
					vcaAdapter.editarVisPuntoDiagnostico(visitapto);
					publishProgress("Actualizando visitas de puntos base de datos local", Integer.valueOf(mPtoDxVisits.indexOf(visitapto)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==CRIADEROS){
			c = mCriaderos.size();
			if(c>0){
				for (Criadero criadero : mCriaderos) {
					criadero.setEstado(estado);
					vcaAdapter.editarCriadero(criadero);
					publishProgress("Actualizando criaderos base de datos local", Integer.valueOf(mCriaderos.indexOf(criadero)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==TRAT_CRIADEROS){
			c = mCriaderoTxs.size();
			if(c>0){
				for (CriaderoTx txcriadero : mCriaderoTxs) {
					txcriadero.setEstado(estado);
					vcaAdapter.editarCriaderoTx(txcriadero);
					publishProgress("Actualizando tratamiento de criadero base de datos local", Integer.valueOf(mCriaderoTxs.indexOf(txcriadero)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
		if(opcion==PUNTOS_CRIADEROS){
			c = mPuntosCriaderos.size();
			if(c>0){
				for (PuntosCriadero ptocriadero : mPuntosCriaderos) {
					ptocriadero.setEstado(estado);
					vcaAdapter.editarPuntosCriadero(ptocriadero);
					publishProgress("Actualizando punto de criadero base de datos local", Integer.valueOf(mPuntosCriaderos.indexOf(ptocriadero)).toString(), Integer
							.valueOf(c).toString());
				}
			}
		}
	}

	
	/***************************************************/
	/********************* Household ********************/
	/***************************************************/
    // url, username, password
    protected String cargarDatos(String url, String username, 
    		String password) throws Exception {
    	try {
    		if(mCasos.size()>0||mMuestras.size()>0||mPuntosDx.size()>0||mCriaderos.size()>0||mPtoDxVisits.size()>0||mCriaderoTxs.size()>0||mPuntosCriaderos.size()>0){
    			// La URL de la solicitud POST
    			publishProgress("Enviando datos mapeo!", "1", TOTAL_TASK);
    			final String urlRequest = url + "/movil/datosmapeo";
    			HttpHeaders requestHeaders = new HttpHeaders();
    			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
    			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    			requestHeaders.setAuthorization(authHeader);
    			HttpEntity<DatosMapeo> requestEntity = 
    					new HttpEntity<DatosMapeo>(datos, requestHeaders);
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