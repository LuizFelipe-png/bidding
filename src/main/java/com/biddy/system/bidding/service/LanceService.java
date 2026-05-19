/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.service;

import com.biddy.system.bidding.model.EditalDTO;
import com.biddy.system.bidding.model.LanceDTO;
import com.biddy.system.bidding.model.UserDTO;
import com.biddy.system.bidding.repository.EditalRepository;
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
    
    @Autowired
    private EditalRepository editalRepository;
    
    public void cadastrarLance(Long id, LanceDTO lance, String token){
        if(tokenService.validarToken(token)){
            
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido!");
        }
        UserDTO usuarioLogado = tokenService.extrairClaims(token);
        
        if (usuarioLogado.getRole().equals("FORNECEDOR")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Você precisa ser Fornecedor para fazer um lance!");
        }
        EditalDTO edital = editalRepository.getById(id);
        
        if(!edital.getStatus().equals("ABERTO")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),
            "Você não pode criar lances para um edital fechado!");
        }
        if(edital.getData_fechamento().before(lance.getData_lance())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Data do lance posterior ao fechamento");
        }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido!");
        }
    }
}
