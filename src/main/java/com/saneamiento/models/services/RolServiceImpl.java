package com.saneamiento.models.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.IRolDao;
import com.saneamiento.models.entity.Rol;


@Service
public class RolServiceImpl implements IRolService{
	
	@Autowired
	private IRolDao rolDao;

	@Override
	@Transactional(readOnly = true)
	public List<Rol> findAll() {
		return (List<Rol>)this.rolDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Rol findById(Long id) {
		return this.rolDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Rol save(Rol rol) {
		return this.rolDao.save(rol);
	}

	@Override
	@Transactional
	public int deleteUsuario(Long id, LocalDateTime fecha) {
		return this.rolDao.deleteUsuario(id, fecha);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Rol> listadoRolVigentes() {
		return this.rolDao.listadoRolVigentes();
	}

}
