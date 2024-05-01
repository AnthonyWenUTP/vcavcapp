package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.BrigadaService;
import org.clintonhealthaccess.vca.service.CensadorService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * Controlador que provee los mapeos de la aplicacion movil para:
 * 
 * <ul>
 * <li>Pedir los catalogos de la aplicacion
 * </ul>
 * 
 * @author William Aviles
 **/
@Controller
@RequestMapping("/movil/*")
public class CatalogosController {

    private static final Logger logger = LoggerFactory.getLogger(CatalogosController.class);

    @Resource(name = "messageResourceService")
    private MessageResourceService messageResourceService;
    @Resource(name = "censadorService")
    private CensadorService censadorService;
    @Resource(name = "personalService")
    private PersonalService personalService;
    @Resource(name = "brigadaService")
    private BrigadaService brigadaService;

    /**
     * Retorna catalogos. Acepta una solicitud GET para JSON
     * @return Catalogo JSON
     */
    @RequestMapping(value = "catalogos", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Catalogos getCatalogos(){
        logger.info("Descargando toda la informacion de los datos de los catalogos");
        List<MessageResource> messages = messageResourceService.getCatalogos();
        if (messages == null){
        	logger.debug(new Date() + " - Mensajes - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos de los censadores");
        List<Censador> censadores = censadorService.getActiveCensadores();
        if (censadores == null){
        	logger.debug(new Date() + " - Censadores - Nulo");
        }
        logger.info("Descargando toda la informacion de los datos de las personas relacionadas con IRS");
        List<Personal> personal = personalService.getActivePersonales();
        if (personal == null){
        	logger.debug(new Date() + " - Personal - Nulo");
        }
        
        logger.info("Descargando toda la informacion de los datos de los brigadas");
        List<Brigada> brigadas = brigadaService.getActiveBrigadas();
        if (brigadas == null){
        	logger.debug(new Date() + " - Brigadas - Nulo");
        }
        
        //Crea la clase Catalogos
        Catalogos catalogos = new Catalogos();
        catalogos.setCatalogos(messages);
        catalogos.setCensadores(censadores);
        catalogos.setPersonalIrs(personal);
        catalogos.setBrigadas(brigadas);
        return  catalogos;
    }
    
    /**
     * Retorna catalogos. Acepta una solicitud GET para JSON
     * @return Catalogo JSON
     */
    @RequestMapping(value = "catalogosmapeo", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    CatalogosMapeo getCatalogosMapeo(){
        logger.info("Descargando toda la informacion de los datos de los catalogos");
        List<MessageResource> messages = messageResourceService.getCatalogos();
        if (messages == null){
        	logger.debug(new Date() + " - Mensajes - Nulo");
        }
        
        CatalogosMapeo catalogos = new CatalogosMapeo();
        catalogos.setCatalogos(messages);
        return  catalogos;
    }

}
