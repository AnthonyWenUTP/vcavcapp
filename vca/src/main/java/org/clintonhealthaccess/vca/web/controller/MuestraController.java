package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.MuestraService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
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
@RequestMapping("/admin/tests/*")
public class MuestraController {
	private static final Logger logger = LoggerFactory.getLogger(MuestraController.class);
	@Resource(name="muestraService")
	private MuestraService muestraService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	
	@Resource(name="parametroService")
	private ParametroService parametroService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando Muestras en JSP");
    	List<Muestra> muestras = muestraService.getMuestras();
    	if (muestras == null){
        	logger.debug("Nulo");
        }
    	model.addAttribute("muestras", muestras);
    	return "muestra/list";
	}
	
	@RequestMapping(value = "/map/", method = RequestMethod.GET)
    public String getEntitiesForMap(Model model) throws ParseException { 	
    	logger.debug("Mostrando Muestras en JSP para mapear");
    	try {
    		Double latitud = 0D;
			Double longitud = 0D;
	    	Integer zoom = 0;
        	
        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
        	
    		List<Muestra> muestras = muestraService.getMuestras();
    		for(Muestra loc: muestras) {
    			if (loc.getLatitude()== null || loc.getLatitude()==null) {
    				muestras.remove(loc);
    			}
    			if (muestras.size()==0) {
    				break;
    			}
    		}
        	model.addAttribute("muestras", muestras);
        	model.addAttribute("latitude",latitud);
        	model.addAttribute("longitude",longitud);
        	model.addAttribute("zoom",zoom);
        	return "muestra/mapa";
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
    	Double latitudDef=0D;
    	Double longitudDef=0D;
    	Integer zoomDef=1;
    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
    	if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
    	if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
    	if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
    	if(parametroService.getParametroByCode("zoom")!=null) zoomDef = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
    	if(parametroService.getParametroByCode("lat")!=null) latitudDef = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
    	if(parametroService.getParametroByCode("long")!=null) longitudDef = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
    	model.addAttribute("latitudMinima", latitudMinima);
    	model.addAttribute("latitudMaxima", latitudMaxima);
    	model.addAttribute("longitudMinima", longitudMinima);
    	model.addAttribute("longitudMaxima", longitudMaxima);
    	model.addAttribute("latitudDef", latitudDef);
    	model.addAttribute("longitudDef", longitudDef);
    	model.addAttribute("zoomDef", zoomDef);
    	return "muestra/enterForm";
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
    	Muestra muestra = this.muestraService.getMuestra(ident);
    	Double latitudDef=0D;
    	Double longitudDef=0D;
    	Integer zoomDef=0;
        if(muestra==null){
        	mav = new ModelAndView("403");
        }
        else{
        	try {
	        	mav = new ModelAndView("muestra/viewForm");
	        	
	        	mav.addObject("muestra",muestra);
	        	if(parametroService.getParametroByCode("zoom")!=null) zoomDef = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitudDef = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitudDef = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	mav.addObject("latitudDef",latitudDef);
	        	mav.addObject("longitudDef",longitudDef);
	        	mav.addObject("zoomDef",zoomDef);
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
		Muestra muestra = this.muestraService.getMuestra(ident);
		if(muestra!=null){
			Float latitudMinima=0F;
	    	Float latitudMaxima=0F;
	    	Float longitudMinima=0F;
	    	Float longitudMaxima=0F;
	    	Double latitudDef=0D;
	    	Double longitudDef=0D;
	    	Integer zoomDef=1;
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	model.addAttribute("muestra",muestra);
			if(parametroService.getParametroByCode("latMin")!=null) latitudMinima = Float.parseFloat(parametroService.getParametroByCode("latMin").getValue());
			if(parametroService.getParametroByCode("latMax")!=null) latitudMaxima = Float.parseFloat(parametroService.getParametroByCode("latMax").getValue());
			if(parametroService.getParametroByCode("longMin")!=null) longitudMinima = Float.parseFloat(parametroService.getParametroByCode("longMin").getValue());
			if(parametroService.getParametroByCode("longMax")!=null) longitudMaxima = Float.parseFloat(parametroService.getParametroByCode("longMax").getValue());
			if(parametroService.getParametroByCode("zoom")!=null) zoomDef = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	if(parametroService.getParametroByCode("lat")!=null) latitudDef = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	if(parametroService.getParametroByCode("long")!=null) longitudDef = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	    	model.addAttribute("latitudMinima", latitudMinima);
	    	model.addAttribute("latitudMaxima", latitudMaxima);
	    	model.addAttribute("longitudMinima", longitudMinima);
	    	model.addAttribute("longitudMaxima", longitudMaxima);
	    	model.addAttribute("latitudDef", latitudDef);
	    	model.addAttribute("longitudDef", longitudDef);
	    	model.addAttribute("zoomDef", zoomDef);
			return "muestra/enterForm";
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
	        , @RequestParam( value="local", required=true) String local
	        , @RequestParam( value="latitude", required=false, defaultValue ="" ) String latitude
	        , @RequestParam( value="longitude", required=false, defaultValue ="" ) String longitude
	        , @RequestParam( value="casa", required=true ) String casa
	        , @RequestParam( value="mxDate", required=true ) String mxDate
	        , @RequestParam( value="mxProactiva", required=true ) Integer mxProactiva
	        , @RequestParam( value="mxReactiva", required=true ) Integer mxReactiva
	        )
	{
    	try{
    		Double latitud = null;
    		Double longitud = null;
    		
    		if(!latitude.equals("")) latitud = Double.valueOf(latitude);
    		if(!longitude.equals("")) longitud = Double.valueOf(longitude);
    		
    		
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaMuestra =  null;    		
    		if (!mxDate.equals("")) fechaMuestra = formatter.parse(mxDate);
    		
    		Localidad disLoc = this.localidadService.getLocal(local);
			Muestra muestra = new Muestra();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				muestra.setIdent(ident);
				muestra.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				muestra.setRecordDate(new Date());
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				muestra = muestraService.getMuestra(ident);
			}
			muestra.setLocal(disLoc);
			muestra.setLatitude(latitud);
			muestra.setLongitude(longitud);
			muestra.setMxDate(fechaMuestra);
			muestra.setCasa(casa);
			muestra.setMxProactiva(mxProactiva);
			muestra.setMxReactiva(mxReactiva);
			muestra.setEstado('2');
			//Actualiza
			this.muestraService.saveMuestra(muestra);
			return createJsonResponse(muestra);
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
		Muestra muestra = this.muestraService.getMuestra(ident);
    	if(muestra!=null){
    		muestra.setPasive('1');
    		this.muestraService.saveMuestra(muestra);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", muestra.getCasa());
    		redirecTo = "redirect:/admin/tests/"+muestra.getIdent()+"/";
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
		Muestra muestra = this.muestraService.getMuestra(ident);
    	if(muestra!=null){
    		muestra.setPasive('0');
    		this.muestraService.saveMuestra(muestra);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", muestra.getCasa());
    		redirecTo = "redirect:/admin/tests/"+muestra.getIdent()+"/";
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

	
}
