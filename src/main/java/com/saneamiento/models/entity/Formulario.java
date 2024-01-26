package com.saneamiento.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="formulario")
public class Formulario implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String estado;
	
	private String sigla;
	
	private String desripcion;
	
	@OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<FormularioPregunta> preguntas;
	
	@ManyToOne
	@JoinColumn(name = "tipo_saneo_id")
	private TipoSaneo tipoSaneoFormulario;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
