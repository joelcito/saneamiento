package com.saneamiento.models.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="tipo_detalle_tipo_saneo")
public class TipoDetalleTipoSaneo implements Serializable{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "detalle_tipo_saneo_id")
	@JsonBackReference
	private DetalleTipoSaneo detalleTipoSaneo;
	
	private String nombre;
	
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
