package org.clintonhealthaccess.vca.server.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.clintonhealthaccess.vca.database.VcaAdapter;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.clintonhealthaccess.vca.server.DatosMapeo;
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



public class DownloadDatosMapeoTask extends DownloadTask {
	
	private final Context mContext;
	
	public DownloadDatosMapeoTask(Context context) {
		mContext = context;
	}
	
	protected static final String TAG = DownloadDatosMapeoTask.class.getSimpleName();
	private VcaAdapter vcaAdapter = null;
	List<Caso> mCasos = null;
	List<Muestra> mMuestras = null;
	List<PuntoDiagnostico> mPuntos = null;
	List<PtoDxVisit> mVisitaspdx= null;
	List<Criadero> mCriaderos = null;
	List<CriaderoTx> mCriaderoTxs = null;
	List<PuntosCriadero> mPuntosCriaderos = null;
	
	
	
	public static final String CONECTAR = "1";
	public static final String DESCARGAR = "2";
	public static final String ABRIRBD = "3";
	public static final String CERRARBD = "4";
	private static final String TOTAL_TASK = "5";

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
		vcaAdapter.borrarCasos();
		vcaAdapter.borrarMuestras();
		vcaAdapter.borrarPuntoDiagnosticos();
		vcaAdapter.borrarVisPuntoDiagnosticos();
		vcaAdapter.borrarCriaderos();
		vcaAdapter.borrarCriaderoTxs();
		vcaAdapter.borrarPuntosCriaderos();
		
		try {
			if (mCasos != null){
				v = mCasos.size();
				ListIterator<Caso> iter = mCasos.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearCaso(iter.next());
					publishProgress("Insertando casos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mMuestras != null){
				v = mMuestras.size();
				ListIterator<Muestra> iter = mMuestras.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearMuestra(iter.next());
					publishProgress("Insertando muestras en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mPuntos != null){
				v = mPuntos.size();
				ListIterator<PuntoDiagnostico> iter = mPuntos.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearPuntoDiagnostico(iter.next());
					publishProgress("Insertando puntos dx en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mVisitaspdx != null){
				v = mVisitaspdx.size();
				ListIterator<PtoDxVisit> iter = mVisitaspdx.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearVisPuntoDiagnostico(iter.next());
					publishProgress("Insertando visitas puntos dx en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mCriaderos != null){
				v = mCriaderos.size();
				ListIterator<Criadero> iter = mCriaderos.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearCriadero(iter.next());
					publishProgress("Insertando criaderos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mCriaderoTxs != null){
				v = mCriaderoTxs.size();
				ListIterator<CriaderoTx> iter = mCriaderoTxs.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearCriaderoTx(iter.next());
					publishProgress("Insertando tratamiento de criaderos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
							.valueOf(v).toString());
				}
			}
			if (mPuntosCriaderos != null){
				v = mPuntosCriaderos.size();
				ListIterator<PuntosCriadero> iter = mPuntosCriaderos.listIterator();
				while (iter.hasNext()){
					vcaAdapter.crearPuntosCriadero(iter.next());
					publishProgress("Insertando puntos de criaderos en la base de datos...", Integer.valueOf(iter.nextIndex()).toString(), Integer
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
            urlRequest = url + "/movil/datosmapeo";
            publishProgress("Solicitando datos",DESCARGAR,TOTAL_TASK);
            // Perform the HTTP GET request
            ResponseEntity<DatosMapeo> responseEntityCatalogos = restTemplate.exchange(urlRequest, HttpMethod.GET, requestEntity,
            		DatosMapeo.class);
            
            mCasos = responseEntityCatalogos.getBody().getCasos();
            mMuestras = responseEntityCatalogos.getBody().getMuestras();
            mPuntos = responseEntityCatalogos.getBody().getPuntosdx();
            mCriaderos = responseEntityCatalogos.getBody().getCriaderos();
            mVisitaspdx= responseEntityCatalogos.getBody().getVisitaspdx();
            mCriaderoTxs=responseEntityCatalogos.getBody().getTxscriadero();
            mPuntosCriaderos = responseEntityCatalogos.getBody().getPuntoscriadero();
            responseEntityCatalogos = null;
            return null;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return e.getLocalizedMessage();
        }
    }
    
}
