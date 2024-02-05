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
@Table(name = "tramite_detalle")
public class TramiteDetalle implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tramite_id")
	private Tramite tramite;
	
	private String pregunta;
	
	private String respuesta;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

