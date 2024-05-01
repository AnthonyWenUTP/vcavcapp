package org.clintonhealthaccess.vca.movil.controller;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.service.HouseholdService;
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
public class ViviendasController {

    private static final Logger logger = LoggerFactory.getLogger(ViviendasController.class);

    @Resource(name = "householdService")
    private HouseholdService householdService;

    /**
     * Acepta una solicitud POST con un JSON
     * @param envio Objeto serializado de Household
     * @return String con el resultado
     */
    @RequestMapping(value = "viviendas", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody String saveViviendas(@RequestBody Household[] envio) {
        logger.debug("Insertando/Actualizando formularios viviendas");
        if (envio == null){
            logger.debug("Nulo");
            return "No recibi nada!";
        }
        else{
            List<Household> viviendas = Arrays.asList(envio);
            for (Household vivienda : viviendas){
            	householdService.saveVivienda(vivienda);
            }
        }
        return "Datos recibidos!";
    }

}
