package org.clintonhealthaccess.vca.web.controller;

import org.clintonhealthaccess.vca.domain.Parametro;
import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.service.ParametroService;
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
 * Controlador web de peticiones relacionadas a parametros
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/admin/parameters/*")
public class AdminParametrosController {
	private static final Logger logger = LoggerFactory.getLogger(AdminParametrosController.class);
	@Resource(name="parametroService")
	private ParametroService parametroService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	@Resource(name="messageResourceService")
	private MessageResourceService messageResourceService;
    
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String getEntities(Model model) throws ParseException { 	
    	logger.debug("Mostrando Parametros en JSP");
    	List<Parametro> parametros = parametroService.getParametros();
    	model.addAttribute("parametros", parametros);
    	return "admin/parametros/list";
	}
	
	/**
     * Custom handler for adding.
     * @param model Modelo enlazado a la vista
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/newEntity/", method = RequestMethod.GET)
	public String addEntity(Model model) {
    	model.addAttribute("nuevo","1");
    	return "admin/parametros/enterForm";
	}
    
    
    /**
     * Custom handler for editing.
     * @param model Modelo enlazado a la vista
     * @param ident the ID to edit
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/editEntity/{ident}/", method = RequestMethod.GET)
	public String editEntity(@PathVariable("ident") String ident, Model model) {
		Parametro parametro = this.parametroService.getParametro(ident);
		if(parametro!=null){
			model.addAttribute("nuevo","0");
			model.addAttribute("parametro",parametro);
			return "admin/parametros/enterForm";
		}
		else{
			return "403";
		}
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
    	Parametro parametro = this.parametroService.getParametro(ident);
        if(parametro==null){
        	mav = new ModelAndView("403");
        }
        else{
        	mav = new ModelAndView("admin/parametros/viewForm");
        	mav.addObject("parametro",parametro);
            List<AuditTrail> bitacora = auditTrailService.getBitacora(ident);
            mav.addObject("bitacora",bitacora);
        }
        return mav;
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
	        , @RequestParam( value="value", required=true ) String value
	        )
	{
    	try{
			Parametro parametro = new Parametro();
			//Si el ident viene en blanco es nuevo
			if (ident.equals("")){
				//Crear nuevo
				ident = new UUID(SecurityContextHolder.getContext().getAuthentication().getName().hashCode(),new Date().hashCode()).toString();
				parametro.setIdent(ident);
				parametro.setCode(code);
				parametro.setName(name);
				parametro.setValue(value);
				parametro.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
				parametro.setRecordDate(new Date());
				//Guardar nuevo
				this.parametroService.saveParametro(parametro);
			}
			//Si el ident no viene en blanco hay que editar
			else{
				//Recupera de la base de datos
				parametro = parametroService.getParametro(ident);
				parametro.setCode(code);
				parametro.setName(name);
				parametro.setValue(value);
				//Actualiza
				this.parametroService.saveParametro(parametro);
			}
			return createJsonResponse(parametro);
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
		Parametro parametro = this.parametroService.getParametro(ident);
    	if(parametro!=null){
    		parametro.setPasive('1');
    		this.parametroService.saveParametro(parametro);
    		redirectAttributes.addFlashAttribute("entidadDeshabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", parametro.getName());
    		redirecTo = "redirect:/admin/parametros/"+parametro.getIdent()+"/";
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
		Parametro parametro = this.parametroService.getParametro(ident);
    	if(parametro!=null){
    		parametro.setPasive('0');
    		this.parametroService.saveParametro(parametro);
    		redirectAttributes.addFlashAttribute("entidadHabilitada", true);
    		redirectAttributes.addFlashAttribute("nombreEntidad", parametro.getName());
    		redirecTo = "redirect:/admin/parametros/"+parametro.getIdent()+"/";
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
