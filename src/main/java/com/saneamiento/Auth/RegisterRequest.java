package com.saneamiento.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	String username;
	String password;
	String firstname;
	String lastname;
	String country;
	Boolean estado;
	
	
	String cedula;
	String complemento;
	String nombres;
	String primer_apellido;
	String segundo_apellido;
	String nombre_organizacion;
	String nombre_dependencia;
	String nombre_cargo;
	String departamento;
}
