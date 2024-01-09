package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saneamiento.models.dao.IFormulario;
import com.saneamiento.models.entity.Formulario;

@Service
public class FormularioServiceImpl implements IFormularioService {
	
	@Autowired
	private IFormulario formularioDao;

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

}
