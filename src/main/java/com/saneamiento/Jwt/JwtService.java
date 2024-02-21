package com.saneamiento.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY="e710b0cbf6418af7f825f84b7b94df89fe4b9fb99d057e74825982d172890408";

	public String getToken(UserDetails user) {
		return getToken(new HashMap<>(), user);
	}

	private String getToken(Map<String, Object> extraClaims, UserDetails user) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				//.setExpiration(new Date(System.currentTimeMillis()+1000*60*24)) 					// 24 horas
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30)) 					// 30 minutos
				//.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2)) 	// 48 horas
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getUsernameFromToken(String token) {
		return	getClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private Claims getAllClams(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
				
	}
	
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClams(token) ;
		return claimsResolver.apply(claims);
	}
	
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

}
