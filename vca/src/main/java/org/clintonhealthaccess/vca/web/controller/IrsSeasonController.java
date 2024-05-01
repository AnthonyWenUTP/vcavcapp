package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.service.IrsSeasonService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.HouseholdService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.ParametroService;
import org.clintonhealthaccess.vca.service.TargetService;
import org.clintonhealthaccess.vca.service.VisitService;
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
import java.util.UUID;

/**
 * Controlador web de peticiones relacionadas a temporadas de salud
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/irs/season/*")
public class IrsSeasonController {
	private static final Logger logger = LoggerFactory.getLogger(IrsSeasonController.class);
	@Resource(name="temporadaService")
	private IrsSeasonService temporadaService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="householdService")
	private HouseholdService householdService;
	@Resource(name="targetService")
	private TargetService targetService;
	@Resource(name="parametroService")
	private ParametroService parametroService;
	@Resource(name="visitService")
	private VisitService visitService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando IrsSeasons en JSP");
    	List<IrsSeason> temporadas = temporadaService.getIrsSeasons();
    	model.addAttribute("temporadas", temporadas);
    	return "irsseason/list";
	}
	
	/**
     * Custom handler for adding.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String addEntity(Model model) {
    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	return "irsseason/newForm";
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
    	IrsSeason temporada = this.temporadaService.getIrsSeason(ident);
        if(temporada==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("irsseason/viewForm");
        	mav.addObject("temporada",temporada);
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
		IrsSeason temporada = this.temporadaService.getIrsSeason(ident);
		if(temporada!=null){
			model.addAttribute("temporada",temporada);
			return "irsseason/enterForm";
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
	        , @RequestParam( value="startDate", required=true ) String startDate
	        , @RequestParam( value="endDate", required=true ) String endDate
	        , @RequestParam( value="obs", required=false ) String obs
	        , @RequestParam( value="localidades", required=false, defaultValue="") List<String> localidades
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		Date fechaInicio =  null;
    		Date fechaFin =  null;
    		
    		fechaInicio=formatter.parse(startDate);
    		fechaFin=formatter.parse(endDate);
    		

    		Date fechaHoy = null;
    		String fechaactual = formatter.format((new Date()));
    		fechaHoy=formatter.parse(fechaactual);
    		
    		
    		String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();
    		
    		//Calculate number of days
    		Integer numberDays=0;
    		long diff = fechaFin.getTime() - fechaInicio.getTime();
    		numberDays = (int) ((diff / 1000 /60 / 60/ 24 ) + 1);
    		
			IrsSeason temporada = new IrsSeason();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(usuarioActual.hashCode(),new Date().hashCode()).toString();
				temporada.setIdent(ident);
				temporada.setCode(code);
				temporada.setName(name);
				temporada.setRecordUser(usuarioActual);
				temporada.setRecordDate(new Date());
				temporada.setStartDate(fechaInicio);
				temporada.setEndDate(fechaFin);
				temporada.setNumberDays(numberDays);
				temporada.setObs(obs);
				
				//Guardar nuevo
				this.temporadaService.saveIrsSeason(temporada);
				Integer counter = 0;
				for(String l:localidades){
					List<Household> casasEnLocalidad = this.householdService.getHousesFiltro(null, null, null, null, l, "ALL", "ALL", 
							usuarioActual,"0");
					for(Household casa:casasEnLocalidad){
							counter++;
							Target newTarget = new Target();
							ident = new UUID(counter.hashCode(),new Date().hashCode()).toString();
							newTarget.setIdent(ident);
							newTarget.setHousehold(casa);
							newTarget.setIrsSeason(temporada);
							if(casa.getSprRooms()!=null) {
								if(casa.getSprRooms()>0) {
									newTarget.setSprayStatus("NOTVIS");
								}
								else {
									newTarget.setSprayStatus("DROPPED");
								}
							}
							else {
								newTarget.setSprayStatus("DROPPED");
							}
							newTarget.setLastModified(fechaHoy);
							newTarget.setRecordUser(usuarioActual);
							newTarget.setRecordDate(new Date());
							newTarget.setEstado('2');
							this.temporadaService.saveTarget(newTarget);
					}
				}
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				temporada = temporadaService.getIrsSeason(ident);
				temporada.setCode(code);
				temporada.setName(name);
				temporada.setStartDate(fechaInicio);
				temporada.setEndDate(fechaFin);
				temporada.setNumberDays(numberDays);
				temporada.setObs(obs);
				//Actualiza
				this.temporadaService.saveIrsSeason(temporada);
			}
			return createJsonResponse(temporada);
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
		IrsSeason temporada = this.temporadaService.getIrsSeason(ident);
    	if(temporada!=null){
    		temporada.setPasive('1');
    		this.temporadaService.saveIrsSeason(temporada);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", temporada.getName());
    		redirecTo = "redirect:/irs/season/"+temporada.getIdent()+"/";
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
		IrsSeason temporada = this.temporadaService.getIrsSeason(ident);
    	if(temporada!=null){
    		temporada.setPasive('0');
    		this.temporadaService.saveIrsSeason(temporada);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", temporada.getName());
    		redirecTo = "redirect:/irs/season/"+temporada.getIdent()+"/";
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
    @RequestMapping( value="/saveEntityG/", method=RequestMethod.POST)
	public ResponseEntity<String> processEntityG( @RequestParam(value="ident", required=true) String ident
	        , @RequestParam( value="code", required=true ) String code
	        , @RequestParam( value="name", required=true ) String name
	        , @RequestParam( value="startDate", required=true ) String startDate
	        , @RequestParam( value="endDate", required=true ) String endDate
	        , @RequestParam( value="obs", required=false ) String obs
	        )
	{
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		Date fechaInicio =  null;
    		Date fechaFin =  null;
    		fechaInicio=formatter.parse(startDate);
    		fechaFin=formatter.parse(endDate);
    		
    		//Calculate number of days
    		Integer numberDays=0;
    		long diff = fechaFin.getTime() - fechaInicio.getTime();
    		numberDays = (int) ((diff / 1000 /60 / 60/ 24 ) + 1);
    		
			
			//Recupera de la base de datos
			IrsSeason temporada = temporadaService.getIrsSeason(ident);
			temporada.setCode(code);
			temporada.setName(name);
			temporada.setStartDate(fechaInicio);
			temporada.setEndDate(fechaFin);
			temporada.setNumberDays(numberDays);
			temporada.setObs(obs);
			//Actualiza
			this.temporadaService.saveIrsSeason(temporada);
			return createJsonResponse(temporada);
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
    
    
    @RequestMapping(value = "/targets/", method = RequestMethod.GET)
    public String getTargets(Model model) throws ParseException { 	
		logger.debug("Mostrando targets en JSP");
    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	List<MessageResource> estados = messageResourceService.getCatalogo("CAT_STATUS");
    	model.addAttribute("estados",estados);
    	List<IrsSeason> temporadas = this.temporadaService.getIrsSeasons();
    	model.addAttribute("temporadas",temporadas);
    	return "irsseason/target";
	}
    
    @RequestMapping(value = "/searchTargets/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Target> fetchTargets(@RequestParam(value = "codeHouse", required = false) String codeHouse,
    		@RequestParam(value = "ownerName", required = false) String ownerName,
    		@RequestParam(value = "fecActRange", required = false, defaultValue = "") String fecActRange,
    		@RequestParam(value = "local", required = true) String local,
    		@RequestParam(value = "irsSeason", required = true) String irsSeason,
    		@RequestParam(value = "sprayStatus", required = true) String sprayStatus
    		) throws ParseException {
        logger.info("Obteniendo resultados");
        Long desde = null;
        Long hasta = null;
        
        if (!fecActRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecActRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecActRange.substring(fecActRange.length()-10, fecActRange.length())).getTime();
        }
        List<Target> datos = this.targetService.getMetasFiltro(codeHouse, ownerName, desde, hasta, local, 
        		irsSeason, sprayStatus, SecurityContextHolder.getContext().getAuthentication().getName(),null);
        if (datos == null){
        	logger.debug("Nulo");
        }
        else {
        	for (Target meta:datos) {
        		MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(meta.getHousehold().getMaterial(),"CAT_MAT");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			meta.getHousehold().setMaterial(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(meta.getHousehold().getInhabited(),"CAT_HAB");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			meta.getHousehold().setInhabited(descCatalogo);
        		}
        		if(!(meta.getHousehold().getNoSproomsReasons()==null)) {
        			String[] partsNSP = meta.getHousehold().getNoSproomsReasons().split(",");
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
        			meta.getHousehold().setNoSproomsReasons(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(meta.getSprayStatus(),"CAT_STATUS");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			meta.setSprayStatus(descCatalogo);
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
    @RequestMapping("/targets/{ident}/")
    public ModelAndView showEntity2(@PathVariable("ident") String ident) {
    	ModelAndView mav;
    	Double latitud;
    	Double longitud;
    	Integer zoom;
    	Target target = this.targetService.getMeta(ident, SecurityContextHolder.getContext().getAuthentication().getName());
    	
        if(target==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("irsseason/viewTargetForm");
        	zoom = Integer.parseInt(parametroService.getParametroByCode("zoom").getValue());
        	latitud = Double.parseDouble(parametroService.getParametroByCode("lat").getValue());
        	longitud = Double.parseDouble(parametroService.getParametroByCode("long").getValue());
        	if(target.getHousehold().getLatitude()!=null) latitud = target.getHousehold().getLatitude();
        	if(target.getHousehold().getLongitude()!=null) longitud = target.getHousehold().getLongitude();
        	MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(target.getHousehold().getMaterial(),"CAT_MAT");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			target.getHousehold().setMaterial(descCatalogo);
    		}
    		if(!(target.getHousehold().getNoSproomsReasons()==null)) {
    			String[] partsNSP = target.getHousehold().getNoSproomsReasons().split(",");
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
    			target.getHousehold().setNoSproomsReasons(descCatalogo);
    		}
        	mav.addObject("target",target);
        	mav.addObject("zoom",zoom);
        	mav.addObject("latitud",latitud);
        	mav.addObject("longitud",longitud);
            List<AuditTrail> bitacora = auditTrailService.getBitacora(ident);
            mav.addObject("bitacora",bitacora);
            List<Visit> visitas = this.visitService.getTargetVisits(target.getIdent());
            for (Visit visita: visitas) {
        		mr = this.messageResourceService.getMensaje(visita.getActivity(),"CAT_VIS_TYPE");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			visita.setActivity(descCatalogo);
        		}
        		mr = this.messageResourceService.getMensaje(visita.getCompVisit(),"CAT_SINO");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			visita.setCompVisit(descCatalogo);
        		}
            }
            mav.addObject("visitas",visitas);
        }
        return mav;
    }
    
    /**
     * Custom handler for disabling.
     *
     * @param ident the ID to disable
     * @param redirectAttributes 
     * @return a String
     */
    @RequestMapping("/targets/disableEntity/{ident}/")
    public String disableEntity2(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Target meta = this.targetService.getMeta(ident, SecurityContextHolder.getContext().getAuthentication().getName());
    	if(meta!=null){
    		meta.setPasive('1');
    		this.targetService.saveMeta(meta);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", meta.getHousehold().getCode());
    		redirecTo = "redirect:/irs/season/targets/"+meta.getIdent()+"/";
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
    @RequestMapping("/targets/enableEntity/{ident}/")
    public String enableEntity2(@PathVariable("ident") String ident, 
    		RedirectAttributes redirectAttributes) {
    	String redirecTo="404";
		Target meta = this.targetService.getMeta(ident, SecurityContextHolder.getContext().getAuthentication().getName());
    	if(meta!=null){
    		meta.setPasive('0');
    		this.targetService.saveMeta(meta);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", meta.getHousehold().getCode());
    		redirecTo = "redirect:/irs/season/targets/"+meta.getIdent()+"/";
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
