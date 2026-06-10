/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.service;

import com.bidding.system.bidding.model.EditalDTO;
import com.biddy.system.bidding.model.UserDTO;
import com.biddy.system.bidding.repository.EditalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class EditalService {
    
    @Autowired
    private EditalRepository repository;
    
    @Autowired
    private TokenService tokenService;
    
    public void criarEdital(EditalDTO edital, String token){
        UserDTO userLogado = tokenService.extrairClaims(token);
        
        if(userLogado.getRole().equals("COMPRADOR")){
            String mensagem = "";
            if(edital.getTitulo().equals("")){
            mensagem += "Titulo não preenchido!\n";
            }
            if(edital.getDescricao().equals("")){
            mensagem += "Descrição não preenchida!\n";
            }
            if(edital.getDataFechamento() == null){
            mensagem += "Data não preenchida!\n";
            }
            if(!mensagem.equals("")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
            }
            edital.setStatus("ABERTO");
            int linhas = repository.criar(edital);
            if(linhas == 0){
                throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao cadastrar no banco de dados");
            }
        }
            else{
                throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Acesso não autorizado!");
            }
    }
    
    public List<EditalDTO> listarEditais(String token){
        if(tokenService.validarToken(token)){
            return repository.listar();
        } else{
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Necessário logar com conta válida");
        }
        
    }

}
