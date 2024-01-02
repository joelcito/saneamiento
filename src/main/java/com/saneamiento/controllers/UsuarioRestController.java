package com.saneamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.services.IUsuarioService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("")
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
}
