/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.model;

import java.sql.Date;

/**
 *
 * @author Aluno
 */
public class EditaisDTO {
    private int id;
    private String titulo;
    private String descricao;
    private Date data_fechamento;
    private Boolean Status;

    public EditaisDTO() {
    }

    public EditaisDTO(int id, String titulo, String descricao, Date data_fechamento, Boolean Status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data_fechamento = data_fechamento;
        this.Status = Status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData_fechamento() {
        return data_fechamento;
    }

    public void setData_fechamento(Date data_fechamento) {
        this.data_fechamento = data_fechamento;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }
    
    
}
