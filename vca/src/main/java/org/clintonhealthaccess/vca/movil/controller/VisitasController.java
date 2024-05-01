package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Arrays;
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
public class VisitasController {

    private static final Logger logger = LoggerFactory.getLogger(VisitasController.class);

    @Resource(name = "visitService")
    private VisitService visitService;

    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Visit
     * @return String con el resultado
     */
    @RequestMapping(value = "visitas", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveVisitas(@RequestBody Visit[] envio) {
        logger.debug("Insertando/Actualizando formularios visitas");
        if (envio == null){
            logger.debug("Nulo");
            return "No recibi nada!";
        }
        else{
            List<Visit> visitas = Arrays.asList(envio);
            for (Visit visita : visitas){
            	visitService.saveVisit(visita);
            }
        }
        return "Datos recibidos!";
    }

}
