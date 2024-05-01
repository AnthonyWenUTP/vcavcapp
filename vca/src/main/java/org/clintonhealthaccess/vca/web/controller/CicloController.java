package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.HouseholdService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.mtilds.CicloService;
import org.clintonhealthaccess.vca.service.mtilds.EntregaTargetService;
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
 * Controlador web de peticiones relacionadas a temporadas de mosquiteros
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/llins/season/*")
public class CicloController {
	private static final Logger logger = LoggerFactory.getLogger(CicloController.class);
	@Resource(name="cicloService")
	private CicloService cicloService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="householdService")
	private HouseholdService householdService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="entregaTargetService")
	private EntregaTargetService entregaTargetService;

    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando Ciclos en JSP");
    	List<Ciclo> ciclos = cicloService.getCiclos();
    	model.addAttribute("ciclos", ciclos);
    	return "llinsseasons/list";
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
    	return "llinsseasons/newForm";
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
    	Ciclo ciclo = this.cicloService.getCiclo(ident);
        if(ciclo==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("llinsseasons/viewForm");
        	mav.addObject("ciclo",ciclo);
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
		Ciclo ciclo = this.cicloService.getCiclo(ident);
		if(ciclo!=null){
			model.addAttribute("ciclo",ciclo);
			return "llinsseasons/enterForm";
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
    		
			Ciclo ciclo = new Ciclo();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(usuarioActual.hashCode(),new Date().hashCode()).toString();
				ciclo.setIdent(ident);
				ciclo.setCode(code);
				ciclo.setName(name);
				ciclo.setRecordUser(usuarioActual);
				ciclo.setRecordDate(new Date());
				ciclo.setStartDate(fechaInicio);
				ciclo.setEndDate(fechaFin);
				ciclo.setNumberDays(numberDays);
				ciclo.setObs(obs);
				
				//Guardar nuevo
				this.cicloService.saveCiclo(ciclo);
				Integer counter = 0;
				for(String l:localidades){
					List<Household> casasEnLocalidad = this.householdService.getHousesFiltro(null, null, null, null, l, "ALL", "ALL", 
							usuarioActual,"0");
					for(Household casa:casasEnLocalidad){
						if(casa.getHabitants()!=null && casa.getHabitants()>0) {
							counter++;
							EntregaTarget newTarget = new EntregaTarget();
							ident = new UUID(counter.hashCode(),new Date().hashCode()).toString();
							newTarget.setIdent(ident);
							newTarget.setHousehold(casa);
							newTarget.setCiclo(ciclo);
							newTarget.setStatus("PENDING");
							newTarget.setHabitantes(casa.getHabitants());
							newTarget.setEnrollmentDate(fechaHoy);
							newTarget.setLastModified(fechaHoy);
							newTarget.setRecordUser(usuarioActual);
							newTarget.setRecordDate(new Date());
							newTarget.setEstado('2');
							this.entregaTargetService.saveMeta(newTarget);
						}
					}
				}
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				ciclo = cicloService.getCiclo(ident);
				ciclo.setCode(code);
				ciclo.setName(name);
				ciclo.setStartDate(fechaInicio);
				ciclo.setEndDate(fechaFin);
				ciclo.setNumberDays(numberDays);
				ciclo.setObs(obs);
				//Actualiza
				this.cicloService.saveCiclo(ciclo);
			}
			return createJsonResponse(ciclo);
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
		Ciclo ciclo = this.cicloService.getCiclo(ident);
    	if(ciclo!=null){
    		ciclo.setPasive('1');
    		this.cicloService.saveCiclo(ciclo);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", ciclo.getName());
    		redirecTo = "redirect:/llins/season/"+ciclo.getIdent()+"/";
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
    	Ciclo ciclo = this.cicloService.getCiclo(ident);
    	if(ciclo!=null){
    		ciclo.setPasive('0');
    		this.cicloService.saveCiclo(ciclo);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", ciclo.getName());
    		redirecTo = "redirect:/llins/season/"+ciclo.getIdent()+"/";
    	}
    	else{
    		redirecTo = "403";
    	}
    	return redirecTo;	
    }
    
    @RequestMapping(value = "/targets/", method = RequestMethod.GET)
    public String getTargets(Model model) throws ParseException { 	
		logger.debug("Mostrando targets en JSP");
    	List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	List<MessageResource> estados = messageResourceService.getCatalogo("CAT_STATUS_LLIN");
    	model.addAttribute("estados",estados);
    	List<Ciclo> ciclos = this.cicloService.getCiclos();
    	model.addAttribute("ciclos",ciclos);
    	return "llinsseasons/target";
	}
    
    
    @RequestMapping(value = "/searchTargets/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<EntregaTarget> fetchTargets(@RequestParam(value = "codeHouse", required = false) String codeHouse,
    		@RequestParam(value = "ownerName", required = false) String ownerName,
    		@RequestParam(value = "fecActRange", required = false, defaultValue = "") String fecActRange,
    		@RequestParam(value = "local", required = true) String local,
    		@RequestParam(value = "llinSeason", required = true) String llinSeason,
    		@RequestParam(value = "llinStatus", required = true) String llinStatus
    		) throws ParseException {
        logger.info("Obteniendo resultados");
        Long desde = null;
        Long hasta = null;
        
        if (!fecActRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecActRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecActRange.substring(fecActRange.length()-10, fecActRange.length())).getTime();
        }
        List<EntregaTarget> datos = this.entregaTargetService.getMetasFiltro(codeHouse, ownerName, desde, hasta, local, 
        		llinSeason, llinStatus, SecurityContextHolder.getContext().getAuthentication().getName(),null);
        if (datos == null){
        	logger.debug("Nulo");
        }
        else {
        	for (EntregaTarget meta:datos) {
        		MessageResource mr = null;
        		String descCatalogo = null;
        		mr = this.messageResourceService.getMensaje(meta.getStatus(),"CAT_STATUS_LLIN");
        		if(mr!=null) {
        			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
        			meta.setStatus(descCatalogo);
        		}
        	}
        }
        return datos;
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
