package com.saneamiento.models.services;

import java.time.LocalDateTime;
import java.util.List;

import com.saneamiento.models.entity.DetalleTipoSaneo;
import com.saneamiento.models.entity.DocumentoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoSaneo;

public interface ITipoSaneoService {

	public List<TipoSaneo> findAll();
	public TipoSaneo findById(Long id);	
	public TipoSaneo save(TipoSaneo tipoSaneo);
	
	int deleteTipoSaneo(Long id, LocalDateTime fecha);	
	public List<TipoSaneo> listadoRolVigentes();
	
	
	
	// ***************** DETALLE TIPO SANEO *****************
	public DetalleTipoSaneo findByIdDetalleTipoSaneo(Long detalle_tipo_saneo_id);
	
	public List<DetalleTipoSaneo> getDetalleTiposaneo(Long detalle_tipo_saneo_id);
	
	int saveByNombreAndTipoSaneo(String nombre, TipoSaneo tipoSaneo);

	
	//***************** DOCUMENTO DETALLE TIPO SANEO *****************
	public List<DocumentoDetalleTipoSaneo> getDocumentoDetalleTipoSaneo(Long detalle_tipo_saneo_id);
	
	public DocumentoDetalleTipoSaneo findByIdDocumentoDetalleTipoSaneo(Long documento_detalle_tipo_saneo_id);
	
	//public int saveDocumentoDetalleTipoSaneo(String nombre, DetalleTipoSaneo detalleTipoSaneo);
	public int saveDocumentoDetalleTipoSaneo(String nombre, Long usuario, Long detalleTipoSaneo, LocalDateTime fecha, String tamanio, String tipo_documento);
	
	public Object[] getTipoSaneoDetalle(Long detalleTipoSaneoId);

	// ***************** TIPO DETALLE TIPO SANEO *****************
	public  List<TipoDetalleTipoSaneo> getTiposDetallesTipoSaneo();
}
