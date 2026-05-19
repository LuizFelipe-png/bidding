/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.biddy.system.bidding.repository;

import com.biddy.system.bidding.model.EditalDTO;
import com.biddy.system.bidding.model.LanceDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.spi.DirStateFactory.Result;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class EditalRepository {
    public int cadastrarEdital(EditalDTO editais){
        try{
           Connection conn = Conexao.conectar();
           PreparedStatement stmt = null;
           stmt = conn.prepareStatement("INSERT INTO editais (titulo, descricao, data_fechamento, status)"
                    + "VALUES (?,?,?,?)");
           
           stmt.setString(1, editais.getTitulo());
           stmt.setString(2, editais.getDescricao());
           stmt.setDate(3, editais.getData_fechamento());
           stmt.setString(4, editais.getStatus());
           
           return stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<EditalDTO> ler(){
        List<EditalDTO> lista = new ArrayList();
        try{
           Connection conn = Conexao.conectar();
           PreparedStatement stmt = null;
           ResultSet rs = null;
           
           stmt = conn.prepareStatement("SELECT * FROM editais");
           rs = stmt.executeQuery();
           
           while(rs.next()){
           EditalDTO editais = new EditalDTO();  
           editais.setId(rs.getLong("id"));
           editais.setTitulo(rs.getString("titulo"));
           editais.setDescricao(rs.getString("descricao"));
           editais.setData_fechamento(rs.getDate("data_fechamento"));
           editais.setStatus(rs.getString("status"));
           
           lista.add(editais);//colocando os dados em uma nova linha na lista
           }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public EditalDTO getById(Long id){
        EditalDTO edital = new EditalDTO();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            
            stmt = conn.prepareStatement("SELECT * FROM editais WHERE id = ?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                edital.setData_fechamento(rs.getDate("data_fechamento"));
                edital.setStatus(rs.getString("status"));
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return edital;
    }
}
