package org.clintonhealthaccess.vca.movil.controller;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Foco;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.relationships.UsuarioLocalidad;
import org.clintonhealthaccess.vca.service.FocoService;
import org.clintonhealthaccess.vca.service.LocalidadService;
import org.clintonhealthaccess.vca.service.UsuarioService;
import org.clintonhealthaccess.vca.users.model.Authority;
import org.clintonhealthaccess.vca.users.model.UserSistema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador que provee los mapeos de la aplicacion movil para:
 * 
 * <ul>
 * <li>Ingreso de usuario
 * </ul>
 * 
 * @author William Aviles
 **/
@Controller
@RequestMapping("/movil/*")
public class UsuariosMovilController {
	@Resource(name="usuarioService")
	private UsuarioService usuarioService;
	@Resource(name="localidadService")
	private LocalidadService localidadService;
	@Resource(name="focoService")
	private FocoService focoService;
	private static final Logger logger = LoggerFactory.getLogger(UsuariosMovilController.class);
	
	/**
     * Retorna un usuario. Acepta una solicitud GET para JSON
     * @param username Nombre del usuario.
     * @return <code>UserSistema</code>
     */
	@RequestMapping(value = "ingreso/{username}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody UserSistema getMessage(@PathVariable String username) {
    	logger.info("Accessando a la aplicacion " + username);
    	UserSistema user = usuarioService.getUser(username);
    	return user;
    }
	
    /**
     * Retorna roles. Acepta una solicitud GET para JSON
     * @param username Nombre del usuario.
     * @return roles JSON
     */
    @RequestMapping(value = "roles/{username}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Authority> descargarRolesUsuario(@PathVariable String username) {
        logger.info("Descargando toda la informacion de los datos de los roles para el usuario "+username);
        List<Authority> roles = usuarioService.getRolesUsuario(username);
        if (roles == null){
        	logger.debug(new Date() + " - Nulo");
        }
        else{
        	for (Authority auth : roles) {
        		auth.setUser(null);
        		auth.setRol(null);
        	}
        }
        return roles;	
    }
    
    /**
     * Retorna roles. Acepta una solicitud GET para JSON
     * @param username Nombre del usuario.
     * @return roles JSON
     */
    @RequestMapping(value = "localidades/{username}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Localidad> descargarLocalidadesUsuario(@PathVariable String username) {
        logger.info("Descargando toda la informacion de los datos de las localidades para el usuario "+username);
        List<Localidad> localidades = localidadService.getLocalities();
        if (localidades == null){
        	logger.debug(new Date() + " - Nulo");
        }else {
        	for(Localidad localidad:localidades) {
        		UsuarioLocalidad uloc = usuarioService.getUsuarioLocalidadActivo(username, localidad.getIdent());
        		if (uloc==null) {
        			localidad.setTieneAcceso(false);
        		}
        		else {
        			localidad.setTieneAcceso(true);
        		}
        		List<Foco> focos = focoService.getFocosLocalidad(localidad.getIdent());
        		String foco = "";
        		for (Foco foc: focos) {
        			if(!foco.matches("")) {
        				foco = foco + " - " + foc.getName();
        			}else {
        				foco = foc.getName();
        			}
        		}
        		if(!foco.matches("")) {
        			localidad.setName(localidad.getName() + " - " + foco);
        		}
        	}
        }
        
        return localidades;	
    }
       
}
