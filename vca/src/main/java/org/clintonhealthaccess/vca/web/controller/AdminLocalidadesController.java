package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Distrito;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.service.DistritoService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.ParametroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Controlador web de peticiones
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/localities/*")
public class AdminLocalidadesController {
	private static final Logger logger = LoggerFactory.getLogger(AdminLocalidadesController.class);
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="distritoService")
	private DistritoService distritoService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	
	@Resource(name="parametroService")
	private ParametroService parametroService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando Localidads en JSP");
    	List<Localidad> localidades = localidadService.getLocalities();
    	model.addAttribute("localidades", localidades);
    	return "admin/localidades/list";
	}
	
	@RequestMapping(value = "/map/", method = RequestMethod.GET)
    public String getEntitiesForMap(Model model) throws ParseException { 	
    	logger.debug("Mostrando Localidades en JSP para mapear");
    	try {
    		Double latitud = 0D;
			Double longitud = 0D;
	    	Integer zoom = 0;
        	
        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
        	
    		List<Localidad> localidades = localidadService.getLocalities();
    		for(Localidad loc: localidades) {
    			if (loc.getLatitude()== null || loc.getLatitude()==null) {
    				localidades.remove(loc);
    			}
    		}
        	model.addAttribute("localidades", localidades);
        	model.addAttribute("latitude",latitud);
        	model.addAttribute("longitude",longitud);
        	model.addAttribute("zoom",zoom);
        	return "admin/localidades/mapa";
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
    	List <Distrito> distritos = this.distritoService.getActiveDistricts();
    	model.addAttribute("distritos", distritos);
    	if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
    	if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
    	if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
    	if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
    	model.addAttribute("latitudMinima", latitudMinima);
    	model.addAttribute("latitudMaxima", latitudMaxima);
    	model.addAttribute("longitudMinima", longitudMinima);
    	model.addAttribute("longitudMaxima", longitudMaxima);
    	return "admin/localidades/enterForm";
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
    	Localidad localidad = this.localidadService.getLocal(ident);
    	Double latitud=0D;
    	Double longitud=0D;
    	Integer zoom=0;
        if(localidad==null){
        	mav = new ModelAndView("403");
        }
        else{
        	try {
	        	mav = new ModelAndView("admin/localidades/viewForm");
	        	mav.addObject("localidad",localidad);
	        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	if(localidad.getLatitude()!=null) latitud = localidad.getLatitude();
	        	if(localidad.getLongitude()!=null) longitud = localidad.getLongitude();
	        	if(localidad.getZoom()!=null) zoom = localidad.getZoom();
	        	mav.addObject("latitude",latitud);
	        	mav.addObject("longitude",longitud);
	        	mav.addObject("zoom",zoom);
	        	List<AuditTrail> bitacora = auditTrailService.getBitacora(ident);
	            mav.addObject("bitacora",bitacora);
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
		Localidad localidad = this.localidadService.getLocal(ident);
		if(localidad!=null){
			Float latitudMinima=0F;
	    	Float latitudMaxima=0F;
	    	Float longitudMinima=0F;
	    	Float longitudMaxima=0F;
			List <Distrito> distritos = this.distritoService.getActiveDistricts();
	    	model.addAttribute("distritos", distritos);
			model.addAttribute("localidad",localidad);
			if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
			if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
			if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
			if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
	    	model.addAttribute("latitudMinima", latitudMinima);
	    	model.addAttribute("latitudMaxima", latitudMaxima);
	    	model.addAttribute("longitudMinima", longitudMinima);
	    	model.addAttribute("longitudMaxima", longitudMaxima);
			return "admin/localidades/enterForm";
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
		Localidad localidad = this.localidadService.getLocal(ident);
		if(localidad!=null){
			try {
				model.addAttribute("localidad",localidad);
				Double latitud = 0D;
				Double longitud = 0D;
		    	Integer zoom = 0;
		    	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	if(localidad.getLatitude()!=null) latitud = localidad.getLatitude();
	        	if(localidad.getLongitude()!=null) longitud = localidad.getLongitude();
	        	if(localidad.getZoom()!=null) zoom = localidad.getZoom();
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
				return "admin/localidades/enterLocation";
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
	        , @RequestParam( value="code", required=true ) String code
	        , @RequestParam( value="name", required=true ) String name
	        , @RequestParam( value="district", required=true) String district
	        , @RequestParam( value="latitude", required=false, defaultValue ="" ) String latitude
	        , @RequestParam( value="longitude", required=false, defaultValue ="" ) String longitude
	        , @RequestParam( value="zoom", required=false, defaultValue ="" ) String zoom
	        , @RequestParam( value="population", required=false, defaultValue ="" ) String population
	        , @RequestParam( value="pattern", required=false, defaultValue ="" ) String pattern
	        , @RequestParam( value="obs", required=false, defaultValue ="" ) String obs
	        )
	{
    	try{
    		Double latitud = null;
    		Double longitud = null;
    		Integer vista= null;
    		Integer poblacion= null;
    		
    		if(!latitude.equals("")) latitud = Double.valueOf(latitude);
    		if(!longitude.equals("")) longitud = Double.valueOf(longitude);
    		if(!zoom.equals("")) vista = Integer.valueOf(zoom);
    		if(!population.equals("")) poblacion = Integer.valueOf(population);
    		
    		try {
                Pattern.compile(pattern);
            } catch (PatternSyntaxException exception) {
            	String message = "Regex error! .... " + exception.getDescription();
    			Gson gson = new Gson();
    		    String json = gson.toJson(message);
    		    return createJsonResponse(json);
            }
    		
    		Distrito disLoc = this.distritoService.getDistrict(district);
			Localidad localidad = new Localidad();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				localidad.setIdent(ident);
				localidad.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				localidad.setRecordDate(new Date());
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				localidad = localidadService.getLocal(ident);
			}
			localidad.setCode(code);
			localidad.setName(name);
			localidad.setDistrict(disLoc);
			localidad.setLatitude(latitud);
			localidad.setLongitude(longitud);
			localidad.setZoom(vista);
			localidad.setPopulation(poblacion);
			localidad.setPattern(pattern);
			localidad.setObs(obs);
			//Actualiza
			this.localidadService.saveLocal(localidad);
			return createJsonResponse(localidad);
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
		Localidad distrito = this.localidadService.getLocal(ident);
    	if(distrito!=null){
    		distrito.setPasive('1');
    		this.localidadService.saveLocal(distrito);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", distrito.getName());
    		redirecTo = "redirect:/admin/localities/"+distrito.getIdent()+"/";
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
		Localidad localidad = this.localidadService.getLocal(ident);
    	if(localidad!=null){
    		localidad.setPasive('0');
    		this.localidadService.saveLocal(localidad);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", localidad.getName());
    		redirecTo = "redirect:/admin/localities/"+localidad.getIdent()+"/";
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
    
    
    @RequestMapping(value = "/getMap/", method = RequestMethod.GET)
    public String fetchCohorteJSP(Model model) throws ParseException {
    	logger.debug("Mostrando Mapa en JSP");
    	return "admin/localidades/mapa";
	}  
	
}
