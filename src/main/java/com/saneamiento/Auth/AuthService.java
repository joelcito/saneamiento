package com.saneamiento.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saneamiento.Jwt.JwtService;
import com.saneamiento.models.dao.IUsuarioDao;
import com.saneamiento.models.entity.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private final IUsuarioDao usuarioDao;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager; 
	
	public AuthResponse login(LoginRequest request) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		/*
		try {
		    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		    // Si no se lanza una excepción, la autenticación fue exitosa
		    System.out.println("Autenticación exitosa");
		} catch (AuthenticationException e) {
		    // Si se lanza una excepción, la autenticación falló
		    System.out.println("Autenticación fallida: " + e.getMessage());
		}
		*/
		//System.out.print(request.getUsername());
		//System.out.print(request.getPassword());
		//System.out.print("holaa");
		
		
		//UserDetails user = usuarioDao.findByUsernameNative(request.getUsername());
		UserDetails user = usuarioDao.findByUsername(request.getUsername()).orElseThrow();
		
		//System.out.println(user);
		
		String token = jwtService.getToken(user);
		
		return AuthResponse.builder()
				.token(token)
				.user(user)
				.build();
	}

	public AuthResponse register(RegisterRequest request) {
			
		Usuario user = Usuario.builder()
								.username(request.getUsername())
								.password(passwordEncoder.encode(request.getPassword())) 
								.country(request.getCountry())
								.estado(request.getEstado())
								
								.cedula(request.getCedula())
								.complemento(request.getComplemento())
								.nombres(request.getNombres())
								.primer_apellido(request.getPrimer_apellido())
								.segundo_apellido(request.getSegundo_apellido())
								.nombre_organizacion(request.getNombre_organizacion())
								.nombre_dependencia(request.getNombre_dependencia())
								.nombre_cargo(request.getNombre_cargo())
								.departamento(request.getDepartamento())							
								.build();
		
		//System.out.println(sout);		
		
		usuarioDao.save(user);
		
		return AuthResponse.builder()
				.token(jwtService.getToken(user))
				.build();
		
	}

}
