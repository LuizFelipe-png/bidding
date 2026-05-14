/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.service;

import com.biddy.system.bidding.model.LanceDTO;
import com.biddy.system.bidding.model.UserDTO;
import com.biddy.system.bidding.repository.LanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class LanceService {
    
    @Autowired
    private LanceRepository repository;
    
    @Autowired
    private TokenService tokenService;
    
    public void cadastrarLance(LanceDTO lance, String token){
        UserDTO usuarioLogado = tokenService.extrairClaims(token);
        
        if (usuarioLogado.getRole().equals("COMPRADOR")) {
            String mensagem = "";
            if(lance.getValor().equals("")){
                mensagem += "Valor não preenchido!\n";
            }if(lance.getData_lance().equals("")){
                mensagem += "A data do lance não preenchida!\n";
            }if(!mensagem.equals("")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
            }
        
            int linhas = repository.cadastrarLance(lance);
        if(!mensagem.equals("")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Erro ao cadastrar ao banco de dados!");
        }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Acesso não autorizado");
            }
        }
    }
}
