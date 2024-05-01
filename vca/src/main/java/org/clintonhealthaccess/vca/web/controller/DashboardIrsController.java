package org.clintonhealthaccess.vca.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.service.DashboardIrsService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/view/irs/*")
public class DashboardIrsController {
	
	@Resource(name="dashboardIrsService")
	private DashboardIrsService dashboardIrsService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
    private static final Logger logger = LoggerFactory.getLogger(DashboardIrsController.class);
    
    
    @RequestMapping(value = "/estado/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerEstado(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "temporada", required = true) String temporada
    		) throws ParseException {
        logger.info("Obteniendo metas por temporada");       
        List<Object> datos = dashboardIrsService.getDatosMetas(area,district,foci,localidad,temporada,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }

    @RequestMapping(value = "/pordia/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerVisitasxDia(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "temporada", required = true) String temporada,
    		@RequestParam(value = "rociador", required = true) String rociador,
    		@RequestParam(value = "supervisor", required = true) String supervisor,
    		@RequestParam(value = "brigada", required = true) String brigada,
    		@RequestParam(value = "tiempo", required = true) String tiempo,
    		@RequestParam(value = "fecVisitaRange", required = false, defaultValue = "") String fecVisitaRange
    		) throws ParseException {
        logger.info("Obteniendo visitas por dia");
        Long desde = null;
        Long hasta = null;
        
        if (!fecVisitaRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecVisitaRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecVisitaRange.substring(fecVisitaRange.length()-10, fecVisitaRange.length())).getTime();
        }
        List<Object> datos = dashboardIrsService.getDatosVisitasxFecha(area,district,foci,localidad,temporada,rociador,supervisor, brigada, desde, hasta,tiempo,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    @RequestMapping(value = "/porou/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerViviendasxOU(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "temporada", required = true) String temporada,
    		@RequestParam(value = "rociador", required = true) String rociador,
    		@RequestParam(value = "supervisor", required = true) String supervisor,
    		@RequestParam(value = "brigada", required = true) String brigada,
    		@RequestParam(value = "tipoou", required = true) String tipoou,
    		@RequestParam(value = "fecVisitaRange", required = false, defaultValue = "") String fecVisitaRange
    		) throws ParseException {
        logger.info("Obteniendo visitas por unidad organizativa");
        Long desde = null;
        Long hasta = null;
        
        if (!fecVisitaRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecVisitaRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecVisitaRange.substring(fecVisitaRange.length()-10, fecVisitaRange.length())).getTime();
        }
        List<Object> datos = dashboardIrsService.getDatosVisitasxOU(area,district,foci,localidad,temporada,rociador,supervisor, brigada, desde, hasta,tipoou,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    
    @RequestMapping(value = "/porubi/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Target> obtenerMetasxUbi(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "temporada", required = true) String temporada
    		) throws ParseException {
        logger.info("Obteniendo visitas");
        
        List<Target> datos = dashboardIrsService.getDatosTargetxUbi(area,district,foci,localidad,temporada,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    @RequestMapping(value = "/consumo/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerConsumoxOU(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "temporada", required = true) String temporada,
    		@RequestParam(value = "rociador", required = true) String rociador,
    		@RequestParam(value = "supervisor", required = true) String supervisor,
    		@RequestParam(value = "brigada", required = true) String brigada,
    		@RequestParam(value = "tipoou", required = true) String tipoou,
    		@RequestParam(value = "fecVisitaRange", required = false, defaultValue = "") String fecVisitaRange
    		) throws ParseException {
        logger.info("Obteniendo consumos por unidad organizativa");
        Long desde = null;
        Long hasta = null;
        
        if (!fecVisitaRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecVisitaRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecVisitaRange.substring(fecVisitaRange.length()-10, fecVisitaRange.length())).getTime();
        }
        List<Object> datos = dashboardIrsService.getDatosConsumoxOU(area,district,foci,localidad,temporada,rociador,supervisor, brigada, desde, hasta,tipoou,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    @RequestMapping(value = "/consumosemana/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerConsumoxOUxSemana(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "temporada", required = true) String temporada,
    		@RequestParam(value = "rociador", required = true) String rociador,
    		@RequestParam(value = "supervisor", required = true) String supervisor,
    		@RequestParam(value = "brigada", required = true) String brigada,
    		@RequestParam(value = "tipoou", required = true) String tipoou,
    		@RequestParam(value = "fecVisitaRange", required = false, defaultValue = "") String fecVisitaRange
    		) throws ParseException {
        logger.info("Obteniendo consumos por unidad organizativa");
        Long desde = null;
        Long hasta = null;
        
        if (!fecVisitaRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecVisitaRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecVisitaRange.substring(fecVisitaRange.length()-10, fecVisitaRange.length())).getTime();
        }
        List<Object> datos = dashboardIrsService.getDatosConsumoxOUxSemana(area,district,foci,localidad,temporada,rociador,supervisor, brigada, desde, hasta,tipoou,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
}
