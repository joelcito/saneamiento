package com.saneamiento.models.services;

import java.util.List;

import com.saneamiento.models.entity.DetalleTipoSaneo;
import com.saneamiento.models.entity.TipoSaneo;

public interface ITipoSaneoService {

	public List<TipoSaneo> findAll();
	
	public TipoSaneo findById(Long id);
	
	public TipoSaneo save(TipoSaneo tipoSaneo);
	
	// ***************** DETALLE TIPO SANEO *****************
	public List<DetalleTipoSaneo> getDetalleTiposaneo(Long detalle_tipo_saneo_id);
	
	//int saveDetalleTipoSaneo(String nombre, TipoSaneo tipoSaneo);
	//int saveDetalleTipoSaneo(String nombre, Long tipo_saneo_id);
	
	
	int saveByNombreAndTipoSaneo(String nombre, TipoSaneo tipoSaneo);
	 
}
