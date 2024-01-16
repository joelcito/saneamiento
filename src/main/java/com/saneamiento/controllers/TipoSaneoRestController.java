package com.saneamiento.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saneamiento.models.entity.DetalleTipoSaneo;
import com.saneamiento.models.entity.DocumentoDetalleTipoSaneo;
import com.saneamiento.models.entity.TipoSaneo;
import com.saneamiento.models.services.ITipoSaneoService;

@RestController
@RequestMapping("/api/tipoSaneos")
@CrossOrigin(origins = {"http://localhost:4200"})
public class TipoSaneoRestController {
	
	@Autowired
	private ITipoSaneoService tipoSaneoService;
	
	@GetMapping("/listado")
	public List<TipoSaneo> index() {
		return tipoSaneoService.findAll();
	}
	
	@PostMapping("/")
	public TipoSaneo save(@RequestBody Map<String, Object> requestBody) {
		
		TipoSaneo newTipoSaneo = new TipoSaneo();
		String nombre 		= requestBody.get("nombre").toString();
		String descripcion 	= requestBody.get("descripcion").toString();
				
		newTipoSaneo.setNombre(nombre);
		newTipoSaneo.setDescripcion(descripcion);	
			
		return this.tipoSaneoService.save(newTipoSaneo);
	}
	
	@PutMapping("/{id}")
	public TipoSaneo update(@RequestBody Map<String, Object> requesBody, @PathVariable Long id) {
			
		TipoSaneo tipoSaneoBuscado = this.tipoSaneoService.findById(id);
		tipoSaneoBuscado.setNombre(requesBody.get("nombre").toString());
		tipoSaneoBuscado.setDescripcion(requesBody.get("descripcion").toString());
		
		return this.tipoSaneoService.save(tipoSaneoBuscado);
	}
	
	@GetMapping("/{tipo_saneo_id}")
	private TipoSaneo findById(@PathVariable Long tipo_saneo_id) {
		return this.tipoSaneoService.findById(tipo_saneo_id);
	}
	
	// ***************** DETALLE TIPO SANEO *****************
	@GetMapping("/detalle_tipo_saneo/{tipo_saneo_id}")
	public List<DetalleTipoSaneo> getDetalleTiposaneo(@PathVariable Long tipo_saneo_id){
		System.out.println(tipo_saneo_id);
		return this.tipoSaneoService.getDetalleTiposaneo(tipo_saneo_id);		
	}
		
	@PostMapping("/detalle_tipo_saneo/")
	public DetalleTipoSaneo saveDetalleTipoSaneo(@RequestBody Map<String, Object> requestBody) {

		Long tipo_saneo_id 						= ((Integer) requestBody.get("tipoSaneo")).longValue();
		TipoSaneo tipo_saneo 					= this.tipoSaneoService.findById(tipo_saneo_id);		
		List<DetalleTipoSaneo> lisDetaTipoSane 	= tipo_saneo.getDetalles();
		
		String nombre 				= requestBody.get("nombre").toString();
		DetalleTipoSaneo newDeta 	= new DetalleTipoSaneo();
		newDeta.setNombre(nombre);
		newDeta.setTipoSaneo(tipo_saneo);
		
		lisDetaTipoSane.add(newDeta);		
		tipo_saneo.setDetalles(lisDetaTipoSane);
		
		this.tipoSaneoService.save(tipo_saneo);
		
	    return new DetalleTipoSaneo();
	}
	
	
	// ***************** DOCUMENTO DETALLE TIPO SANEO *****************
	@GetMapping("/documento_detalle_tipo_saneo/{detalle_tipo_saneo_id}")
	public List<DocumentoDetalleTipoSaneo> getDocumentoDetalleTipoSaneo(@PathVariable Long detalle_tipo_saneo_id) {
		return this.tipoSaneoService.getDocumentoDetalleTipoSaneo(detalle_tipo_saneo_id);		
	}
	
	@PostMapping("/documento_detalle_tipo_saneo/")
	public DocumentoDetalleTipoSaneo saveDocumentoDetalleTipoSaneo(@RequestBody Map<String, Object> requestBody) {

		//System.out.println(requestBody);
		Long detalle_tipo_saneo_id 				= ((Integer) requestBody.get("detalleTipoSaneo")).longValue();
		String nombre 							= requestBody.get("nombre").toString();

		Object[] result = this.tipoSaneoService.getTipoSaneoDetalle(detalle_tipo_saneo_id);

		// Accede a los elementos del array
	    Object[] fila = (Object[]) result[0];

		Boolean sw = true;

	    // Supongamos que estás buscando el primer y segundo elemento de la fila
	    Long id_tipo_saneo 					= ((Long) fila[0]).longValue();
	    //String nombre_tipo_saneo 			= fila[1].toString();
	    Long id_detalle_tipo_saneo 			= ((Long)fila[2]).longValue();
	    //String nombre_detalle_tipo_saneo 	= fila[3].toString();

		TipoSaneo tipo_saneo 					= this.tipoSaneoService.findById(id_tipo_saneo);
		List<DetalleTipoSaneo> lisDetaTipoSane 	= tipo_saneo.getDetalles();

		Iterator<DetalleTipoSaneo> iterator = lisDetaTipoSane.iterator();
		while (iterator.hasNext() && sw) {
			DetalleTipoSaneo dar = iterator.next();
			if (dar.getId() == id_detalle_tipo_saneo) {
				List<DocumentoDetalleTipoSaneo> listDoc = dar.getDocumentosDetalles();

				DocumentoDetalleTipoSaneo newDocumento = new DocumentoDetalleTipoSaneo();
				newDocumento.setNombre(nombre);
				newDocumento.setDetalleTipoSaneo(dar);

				listDoc.add(newDocumento);

				dar.setDocumentosDetalles(listDoc);
				iterator.remove(); // Remover el elemento actual de la lista para evitar ConcurrentModificationException
				sw = false;
			}
		}
		
		this.tipoSaneoService.save(tipo_saneo);
		
		return new DocumentoDetalleTipoSaneo();
	}
	
}
