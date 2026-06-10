/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Define o pacote onde está localizada esta classe
package com.biddy.system.bidding.repository;


import com.bidding.system.bidding.model.EditalDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public class EditalRepository {
    public int criar(EditalDTO edital){

        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;

            stmt = conn.prepareStatement("INSERT INTO editais (titulo, descricao, data_fechamento, status) VALUES (?,?,?,?)");

            stmt.setString(1, edital.getTitulo());
            stmt.setString(2, edital.getDescricao());
            stmt.setDate(3, edital.getDataFechamento());
            stmt.setString(4, edital.getStatus());

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }
    
    public List<EditalDTO> listar(){
        //List<EditalDTO> listar = new ArrayList();
        //List<EditalDTO> listar = new ArrayList<>();
        List<EditalDTO> listar = new ArrayList<EditalDTO>();
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conn.prepareStatement("SELECT * FROM editais");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                EditalDTO edital = new EditalDTO();
                edital.setId(rs.getLong("id"));
                edital.setTitulo(rs.getString("titulo"));
                edital.setDescricao(rs.getString("descricao"));
                edital.setDataFechamento(rs.getDate("data_fechamento"));
                edital.setStatus(rs.getString("status"));
                
                listar.add(edital); //colocando os dados em uma nova linha na lista
            }

            

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return listar;
    }
    public EditalDTO getById(Long id){
        EditalDTO edital = new EditalDTO();
        try{
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conn.prepareStatement("SELECT data_fechamento, status FROM editais WHERE id = ?");
            stmt.setLong(1, id);
            
            rs = stmt.executeQuery();
            
            if(rs.next()){
                edital.setDataFechamento(rs.getDate("data_fechamento"));
                edital.setStatus(rs.getString("status"));
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return edital;
    }
}