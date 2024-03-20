package com.saneamiento.models.entity;

//import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="usuario_creador", length = 10)
	private String UsuarioCreador;
	
	@Column(name="usuario_modificador", length = 10)
	private String UsuarioModificador;
	
	@Column(name="usuario_eliminador", length = 10)
	private String UsuarioEliminador;

	@Column(unique = true, length = 100, nullable = false)
	private String username;

	@Column(length = 120)
	private String password;
	
	@Column(length = 60)
	private String country;

	private Boolean estado;
	
	private String nombres;
	
	private String primer_apellido;
	
	private String segundo_apellido;
	
	private String cedula;
	
	private String complemento;
	
	private String departamento;
	
	private String nombre_organizacion;
	
	private String nombre_dependencia;
	
	private String nombre_cargo;
	
	@Column(length = 5)
	private String dependencia_id;
	
	@Column(length = 5)
	private String organizacion_id;
	
	@Column(length = 5)
	private String jer_org_id;

	private String tipo_dependencia;
	
	/*
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns =@JoinColumn(name="rol_id"))
	private List<Rol> roles;
	*/
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "usuario_rol", 
			joinColumns = @JoinColumn(name="usuario_id"), 
			inverseJoinColumns =@JoinColumn(name="rol_id")
			)
	private List<Rol> roles;
	
	@Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaModificacion;

	@Column(name = "fecha_eliminacion", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaEliminacion;

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (roles != null) {
            for (Rol rol : roles) {
                authorities.add(new SimpleGrantedAuthority(rol.getNombre())); // Suponiendo que 'getNombre()' devuelve el nombre del rol
            }
        }

        return authorities;
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}



	@Override
	public boolean isEnabled() {
		return true;
	}

}
