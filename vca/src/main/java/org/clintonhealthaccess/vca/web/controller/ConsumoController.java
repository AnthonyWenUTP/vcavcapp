package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.IrsSeasonService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.BrigadaService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.PersonalService;
import org.clintonhealthaccess.vca.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * Controlador web de peticiones
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/irs/consumo/*")
public class ConsumoController {
	private static final Logger logger = LoggerFactory.getLogger(ConsumoController.class);
	@Resource(name="temporadaService")
	private IrsSeasonService temporadaService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="visitService")
	private VisitService visitService;
	@Resource(name="personalService")
	private PersonalService personalService;
	@Resource(name="brigadaService")
	private BrigadaService brigadaService;

    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
		logger.debug("Mostrando Consumos en JSP");
		List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	List<MessageResource> tiposvisita = messageResourceService.getCatalogo("CAT_VIS_TYPE");
    	model.addAttribute("tiposvisita",tiposvisita);
    	List<MessageResource> visitacompletasn = messageResourceService.getCatalogo("CAT_SINO");
    	model.addAttribute("visitacompletasn",visitacompletasn);
    	List<IrsSeason> temporadas = this.temporadaService.getIrsSeasons();
    	model.addAttribute("temporadas",temporadas);
    	return "irs/consumo";
	}
    
    
    
	
}
