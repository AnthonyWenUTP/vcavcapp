package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.service.TargetService;
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
public class MetasController {

    private static final Logger logger = LoggerFactory.getLogger(MetasController.class);

    @Resource(name = "targetService")
    private TargetService targetService;

    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Target
     * @return String con el resultado
     */
    @RequestMapping(value = "metas", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveMetas(@RequestBody Target[] envio) {
        logger.debug("Insertando/Actualizando formularios metas");
        if (envio == null){
            logger.debug("Nulo");
            return "No recibi nada!";
        }
        else{
            List<Target> metas = Arrays.asList(envio);
            for (Target meta : metas){
            	targetService.saveMeta(meta);
            }
        }
        return "Datos recibidos!";
    }

}
