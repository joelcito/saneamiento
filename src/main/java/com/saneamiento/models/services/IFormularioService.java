package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Formulario;

public interface IFormularioService {

	public List<Formulario> findAll();
	
	public Formulario save(Formulario formulario);
	
	public Formulario findById(Long id);
}
