package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.entity.FormularioPregunta;

public interface IFormularioService {

	public List<Formulario> findAll();
	
	public Formulario save(Formulario formulario);
	
	public Formulario findById(Long id);

	//***************** PREGUNTA FORMULARIO *****************
	public List<FormularioPregunta> getFormularioPregunta(Long formulario_id);
	
	
}
