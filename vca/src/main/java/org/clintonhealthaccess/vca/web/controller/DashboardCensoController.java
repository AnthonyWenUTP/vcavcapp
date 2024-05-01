package org.clintonhealthaccess.vca.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.DashboardCensoService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/view/censo/*")
public class DashboardCensoController {
	
	@Resource(name="dashboardCensoService")
	private DashboardCensoService dashboardCensoService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
    private static final Logger logger = LoggerFactory.getLogger(DashboardCensoController.class);

    @RequestMapping(value = "/pordia/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerViviendasxDia(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "censustaker", required = true) String censustaker,
    		@RequestParam(value = "tiempo", required = true) String tiempo,
    		@RequestParam(value = "fecCensoRange", required = false, defaultValue = "") String fecCensoRange
    		) throws ParseException {
        logger.info("Obteniendo viviendas por dia de censo");
        Long desde = null;
        Long hasta = null;
        
        if (!fecCensoRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecCensoRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecCensoRange.substring(fecCensoRange.length()-10, fecCensoRange.length())).getTime();
        }
        List<Object> datos = dashboardCensoService.getDatosHouseholdxFecha(area,district,foci,localidad,censustaker, desde, hasta,tiempo,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    @RequestMapping(value = "/porou/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerViviendasxOU(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "censustaker", required = true) String censustaker,
    		@RequestParam(value = "tipoou", required = true) String tipoou,
    		@RequestParam(value = "fecCensoRange", required = false, defaultValue = "") String fecCensoRange
    		) throws ParseException {
        logger.info("Obteniendo viviendas por unidad organizativa");
        Long desde = null;
        Long hasta = null;
        
        if (!fecCensoRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecCensoRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecCensoRange.substring(fecCensoRange.length()-10, fecCensoRange.length())).getTime();
        }
        List<Object> datos = dashboardCensoService.getDatosHouseholdxOU(area,district,foci,localidad,censustaker, desde, hasta,tipoou,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    
    @RequestMapping(value = "/porubi/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Household> obtenerViviendasxUbi(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "censustaker", required = true) String censustaker,
    		@RequestParam(value = "fecCensoRange", required = false, defaultValue = "") String fecCensoRange
    		) throws ParseException {
        logger.info("Obteniendo viviendas");
        Long desde = null;
        Long hasta = null;
        
        if (!fecCensoRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecCensoRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecCensoRange.substring(fecCensoRange.length()-10, fecCensoRange.length())).getTime();
        }
        List<Household> datos = dashboardCensoService.getDatosHouseholdxUbi(area,district,foci,localidad,censustaker, desde, hasta,SecurityContextHolder.getContext().getAuthentication().getName());
        
        return datos;
    }
    
    
    @RequestMapping(value = "/pormat/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object[]> obtenerViviendasxMat(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "censustaker", required = true) String censustaker,
    		@RequestParam(value = "fecCensoRange", required = false, defaultValue = "") String fecCensoRange
    		) throws ParseException {
        logger.info("Obteniendo viviendas por material");
        Long desde = null;
        Long hasta = null;
        
        if (!fecCensoRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecCensoRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecCensoRange.substring(fecCensoRange.length()-10, fecCensoRange.length())).getTime();
        }
        List<Object[]> datos = dashboardCensoService.getDatosHouseholdxMat(area,district,foci,localidad,censustaker, desde, hasta, SecurityContextHolder.getContext().getAuthentication().getName());
        
        MessageResource mr = null;
		String descCatalogo = null;
		
		for(Object[] dato: datos) {
			if(dato[0]!=null) {
				mr = this.messageResourceService.getMensaje(dato[0].toString(),"CAT_MAT");
				if(mr!=null) {
					descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
					dato[0]=descCatalogo;
				}
			}
		}
		
        
        return datos;
    }
}
