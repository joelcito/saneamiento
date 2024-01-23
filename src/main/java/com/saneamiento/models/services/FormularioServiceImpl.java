package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saneamiento.models.dao.IFormularioDao;
import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.entity.FormularioPregunta;

@Service
public class FormularioServiceImpl implements IFormularioService {
	
	@Autowired
	private IFormularioDao formularioDao;

	@Override
	public List<Formulario> findAll() {
		return (List<Formulario>) this.formularioDao.findAll();
	}

	@Override
	public Formulario save(Formulario formulario) {
		return this.formularioDao.save(formulario);
	}

	@Override
	public Formulario findById(Long id) {
		return this.formularioDao.findById(id).orElse(null);
	}

	
	//***************** PREGUNTA FORMULARIO *****************
	@Override
	public List<FormularioPregunta> getFormularioPregunta(Long formulario_id) {
		return this.formularioDao.getFormularioPregunta(formulario_id);
	}

}
