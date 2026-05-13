/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.repository;

import com.biddy.system.bidding.model.EditaisDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class EditaisRepository {
    public void criar(EditaisDTO editais){
        try{
           Connection conn = Conexao.conectar();
           PreparedStatement stmt = null;
           stmt = conn.prepareStatement("Insert into editais (titulo, descricao, data_fechamento, status) values (?,?,?,?)");
           
           stmt.setString(1, editais.getTitulo());
           stmt.setString(2, editais.getDescricao());
           stmt.setDate(3, editais.getData_fechamento());
           stmt.setBoolean(4, editais.getStatus());
           
           int linhasAfetadas = stmt.executeUpdate();
           if(linhasAfetadas == 0){
               throw new SQLException("Falha ao criar, você não é um ROLER comprador - Nenhuma linha foi afetada");
           }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
