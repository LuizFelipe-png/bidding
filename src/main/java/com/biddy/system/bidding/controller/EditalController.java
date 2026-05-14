/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.controller;

import com.biddy.system.bidding.model.EditalDTO;
import com.biddy.system.bidding.model.LanceDTO;
import com.biddy.system.bidding.service.EditalService;
import com.biddy.system.bidding.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aluno
 */
@RestController
@RequestMapping("/api/edital")
public class EditalController {
    
    @Autowired
    EditalService service;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public String cadastrarEdital (@RequestHeader("Authorization") String auth, @RequestBody EditalDTO edital){
        String token = auth.replace("Bearer ", "");
        service.criarEdital(edital, token);
        return "Edital Cadastrado com sucesso!";
    }
    
    @GetMapping
    public List<EditalDTO> lerEditais(@RequestHeader("Authorization") String auth){
        String token = auth.replace("Bearer ", "");
        return service.lerEditais(token);
    }
    
    @PostMapping("/{id}/lances")
    public String cadastrarLance (@RequestHeader("Authorization") String auth, @RequestBody LanceDTO lance){
        String token = auth.replace("Bearer ", "");
        service.cadastrarLance(lance, token);
        return "Lance cadastrado com sucesso!";
    } 
}
