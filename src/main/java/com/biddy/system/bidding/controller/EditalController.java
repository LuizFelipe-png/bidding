/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.controller;

import com.biddy.system.bidding.model.EditaisDTO;
import com.biddy.system.bidding.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author Aluno
 */
public class EditalController {
    
    @Autowired
    private TokenService tokenservice;
    
    @PostMapping
    public void cadastrarEdital (@RequestHeader("Authorization") String auth, @RequestBody EditaisDTO edital){
        
    }
}
