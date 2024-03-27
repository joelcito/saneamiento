package com.saneamiento.models.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.SolicitudArchivo;
import com.saneamiento.models.entity.SolicitudConversacion;
import com.saneamiento.models.entity.TemporalSolicitud;
import com.saneamiento.models.entity.Tramite;
import com.saneamiento.models.entity.Usuario;

public interface ISolicitudService {
	
	//***************** PARA LA TABLA DE SOLICITUD *****************
	public List<Solicitud> listadoSolicitudesAsignados(Long usuario_asignado_id);
	
	public List<Solicitud> listadoSolicitudes(Long usuario_solicitante_id);	
	
	public List<Solicitud> findAll();
	
	public Solicitud save(Solicitud solicitud);
	
	public Solicitud findById(Long id); // HASTA FEBRERO
	
	public List<Solicitud> listadoCasos(String dependencia);
	
	
	
	//***************** PARA LA TABLA DE TRAMITE *****************			
	public List<Map<String, Object>> verificaSiTieneTramatiesEnviados(String serialextregistros, Long detalle_tipo_saneo_id);
	
	public int saveTramite (Long detalle_tipo_saneo_id, Long solicitud_id);

	public int saveTramiteDetalle(Long tramite_id,String pregunta,String respuesta);
	
	public List<Map<String, Object>> tramitesSolicitudesByIdSolicitud(Long solicitud_id);
	
	public Tramite buscaByTipoSolicitudBySolicitudId(Long solicitud_id,Long detalle_tipo_saneo_id); // HASTA FEBRERO
	
	
	
	//***************** PARA LA TABLA DE EXTRANJERIA*****************	
	public Extranjeria buscaSerialExtranjero(String serialExtRegistros);
	
	public int saveExtranjeria (String serialExtRegistros, String serialDocumentoExtRegistros, String nroCedulaBolExtRegistros);
		
	public Map<String, Object> solicitudesPorIdExtranjero(Long solicitud_id); // HASTA FEBRERO
	
	
	
	//***************** PARA LA ASIGNACION AUTOMATICA *****************
	public List<Map<String, Object>> getReglasVigentes(String horaParam,LocalDate startDate,LocalDate endDate);
	
	public int updateReglaAsignacion(String asignacion,Long id); // HASTA FEBRERO
	
	
	//***************** PARA LA TABLA DE TEMPORALES DE LA SOLICITUD *****************
	public int saveTemporalSolicitud(String campo, String dato_actual, String respuesta, Long solicitud, LocalDateTime fechaCreacion, String usuario_creador);
	
	public List<TemporalSolicitud> listadoTemporalSolicitud(String campo, String dato_actual, String respuesta, Long solicitud_id);
	
	public int eliminacionLogicaTemporalSolicitud(LocalDateTime fechaEliminacion,String UsuarioEliminador,Long solicitud_id,String campo, String respuesta);
	
	public int eliminacionLogicaTemporalSolicitudDeseleccion(LocalDateTime fechaEliminacion,String UsuarioEliminador,Long solicitud_id,String campo);
	
	public List<TemporalSolicitud> getTemporalesByIdSolicitud(Long solicitud_id); // HASTA FEBRERO
	
	//***************** SOLICITUD ARCHIVOS *****************
	public int saveSolicitudArchivo(Long Solicitud, String usuario_creador, String gestion, String sistema, String mes, Date fecha, String nombre_archivo, String ETag, String Location, String key, String Bucket, LocalDateTime fechaCreacion, String tipo_archivo, Long conversacion_id);
	
	public List<SolicitudArchivo> getSolicitudArchivosById(Long solicitud_id, Long conversacion_id);
	
	
	//***************** SOLICITUD CONVERSACION *****************
	//public int saveSolicitudConversacion(String usuario_creador, Solicitud solicitud, Usuario usuarioSolicitante, String texto, String estado, String tipo, LocalDateTime fechaCreacion);
	//public int saveSolicitudConversacion(String usuario_creador, Long solicitud_id, Long usuario_id_solicitante, String texto, String estado, String tipo, LocalDateTime fecha_creacion);
	public int saveSolicitudConversacion(String usuario_creador, Long solicitud_id, Long usuario_id_solicitante, String texto, String estado, String tipo, LocalDateTime fecha_creacion);
	
	public int saveSolicitudConversacionRespuesta(String usuario_creador, Long solicitud_id, Long usuario_id_respuesta, String texto, String estado, String tipo, LocalDateTime fecha_creacion);
	
	public List<SolicitudConversacion> getSolicitudConversacionById(Long solicitud_id);

	public Long maxSolicitudConversacionByIdSolicitud(Long solicitudId);
	
	
		
}
