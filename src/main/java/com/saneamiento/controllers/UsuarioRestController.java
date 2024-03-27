package com.saneamiento.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;
import com.saneamiento.models.services.IRolService;
import com.saneamiento.models.services.IUsuarioService;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRolService rolService;
	
	@GetMapping("/listado")
	public List<Usuario> index() {
		//return this.usuarioService.findAll();
		return this.usuarioService.listadoUsuarioVigentes();
	}
	
	@PostMapping("/detalle")
	public String demo() {
		return "ESTE ES LA DEMO CHEE";
	}
	
	@PostMapping("/{id}")
	public Usuario findById(@PathVariable Long id) {
		return this.usuarioService.findById(id);
	}
	
	@DeleteMapping("/deleteUsuer/{id}")
	public int deleteUsuario(@PathVariable Long id){
		LocalDateTime fechaYHoraActual = LocalDateTime.now();
		return this.usuarioService.deleteUsuario(id, fechaYHoraActual);
	}
	
	@PostMapping("/menu")
	public UsuarioRol menu(@RequestBody Map<String, Object> requesBody){		
		Long usuario = ((Integer) requesBody.get("usuario")).longValue();
		Long rol = ((Integer) requesBody.get("rol")).longValue();		
		Usuario usuarioBuscado = this.usuarioService.findById(usuario);
		Rol rolBuscado = this.rolService.findById(rol);				
		return this.usuarioService.getPermisosUser(usuarioBuscado , rolBuscado);
    }
	
	@GetMapping("/roles_user/{user_id}")
	public List<UsuarioRol> getRolesUser(@PathVariable Long user_id){
		Usuario user = this.usuarioService.findById(user_id);
		return this.usuarioService.getRolesUser(user);
	}
	
	@PostMapping("/saveMenuUserById")
	public UsuarioRol saveMenuUserById(@RequestBody Map<String, Object> requesBody) {
		
		System.out.println("faafa => "+requesBody);
		
		Long usuario_id 	= ((Integer) requesBody.get("userid")).longValue();
		Long rol_id 		= ((Integer) requesBody.get("rolid")).longValue();
		Long usuario_rol_id = ((Integer) requesBody.get("usuariorol")).longValue();
		
		Usuario userBuscado = this.usuarioService.findById(usuario_id);
		Rol rolBuscado 		= this.rolService.findById(rol_id);
		
		List<Map<String, Object>> listaPadre =  new ArrayList<>();
		List<Map<String, Object>> listaHijos =  new ArrayList<>();
		
		UsuarioRol usuarioRolBuscado = this.usuarioService.getPermisosUser(userBuscado, rolBuscado);
		
		//System.out.println(usuarioRolBuscado.getMenus());
		
		requesBody.forEach((nodo, valor) ->{			
			
			//System.out.println("********************************");
			//System.out.println(nodo+" : "+valor);
			
			String[] partes 			= nodo.split("_");
			
			if(partes.length > 2) {
				//System.out.println(partes[0]+" "+partes[1]+" "+partes[2]);	
				 Map<String, Object> dato = new HashMap<>();
				 if(partes[2].equals("padre")) {
					 dato.put(partes[0], valor);
					 listaPadre.add(dato);
				 }else {
					 dato.put(partes[0], valor);
					 listaHijos.add(dato);
				 }			 
			}
			
			//Long rol_id 				= Long.parseLong(partes[1]);
			//System.out.println(valor);
		});
		
		System.out.println("----------------------------------------");
		System.out.println(listaHijos);
		System.out.println(listaPadre);
		
		
		//String json = usuarioRolBuscado.getMenus(); 
		
		//String jsonString = "[{\"name\":\"Home\",\"icon\":\"home\",\"route\":\"/home\",\"active\":false,\"subMenus\":[{\"name\":\"Listado\",\"url\":\"home\",\"active\":false}]},{\"name\":\"Usuario\",\"icon\":\"person\",\"route\":\"/profile\",\"active\":true,\"subMenus\":[{\"name\":\"Listado Usuarios\",\"url\":\"usuario\",\"active\":true},{\"name\":\"Listado Rol\",\"url\":\"rol\",\"active\":true}]},{\"name\":\"Tipo Saneo\",\"icon\":\"find_in_page\",\"route\":\"/home\",\"active\":true,\"subMenus\":[{\"name\":\"Listado\",\"url\":\"tipo_saneo\",\"active\":true}]}]";
		String jsonString = usuarioRolBuscado.getMenus();

		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(jsonString);

	        // Modificar los valores 'active' según tu lógica
	        for (JsonNode node : jsonNode) {
	      	        	    	
	            
	            String name = node.get("name").asText();
               // boolean isActive = node.get("active").asBoolean();
                //System.out.println("Name: " + name + ", Active: " + isActive);
                
                //Object valorSubMenu = buscarValor(listaPadre,name);
                String nameEditadoPadre = name.trim().replaceAll("\\s+", "");
                Boolean valorSubMenuPadre = buscarValor(listaPadre,nameEditadoPadre);
                
            	//if(valorSubMenu){
            		// Modifica los valores de 'active' según tu lógica
    	            ((ObjectNode) node).put("active", valorSubMenuPadre);
            		//System.out.println("boo => "+valorSubMenu);
	        	//}
    	           
	            // Modificar los valores 'active' de los submenús si existen
	            if (node.has("subMenus")) {
	                JsonNode subMenus = node.get("subMenus");
	                for (JsonNode subMenu : subMenus) {
	                	
	                	String subMenuName = subMenu.get("name").asText();
                        //boolean subMenuIsActive = subMenu.get("active").asBoolean();
                        //System.out.println("\tSubMenu Name: " + subMenuName + ", Active: " + subMenuIsActive);
                        
                        String nameEditadoHijo = subMenuName.trim().replaceAll("\\s+", "");
                        Boolean valorSubMenuHijo = buscarValor(listaHijos,nameEditadoHijo);
                        //System.out.println("=> "+nameEditadoHijo);
                    
	                    ((ObjectNode) subMenu).put("active", valorSubMenuHijo); // Ejemplo de modificación
	                }
	            }
	        }

	        // Convertir el JSONNode modificado de nuevo a String
	        String updatedJsonString = objectMapper.writeValueAsString(jsonNode);
	        
	        System.out.println("##################################################");
	        System.out.println(updatedJsonString);
	        this.usuarioService.actualizarMenusUsuarioRol(usuario_rol_id, updatedJsonString);

	        // Guardar el JSON actualizado en tu entidad UsuarioRol
	        //usuarioRolBuscado.setMenus(updatedJsonString);
	        //this.usuarioService.save(usuarioRolBuscado);

	    } catch (Exception e) {
	        e.printStackTrace();
	        // Manejo de errores
	    }
					
		UsuarioRol ur =  new UsuarioRol();
				
		return ur;
	}
	
	@PostMapping("/upDateUsuario/{usuario_id}")
	public Usuario upDateUsuario(@RequestBody Map<String, Object> requestBody,@PathVariable Long usuario_id) {
		Usuario userBuscado = this.usuarioService.findById(usuario_id);
		
		userBuscado.setCedula(requestBody.get("complemento").toString());
		userBuscado.setComplemento(requestBody.get("complemento").toString());
		userBuscado.setNombres(requestBody.get("complemento").toString());
		userBuscado.setPrimer_apellido(requestBody.get("complemento").toString());
		userBuscado.setSegundo_apellido(requestBody.get("complemento").toString());
		userBuscado.setNombre_organizacion(requestBody.get("complemento").toString());
		userBuscado.setNombre_dependencia(requestBody.get("complemento").toString());
		userBuscado.setNombre_cargo(requestBody.get("complemento").toString());
		userBuscado.setDepartamento(requestBody.get("complemento").toString());
		userBuscado.setUsername(requestBody.get("complemento").toString());
		userBuscado.setPassword(requestBody.get("complemento").toString());
		
		this.usuarioService.save(userBuscado);
		
		System.out.println(requestBody);
		System.out.println(usuario_id);
		
		return userBuscado;
	}
	
	
	
	//***************************** FUNCIONES PRIVADAS ****************************
	public static Boolean buscarValor(List<Map<String, Object>> lista, String clave) {
        for (Map<String, Object> mapa : lista) {
          	if(mapa.containsKey(clave)) {
          		//System.out.println(mapa);
          		//System.out.println(mapa.containsKey(clave));
          		//System.out.println(clave);
        		return (Boolean) mapa.get(clave);
          	}
          		
        }
        return false;
    }
	/*
	public static Object buscarValor(List<Map<String, Object>> lista, String clave) {
        for (Map<String, Object> mapa : lista) {
            if (mapa.containsKey(clave)) {
                return mapa.get(clave);
            }
        }
        return null;
    }
    */
	
}
