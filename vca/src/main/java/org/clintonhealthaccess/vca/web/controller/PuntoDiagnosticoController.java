package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.PuntoDiagnosticoService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.ParametroService;
import org.clintonhealthaccess.vca.service.PtoDxVisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Controlador web de peticiones
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/puntos/*")
public class PuntoDiagnosticoController {
	private static final Logger logger = LoggerFactory.getLogger(PuntoDiagnosticoController.class);
	@Resource(name="puntoDiagnosticoService")
	private PuntoDiagnosticoService puntoDiagnosticoService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	
	@Resource(name="parametroService")
	private ParametroService parametroService;
	
	@Resource(name="ptoDxVisitService")
	private PtoDxVisitService ptoDxVisitService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando PuntoDiagnosticos en JSP");
    	List<PuntoDiagnostico> puntoDiagnosticos = puntoDiagnosticoService.getPuntoDiagnosticos();
    	if (puntoDiagnosticos == null){
        	logger.debug("Nulo");
        }
        else {
        	for (PuntoDiagnostico punto:puntoDiagnosticos) {
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
        }
    	model.addAttribute("puntoDiagnosticos", puntoDiagnosticos);
    	return "puntoDiagnostico/list";
	}
	
	@RequestMapping(value = "/map/", method = RequestMethod.GET)
    public String getEntitiesForMap(Model model) throws ParseException { 	
    	logger.debug("Mostrando PuntoDiagnosticos en JSP para mapear");
    	try {
    		Double latitud = 0D;
			Double longitud = 0D;
	    	Integer zoom = 0;
        	
        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
        	
    		List<PuntoDiagnostico> puntoDiagnosticos = puntoDiagnosticoService.getPuntoDiagnosticos();
    		for(PuntoDiagnostico loc: puntoDiagnosticos) {
    			if (loc.getLatitude()== null || loc.getLatitude()==null) {
    				puntoDiagnosticos.remove(loc);
    			}
    			if (puntoDiagnosticos.size()==0) {
    				break;
    			}
    		}
        	model.addAttribute("puntoDiagnosticos", puntoDiagnosticos);
        	model.addAttribute("latitude",latitud);
        	model.addAttribute("longitude",longitud);
        	model.addAttribute("zoom",zoom);
        	return "puntoDiagnostico/mapa";
    	}
    	catch (Exception e) {
    		model.addAttribute("errormsg",e.getLocalizedMessage());
    		return "505";
    	}
	}
	
	/**
     * Custom handler for adding.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String addEntity(Model model) {
    	Float latitudMinima=0F;
    	Float latitudMaxima=0F;
    	Float longitudMinima=0F;
    	Float longitudMaxima=0F;
    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	List<MessageResource> tipoPuntoDx = this.messageResourceService.getCatalogo("CAT_TIPOPDX"); 
    	model.addAttribute("tipoPuntoDx", tipoPuntoDx);
    	List<MessageResource> estadoPuntoDx = this.messageResourceService.getCatalogo("CAT_ESTADOPDX"); 
    	model.addAttribute("estadoPuntoDx", estadoPuntoDx);
    	if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
    	if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
    	if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
    	if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
    	model.addAttribute("latitudMinima", latitudMinima);
    	model.addAttribute("latitudMaxima", latitudMaxima);
    	model.addAttribute("longitudMinima", longitudMinima);
    	model.addAttribute("longitudMaxima", longitudMaxima);
    	return "puntoDiagnostico/enterForm";
	}
    
    /**
     * Custom handler for displaying.
     *
     * @param ident the ID to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/{ident}/")
    public ModelAndView showEntity(@PathVariable("ident") String ident) {
    	ModelAndView mav;
    	PuntoDiagnostico puntoDiagnostico = this.puntoDiagnosticoService.getPuntoDiagnostico(ident);
    	Double latitud=0D;
    	Double longitud=0D;
    	Integer zoom=0;
        if(puntoDiagnostico==null){
        	mav = new ModelAndView("403");
        }
        else{
        	try {
	        	mav = new ModelAndView("puntoDiagnostico/viewForm");
	        	MessageResource mr = null;
	    		String descCatalogo = null;
	    		mr = this.messageResourceService.getMensaje(puntoDiagnostico.getTipo(),"CAT_TIPOPDX");
	    		if(mr!=null) {
	    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
	    			puntoDiagnostico.setTipo(descCatalogo);
	    		}
	    		mr = this.messageResourceService.getMensaje(puntoDiagnostico.getStatus(),"CAT_ESTADOPDX");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			puntoDiagnostico.setStatus(descCatalogo);
        		}
	        	mav.addObject("puntoDiagnostico",puntoDiagnostico);
	        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	if(puntoDiagnostico.getLatitude()!=null) latitud = puntoDiagnostico.getLatitude();
	        	if(puntoDiagnostico.getLongitude()!=null) longitud = puntoDiagnostico.getLongitude();
	        	if(puntoDiagnostico.getZoom()!=null) zoom = puntoDiagnostico.getZoom();
	        	mav.addObject("latitude",latitud);
	        	mav.addObject("longitude",longitud);
	        	mav.addObject("zoom",zoom);
	        	List<AuditTrail> bitacora = auditTrailService.getBitacora(ident);
	            mav.addObject("bitacora",bitacora);
	            List<PtoDxVisit> visitas = ptoDxVisitService.getPtoDxVisits(ident);
	            for (PtoDxVisit visita:visitas) {
	        		mr = this.messageResourceService.getMensaje(visita.getVisitType(),"CAT_TIPOVISPDX");
	        		if(mr!=null) {
	        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
	        			visita.setVisitType(descCatalogo);
	        		}
	        	}
	            mav.addObject("visitas",visitas);
        	}
        	catch (Exception e) {
        		mav = new ModelAndView("505");
        		mav.addObject("errormsg","Error: " +  e.getLocalizedMessage());
        	}
        }
        return mav;
    }
    
    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param ident the ID to edit
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{ident}/", method = RequestMethod.GET)
	public String editEntity(@PathVariable("ident") String ident, Model model) {
		PuntoDiagnostico puntoDiagnostico = this.puntoDiagnosticoService.getPuntoDiagnostico(ident);
		if(puntoDiagnostico!=null){
			Float latitudMinima=0F;
	    	Float latitudMaxima=0F;
	    	Float longitudMinima=0F;
	    	Float longitudMaxima=0F;
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<MessageResource> tipoPuntoDx = this.messageResourceService.getCatalogo("CAT_TIPOPDX"); 
	    	model.addAttribute("tipoPuntoDx", tipoPuntoDx);
	    	List<MessageResource> estadoPuntoDx = this.messageResourceService.getCatalogo("CAT_ESTADOPDX"); 
	    	model.addAttribute("estadoPuntoDx", estadoPuntoDx);
			model.addAttribute("puntoDiagnostico",puntoDiagnostico);
			if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
			if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
			if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
			if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
	    	model.addAttribute("latitudMinima", latitudMinima);
	    	model.addAttribute("latitudMaxima", latitudMaxima);
	    	model.addAttribute("longitudMinima", longitudMinima);
	    	model.addAttribute("longitudMaxima", longitudMaxima);
			return "puntoDiagnostico/enterForm";
		}
		else{
			return "403";
		}
	}
    
    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param ident the ID to edit
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/enterLocation/{ident}/", method = RequestMethod.GET)
	public String enterLocation(@PathVariable("ident") String ident, Model model) {
		PuntoDiagnostico puntoDiagnostico = this.puntoDiagnosticoService.getPuntoDiagnostico(ident);
		if(puntoDiagnostico!=null){
			try {
				model.addAttribute("puntoDiagnostico",puntoDiagnostico);
				Double latitud = 0D;
				Double longitud = 0D;
		    	Integer zoom = 0;
		    	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	if(puntoDiagnostico.getLatitude()!=null) latitud = puntoDiagnostico.getLatitude();
	        	if(puntoDiagnostico.getLongitude()!=null) longitud = puntoDiagnostico.getLongitude();
	        	if(puntoDiagnostico.getZoom()!=null) zoom = puntoDiagnostico.getZoom();
	        	model.addAttribute("latitude",latitud);
	        	model.addAttribute("longitude",longitud);
	        	model.addAttribute("zoom",zoom);
	        	Float latitudMinima=0F;
		    	Float latitudMaxima=0F;
		    	Float longitudMinima=0F;
		    	Float longitudMaxima=0F;
		    	if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
		    	if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
		    	if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
		    	if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
		    	model.addAttribute("latitudMinima", latitudMinima);
		    	model.addAttribute("latitudMaxima", latitudMaxima);
		    	model.addAttribute("longitudMinima", longitudMinima);
		    	model.addAttribute("longitudMaxima", longitudMaxima);
				return "puntoDiagnostico/enterLocation";
			}
        	catch (Exception e) {
        		model.addAttribute("errormsg","Error: " + e.getLocalizedMessage());
        		return "505";
        	}
		}
		else{
			return "403";
		}
	}
	
    /**
     * Custom handler for saving.
     * 
     * @param ident Identificador unico
     * @param code codigo
     * @param name nombre
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processEntity( @RequestParam(value="ident", required=false, defaultValue="" ) String ident
	        , @RequestParam( value="clave", required=true ) String clave
	        , @RequestParam( value="tipo", required=true ) String tipo
	        , @RequestParam( value="local", required=true) String local
	        , @RequestParam( value="latitude", required=false, defaultValue ="" ) String latitude
	        , @RequestParam( value="longitude", required=false, defaultValue ="" ) String longitude
	        , @RequestParam( value="zoom", required=false, defaultValue ="" ) String zoom
	        , @RequestParam( value="status", required=true, defaultValue ="" ) String status
	        , @RequestParam( value="info", required=false, defaultValue ="" ) String info
	        )
	{
    	try{
    		Double latitud = null;
    		Double longitud = null;
    		Integer vista= null;
    		
    		if(!latitude.equals("")) latitud = Double.valueOf(latitude);
    		if(!longitude.equals("")) longitud = Double.valueOf(longitude);
    		if(!zoom.equals("")) vista = Integer.valueOf(zoom);
    		
    		
    		Localidad disLoc = this.localidadService.getLocal(local);
			PuntoDiagnostico puntoDiagnostico = new PuntoDiagnostico();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				puntoDiagnostico.setIdent(ident);
				puntoDiagnostico.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				puntoDiagnostico.setRecordDate(new Date());
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				puntoDiagnostico = puntoDiagnosticoService.getPuntoDiagnostico(ident);
			}
			puntoDiagnostico.setClave(clave);
			puntoDiagnostico.setLocal(disLoc);
			puntoDiagnostico.setLatitude(latitud);
			puntoDiagnostico.setLongitude(longitud);
			puntoDiagnostico.setZoom(vista);
			puntoDiagnostico.setStatus(status);
			puntoDiagnostico.setTipo(tipo);
			puntoDiagnostico.setInfo(info);
			puntoDiagnostico.setEstado('2');
			//Actualiza
			this.puntoDiagnosticoService.savePuntoDiagnostico(puntoDiagnostico);
			return createJsonResponse(puntoDiagnostico);
    	}
		catch (DataIntegrityViolationException e){
			String message = e.getMostSpecificCause().getMessage();
			Gson gson = new Gson();
		    String json = gson.toJson(message);
		    return createJsonResponse(json);
		}
		catch(Exception e){
			Gson gson = new Gson();
		    String json = gson.toJson(e.toString());
		    return createJsonResponse(json);
		}
    	
	}
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/disableEntity/{ident}/")
    public String disableEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		PuntoDiagnostico pdx = this.puntoDiagnosticoService.getPuntoDiagnostico(ident);
    	if(pdx!=null){
    		pdx.setPasive('1');
    		this.puntoDiagnosticoService.savePuntoDiagnostico(pdx);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", pdx.getClave());
    		redirecTo = "redirect:/admin/puntos/"+pdx.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for enabling.
     *
     * @param ident the ID to enable
     * @param redirectAttributes
     * @return a String
     */
    @RequestMapping("/enableEntity/{ident}/")
    public String enableEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		PuntoDiagnostico pdx = this.puntoDiagnosticoService.getPuntoDiagnostico(ident);
    	if(pdx!=null){
    		pdx.setPasive('0');
    		this.puntoDiagnosticoService.savePuntoDiagnostico(pdx);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", pdx.getClave());
    		redirecTo = "redirect:/admin/puntos/"+pdx.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
	
    private ResponseEntity<String> createJsonResponse( Object o )
	{
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/json");
	    Gson gson = new Gson();
	    String json = gson.toJson(o);
	    return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
	}
    
    
	/**
     * Custom handler for adding.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/visits/newEntity/{ident}/", method = RequestMethod.GET)
	public String addVisitEntity(Model model,@PathVariable("ident") String ident) {
    	PuntoDiagnostico puntoDiagnostico = this.puntoDiagnosticoService.getPuntoDiagnostico(ident);
    	model.addAttribute("puntoDiagnostico", puntoDiagnostico);
    	List<MessageResource> tipoVisita = this.messageResourceService.getCatalogo("CAT_TIPOVISPDX"); 
    	model.addAttribute("tipoVisita", tipoVisita);
    	return "puntoDiagnostico/enterVisitForm";
	}
    
    /**
     * Custom handler for saving.
     * 
     * @param ident Identificador unico
     * @param code codigo
     * @param name nombre
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/visits/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processVisitEntity( @RequestParam(value="ident", required=false, defaultValue="" ) String ident
			, @RequestParam( value="punto", required=true ) String punto
	        , @RequestParam( value="visitType", required=true ) String visitType
	        , @RequestParam( value="visitDate", required=true ) String visitDate
	        , @RequestParam( value="obs", required=false, defaultValue ="" ) String obs
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		Date fechaVisita =  null;
    		if (!visitDate.equals("")) fechaVisita = formatter.parse(visitDate);
    		
    		PuntoDiagnostico puntoDiagnostico = this.puntoDiagnosticoService.getPuntoDiagnostico(punto);
    		PtoDxVisit puntoDiagnosticoVisit = new PtoDxVisit();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				puntoDiagnosticoVisit.setIdent(ident);
				puntoDiagnosticoVisit.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				puntoDiagnosticoVisit.setRecordDate(new Date());
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				puntoDiagnosticoVisit = ptoDxVisitService.getPtoDxVisit(ident);
			}
			puntoDiagnosticoVisit.setPunto(puntoDiagnostico);
			puntoDiagnosticoVisit.setVisitDate(fechaVisita);
			puntoDiagnosticoVisit.setVisitType(visitType);
			puntoDiagnosticoVisit.setObs(obs);
			puntoDiagnosticoVisit.setEstado('2');
			//Actualiza
			this.ptoDxVisitService.savePtoDxVisit(puntoDiagnosticoVisit);
			puntoDiagnosticoVisit.setIdent(puntoDiagnosticoVisit.getPunto().getIdent());
			return createJsonResponse(puntoDiagnosticoVisit);
    	}
		catch (DataIntegrityViolationException e){
			String message = e.getMostSpecificCause().getMessage();
			Gson gson = new Gson();
		    String json = gson.toJson(message);
		    return createJsonResponse(json);
		}
		catch(Exception e){
			Gson gson = new Gson();
		    String json = gson.toJson(e.toString());
		    return createJsonResponse(json);
		}
    	
	}
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/visits/disableEntity/{ident}/")
    public String disableVisitEntity(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
    	PtoDxVisit pdxv = this.ptoDxVisitService.getPtoDxVisit(ident);
    	if(pdxv!=null){
    		pdxv.setPasive('1');
    		this.ptoDxVisitService.savePtoDxVisit(pdxv);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", pdxv.getPunto().getClave());
    		redirecTo = "redirect:/admin/puntos/"+pdxv.getPunto().getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/visits/editEntity/{ident}/", method = RequestMethod.GET)
	public String editVisitEntity(Model model,@PathVariable("ident") String ident) {
    	PtoDxVisit pdxv = this.ptoDxVisitService.getPtoDxVisit(ident);
    	if(pdxv!=null){
	    	model.addAttribute("pdxv", pdxv);
	    	model.addAttribute("puntoDiagnostico", pdxv.getPunto());
	    	List<MessageResource> tipoVisita = this.messageResourceService.getCatalogo("CAT_TIPOVISPDX"); 
	    	model.addAttribute("tipoVisita", tipoVisita);
	    	return "puntoDiagnostico/enterVisitForm";
    	}
    	else {
    		return "403";
    	}
	}
    

	
}
