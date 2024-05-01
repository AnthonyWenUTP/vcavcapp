package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.language.MessageResource;
import org.clintonhealthaccess.vca.service.PersonalService;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.MessageResourceService;
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

/**
 * Controlador web de peticiones relacionadas a personal
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/personal/*")
public class AdminPersonalController {
	private static final Logger logger = LoggerFactory.getLogger(AdminPersonalController.class);
	@Resource(name="personalService")
	private PersonalService personalService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando Personales en JSP");
    	List<Personal> personal = personalService.getPersonales();
    	model.addAttribute("personal", personal);
    	return "admin/personal/list";
	}
	
	/**
     * Custom handler for adding.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String addEntity(Model model) {
    	List<MessageResource> sinos = messageResourceService.getCatalogo("CAT_SINO");
    	model.addAttribute("sinos",sinos);
    	return "admin/personal/enterForm";
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
    	Personal personal = this.personalService.getPersonal(ident);
        if(personal==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/personal/viewForm");
        	mav.addObject("personal",personal);
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
		Personal personal = this.personalService.getPersonal(ident);
		if(personal!=null){
			model.addAttribute("personal",personal);
			List<MessageResource> sinos = messageResourceService.getCatalogo("CAT_SINO");
	    	model.addAttribute("sinos",sinos);
			return "admin/personal/enterForm";
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
	        , @RequestParam( value="sprayer", required=false, defaultValue="" ) String sprayer
	        , @RequestParam( value="sentinel", required=false, defaultValue="" ) String sentinel
	        , @RequestParam( value="supervisor", required=false, defaultValue="" ) String supervisor
	        )
	{
    	try{
			Personal personal = new Personal();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				personal.setIdent(ident);
				personal.setCode(code);
				personal.setName(name);
				if(sprayer.equals("on")) {
					personal.setSprayer(true);
				}
				else {
					personal.setSprayer(false);
				}
				if(sentinel.equals("on")) {
					personal.setSentinel(true);
				}
				else {
					personal.setSentinel(false);
				}
				if(supervisor.equals("on")) {
					personal.setSupervisor(true);
				}
				else {
					personal.setSupervisor(false);
				}
				personal.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				personal.setRecordDate(new Date());
				//Guardar nuevo
				this.personalService.savePersonal(personal);
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				personal = personalService.getPersonal(ident);
				personal.setCode(code);
				personal.setName(name);
				if(sprayer.equals("on")) {
					personal.setSprayer(true);
				}
				else {
					personal.setSprayer(false);
				}
				if(sentinel.equals("on")) {
					personal.setSentinel(true);
				}
				else {
					personal.setSentinel(false);
				}
				if(supervisor.equals("on")) {
					personal.setSupervisor(true);
				}
				else {
					personal.setSupervisor(false);
				}
			
				//Actualiza
				this.personalService.savePersonal(personal);
			}
			return createJsonResponse(personal);
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
		Personal personal = this.personalService.getPersonal(ident);
    	if(personal!=null){
    		personal.setPasive('1');
    		this.personalService.savePersonal(personal);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", personal.getName());
    		redirecTo = "redirect:/admin/personal/"+personal.getIdent()+"/";
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
		Personal personal = this.personalService.getPersonal(ident);
    	if(personal!=null){
    		personal.setPasive('0');
    		this.personalService.savePersonal(personal);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", personal.getName());
    		redirecTo = "redirect:/admin/personal/"+personal.getIdent()+"/";
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
