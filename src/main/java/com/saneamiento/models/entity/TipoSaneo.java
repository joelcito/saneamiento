package com.saneamiento.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tipo_saneo")
public class TipoSaneo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String descripcion;

	@OneToMany(mappedBy = "tipoSaneo", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<DetalleTipoSaneo> detalles;
		
	@OneToMany(mappedBy = "tipoSaneoFormulario", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Formulario> formularios;
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
