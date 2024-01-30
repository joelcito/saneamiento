package com.saneamiento.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name ="extranjeria")
public class Extranjeria implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String serialExtRegistros;
	
	private String serialDocumentoExtRegistros;
	
	private String nroCedulaBolExtRegistros;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
