package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.TipoSaneo;

public interface ITipoSaneoService {

	public List<TipoSaneo> findAll();
	
	public TipoSaneo findById(Long id);
	
	public TipoSaneo save(TipoSaneo tipoSaneo);
}
