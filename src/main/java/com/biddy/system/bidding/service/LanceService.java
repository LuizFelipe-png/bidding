package com.biddy.system.bidding.service;

import com.bidding.system.bidding.model.EditalDTO;
import com.biddy.system.bidding.model.LanceDTO;
import com.biddy.system.bidding.model.UserDTO;
import com.biddy.system.bidding.repository.EditalRepository;
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
    private EditalRepository editalRepository;
    
    @Autowired
    private TokenService tokenService;
    
    public void criarLance(Long id, LanceDTO lance, String token){
        if(tokenService.validarToken(token)){
            UserDTO userLogado = tokenService.extrairClaims(token);
            
            if(!userLogado.getRole().equals("FORNECEDOR")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Você precisa ser Forncedor para fazer uma lance");
            }
            EditalDTO edital = editalRepository.getById(id);
            
            if(!edital.getStatus().equals("ABERTO")){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Você não pode criar lances para um edital fechado!");
            }
            
            if(edital.getDataFechamento().after(lance.getData_lance())){
                
            }
        } else{
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token Inválido");
        }
    }
}