package org.clintonhealthaccess.vca.web.controller;

import com.google.gson.Gson;

import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.service.AuditTrailService;
import org.clintonhealthaccess.vca.service.UsuarioService;
import org.clintonhealthaccess.vca.users.model.Authority;
import org.clintonhealthaccess.vca.users.model.UserAccess;
import org.clintonhealthaccess.vca.users.model.UserSistema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Controlador web de peticiones relacionadas a usuarios
 * 
 * @author William Aviles
 */
@Controller
@RequestMapping("/users/*")
public class UsuariosController {
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="auditTrailService")
	private AuditTrailService auditTrailService;
	
	
	@RequestMapping(value="checkcredential", method=RequestMethod.GET)
	public @ResponseBody boolean getCredential(@RequestParam String userName) {
	    return this.usuarioService.checkCredential(userName);
	}
	
	@RequestMapping(value="checkForcedChangePass", method=RequestMethod.GET)
	public @ResponseBody boolean getChangeCredential(@RequestParam String userName) {
	    return this.usuarioService.checkChangeCredential(userName);
	}
	
	@RequestMapping(value = "forcechgpass", method = RequestMethod.GET)
    public String initForceChangePassForm() {
	    return "forceChgPass";
    }
	
	
	/**
     * Custom handler for displaying a user.
     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("profile")
    public ModelAndView showUser() {
        ModelAndView mav = new ModelAndView("users/viewForm");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSistema user = this.usuarioService.getUser(authentication.getName());
        List<UserAccess> accesosUsuario = usuarioService.getUserAccess(authentication.getName());
        List<AuditTrail> bitacoraUsuario = auditTrailService.getBitacora(authentication.getName());
        mav.addObject("user",user);
        mav.addObject("accesses",accesosUsuario);
        mav.addObject("bitacora",bitacoraUsuario);
        List<Authority> rolesusuario = this.usuarioService.getRolesUsuarioTodos(authentication.getName());
        mav.addObject("rolesusuario", rolesusuario);
        return mav;
    }
    
    
    /**
     * Custom handler for editing a user.
     *     *
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("editUser")
    public ModelAndView editUser() {
        ModelAndView mav = new ModelAndView("users/editForm");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSistema user = this.usuarioService.getUser(authentication.getName());
        mav.addObject("user",user);
        return mav;
    }
    
    
    @RequestMapping( value="saveUser", method=RequestMethod.POST)
	public ResponseEntity<String> processUpdateUserForm( @RequestParam(value="username", required=true ) String userName
	        , @RequestParam( value="completeName", required=true ) String completeName
	        , @RequestParam( value="email", required=true, defaultValue="" ) String email
	        )
	{
    	try{
	    	UserSistema user = this.usuarioService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
	    	user.setModifiedBy(user.getUsername());
			user.setCompleteName(completeName);
			user.setEmail(email);
			user.setModified(new Date());
			this.usuarioService.saveUser(user);
			return createJsonResponse(user);
    	}
    	catch(Exception e){
    		Gson gson = new Gson();
    	    String json = gson.toJson(e.toString());
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
    	}
	}
    
    @RequestMapping(value = "chgpass", method = RequestMethod.GET)
    public String initChangePassForm() {
	    return "users/passForm";
    }
    
    @RequestMapping(value = "resetpass", method = RequestMethod.GET)
    public String initChangeResetPassForm() {
	    return "users/updatePassForm";
    }
    
    @RequestMapping( value="chgPass", method=RequestMethod.POST)
	public ResponseEntity<String> processChangePassForm( @RequestParam( value="password", required=true ) String password
	        )
	{
    	try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserSistema user = usuarioService.getUser(authentication.getName());
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String encodedPass = encoder.encode(password);
		user.setModifiedBy(authentication.getName());
		user.setModified(new Date());
		user.setPassword(encodedPass);
		user.setLastCredentialChange(new Date());
		user.setCredentialsNonExpired(true);
		user.setChangePasswordNextLogin(false);
		this.usuarioService.saveUser(user);
		return createJsonResponse(user);
    	}
    	catch(Exception e){
    		Gson gson = new Gson();
    	    String json = gson.toJson(e.toString());
    		return new ResponseEntity<String>( json, HttpStatus.CREATED);
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
