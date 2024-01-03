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
	@Transactional(readOnly = true)
	public TipoSaneo findById(Long id) {
		return this.tipoSaneoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoSaneo save(TipoSaneo tipoSaneo) {
		return this.tipoSaneoDao.save(tipoSaneo);
	}

	
}
