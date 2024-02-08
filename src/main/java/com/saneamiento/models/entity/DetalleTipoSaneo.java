package com.saneamiento.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "detalle_tipo_saneo")
public class DetalleTipoSaneo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "tipo_saneo_id")
	private TipoSaneo tipoSaneo;
	
	@OneToMany(mappedBy = "detalleTipoSaneo", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<DocumentoDetalleTipoSaneo> documentosDetalles;
	
//	@OneToMany(mappedBy = "detalleTipoSaneo", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "detalleTipoSaneo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JsonManagedReference
	//@JsonIgnore
	@JsonManagedReference
    private List<TipoDetalleTipoSaneo> tipoDetalleTipoSaneo;
    
	
	private String nombre;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
