package com.saneamiento.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "documento_detalle_tipo_saneo")
public class DocumentoDetalleTipoSaneo implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "detalle_tipo_saneo_id")
	private DetalleTipoSaneo detalleTipoSaneo;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
