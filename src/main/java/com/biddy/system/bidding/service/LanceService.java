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

@Service
public class LanceService {
    
    @Autowired
    private LanceRepository repository;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private EditalRepository editalRepository;
    
    public void cadastrarLance(Long id, LanceDTO lance, String token) {
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido ou expirado!");
        }
        
        UserDTO usuarioLogado = tokenService.extrairClaims(token);
        
        if (!"FORNECEDOR".equals(usuarioLogado.getRole())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Acesso negado: Perfil incorreto.");
        }
        
        EditalDTO edital = editalRepository.getById(id);
        if (edital == null || edital.getStatus() == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Edital não encontrado!");
        }
        
        if (!"ABERTO".equalsIgnoreCase(edital.getStatus())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Edital fechado!");
        }
        
        if (edital.getData_fechamento() != null && lance.getData_lance() != null) {
            if (edital.getData_fechamento().before(lance.getData_lance())) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Data expirada!");
            }
        }

        lance.setId_edital(id);
        lance.setId_usuario(usuarioLogado.getId()); 
        
        int linhasAfetadas = repository.cadastrarLance(lance);
        if (linhasAfetadas == 0) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao salvar lance.");
        }
    }
}