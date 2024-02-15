package com.saneamiento.models.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.ISolicitudDao;
import com.saneamiento.models.entity.Extranjeria;
import com.saneamiento.models.entity.Solicitud;
import com.saneamiento.models.entity.Tramite;

@Service
public class SolicitudServiceImpl implements ISolicitudService {
	
	@Autowired
	private ISolicitudDao solicitudDao;
	
	@Override
	//@Transactional(readOnly = true)
	@Transactional
	public List<Solicitud> listadoSolicitudesAsignados(Long usuario_asignado_id) {
		return this.solicitudDao.listadoSolicitudesAsignados(usuario_asignado_id);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Solicitud> listadoSolicitudes(Long usuario_solicitante_id) {
		return this.solicitudDao.listadoSolicitudes(usuario_solicitante_id);
	}

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
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> verificaSiTieneTramatiesEnviados(String serialextregistros,Long detalle_tipo_saneo_id) {
		return this.solicitudDao.verificaSiTieneTramatiesEnviados(serialextregistros, detalle_tipo_saneo_id);
	}
	
	//*********************** EXTRANJERIA ***********************
	
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> tramitesSolicitudesByIdSolicitud(Long solicitud_id) {
		return this.solicitudDao.tramitesSolicitudesByIdSolicitud(solicitud_id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> solicitudesPorIdExtranjero(Long solicitud_id) {
		return this.solicitudDao.solicitudesPorIdExtranjero(solicitud_id);
	}

	@Override
	@Transactional
	public Extranjeria buscaSerialExtranjero(String serialExtRegistros) {
		return this.solicitudDao.buscaSerialExtranjero(serialExtRegistros);
	}

	@Override
	@Transactional
	public int saveExtranjeria(String serialExtRegistros, String serialDocumentoExtRegistros,String nroCedulaBolExtRegistros) {
		return this.solicitudDao.saveExtranjeria(serialExtRegistros, serialDocumentoExtRegistros, nroCedulaBolExtRegistros);		
	}
	
	//*********************** TRAMITE ***********************
	@Override
	@Transactional
	public int saveTramite(Long detalle_tipo_saneo_id, Long solicitud_id) {
		return this.solicitudDao.saveTramite(detalle_tipo_saneo_id, solicitud_id);
	}

	@Override
	@Transactional
	public Tramite buscaByTipoSolicitudBySolicitudId(Long solicitud_id, Long detalle_tipo_saneo_id) {
		return this.solicitudDao.buscaByTipoSolicitudBySolicitudId(solicitud_id, detalle_tipo_saneo_id);
	}
	

	//*********************** TRAMITE DETALLE ***********************
	@Override
	@Transactional
	public int saveTramiteDetalle(Long tramite_id, String pregunta, String respuesta) {
		return this.solicitudDao.saveTramiteDetalle(tramite_id, pregunta, respuesta);
	}

	//***************** PARA LA ASIGNACION AUTOMATICA *****************
	@Override
	@Transactional(readOnly = true)
	//public List<Regla> getReglasVigentes(String horaParam, LocalDate startDate, LocalDate endDate) {
	public List<Map<String, Object>> getReglasVigentes(String horaParam, LocalDate startDate, LocalDate endDate) {
		return this.solicitudDao.getReglasVigentes(horaParam, startDate, endDate);
	}

	@Override
	@Transactional
	public int updateReglaAsignacion(String asignacion, Long id) {
		return this.solicitudDao.updateReglaAsignacion(asignacion, id);
	}
}
