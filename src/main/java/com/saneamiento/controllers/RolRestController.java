package com.saneamiento.controllers;

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
import com.saneamiento.models.services.IRolService;

@RestController
@RequestMapping("/api/rol")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RolRestController {
	
	@Autowired
	private IRolService rolService;
	
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

}
