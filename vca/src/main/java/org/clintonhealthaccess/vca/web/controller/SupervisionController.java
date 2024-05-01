package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.IrsSeasonService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
import org.clintonhealthaccess.vca.service.PersonalService;
import org.clintonhealthaccess.vca.service.SupervisionService;
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
@RequestMapping("/irs/supervision/*")
public class SupervisionController {
	private static final Logger logger = LoggerFactory.getLogger(SupervisionController.class);
	@Resource(name="temporadaService")
	private IrsSeasonService temporadaService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="supervisionService")
	private SupervisionService supervisionService;
	@Resource(name="personalService")
	private PersonalService personalService;

    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
		logger.debug("Mostrando Supervisionas en JSP");
		List<Localidad> localidades = localidadService.getActiveLocalitiesUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
    	model.addAttribute("localidades", localidades);
    	List<Personal> rociadores = personalService.getActiveRociadores();
    	model.addAttribute("rociadores", rociadores);
    	List<Personal> supervisores = personalService.getActiveSupervisores();
    	model.addAttribute("supervisores", supervisores);
    	List<IrsSeason> temporadas = this.temporadaService.getIrsSeasons();
    	model.addAttribute("temporadas",temporadas);
    	return "irs/supervision";
	}
    
    
    @RequestMapping(value = "/searchSupervisions/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Supervision> fetchSupervisions(@RequestParam(value = "codeHouse", required = false) String codeHouse,
    		@RequestParam(value = "ownerName", required = false) String ownerName,
    		@RequestParam(value = "fecSupervisionRange", required = false, defaultValue = "") String fecSupervisionRange,
    		@RequestParam(value = "local", required = true) String local,
    		@RequestParam(value = "supervisor", required = true) String supervisor,
    		@RequestParam(value = "irsSeason", required = true) String irsSeason,
    		@RequestParam(value = "rociador", required = true) String rociador
    		) throws ParseException {
        logger.info("Obteniendo resultados");
        Long desde = null;
        Long hasta = null;
        
        if (!fecSupervisionRange.matches("")) {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	desde = formatter.parse(fecSupervisionRange.substring(0, 10)).getTime();
        	hasta = formatter.parse(fecSupervisionRange.substring(fecSupervisionRange.length()-10, fecSupervisionRange.length())).getTime();
        }
        List<Supervision> datos = supervisionService.getSupervisionsFiltro(codeHouse, ownerName, desde, hasta, local, 
        		irsSeason, supervisor, rociador, SecurityContextHolder.getContext().getAuthentication().getName(),null);
        if (datos == null){
        	logger.debug("Nulo");
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
    	Supervision supervision = this.supervisionService.getSupervision(ident);
        if(supervision==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("irs/viewSupervisionForm");
        	MessageResource mr = null;
    		String descCatalogo = null;
    		mr = this.messageResourceService.getMensaje(supervision.getUsoEqProt(),"CAT_SINO");
    		if(mr!=null) {
    			descCatalogo = (LocaleContextHolder.getLocale().getLanguage().equals("en")) ? mr.getEnglish(): mr.getSpanish();
    			supervision.setUsoEqProt(descCatalogo);
    		}
        	mav.addObject("supervision",supervision);
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
    	Supervision supervision = this.supervisionService.getSupervision(ident);
		if(supervision!=null){
			List<Personal> rociadores = personalService.getActiveRociadores();
	    	model.addAttribute("rociadores", rociadores);
	    	List<Personal> supervisores = personalService.getActiveSupervisores();
	    	model.addAttribute("supervisores", supervisores);
	    	List<MessageResource> sinos = messageResourceService.getCatalogo("CAT_SINO");
	    	model.addAttribute("sinos",sinos);
			model.addAttribute("supervision",supervision);
			return "irs/enterSupervisionForm";
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
		Supervision supervision = this.supervisionService.getSupervision(ident);
    	if(supervision!=null){
    		supervision.setPasive('1');
    		this.supervisionService.saveSupervision(supervision);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", supervision.getTarget().getHousehold().getCode());
    		redirecTo = "redirect:/irs/supervision/"+supervision.getIdent()+"/";
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
    	Supervision supervision = this.supervisionService.getSupervision(ident);
    	if(supervision!=null){
    		supervision.setPasive('0');
    		this.supervisionService.saveSupervision(supervision);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", supervision.getTarget().getHousehold().getCode());
    		redirecTo = "redirect:/irs/supervision/"+supervision.getIdent()+"/";
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
    @RequestMapping( value="/saveEntity/", method=RequestMethod.POST)
	public ResponseEntity<String> processEntity( @RequestParam(value="ident", required=true) String ident
			, @RequestParam( value="target", required=true ) String target
	        , @RequestParam( value="supervisionDate", required=true ) String supervisionDate
	        , @RequestParam( value="rociador", required=true ) String rociador
	        , @RequestParam( value="supervisor", required=true) String supervisor
	        , @RequestParam( value="usoEqProt", required=false) String usoEqProt
	        
	        , @RequestParam( value="eqProtBien", required=false) String eqProtBien
	        , @RequestParam( value="numIden", required=false) String numIden
	        , @RequestParam( value="aguaOp", required=false) String aguaOp
	        , @RequestParam( value="prepViv", required=false) String prepViv
	        , @RequestParam( value="coopPrepViv", required=false) String coopPrepViv
	        , @RequestParam( value="mezcla", required=false) String mezcla
	        , @RequestParam( value="aguaAdec", required=false) String aguaAdec
	        , @RequestParam( value="mezclaPrep", required=false) String mezclaPrep
	        , @RequestParam( value="agitaBomba", required=false) String agitaBomba
	        , @RequestParam( value="bombaCerrada", required=false) String bombaCerrada
	        
	        , @RequestParam( value="bombaPresion", required=false) String bombaPresion
	        , @RequestParam( value="compruebaBomba", required=false) String compruebaBomba
	        , @RequestParam( value="colocApropiada", required=false) String colocApropiada
	        , @RequestParam( value="distApropiada", required=false) String distApropiada
	        , @RequestParam( value="distBoquilla", required=false) String distBoquilla
	        , @RequestParam( value="pasoFrente", required=false) String pasoFrente
	        , @RequestParam( value="mantRitmo", required=false) String mantRitmo
	        , @RequestParam( value="metConteo", required=false) String metConteo
	        , @RequestParam( value="velocSuperficies", required=false) String velocSuperficies
	        , @RequestParam( value="supFajas", required=false) String supFajas
	        
	        , @RequestParam( value="pasosLaterales", required=false) String pasosLaterales
	        , @RequestParam( value="salvarObstaculos", required=false) String salvarObstaculos
	        , @RequestParam( value="bienRociado", required=false) String bienRociado
	        , @RequestParam( value="supInvertidas", required=false) String supInvertidas
	        , @RequestParam( value="objPiso", required=false) String objPiso
	        , @RequestParam( value="reportaConsumoAprop", required=false) String reportaConsumoAprop
	        , @RequestParam( value="transEqAprop", required=false) String transEqAprop
	        , @RequestParam( value="eqCompleto", required=false) String eqCompleto
	        , @RequestParam( value="cuidaMatEq", required=false) String cuidaMatEq
	        , @RequestParam( value="buenAspPersonal", required=false) String buenAspPersonal
	        
	        , @RequestParam( value="cumpleInstrucciones", required=false) String cumpleInstrucciones
	        , @RequestParam( value="aceptaSuperv", required=false) String aceptaSuperv
	        , @RequestParam( value="respetuoso", required=false) String respetuoso
	        , @RequestParam( value="camp", required=false) String camp
	        , @RequestParam( value="obs", required=false, defaultValue ="" ) String obs
	        )
	{
    	try{
    		
    		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    		Date fechaSupervision =  null;
    		if (!supervisionDate.equals("")) fechaSupervision = formatter.parse(supervisionDate);
    		
    		Supervision supervision = this.supervisionService.getSupervision(ident);
    		supervision.setSupervisionDate(fechaSupervision);
    		supervision.setRociador(this.personalService.getPersonal(rociador));
    		supervision.setSupervisor(this.personalService.getPersonal(supervisor));
    		supervision.setUsoEqProt(usoEqProt);
    		
    		supervision.setEqProtBien(eqProtBien);
    		supervision.setNumIden(numIden);
    		supervision.setAguaOp(aguaOp);
    		supervision.setPrepViv(prepViv);
    		supervision.setCoopPrepViv(coopPrepViv);
    		supervision.setMezcla(mezcla);
    		supervision.setAguaAdec(aguaAdec);
    		supervision.setMezclaPrep(mezclaPrep);
    		supervision.setAgitaBomba(agitaBomba);
    		supervision.setBombaCerrada(bombaCerrada);
    		
    		supervision.setBombaPresion(bombaPresion);
    		supervision.setCompruebaBomba(compruebaBomba);
    		supervision.setColocApropiada(colocApropiada);
    		supervision.setDistApropiada(distApropiada);
    		supervision.setDistBoquilla(distBoquilla);
    		supervision.setPasoFrente(pasoFrente);
    		supervision.setMantRitmo(mantRitmo);
    		supervision.setMetConteo(metConteo);
    		supervision.setVelocSuperficies(velocSuperficies);
    		supervision.setSupFajas(supFajas);
    		
    		supervision.setPasosLaterales(pasosLaterales);
    		supervision.setSalvarObstaculos(salvarObstaculos);
    		supervision.setBienRociado(bienRociado);
    		supervision.setSupInvertidas(supInvertidas);
    		supervision.setObjPiso(objPiso);
    		supervision.setReportaConsumoAprop(reportaConsumoAprop);
    		supervision.setTransEqAprop(transEqAprop);
    		supervision.setEqCompleto(eqCompleto);
    		supervision.setCuidaMatEq(cuidaMatEq);
    		supervision.setBuenAspPersonal(buenAspPersonal);
    		
    		
    		
    		supervision.setCumpleInstrucciones(cumpleInstrucciones);
    		supervision.setAceptaSuperv(aceptaSuperv);
    		supervision.setRespetuoso(respetuoso);
    		
    		
    		
    		
    		this.supervisionService.saveSupervision(supervision);
    		
			return createJsonResponse(supervision);
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
    
	
}
