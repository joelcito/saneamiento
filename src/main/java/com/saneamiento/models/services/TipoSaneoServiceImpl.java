package com.saneamiento.models.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saneamiento.models.dao.ITipoSaneoDao;
import com.saneamiento.models.entity.DetalleTipoSaneo;
import com.saneamiento.models.entity.DocumentoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoSaneo;

@Service
public class TipoSaneoServiceImpl implements ITipoSaneoService{

	@Autowired
	private ITipoSaneoDao tipoSaneoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoSaneo> findAll() {
		return (List<TipoSaneo>) this.tipoSaneoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TipoSaneo findById(Long id) {
		return this.tipoSaneoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoSaneo save(TipoSaneo tipoSaneo) {
		return this.tipoSaneoDao.save(tipoSaneo);
	}
	
	// ***************** DETALLE TIPO SANEO *****************
	@Override
	@Transactional(readOnly = true)
	public DetalleTipoSaneo findByIdDetalleTipoSaneo(Long detalle_tipo_saneo_id) {
		return this.tipoSaneoDao.findByIdDetalleTipoSaneo(detalle_tipo_saneo_id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DetalleTipoSaneo> getDetalleTiposaneo(Long detalle_tipo_saneo_id) {
		return (List<DetalleTipoSaneo>)this.tipoSaneoDao.getDetalleTiposaneo(detalle_tipo_saneo_id);
	}
	
	@Override
	@Transactional
	public int saveByNombreAndTipoSaneo(String nombre, TipoSaneo tipoSaneo) {
	    return this.tipoSaneoDao.saveByNombreAndTipoSaneo(nombre, tipoSaneo);
	}
	
	// ***************** DOCUMENTO DETALLE TIPO SANEO *****************
	@Override
	@Transactional(readOnly = true)
	public List<DocumentoDetalleTipoSaneo> getDocumentoDetalleTipoSaneo(Long detalle_tipo_saneo_id) {
		return this.tipoSaneoDao.getDocumentoDetalleTipoSaneo(detalle_tipo_saneo_id);
	}

	@Override
	@Transactional(readOnly = true)
	public DocumentoDetalleTipoSaneo findByIdDocumentoDetalleTipoSaneo(Long documento_detalle_tipo_saneo_id) {
		return this.tipoSaneoDao.findByIdDocumentoDetalleTipoSaneo(documento_detalle_tipo_saneo_id);
	}

	@Override
	@Transactional
	//public int saveDocumentoDetalleTipoSaneo(String nombre, DetalleTipoSaneo detalleTipoSaneo) {
	public int saveDocumentoDetalleTipoSaneo(String nombre, Long usuario, Long detalleTipoSaneo, LocalDateTime fecha, String tamanio, String tipo_documento) {
		return this.tipoSaneoDao.saveDocumentoDetalleTipoSaneo(nombre, usuario, detalleTipoSaneo, fecha, tamanio, tipo_documento);
	}

	@Override
	@Transactional(readOnly = true)
	public Object[] getTipoSaneoDetalle(Long detalleTipoSaneoId) {
		return this.tipoSaneoDao.getTipoSaneoDetalle(detalleTipoSaneoId);
	}

	// ***************** TIPO DETALLE TIPO SANEO *****************
	@Override
	@Transactional(readOnly = true)
	public List<TipoDetalleTipoSaneo> getTiposDetallesTipoSaneo() {
		return this.tipoSaneoDao.getTiposDetallesTipoSaneo();
	}

	@Override
	@Transactional
	public int deleteTipoSaneo(Long id, LocalDateTime fecha) {
		return this.tipoSaneoDao.deleteTipoSaneo(id, fecha);	
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoSaneo> listadoRolVigentes() {
		return this.tipoSaneoDao.listadoRolVigentes();
	}

}
