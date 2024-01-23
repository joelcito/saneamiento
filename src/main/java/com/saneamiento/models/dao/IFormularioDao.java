package com.saneamiento.models.dao;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Formulario;
import com.saneamiento.models.entity.FormularioPregunta;

public interface IFormularioDao extends CrudRepository<Formulario, Long> {
	
	// ***************** DETALLE TIPO SANEO *****************
	@Query("SELECT fp FROM FormularioPregunta fp WHERE fp.formulario.id = :formulario_id")
	public List<FormularioPregunta> getFormularioPregunta(@Param("formulario_id") Long formulario_id);
	
}
