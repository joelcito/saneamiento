package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.IFormularioDao;
import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.entity.FormularioPregunta;

@Service
public class FormularioServiceImpl implements IFormularioService {
	
	@Autowired
	private IFormularioDao formularioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Formulario> findAll() {
		return (List<Formulario>) this.formularioDao.findAll();
	}

	@Override
	@Transactional
	public Formulario save(Formulario formulario) {
		return this.formularioDao.save(formulario);
	}

	@Override
	@Transactional(readOnly = true)
	public Formulario findById(Long id) {
		return this.formularioDao.findById(id).orElse(null);
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<Formulario> getFormulariosByIdTipoSaneo(Long tipoSaneoId) {
		return this.formularioDao.getFormulariosByIdTipoSaneo(tipoSaneoId);
	}

	
	//***************** PREGUNTA FORMULARIO *****************
	@Override
	@Transactional(readOnly = true)
	public List<FormularioPregunta> getFormularioPregunta(Long formulario_id) {
		return this.formularioDao.getFormularioPregunta(formulario_id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormularioPregunta> getFormularioPreguntaByTipoSaneoByTipoDato(Long formulario_id, String order_data) {
		return this.formularioDao.getFormularioPreguntaByTipoSaneoByTipoDato(formulario_id, order_data);
	}


}
