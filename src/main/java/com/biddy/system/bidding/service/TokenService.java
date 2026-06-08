/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.service;

import com.biddy.system.bidding.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class TokenService {

    // Pega a chave secreta do application.properties.
    // Exemplo:
    // api.security.token.secret=123456
    @Value("${api.security.token.secret}")
    private String secret;

    
    // Método responsável por gerar a chave criptográfica.
    public SecretKey getKeySign() {

        // Decodifica a chave secreta Base64 para bytes.
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);

        // Cria a chave HMAC usada para assinar o token.
        return Keys.hmacShaKeyFor(keyBytes);
    }

    
    // Método responsável por gerar o token JWT.
    public String gerarToken (UserDTO user){

        // Verifica se algum dado obrigatório está faltando.
        if(
                
           // Verifica se o ID é nulo ou 0.
           // AQUI TEM UM PROBLEMA:
           // Se getId() for Integer, tudo bem.
           // Mas se for int, ele nunca será null.
           (user.getId() == 0 || user.getId() == null) ||

                // Verifica se nome está vazio.
                user.getNome().equals("") ||

                // Verifica se email está vazio.
                user.getEmail().equals("") ||

                // Verifica se role está vazio.
                user.getRole().equals("")
          ){

            // Retorna erro HTTP 400.
            throw new ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Um ou mais campos faltantes"
            );
        }

        
        // Cria e retorna o token JWT.
        return Jwts.builder()

                // Define o "subject" do token.
                // Aqui você colocou o nome do usuário.
                .subject(user.getNome())

                // Adiciona um dado extra no token.
                // Aqui está salvando o usuário inteiro.
                .claim("nome", user.getNome())

                .claim("role", user.getRole())
                
                // Define data de criação do token.
                .issuedAt(new Date())

                // Define data de expiração do token.
                // System.currentTimeMillis() = agora.
                // +3000000 = tempo até expirar.
                .expiration(new Date(System.currentTimeMillis() + 3000000))

                // Assina o token com a chave secreta.
                .signWith(this.getKeySign())

                // Gera o token final em String.
                .compact();
    }
    
    public UserDTO extrairClaims(String token){
        Claims claims = Jwts.parser()
                .verifyWith(this.getKeySign())                
                .build()
                .parseSignedClaims(token)
                .getPayload();
        
        //Recupera o usuário do claim "usuario"
        UserDTO user = new UserDTO();
        user.setId(claims.get("id", Long.class));
        user.setNome(claims.get("nome", String.class));
        user.setRole(claims.get("role", String.class));
        
        return user;
    }
    
    public boolean validarToken(String token) {
        try {
            // Cria um parser JWT com a chave secreta para validação
            Jwts.parser()
                    .setSigningKey(getKeySign())
                    .build()
                    // Analisa e valida o token (lança exceção se inválido ou expirado)
                    .parseClaimsJws(token);
            // Se chegou aqui, o token é válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Se qualquer exceção ocorrer, o token é inválido ou expirou
            return false;
        }
    }
}