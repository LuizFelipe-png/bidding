package com.biddy.system.bidding.service;

import com.biddy.system.bidding.model.UserDTO;
import com.biddy.system.bidding.model.UserRequestDTO;
import com.biddy.system.bidding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private TokenService tokenService;
    
    public void register(UserDTO user) {
        String mensagem = "";
        
        if (user.getNome() == null || user.getNome().trim().isEmpty()) {
            mensagem = "Nome não preenchido!";
        } else if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            mensagem = "Email não preenchido!";
        } else if (user.getSenha() == null || user.getSenha().trim().isEmpty()) {
            mensagem = "Senha não preenchida!";
        }
        
        if (!mensagem.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("FORNECEDOR");
        }
        
        repository.register(user);
    }
    
    public String logar(UserRequestDTO user) {
        String mensagem = "";
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            mensagem = "Email não preenchido!";
        } else if (user.getSenha() == null || user.getSenha().trim().isEmpty()) {
            mensagem = "Senha não preenchida!";
        }
        
        if (!mensagem.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        
        UserDTO dadosLogado = repository.logar(user.getEmail(), user.getSenha());
        
        if (dadosLogado == null || dadosLogado.getId() == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "E-mail ou senha incorretos!");
        }
        
        return tokenService.gerarToken(dadosLogado);
    }
}