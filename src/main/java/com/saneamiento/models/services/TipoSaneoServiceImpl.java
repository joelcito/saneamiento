package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.ITipoSaneoDao;
import com.saneamiento.models.entity.TipoSaneo;

@Service
public class TipoSaneoServiceImpl implements ITipoSaneoService{

	@Autowired
	private ITipoSaneoDao tipoSaneoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoSaneo> findAll() {
		return (List<TipoSaneo>) this.tipoSaneoDao.findAll();
	}

	@Override
	public TipoSaneo findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
