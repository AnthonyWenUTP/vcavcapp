package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.clintonhealthaccess.vca.service.CasoService;
import org.clintonhealthaccess.vca.service.CriaderoService;
import org.clintonhealthaccess.vca.service.CriaderoTxService;
import org.clintonhealthaccess.vca.service.MuestraService;
import org.clintonhealthaccess.vca.service.PtoDxVisitService;
import org.clintonhealthaccess.vca.service.PuntoDiagnosticoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * Controlador que provee los mapeos de la aplicacion movil para:
 * 
 * <ul>
 * <li>Pedir los datos de la aplicacion
 * </ul>
 * 
 * @author William Aviles
 **/
@Controller
@RequestMapping("/movil/*")
public class DatosMapeoController {

    private static final Logger logger = LoggerFactory.getLogger(DatosMapeoController.class);

    @Resource(name = "casoService")
    private CasoService casoService;
    
    @Resource(name = "muestraService")
    private MuestraService muestraService;
    
    @Resource(name = "criaderoService")
    private CriaderoService criaderoService;
    
    @Resource(name = "criaderoTxService")
    private CriaderoTxService criaderoTxService;
    
    @Resource(name = "puntoDiagnosticoService")
    private PuntoDiagnosticoService puntoDiagnosticoService;
    
    @Resource(name = "ptoDxVisitService")
    private PtoDxVisitService ptoDxVisitService;
    
    

    /**
     * Retorna datos. Acepta una solicitud GET para JSON
     * @return Datos JSON
     */
    @RequestMapping(value = "datosmapeo", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    DatosMapeo getDatos(){
        
        logger.info("Descargando toda la informacion de los casos");
        List<Caso> casos = casoService.getActiveCasosMovil();
        if (casos == null){
        	logger.debug(new Date() + " - Casos - Nulo");
        }
        
        List<Muestra> muestras = muestraService.getActiveMuestrasMovil();
        if (muestras == null){
        	logger.debug(new Date() + " - Muestras - Nulo");
        }
        
        List<PuntoDiagnostico> puntosdx = puntoDiagnosticoService.getActivePuntoDiagnosticosMovil();
        if (puntosdx == null){
        	logger.debug(new Date() + " - Puntos Dx - Nulo");
        }
        
        List<PtoDxVisit> visitspdx = ptoDxVisitService.getActivePtoDxVisitsMovil();
        if (visitspdx == null){
        	logger.debug(new Date() + " - Visitas Puntos Dx - Nulo");
        }
        
        List<Criadero> criaderos = criaderoService.getActiveCriaderosMovil();
        if (criaderos == null){
        	logger.debug(new Date() + " - Criaderos - Nulo");
        }
        
        List<CriaderoTx> criaderostxs = criaderoTxService.getActiveCriaderoTxsMovil();
        if (criaderostxs == null){
        	logger.debug(new Date() + " - Criaderos Tx - Nulo");
        }
        
        List<PuntosCriadero> criaderospuntos = criaderoService.getActivePuntosCriaderosMovil();
        if (criaderospuntos == null){
        	logger.debug(new Date() + " - Puntos Criaderos - Nulo");
        }
        
        
        //Crea la clase DatosMapeo
        DatosMapeo datos = new DatosMapeo();
        datos.setCasos(casos);
        datos.setMuestras(muestras);
        datos.setPuntosdx(puntosdx);
        datos.setCriaderos(criaderos);
        datos.setTxscriadero(criaderostxs);
        datos.setVisitaspdx(visitspdx);
        datos.setPuntoscriadero(criaderospuntos);
        return  datos;
    }
    
    
    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Caso
     * @return String con el resultado
     */
    @RequestMapping(value = "datosmapeo", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveDatosMtild(@RequestBody DatosMapeo envio) {
        logger.debug("Insertando/Actualizando formularios casos");
        try{
	        if (envio == null){
	            logger.debug("Nulo");
	            return "No recibi nada!";
	        }
	        else{
	        	if (envio.getCasos() != null){
		            for (Caso caso : envio.getCasos()){
		            	casoService.saveCaso(caso);;
		            }
	        	}
	        	if (envio.getMuestras() != null){
		            for (Muestra muestra : envio.getMuestras()){
		            	muestraService.saveMuestra(muestra);
		            }
	        	}
	        	if (envio.getPuntosdx() != null){
		            for (PuntoDiagnostico pto : envio.getPuntosdx()){
		            	puntoDiagnosticoService.savePuntoDiagnostico(pto);
		            }
	        	}
	        	if (envio.getVisitaspdx() != null){
		            for (PtoDxVisit vis : envio.getVisitaspdx()){
		            	ptoDxVisitService.savePtoDxVisit(vis);
		            }
	        	}
	        	if (envio.getCriaderos() != null){
		            for (Criadero criadero : envio.getCriaderos()){
		            	criaderoService.saveCriadero(criadero);
		            }
	        	}
	        	
	        	if (envio.getTxscriadero() != null){
		            for (CriaderoTx txcriad : envio.getTxscriadero()){
		            	criaderoTxService.saveCriaderoTx(txcriad);
		            }
	        	}
	        	
	        	
	        	if (envio.getPuntoscriadero() != null){
		            for (PuntosCriadero ptocriad : envio.getPuntoscriadero()){
		            	criaderoService.savePuntosCriadero(ptocriad);
		            }
	        	}
	        }
	        return "Datos recibidos!";
        }
		catch (DataIntegrityViolationException e){
			String message = e.getMostSpecificCause().getMessage();
		    return message;
		}
		catch(Exception e){
		    return e.toString();
		}
    }

}
