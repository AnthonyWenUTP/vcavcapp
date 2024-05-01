package org.clintonhealthaccess.vca.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.Foco;
import org.clintonhealthaccess.vca.domain.PoligonCriadero;
import org.clintonhealthaccess.vca.domain.PoligonFoco;
import org.clintonhealthaccess.vca.domain.Punto;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.clintonhealthaccess.vca.domain.PuntosFoco;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.movil.controller.DatosMapa;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.CasoService;
import org.clintonhealthaccess.vca.service.CriaderoService;
import org.clintonhealthaccess.vca.service.DashboardMap1Service;
import org.clintonhealthaccess.vca.service.FocoService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.ParametroService;
import org.clintonhealthaccess.vca.service.PuntoDiagnosticoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view/maps/*")
public class Mapas1Controller {
	
	@Resource(name="dashboardMap1Service")
	private DashboardMap1Service dashboardMap1Service;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="puntoDiagnosticoService")
	private PuntoDiagnosticoService puntoDiagnosticoService;
	@Resource(name="criaderoService")
	private CriaderoService criaderoService;
	@Resource(name="casoService")
	private CasoService casoService;
	@Resource(name="focoService")
	private FocoService focoService;
	@Resource(name="parametroService")
	private ParametroService parametroService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
    private static final Logger logger = LoggerFactory.getLogger(Mapas1Controller.class);

    @RequestMapping(value = "/pordia/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerCasosxDia(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "estado", required = true) String estado,
    		@RequestParam(value = "tiempo", required = true) String tiempo,
    		@RequestParam(value = "fecMuestraRange", required = false, defaultValue = "") String fecMuestraRange
    		) throws ParseException {
        logger.info("Obteniendo casos por dia");
        Long desde = null;
        Long hasta = null;
        
        if (!fecMuestraRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecMuestraRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecMuestraRange.substring(fecMuestraRange.length()-10, fecMuestraRange.length())).getTime();
        }
        List<Object> datos = dashboardMap1Service.getDatosCasosxFecha(area,district,foci,localidad, desde, hasta,tiempo,SecurityContextHolder.getContext().getAuthentication().getName(),estado);
        return datos;
    }
    
    @RequestMapping(value = "/porou/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerCasosxOU(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "estado", required = true) String estado,
    		@RequestParam(value = "tipoou", required = true) String tipoou,
    		@RequestParam(value = "fecMuestraRange", required = false, defaultValue = "") String fecMuestraRange
    		) throws ParseException {
        logger.info("Obteniendo casos por unidad organizativa");
        Long desde = null;
        Long hasta = null;
        
        if (!fecMuestraRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecMuestraRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecMuestraRange.substring(fecMuestraRange.length()-10, fecMuestraRange.length())).getTime();
        }
        List<Object> datos = dashboardMap1Service.getDatosCasosxOU(area,district,foci,localidad,desde, hasta,tipoou,SecurityContextHolder.getContext().getAuthentication().getName(),estado);
        return datos;
    }
    
    @RequestMapping(value = "/porestado/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Object> obtenerCasosxEstado(@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "tipoou", required = true) String tipoou,
    		@RequestParam(value = "fecMuestraRange", required = false, defaultValue = "") String fecMuestraRange
    		) throws ParseException {
        logger.info("Obteniendo casos por estado");
        Long desde = null;
        Long hasta = null;
        
        if (!fecMuestraRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecMuestraRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecMuestraRange.substring(fecMuestraRange.length()-10, fecMuestraRange.length())).getTime();
        }
        List<Object> datos = dashboardMap1Service.getDatosCasosxEstado(area,district,foci,localidad,desde, hasta,tipoou,SecurityContextHolder.getContext().getAuthentication().getName());
        return datos;
    }
    
    
    @RequestMapping(value = "/porubi/{mapa}/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody DatosMapa obtenerCasosxUbi(@PathVariable("mapa") String mapa,
    		@RequestParam(value = "area", required = true) String area,
    		@RequestParam(value = "district", required = true) String district,
    		@RequestParam(value = "foci", required = true) String foci,
    		@RequestParam(value = "estado", required = true) String estado,
    		@RequestParam(value = "localidad", required = true) String localidad,
    		@RequestParam(value = "fecMuestraRange", required = false, defaultValue = "") String fecMuestraRange
    		) throws ParseException {
        logger.info("Obteniendo casos");
        Long desde = null;
        Long hasta = null;
        
        if (!fecMuestraRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecMuestraRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecMuestraRange.substring(fecMuestraRange.length()-10, fecMuestraRange.length())).getTime();
        }
        List<String> estadocaso = new ArrayList<String>();
        if(estado.equals("ALL")) {
        	estadocaso.add("CONF");
            estadocaso.add("TRAT");
            estadocaso.add("TRATC");
            estadocaso.add("SEG2");
            estadocaso.add("SEG4");
            estadocaso.add("SEGPOS");
            estadocaso.add("SEGINC");
        }
        else {
        	estadocaso.add(estado);
        }
        
        List<Caso> datos = dashboardMap1Service.getDatosCasosxUbi(area,district,foci,localidad, desde, hasta,SecurityContextHolder.getContext().getAuthentication().getName(),estadocaso);
        List<PuntoDiagnostico> puntos = puntoDiagnosticoService.getActivePuntoDiagnosticos();
        for (PuntoDiagnostico punto:puntos) {
    		MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(punto.getTipo(),"CAT_TIPOPDX");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			punto.setTipo(descCatalogo);
    		}
    		mr = this.messageResourceService.getMensaje(punto.getStatus(),"CAT_ESTADOPDX");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			punto.setStatus(descCatalogo);
    		}
    	}

        
        List<PoligonFoco> poligonosFoco = new ArrayList<PoligonFoco>();
        List<Foco> focos = this.focoService.getActiveFocos();
        for(Foco foco:focos) {
        	List<PuntosFoco> puntosFoco = this.focoService.getPuntosFocos(foco.getIdent());
        	List<Punto> puntoCoordenadas = new ArrayList<Punto>();
        	for(PuntosFoco pf:puntosFoco) {
        		puntoCoordenadas.add(new Punto (pf.getLatitude(),pf.getLongitude()));
        	}
        	poligonosFoco.add(new PoligonFoco(foco.getName(),puntoCoordenadas,foco.getColor()));
        }
        
        List<PoligonCriadero> poligonosCriadero = new ArrayList<PoligonCriadero>();
        List<Criadero> criaderos = criaderoService.getActiveCriaderos();
        for(Criadero criadero:criaderos) {
        	List<PuntosCriadero> puntosCriadero = this.criaderoService.getPuntosCriaderos(criadero.getIdent());
        	List<Punto> puntoCoordenadas = new ArrayList<Punto>();
        	for(PuntosCriadero pf:puntosCriadero) {
        		puntoCoordenadas.add(new Punto (pf.getLatitude(),pf.getLongitude()));
        	}
        	String colorCriadero = "#0000FF";
        	if(criadero.getTipo().matches("PR")) {
        		colorCriadero = "#e02222";
        	}
        	else if(criadero.getTipo().matches("PT")) {
        		colorCriadero = "#ebbc21";
        	}
        	poligonosCriadero.add(new PoligonCriadero(criadero.getInfo(),puntoCoordenadas,colorCriadero));
        }
        
        DatosMapa datosMapa = new DatosMapa();
        datosMapa.setCasos(datos);
        datosMapa.setPuntoDiagnosticos(puntos);
        datosMapa.setFocos(poligonosFoco);
        datosMapa.setCriaderos(poligonosCriadero);
        
        return datosMapa;
    }
    
    
    
    /**
     * Custom handler for displaying.
     *
     * @param ident the ID to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/case/{ident}/")
    public ModelAndView showEntity(@PathVariable("ident") String ident) {
    	ModelAndView mav;
    	Caso caso = this.casoService.getCaso(ident);
    	Double latitudDef=0D;
    	Double longitudDef=0D;
    	Integer zoomDef=0;
        if(caso==null){
        	mav = new ModelAndView("403");
        }
        else{
        	try {
	        	mav = new ModelAndView("caso/viewForm");
	        	MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(caso.getEstadocaso(),"CAT_ESTADOCASO");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setEstadocaso(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(caso.getLostFollowUpReason(),"CAT_LOSTFOLLOWUP");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setLostFollowUpReason(descCatalogo);
        		}
	        	mav.addObject("caso",caso);
	        	if(parametroService.getParametroByCode("zoom")!=null) zoomDef = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitudDef = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitudDef = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	mav.addObject("latitudDef",latitudDef);
	        	mav.addObject("longitudDef",longitudDef);
	        	mav.addObject("zoomDef",zoomDef);
	        	List<AuditTrail> bitacora = auditTrailService.getBitacora(ident);
	            mav.addObject("bitacora",bitacora);
	            List<MessageResource> resultados = this.messageResourceService.getCatalogo("CAT_RES"); 
	            mav.addObject("resultados",resultados);
	            List<MessageResource> razones = this.messageResourceService.getCatalogo("CAT_LOSTFOLLOWUP"); 
	            mav.addObject("razones",razones);
	            List<MessageResource> diasTx = this.messageResourceService.getCatalogo("CAT_DIASSX"); 
	            mav.addObject("diasTx",diasTx);
	            
        	}
        	catch (Exception e) {
        		mav = new ModelAndView("505");
        		mav.addObject("errormsg","Error: " +  e.getLocalizedMessage());
        	}
        }
        return mav;
    }
    
}
