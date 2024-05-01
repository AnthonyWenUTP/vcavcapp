package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.service.SupervisionService;
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
public class SupervisionesController {

    private static final Logger logger = LoggerFactory.getLogger(SupervisionesController.class);

    @Resource(name = "supervisionService")
    private SupervisionService supervisionService;

    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Visit
     * @return String con el resultado
     */
    @RequestMapping(value = "supervisiones", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveVisitas(@RequestBody Supervision[] envio) {
        logger.debug("Insertando/Actualizando formularios supervisiones");
        if (envio == null){
            logger.debug("Nulo");
            return "No recibi nada!";
        }
        else{
            List<Supervision> supervisiones = Arrays.asList(envio);
            for (Supervision supervision : supervisiones){
            	supervisionService.saveSupervision(supervision);
            }
        }
        return "Datos recibidos!";
    }

}
