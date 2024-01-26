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

	//OPCION CON ENTITY
	@Query("SELECT f FROM Formulario f WHERE f.tipoSaneoFormulario.id = :tipoSaneoId")
	List<Formulario> getFormulariosByIdTipoSaneo(@Param("tipoSaneoId") Long tipoSaneoId);
	
	// ***************** SOLICITUD FORMULARIO *****************	
	@Query("SELECT fp FROM FormularioPregunta fp WHERE fp.formulario.id = :formulario_id AND fp.order_data = :order_data")
	public List<FormularioPregunta> getFormularioPreguntaByTipoSaneoByTipoDato(@Param("formulario_id") Long formulario_id, @Param("order_data") String order_data);

	
	//OPCION CON NATIVO
	//@Query(value = "SELECT * FROM formulario WHERE tipo_saneo_id = :tipoSaneoId", nativeQuery = true)
	//List<Formulario> getFormulariosByIdTipoSaneo(@Param("tipoSaneoId") Long tipoSaneoId);


	
}
