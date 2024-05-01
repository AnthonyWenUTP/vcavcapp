package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.HouseholdService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.CensadorService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.ParametroService;
import org.clintonhealthaccess.vca.service.UsuarioService;
import org.clintonhealthaccess.vca.users.model.UserSistema;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controlador web de peticiones
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/census/*")
public class CensoController {
	private static final Logger logger = LoggerFactory.getLogger(CensoController.class);
	@Resource(name="householdService")
	private HouseholdService householdService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="censadorService")
	private CensadorService censadorService;
	@Resource(name="parametroService")
	private ParametroService parametroService;
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
		logger.debug("Mostrando Viviendas en JSP");
    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	List<Censador> censadores = censadorService.getCensadores();
    	model.addAttribute("censadores", censadores);
    	List<UserSistema> usuarios = this.usuarioService.getUsers();
    	model.addAttribute("usuarios", usuarios);
    	return "censo/censo";
	}
    
    
    @RequestMapping(value = "/searchHouses/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Household> fetchHouses(@RequestParam(value = "codeHouse", required = false) String codeHouse,
    		@RequestParam(value = "ownerName", required = false) String ownerName,
    		@RequestParam(value = "fecCensoRange", required = false, defaultValue = "") String fecCensoRange,
    		@RequestParam(value = "local", required = true) String local,
    		@RequestParam(value = "censusTaker", required = true) String censusTaker,
    		@RequestParam(value = "recordUser", required = true) String recordUser
    		) throws ParseException {
        logger.info("Obteniendo resultados");
        Long desde = null;
        Long hasta = null;
        
        if (!fecCensoRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecCensoRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecCensoRange.substring(fecCensoRange.length()-10, fecCensoRange.length())).getTime();
        }
        List<Household> datos = householdService.getHousesFiltro(codeHouse, ownerName, desde, hasta, local, 
        		censusTaker, recordUser, SecurityContextHolder.getContext().getAuthentication().getName(),null);
        if (datos == null){
        	logger.debug("Nulo");
        }
        else {
        	for (Household vivienda:datos) {
        		MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(vivienda.getMaterial(),"CAT_MAT");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			vivienda.setMaterial(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(vivienda.getInhabited(),"CAT_SINO");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			vivienda.setInhabited(descCatalogo);
        		}
        		if(!(vivienda.getNoSproomsReasons()==null)) {
        			String[] partsNSP = vivienda.getNoSproomsReasons().split(",");
        			descCatalogo = null;
        			for (int i =0; i<partsNSP.length; i++) {
        				mr = this.messageResourceService.getMensaje(partsNSP[i],"CAT_RNR");
                		if(mr!=null) {
                			if(descCatalogo==null) {
                				descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
                			}
                			else {
                				String nuevo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
                				descCatalogo = descCatalogo + ", " +nuevo;
                			}
                		}
        			}
        			vivienda.setNoSproomsReasons(descCatalogo);
        		}
        	}
        }
        return datos;
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
    	Double latitud=0D;
    	Double longitud=0D;
    	Integer zoom = 0;
    	Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
        if(vivienda==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("censo/viewForm");
        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
        	if(vivienda.getLatitude()!=null) latitud = vivienda.getLatitude();
        	if(vivienda.getLongitude()!=null) longitud = vivienda.getLongitude();
        	MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(vivienda.getMaterial(),"CAT_MAT");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			vivienda.setMaterial(descCatalogo);
    		}
    		if(!(vivienda.getNoSproomsReasons()==null)) {
    			String[] partsNSP = vivienda.getNoSproomsReasons().split(",");
    			descCatalogo = null;
    			for (int i =0; i<partsNSP.length; i++) {
    				mr = this.messageResourceService.getMensaje(partsNSP[i],"CAT_RNR");
            		if(mr!=null) {
            			if(descCatalogo==null) {
            				descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
            			}
            			else {
            				String nuevo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
            				descCatalogo = descCatalogo + ", " +nuevo;
            			}
            		}
    			}
    			vivienda.setNoSproomsReasons(descCatalogo);
    		}
        	mav.addObject("vivienda",vivienda);
        	mav.addObject("zoom",zoom);
        	mav.addObject("latitud",latitud);
        	mav.addObject("longitud",longitud);
            List<AuditTrail> bitacora = auditTrailService.getBitacora(ident);
            mav.addObject("bitacora",bitacora);
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
    	Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
		if(vivienda!=null){
			List<Censador> censadores = censadorService.getActiveCensadores();
	    	model.addAttribute("censadores", censadores);
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<MessageResource> sinos = messageResourceService.getCatalogo("CAT_HAB");
	    	model.addAttribute("sinos",sinos);
	    	List<MessageResource> materials = messageResourceService.getCatalogo("CAT_MAT");
	    	model.addAttribute("materials",materials);
	    	List<MessageResource> razones = messageResourceService.getCatalogo("CAT_RNR");
	    	model.addAttribute("razones",razones);
			model.addAttribute("vivienda",vivienda);
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
			return "censo/enterForm";
		}
		else{
			return "403";
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
		Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
    	if(vivienda!=null){
    		vivienda.setPasive('1');
    		this.householdService.saveVivienda(vivienda);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", vivienda.getCode());
    		redirecTo = "redirect:/census/"+vivienda.getIdent()+"/";
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
    	Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
    	if(vivienda!=null){
    		vivienda.setPasive('0');
    		this.householdService.saveVivienda(vivienda);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", vivienda.getCode());
    		redirecTo = "redirect:/census/"+vivienda.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param ident the ID to edit
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/enterLocation/{ident}/", method = RequestMethod.GET)
	public String enterLocation(@PathVariable("ident") String ident, Model model) {
		Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
		if(vivienda!=null){
			try {
				model.addAttribute("vivienda",vivienda);
				Double latitud = 0D;
				Double longitud = 0D;
		    	Integer zoom = 0;
		    	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
	        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
	        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
	        	if(vivienda.getLatitude()!=null) latitud = vivienda.getLatitude();
	        	if(vivienda.getLongitude()!=null) longitud = vivienda.getLongitude();
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
				return "censo/enterLocation";
			}
        	catch (Exception e) {
        		model.addAttribute("errormsg",e.getLocalizedMessage());
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
	        , @RequestParam( value="ownerName", required=false ) String ownerName
	        , @RequestParam( value="local", required=true) String local
	        , @RequestParam( value="censusTaker", required=true) String censusTaker
	        , @RequestParam( value="censusDate", required=true) String censusDate
	        , @RequestParam( value="inhabited", required=true) String inhabited
	        , @RequestParam( value="habitants", required=false) Integer habitants
	        , @RequestParam( value="material", required=false) String material
	        , @RequestParam( value="rooms", required=false) Integer rooms
	        , @RequestParam( value="sprRooms", required=false) Integer sprRooms
	        , @RequestParam( value="noSprooms", required=false) Integer noSprooms
	        , @RequestParam( value="noSproomsReasons", required=false) String noSproomsReasons
	        , @RequestParam( value="sleep", required=false) Integer sleep
	        , @RequestParam( value="mosqSinInsecticida", required=false) Integer mosqSinInsecticida
	        , @RequestParam( value="personasCharlas", required=false) Integer personasCharlas
	        , @RequestParam( value="latitude", required=false, defaultValue ="" ) String latitude
	        , @RequestParam( value="longitude", required=false, defaultValue ="" ) String longitude
	        , @RequestParam( value="obs", required=false, defaultValue ="" ) String obs
	        , @RequestParam( value="verified", required=false, defaultValue ="" ) String verified
	        
	        , @RequestParam( value="masculinos", required=false) Integer masculinos
	        , @RequestParam( value="femeninos", required=false) Integer femeninos
	        , @RequestParam( value="menores5", required=false) Integer menores5
	        , @RequestParam( value="menores5masc", required=false) Integer menores5masc
	        , @RequestParam( value="menores5fem", required=false) Integer menores5fem
	        , @RequestParam( value="embarazadas", required=false) Integer embarazadas
	        , @RequestParam( value="sitiosDormirCama", required=false) Integer sitiosDormirCama
	        , @RequestParam( value="sitiosDormirHamaca", required=false) Integer sitiosDormirHamaca
	        , @RequestParam( value="sitiosDormirSuelo", required=false) Integer sitiosDormirSuelo
	        , @RequestParam( value="sitiosDormirOtro", required=false) Integer sitiosDormirOtro
	        , @RequestParam( value="mtildExistentes", required=false) Integer mtildExistentes
	        
	        )
	{

    	try{
    		Double latitud = null;
    		Double longitud = null;
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		Date fechaCenso =  null;
    		
    		if(!latitude.equals("")) latitud = Double.valueOf(latitude);
    		if(!longitude.equals("")) longitud = Double.valueOf(longitude);
    		if (!censusDate.equals("")) fechaCenso = formatter.parse(censusDate);
    		
    		Localidad localidad = this.localidadService.getLocal(local);
			Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
			vivienda.setCode(code);
			vivienda.setLocal(localidad);
			vivienda.setOwnerName(ownerName);
			vivienda.setCensusTaker(this.censadorService.getCensador(censusTaker));
			vivienda.setCensusDate(fechaCenso);
			vivienda.setInhabited(inhabited);
			vivienda.setHabitants(habitants);
			vivienda.setMaterial(material);
			vivienda.setRooms(rooms);
			vivienda.setSprRooms(sprRooms);
			vivienda.setNoSprooms(noSprooms);
			vivienda.setNoSproomsReasons(noSproomsReasons);
			vivienda.setSleep(sleep);
			//vivienda.setNumNets(numNets);
			vivienda.setMosqSinInsecticida(mosqSinInsecticida);
			vivienda.setPersonasCharlas(personasCharlas);
			vivienda.setLatitude(latitud);
			vivienda.setLongitude(longitud);
			vivienda.setObs(obs);
			vivienda.setVerified(verified);
			
			vivienda.setMasculinos(masculinos);
			vivienda.setFemeninos(femeninos);
			vivienda.setMenores5(menores5);
			vivienda.setMenores5masc(menores5masc);
			vivienda.setMenores5fem(menores5fem);
			vivienda.setEmbarazadas(embarazadas);
			vivienda.setSitiosDormirCama(sitiosDormirCama);
			vivienda.setSitiosDormirHamaca(sitiosDormirHamaca);
			vivienda.setSitiosDormirSuelo(sitiosDormirSuelo);
			vivienda.setSitiosDormirOtro(sitiosDormirOtro);
			vivienda.setMosqSinInsecticida(mosqSinInsecticida);
			vivienda.setMtildExistentes(mtildExistentes);
			
			
			this.householdService.saveVivienda(vivienda);
			
			return createJsonResponse(vivienda);
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
     * Custom handler for saving location.
     * 
     * @param ident Identificador unico
     * @param code codigo
     * @param name nombre
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/saveEntityLocation/", method=RequestMethod.POST)
	public ResponseEntity<String> processEntityLocation( @RequestParam(value="ident", required=true) String ident
	        , @RequestParam( value="latitude", required=true) String latitude
	        , @RequestParam( value="longitude", required=true) String longitude
	        )
	{
    	try{
    		Double latitud = null;
    		Double longitud = null;
    		
    		
    		if(!latitude.equals("")) latitud = Double.valueOf(latitude);
    		if(!longitude.equals("")) longitud = Double.valueOf(longitude);
    		
    		
			Household vivienda = this.householdService.getVivienda(ident, SecurityContextHolder.getContext().getAuthentication().getName());
			vivienda.setLatitude(latitud);
			vivienda.setLongitude(longitud);
			this.householdService.saveVivienda(vivienda);
			
			return createJsonResponse(vivienda);
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
    
    private ResponseEntity<String> createJsonResponse( Object o )
   	{
   	    HttpHeaders headers = new HttpHeaders();
   	    headers.set("Content-Type", "application/json");
   	    Gson gson = new Gson();
   	    String json = gson.toJson(o);
   	    return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
   	}
    
    
    @RequestMapping(value = "localidad", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Localidad fetchLocalidadJson(@RequestParam(value = "ident", required = true) String idLocalidad) {
        logger.info("Obteniendo la localidad en JSON");
        Localidad localidad = this.localidadService.getLocal(idLocalidad);
        if (localidad == null){
        	logger.debug("Nulo");
        	localidad = new Localidad();
        	localidad.setPattern(".");
        }
        return localidad;	
    }
    
	
}
