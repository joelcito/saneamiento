package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.IValidadorDao;
import com.saneamiento.models.entity.Validador;

@Service
public class ValidadorServiceImpl implements IValidadorService{
	
	@Autowired
	private IValidadorDao validadorDao; 	

	@Override
	@Transactional(readOnly = true)
	public List<Validador> findAll() {
		return (List<Validador>)this.validadorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Validador findByCampoByFormulario(String campo, String formulario) {
		return this.validadorDao.findByCampoByFormulario(campo, formulario);
	}

}
