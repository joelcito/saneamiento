package com.saneamiento.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;
import com.saneamiento.models.services.IRolService;
import com.saneamiento.models.services.IUsuarioService;

@RestController
@RequestMapping("/api/rol")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RolRestController {
	
	@Autowired
	private IRolService rolService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/listado")
	public List<Rol> listado() {
		return this.rolService.findAll();
	}
	
	@PostMapping("/")
	public Rol save(@RequestBody Map<String, Object> requestBody) {
		Rol newRol = new Rol();
		newRol.setNombre(requestBody.get("nombre").toString());
		return this.rolService.save(newRol);
	}
	
	@PutMapping("/{id}")
	public Rol update(@RequestBody Map<String, Object> requesBody, @PathVariable Long id) {
		Rol rolBuscado = this.rolService.findById(id);
		rolBuscado.setNombre(requesBody.get("nombre").toString());
		return this.rolService.save(rolBuscado);
	}
	
	@PostMapping("/user_rol/{id}")
	public Rol updateRolUser(@RequestBody Map<String, Object> requestBody, @PathVariable Long id) {
		Rol rolBuscado = this.rolService.findById(id);
		
		List<Rol> lisRoy  = new ArrayList<>();
		List<Long> rolesSeleccionadosIds = new ArrayList<>();
		Usuario usuarioBuscado 		= this.usuarioService.findById(id);
		
		requestBody.forEach((rol, valor) -> {
			
			String[] partes 			= rol.split("_");			
			Long rol_id 				= Long.parseLong(partes[1]);
			
			Rol rolBuscadoo 			= this.rolService.findById(rol_id);
			
			Boolean vav 				= (Boolean) valor; 
			UsuarioRol userRolBuscado 	= this.usuarioService.getPermisosUser(usuarioBuscado, rolBuscadoo);
			
			if(vav) {
				lisRoy.add(rolBuscadoo);
				rolesSeleccionadosIds.add(rol_id);
			}
			 
        });

		usuarioBuscado.setRoles(lisRoy);		
		this.usuarioService.save(usuarioBuscado);
		
		// ********* PARA MODIFICAR LOS ROLES Y LOS USUARIOS
		List<UsuarioRol> datos = this.usuarioService.getRolesUser(usuarioBuscado);
		
		for(UsuarioRol useRol : datos) {
			Rol newRol =  rolService.findById(useRol.getRol().getId());
			this.usuarioService.actualizarMenusUsuarioRol(useRol.getId(), newRol.getMenus());
		}	
		
		return rolBuscado ;
	}

}
