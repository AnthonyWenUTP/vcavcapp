package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.CasoService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.ParametroService;
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
@RequestMapping("/admin/casos/*")
public class CasoController {
	private static final Logger logger = LoggerFactory.getLogger(CasoController.class);
	@Resource(name="casoService")
	private CasoService casoService;
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
    	logger.debug("Mostrando Casos en JSP");
    	List<Caso> casos = casoService.getCasos();
    	if (casos == null){
        	logger.debug("Nulo");
        }
    	else {
        	for (Caso caso:casos) {
        		MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(caso.getEstadocaso(),"CAT_ESTADOCASO");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setEstadocaso(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(caso.getSxResult(),"CAT_RES");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setSxResult(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(caso.getSxCompResult(),"CAT_RES");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setSxCompResult(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(caso.getLostFollowUpReason(),"CAT_LOSTFOLLOWUP");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setLostFollowUpReason(descCatalogo);
        		}
        	}
        }
    	model.addAttribute("casos", casos);
    	return "caso/list";
	}
	
	@RequestMapping(value = "/map/", method = RequestMethod.GET)
    public String getEntitiesForMap(Model model) throws ParseException { 	
    	logger.debug("Mostrando Casos en JSP para mapear");
    	try {
    		Double latitud = 0D;
			Double longitud = 0D;
	    	Integer zoom = 0;
        	
        	if(parametroService.getParametroByCode("zoom")!=null) zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	if(parametroService.getParametroByCode("lat")!=null) latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	if(parametroService.getParametroByCode("long")!=null) longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
        	
    		List<Caso> casos = casoService.getCasos();
    		for(Caso loc: casos) {
    			if (loc.getLatitude()== null || loc.getLatitude()==null) {
    				casos.remove(loc);
    			}
    			if (casos.size()==0) {
    				break;
    			}
    		}
        	model.addAttribute("casos", casos);
        	model.addAttribute("latitude",latitud);
        	model.addAttribute("longitude",longitud);
        	model.addAttribute("zoom",zoom);
        	return "caso/mapa";
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
    	List<MessageResource> tiposPrueba = this.messageResourceService.getCatalogo("CAT_TIPOPRUEBA"); 
    	model.addAttribute("tiposPrueba", tiposPrueba);
    	List<MessageResource> siNo = this.messageResourceService.getCatalogo("CAT_SINO"); 
    	model.addAttribute("siNo", siNo);
    	
    	List<MessageResource> sexo = this.messageResourceService.getCatalogo("CAT_SEXO"); 
    	model.addAttribute("sexo", sexo);
    	return "caso/enterForm";
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
        		mr = this.messageResourceService.getMensaje(caso.getTxSuspReason(),"CAT_TXSUSP");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			caso.setTxSuspReason(descCatalogo);
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
	            List<MessageResource> tiposResultados = this.messageResourceService.getCatalogo("CAT_TIPORESULTADO"); 
	            mav.addObject("tiposResultados",tiposResultados);
	            List<MessageResource> razonesSusp = this.messageResourceService.getCatalogo("CAT_TXSUSP"); 
	            mav.addObject("razonesSusp",razonesSusp);
	            
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
		Caso caso = this.casoService.getCaso(ident);
		if(caso!=null){
			Float latitudMinima=0F;
	    	Float latitudMaxima=0F;
	    	Float longitudMinima=0F;
	    	Float longitudMaxima=0F;
	    	Double latitudDef=0D;
	    	Double longitudDef=0D;
	    	Integer zoomDef=1;
	    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
	    	model.addAttribute("localidades", localidades);
	    	List<MessageResource> tiposPrueba = this.messageResourceService.getCatalogo("CAT_TIPOPRUEBA"); 
	    	model.addAttribute("tiposPrueba", tiposPrueba);
	    	List<MessageResource> siNo = this.messageResourceService.getCatalogo("CAT_SINO"); 
	    	model.addAttribute("siNo", siNo);
	    	List<MessageResource> sexo = this.messageResourceService.getCatalogo("CAT_SEXO"); 
	    	model.addAttribute("sexo", sexo);
			model.addAttribute("caso",caso);
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
			return "caso/enterForm";
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
	        , @RequestParam( value="codigo", required=true ) String codigo
	        , @RequestParam( value="local", required=true) String local
	        , @RequestParam( value="latitude", required=false, defaultValue ="" ) String latitude
	        , @RequestParam( value="longitude", required=false, defaultValue ="" ) String longitude
	        , @RequestParam( value="zoom", required=false, defaultValue ="" ) String zoom
	        , @RequestParam( value="status", required=true, defaultValue ="" ) String status
	        , @RequestParam( value="info", required=false, defaultValue ="" ) String info
	        , @RequestParam( value="sint", required=false ) String sint
	        , @RequestParam( value="sexo", required=false ) String sexo
	        , @RequestParam( value="edad", required=false ) Integer edad
	        , @RequestParam( value="embarazada", required=false ) String embarazada
	        , @RequestParam( value="menor6meses", required=false ) String menor6meses
	        , @RequestParam( value="fisDate", required=false ) String fisDate
	        , @RequestParam( value="mxDate", required=true ) String mxDate
	        , @RequestParam( value="mxType", required=true ) String mxType
	        , @RequestParam( value="codE1", required=false ) String codE1
	        , @RequestParam( value="cui", required=false ) String cui
	        , @RequestParam( value="casa", required=false ) String casa
	        , @RequestParam( value="nombre", required=false ) String nombre
	        , @RequestParam( value="latitudeOrigin", required=false, defaultValue ="" ) String latitudeOrigin
	        , @RequestParam( value="longitudeOrigin", required=false, defaultValue ="" ) String longitudeOrigin
	        , @RequestParam( value="zoomOrigin", required=false, defaultValue ="" ) String zoomOrigin
	        )
	{
    	try{
    		Double latitud = null;
    		Double longitud = null;
    		Integer vista= null;
    		
    		
    		Double latitudOrigen = null;
    		Double longitudOrigen = null;
    		Integer vistaOrigen= null;
    		
    		if(!latitude.equals("")) latitud = Double.valueOf(latitude);
    		if(!longitude.equals("")) longitud = Double.valueOf(longitude);
    		if(!zoom.equals("")) vista = Integer.valueOf(zoom);
    		if(!latitudeOrigin.equals("")) latitudOrigen = Double.valueOf(latitudeOrigin);
    		if(!longitudeOrigin.equals("")) longitudOrigen = Double.valueOf(longitudeOrigin);
    		if(!zoomOrigin.equals("")) vistaOrigen = Integer.valueOf(zoomOrigin);
    		
    		
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Date fechaMuestra =  null;    		
    		if (!mxDate.equals("")) fechaMuestra = formatter.parse(mxDate);
    		
    		
    		Date fechaSintomas =  null;
    		if (!fisDate.equals("")) fechaSintomas = formatter.parse(fisDate);
    		
    		
    		Localidad disLoc = this.localidadService.getLocal(local);
			Caso caso = new Caso();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				caso.setIdent(ident);
				caso.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				caso.setRecordDate(new Date());
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				caso = casoService.getCaso(ident);
			}
			caso.setCodigo(codigo);
			caso.setLocal(disLoc);
			caso.setLatitude(latitud);
			caso.setLongitude(longitud);
			caso.setZoom(vista);
			caso.setInfo(info);
			caso.setMxDate(fechaMuestra);
			caso.setFisDate(fechaSintomas);
			caso.setSint(sint);
			caso.setCodE1(codE1);
			caso.setCasa(casa);
			caso.setCui(cui);
			caso.setNombre(nombre);
			caso.setMxType(mxType);
			caso.setEdad(edad);
			caso.setSexo(sexo);
			caso.setEmbarazada(embarazada);
			caso.setMenor6meses(menor6meses);
			caso.setLatitudeOrigin(latitudOrigen);
			caso.setLongitudeOrigin(longitudOrigen);
			caso.setZoomOrigin(vistaOrigen);
			caso.setEstado('2');
			
			//Actualiza
			this.casoService.saveCaso(caso);
			return createJsonResponse(caso);
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
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setPasive('1');
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", caso.getCodigo());
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
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
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setPasive('0');
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", caso.getCodigo());
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
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
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/invno/{ident}/")
    public String invNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setInv("0");
    		caso.setInvDate(null);
    		caso.setInvCompDate(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/txno/{ident}/")
    public String txNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setTx("0");
    		caso.setTxSup("No");
    		caso.setTxDate(null);
    		caso.setDayTx01(null);
    		caso.setDayTx02(null);
    		caso.setDayTx03(null);
    		caso.setDayTx04(null);
    		caso.setDayTx05(null);
    		caso.setDayTx06(null);
    		caso.setDayTx07(null);
    		caso.setDayTx08(null);
    		caso.setDayTx09(null);
    		caso.setDayTx10(null);
    		caso.setDayTx11(null);
    		caso.setDayTx12(null);
    		caso.setDayTx13(null);
    		caso.setDayTx14(null);
    		caso.setTxComp("0");
    		caso.setTxCompDate(null);
    		caso.setTxResultType(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/txCompno/{ident}/")
    public String txCompNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setTxComp("0");
    		caso.setTxCompDate(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/txSuspno/{ident}/")
    public String txSuspNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setTxSusp("0");
    		caso.setTxSuspDate(null);
    		caso.setTxSuspReason(null);
    		caso.setTxSuspOtherReason(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/sxno/{ident}/")
    public String sxNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setSx("0");
    		caso.setSxDate(null);
    		caso.setSxResult(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/sxCompno/{ident}/")
    public String sxCompNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setSxComp("0");
    		caso.setSxCompDate(null);
    		caso.setSxCompResult(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/lostno/{ident}/")
    public String lostNo(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Caso caso = this.casoService.getCaso(ident);
    	if(caso!=null){
    		caso.setLostFollowUp("0");
    		caso.setLostFollowUpReason(null);
    		caso.setLostFollowUpOtherReason(null);
    		String estado = obtenerEstado(caso);
    		caso.setEstadocaso(estado);
    		this.casoService.saveCaso(caso);
    		redirectAttributes.addFlashAttribute("completo", true);
    		redirecTo = "redirect:/admin/casos/"+caso.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    
    /**
     * Custom handler for saving.
     * 
     * @param ident Identificador unico
     * @param code codigo
     * @param name nombre
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping( value="/dateValue/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processInvDateEntity( @RequestParam(value="ident", required=false, defaultValue="" ) String ident
			, @RequestParam( value="dataElement", required=true ) String dataElement
	        , @RequestParam( value="dateValue", required=false, defaultValue="" ) String dateValue
	        , @RequestParam( value="dateValue2", required=false, defaultValue="" ) String dateValue2
	        , @RequestParam( value="diaTx", required=false, defaultValue="" ) String diaTx
	        , @RequestParam( value="resultado", required=false, defaultValue="" ) String resultado
	        , @RequestParam( value="txResultType", required=false, defaultValue="" ) String txResultType
	        , @RequestParam( value="lostFollowUpReason", required=false, defaultValue="" ) String lostFollowUpReason
	        , @RequestParam( value="lostFollowUpOtherReason", required=false, defaultValue="" ) String lostFollowUpOtherReason
	        , @RequestParam( value="txSuspReason", required=false, defaultValue="" ) String txSuspReason
	        , @RequestParam( value="txSuspOtherReason", required=false, defaultValue="" ) String txSuspOtherReason
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
    		Date valorFecha =  null;
    		Date valorFecha2 =  null;
    		if (!dateValue.equals("")) valorFecha = formatter.parse(dateValue);
    		if (!dateValue2.equals("")) valorFecha2 = formatter.parse(dateValue2);
    		String fechaIngresada = "";
    		if (valorFecha!=null) fechaIngresada=formatter2.format(valorFecha);    		
    		Caso caso = this.casoService.getCaso(ident);
        	if(caso!=null){
        		if(dataElement.equals("inv")) {
        			if(fechaIngresada.compareTo(formatter2.format(caso.getMxDate()))>=0) {
        				if(valorFecha2!=null) {
        					caso.setInv("1");
        					caso.setInvCompDate(valorFecha2);
        				}
        				else {
        					caso.setInv("0");
        				}
        				caso.setInvDate(valorFecha);
        			}
        			else {
        				return createJsonResponse("Fecha de investigación incorrecta. Tiene que ser mayor o igual a " + formatter.format(caso.getMxDate()));
        			}
        		}
        		else if(dataElement.equals("tx")) {
        			if(fechaIngresada.compareTo(formatter2.format(caso.getMxDate()))>=0) {
        				caso.setTx("1");
        				caso.setTxDate(valorFecha);
        				caso.setTxResultType(txResultType);
        			}
        			else {
        				return createJsonResponse("Fecha de inicio de tratamiento incorrecta. Tiene que ser mayor o igual a " + formatter.format(caso.getMxDate()));
        			}
        		}
        		else if(dataElement.equals("txComp")) {
        			if(fechaIngresada.compareTo(formatter2.format(caso.getTxDate()))>0) {
        				caso.setTxComp("1");
        				caso.setTxCompDate(valorFecha);
        			}
        			else {
        				return createJsonResponse("Fecha de fin de tratamiento incorrecta. Tiene que ser mayor a " + formatter.format(caso.getTxDate()));
        			}
        		}
        		else if(dataElement.equals("sx")) {
        			if(fechaIngresada.compareTo(formatter2.format(caso.getTxCompDate()))>0) {
        				caso.setSx("1");
        				caso.setSxDate(valorFecha);
        				caso.setSxResult(resultado);
        			}
        			else {
        				return createJsonResponse("Fecha de seguimiento incorrecta. Tiene que ser mayor a " + formatter.format(caso.getTxCompDate()));
        			}
        		}
        		else if(dataElement.equals("sxComp")) {
        			if(fechaIngresada.compareTo(formatter2.format(caso.getSxDate()))>0) {
        				caso.setSxComp("1");
        				caso.setSxCompDate(valorFecha);
        				caso.setSxCompResult(resultado);
        			}
        			else {
        				return createJsonResponse("Fecha de seguimiento incorrecta. Tiene que ser mayor " + formatter.format(caso.getSxDate()));
        			}
        		}
        		else if(dataElement.equals("txSup")) {
        			if(fechaIngresada.compareTo(formatter2.format(caso.getTxDate()))>=0) {
	        			caso.setTxSup("Si");
	        			if(diaTx.equals("1")) {
	        				if(validarFechaMenor(fechaIngresada,caso.getDayTx02()) && validarFechaMenor(fechaIngresada,caso.getDayTx03())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx04()) && validarFechaMenor(fechaIngresada,caso.getDayTx05())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx06()) && validarFechaMenor(fechaIngresada,caso.getDayTx07())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx01(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("2")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMenor(fechaIngresada,caso.getDayTx03())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx04()) && validarFechaMenor(fechaIngresada,caso.getDayTx05())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx06()) && validarFechaMenor(fechaIngresada,caso.getDayTx07())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx02(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("3")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx04()) && validarFechaMenor(fechaIngresada,caso.getDayTx05())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx06()) && validarFechaMenor(fechaIngresada,caso.getDayTx07())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx03(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("4")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMenor(fechaIngresada,caso.getDayTx05())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx06()) && validarFechaMenor(fechaIngresada,caso.getDayTx07())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx04(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("5")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx06()) && validarFechaMenor(fechaIngresada,caso.getDayTx07())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx05(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("6")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMenor(fechaIngresada,caso.getDayTx07())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx06(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("7")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx08()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx07(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("8")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMenor(fechaIngresada,caso.getDayTx09())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx08(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("9")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMayor(fechaIngresada,caso.getDayTx08())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx10()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx09(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("10")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMayor(fechaIngresada,caso.getDayTx08())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx09()) && validarFechaMenor(fechaIngresada,caso.getDayTx11())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx10(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("11")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMayor(fechaIngresada,caso.getDayTx08())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx09()) && validarFechaMayor(fechaIngresada,caso.getDayTx10())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx12()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx11(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("12")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMayor(fechaIngresada,caso.getDayTx08())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx09()) && validarFechaMayor(fechaIngresada,caso.getDayTx10())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx11()) && validarFechaMenor(fechaIngresada,caso.getDayTx13())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx12(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("13")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMayor(fechaIngresada,caso.getDayTx08())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx09()) && validarFechaMayor(fechaIngresada,caso.getDayTx10())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx11()) && validarFechaMayor(fechaIngresada,caso.getDayTx12())
	        						&& validarFechaMenor(fechaIngresada,caso.getDayTx14())) {
	        					caso.setDayTx13(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
	        			else if(diaTx.equals("14")) {
	        				if(validarFechaMayor(fechaIngresada,caso.getDayTx01()) && validarFechaMayor(fechaIngresada,caso.getDayTx02())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx03()) && validarFechaMayor(fechaIngresada,caso.getDayTx04())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx05()) && validarFechaMayor(fechaIngresada,caso.getDayTx06())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx07()) && validarFechaMayor(fechaIngresada,caso.getDayTx08())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx09()) && validarFechaMayor(fechaIngresada,caso.getDayTx10())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx11()) && validarFechaMayor(fechaIngresada,caso.getDayTx12())
	        						&& validarFechaMayor(fechaIngresada,caso.getDayTx13())) {
	        					caso.setDayTx14(valorFecha);
	        				}
	        				else {
	        					return createJsonResponse("Fecha de tratamiento supervisado es incorrecta");
	        				}
	        			}
        			}
        			else {
        				return createJsonResponse("Fecha de tx supervisado incorrecta. Tiene que ser mayor o igual a " + formatter.format(caso.getTxDate()));
        			}
        		}
        		else if(dataElement.equals("lostFollowUp")) {
        			caso.setLostFollowUp("1");
        			caso.setLostFollowUpReason(lostFollowUpReason);
        			caso.setLostFollowUpOtherReason(lostFollowUpOtherReason);
        		}
        		else if(dataElement.equals("txSusp")) {
        			caso.setTxSusp("1");
        			caso.setTxSuspDate(valorFecha);
        			caso.setTxSuspReason(txSuspReason);
        			caso.setTxSuspOtherReason(txSuspOtherReason);
        		}
        		String estado = obtenerEstado(caso);
        		caso.setEstadocaso(estado);
        		this.casoService.saveCaso(caso);
        	}
			return createJsonResponse(caso);
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
    
    
    public String obtenerEstado (Caso caso) {
    	
    	if(caso.getLostFollowUp().equals("1")) {
    		return "SEGINC";
    	}
    	else if (caso.getSxCompResult()!= null) {
    		if (caso.getSxCompResult().equals("NEG")) {
    			return "SEG4";
    		}
    		else {
    			return "SEGPOS";
    		}
    	}
    	else if (caso.getSxResult()!= null) {
    		if (caso.getSxResult().equals("NEG")) {
    			return "SEG2";
    		}
    		else {
    			return "SEGPOS";
    		}
    	}
    	else if (caso.getTxComp().equals("1")) {
    		return "TRATC";
    	}
    	else if (caso.getTx().equals("1")) {
    		return "TRAT";
    	}
    	return "CONF";
    }
    
    public boolean validarFechaMenor(String fechaIngresada, Date fechaOtra) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	if(fechaOtra==null) {
    		return true;
    	}
    	else if(fechaIngresada.compareTo(formatter.format(fechaOtra))<0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean validarFechaMayor(String fechaIngresada, Date fechaOtra) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	if(fechaOtra==null) {
    		return true;
    	}
    	else if(fechaIngresada.compareTo(formatter.format(fechaOtra))>0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    

	
}
