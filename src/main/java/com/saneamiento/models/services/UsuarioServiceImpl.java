package com.saneamiento.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.IUsuarioDao;
import com.saneamiento.models.entity.Rol;
import com.saneamiento.models.entity.Usuario;
import com.saneamiento.models.entity.UsuarioRol;


@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>)this.usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		return this.usuarioDao.findById(id).orElse(null);				
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioRol getPermisosUser(Usuario usuario, Rol rol) {
		return this.usuarioDao.getPermisosUser(usuario, rol);
	}

	
}
