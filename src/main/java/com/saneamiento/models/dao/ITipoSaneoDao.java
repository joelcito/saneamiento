package com.saneamiento.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.saneamiento.models.entity.TipoSaneo;

public interface ITipoSaneoDao extends CrudRepository<TipoSaneo, Long>{

}