package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaVisita;
import org.clintonhealthaccess.vca.domain.mtilds.Evaluacion;
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionMosquitero;
import org.clintonhealthaccess.vca.service.HouseholdService;
import org.clintonhealthaccess.vca.service.mtilds.CicloService;
import org.clintonhealthaccess.vca.service.mtilds.EntregaTargetService;
import org.clintonhealthaccess.vca.service.mtilds.EntregaVisitaService;
import org.clintonhealthaccess.vca.service.mtilds.EvaluacionMosquiteroService;
import org.clintonhealthaccess.vca.service.mtilds.EvaluacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class DatosMtildController {

    private static final Logger logger = LoggerFactory.getLogger(DatosMtildController.class);

    @Resource(name = "householdService")
    private HouseholdService householdService;
    
    @Resource(name = "cicloService")
    private CicloService cicloService;
    
    @Resource(name = "entregaTargetService")
    private EntregaTargetService entregaTargetService;
    
    @Resource(name = "entregaVisitaService")
    private EntregaVisitaService entregaVisitaService;
    
    @Resource(name = "evaluacionService")
    private EvaluacionService evaluacionService;
    
    @Resource(name = "evaluacionMosquiteroService")
    private EvaluacionMosquiteroService evaluacionMosquiteroService;
    
    

    /**
     * Retorna datos. Acepta una solicitud GET para JSON
     * @return Datos JSON
     */
    @RequestMapping(value = "datosmtild", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    DatosMtild getDatos(){
        
        logger.info("Descargando toda la informacion de los datos temporadas");
        List<Ciclo> temporadas = cicloService.getActiveCiclos();
        if (temporadas == null){
        	logger.debug(new Date() + " - Temporadas - Nulo");
        }
        
        logger.info("Descargando toda la informacion de los datos vivienda");
        List<Household> viviendas = householdService.getHousesMosquiteros(null, null, null, null,"ALL", "ALL", "ALL",SecurityContextHolder.getContext().getAuthentication().getName(), "0");
        if (viviendas == null){
        	logger.debug(new Date() + " - Viviendas - Nulo");
        }
        
        logger.info("Descargando toda la informacion de los datos de targets");
        List<EntregaTarget> targets = entregaTargetService.getMetasMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (targets == null){
        	logger.debug(new Date() + " - Targets - Nulo");
        }
        
        logger.info("Descargando toda la informacion de los datos de visitas");
        List<EntregaVisita> visits = entregaVisitaService.getVisitasMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (visits == null){
        	logger.debug(new Date() + " - Visits - Nulo");
        }
        
        logger.info("Descargando toda la informacion de los datos de evaluaciones");
        List<Evaluacion> evals = evaluacionService.getEvaluacionesMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (evals == null){
        	logger.debug(new Date() + " - Evaluaciones - Nulo");
        }
        
        logger.info("Descargando toda la informacion de los datos de evaluaciones de mosquiteros");
        List<EvaluacionMosquitero> evalMosqs = evaluacionMosquiteroService.getEvaluacionMosquiterosMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (evalMosqs == null){
        	logger.debug(new Date() + " - Evaluaciones Mosquiteros - Nulo");
        }
        
        //Crea la clase DatosMtild
        DatosMtild datos = new DatosMtild();
        datos.setViviendas(viviendas);
        datos.setTemporadas(temporadas);
        datos.setTargets(targets);
        datos.setVisits(visits);
        datos.setEvals(evals);
        datos.setEvalMosquiteros(evalMosqs);
        return  datos;
    }
    
    
    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Household
     * @return String con el resultado
     */
    @RequestMapping(value = "datosmtild", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveDatosMtild(@RequestBody DatosMtild envio) {
        logger.debug("Insertando/Actualizando formularios viviendas");
        if (envio == null){
            logger.debug("Nulo");
            return "No recibi nada!";
        }
        else{
        	if (envio.getViviendas() != null){
	            for (Household vivienda : envio.getViviendas()){
	            	householdService.saveVivienda(vivienda);
	            }
        	}
        	if (envio.getTargets() != null){
	            for (EntregaTarget target : envio.getTargets()){
	            	entregaTargetService.saveMeta(target);
	            }
        	}
        	if (envio.getVisits() != null){
	            for (EntregaVisita visit : envio.getVisits()){
	            	entregaVisitaService.saveVisita(visit);
	            }
        	}
        	if (envio.getEvals() != null){
	            for (Evaluacion eval : envio.getEvals()){
	            	evaluacionService.saveEvaluacion(eval);
	            }
        	}
        	if (envio.getEvalMosquiteros() != null){
	            for (EvaluacionMosquitero evalMosq : envio.getEvalMosquiteros()){
	            	evaluacionMosquiteroService.saveEvaluacionMosquitero(evalMosq);
	            }
        	}
            
        }
        return "Datos recibidos!";
    }

}
