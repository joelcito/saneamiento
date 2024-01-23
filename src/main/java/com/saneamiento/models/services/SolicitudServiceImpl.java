package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.ISolicitudDao;
import com.saneamiento.models.entity.Solicitud;

@Service
public class SolicitudServiceImpl implements ISolicitudService {
	
	@Autowired
	private ISolicitudDao solicitudDao;

	@Override
	@Transactional(readOnly = true)
	public List<Solicitud> findAll() {
		return (List<Solicitud>)this.solicitudDao.findAll();
	}

	@Override
	 @Transactional
	public Solicitud save(Solicitud solicitud) {
		return this.solicitudDao.save(solicitud);
	}

	@Override
	@Transactional
	public Solicitud findById(Long id) {
		return this.solicitudDao.findById(id).orElse(null);
	}

}
