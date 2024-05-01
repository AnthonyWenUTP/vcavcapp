package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.OldHousehold;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.service.HouseholdService;
import org.clintonhealthaccess.vca.service.IrsSeasonService;
import org.clintonhealthaccess.vca.service.OldHouseholdService;
import org.clintonhealthaccess.vca.service.PersonService;
import org.clintonhealthaccess.vca.service.SupervisionService;
import org.clintonhealthaccess.vca.service.TargetService;
import org.clintonhealthaccess.vca.service.VisitService;
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
public class DatosController {

    private static final Logger logger = LoggerFactory.getLogger(DatosController.class);

    @Resource(name = "householdService")
    private HouseholdService householdService;
    @Resource(name = "oldHouseholdService")
    private OldHouseholdService oldHouseholdService;
    @Resource(name = "personService")
    private PersonService personService;
    @Resource(name = "temporadaService")
    private IrsSeasonService temporadaService;
    @Resource(name = "targetService")
    private TargetService targetService;
    @Resource(name = "visitService")
    private VisitService visitService;
    @Resource(name = "supervisionService")
    private SupervisionService supervisionService;
    

    /**
     * Retorna datos. Acepta una solicitud GET para JSON
     * @return Datos JSON
     */
    @RequestMapping(value = "datos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Datos getDatos(){
        logger.info("Descargando toda la informacion de los datos vivienda");
        List<Household> viviendas = householdService.getHousesFiltro(null, null, null, null,"ALL", "ALL", "ALL",SecurityContextHolder.getContext().getAuthentication().getName(), "0");
        if (viviendas == null){
        	logger.debug(new Date() + " - Viviendas - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos persona");
        List<Person> personas = personService.getPersonasMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (personas == null){
        	logger.debug(new Date() + " - Personas - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos temporadas");
        List<IrsSeason> temporadas = temporadaService.getActiveIrsSeasons();
        if (temporadas == null){
        	logger.debug(new Date() + " - Temporadas - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos metas");
        List<Target> metas = targetService.getMetasMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (metas == null){
        	logger.debug(new Date() + " - Metas - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos visitas");
        List<Visit> visitas = visitService.getVisitsMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (visitas == null){
        	logger.debug(new Date() + " - Visitas - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos supervisiones");
        List<Supervision> supervisiones = supervisionService.getSupervisionsMovil(SecurityContextHolder.getContext().getAuthentication().getName());
        if (supervisiones == null){ 
        	logger.debug(new Date() + " - Supervisiones - Nulo");
        }
        //Crea la clase Datos
        Datos datos = new Datos();
        datos.setViviendas(viviendas);
        datos.setPersonas(personas);
        datos.setTemporadas(temporadas);
        datos.setMetas(metas);
        datos.setVisitas(visitas);
        datos.setSupervisiones(supervisiones);
        return  datos;
    }
    
    
    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Household
     * @return String con el resultado
     */
    @RequestMapping(value = "datoscenso", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveDatosCenso(@RequestBody Datos envio) {
        logger.debug("Insertando/Actualizando formularios viviendas y personas");
        if (envio == null){
            logger.debug("Nulo");
            return "No recibi nada!";
        }
        else{
            for (Household vivienda : envio.getViviendas()){
            	householdService.saveVivienda(vivienda);
            }
            for (Person persona : envio.getPersonas()){
            	personService.savePersona(persona);
            }
        }
        return "Datos recibidos!";
    }
    
    
    /**
     * Retorna datos. Acepta una solicitud GET para JSON
     * @return Datos JSON
     */
    @RequestMapping(value = "datosviviendasanteriores", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Datos2 getDatosViviendasAnteriores(){
        logger.info("Descargando toda la informacion de los datos viviendas anteriores");
        List<OldHousehold> viviendas = oldHouseholdService.getHousesFiltro(null, null, null, null,"ALL", "ALL", "ALL",SecurityContextHolder.getContext().getAuthentication().getName(), "0");
        if (viviendas == null){
        	logger.debug(new Date() + " - Viviendas - Nulo");
        }
        
        //Crea la clase Datos
        Datos2 datos = new Datos2();
        datos.setViviendas(viviendas);
        return  datos;
    }

}
