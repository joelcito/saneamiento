package com.saneamiento.models.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.TemporalSolicitud;
import com.saneamiento.models.entity.Tramite;

public interface ISolicitudService {
	
	public List<Solicitud> listadoSolicitudesAsignados(Long usuario_asignado_id);
	
	public List<Solicitud> listadoSolicitudes(Long usuario_solicitante_id);	
	
	public List<Solicitud> findAll();
	
	public List<Map<String, Object>> verificaSiTieneTramatiesEnviados(String serialextregistros, Long detalle_tipo_saneo_id);
	
	public Solicitud save(Solicitud solicitud);
	
	public Solicitud findById(Long id);
	
	public Extranjeria buscaSerialExtranjero(String serialExtRegistros);
	
	public int saveExtranjeria (String serialExtRegistros, String serialDocumentoExtRegistros, String nroCedulaBolExtRegistros);
	
	public int saveTramite (Long detalle_tipo_saneo_id, Long solicitud_id);
	
	public Tramite buscaByTipoSolicitudBySolicitudId(Long solicitud_id,Long detalle_tipo_saneo_id);
	
	public int saveTramiteDetalle(Long tramite_id,String pregunta,String respuesta);
	
	public Map<String, Object> solicitudesPorIdExtranjero(Long solicitud_id);
	
	public List<Map<String, Object>> tramitesSolicitudesByIdSolicitud(Long solicitud_id);
	
	//***************** PARA LA ASIGNACION AUTOMATICA *****************
	//public List<Regla> getReglasVigentes(String horaParam,LocalDate startDate,LocalDate endDate);
	public List<Map<String, Object>> getReglasVigentes(String horaParam,LocalDate startDate,LocalDate endDate);
	
	public int updateReglaAsignacion(String asignacion,Long id);
	
	
	//***************** PARA LA TABLA DE TEMPORALES DE LA SOLICITUD *****************
	//public int saveTemporalSolicitud(String campo, String respuesta, Solicitud solicitud, LocalDateTime fechaCreacion);
	public int saveTemporalSolicitud(String campo, String dato_actual, String respuesta, Long solicitud, LocalDateTime fechaCreacion, String usuario_creador);
	
	public List<TemporalSolicitud> listadoTemporalSolicitud(String campo, String dato_actual, String respuesta, Long solicitud_id);
	
	public int eliminacionLogicaTemporalSolicitud(LocalDateTime fechaEliminacion,String UsuarioEliminador,Long solicitud_id,String campo, String respuesta);
	
	public int eliminacionLogicaTemporalSolicitudDeseleccion(LocalDateTime fechaEliminacion,String UsuarioEliminador,Long solicitud_id,String campo);
	
	public List<TemporalSolicitud> getTemporalesByIdSolicitud(Long solicitud_id);
		
}
