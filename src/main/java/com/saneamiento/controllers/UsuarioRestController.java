package com.saneamiento.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;
import com.saneamiento.models.services.IRolService;
import com.saneamiento.models.services.IUsuarioService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRolService rolService;
	
	@GetMapping("/listado")
	public List<Usuario> index() {
		return this.usuarioService.findAll();
	}
	
	@PostMapping("/detalle")
	public String demo() {
		return "ESTE ES LA DEMO CHEE";
	}
	
	@PostMapping("/{id}")
	public Usuario findById(@PathVariable Long id) {
		return this.usuarioService.findById(id);
	}
	
	@PostMapping("/menu")
	public UsuarioRol menu(@RequestBody Map<String, Object> requesBody){		
		Long usuario = ((Integer) requesBody.get("usuario")).longValue();
		Long rol = ((Integer) requesBody.get("rol")).longValue();		
		Usuario usuarioBuscado = this.usuarioService.findById(usuario);
		Rol rolBuscado = this.rolService.findById(rol);				
		return this.usuarioService.getPermisosUser(usuarioBuscado , rolBuscado);
    }
}
