package com.saneamiento.models.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Regla;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.Tramite;

public interface ISolicitudService {
	
	public List<Solicitud> listadoSolicitudes(Long usuario_asignado_id);
	
	public List<Solicitud> findAll();
	
	public Solicitud save(Solicitud solicitud);
	
	public Solicitud findById(Long id);
	
	public Extranjeria buscaSerialExtranjero(String serialExtRegistros);
	
	public int saveExtranjeria (String serialExtRegistros, String serialDocumentoExtRegistros, String nroCedulaBolExtRegistros);
	
	public int saveTramite (Long detalle_tipo_saneo_id, Long solicitud_id);
	
	public Tramite buscaByTipoSolicitudBySolicitudId(Long solicitud_id,Long detalle_tipo_saneo_id);
	
	public int saveTramiteDetalle(Long tramite_id,String pregunta,String respuesta);
	
	//***************** PARA LA ASIGNACION AUTOMATICA *****************
	//public List<Regla> getReglasVigentes(String horaParam,LocalDate startDate,LocalDate endDate);
	public List<Map<String, Object>> getReglasVigentes(String horaParam,LocalDate startDate,LocalDate endDate);
	
	public int updateReglaAsignacion(String asignacion,Long id);
	
	
	
}
