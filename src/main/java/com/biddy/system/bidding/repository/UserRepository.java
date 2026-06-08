package com.biddy.system.bidding.repository;

import com.biddy.system.bidding.model.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    
    public void register(UserDTO user) {
        try {
            Connection conn = Conexao.conectar();
            if (conn == null) {
                throw new SQLException("Não foi possível conectar ao banco de dados.");
            }
            
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO usuarios (nome, email, senha, role) VALUES (?,?,?,?)"
            );
            
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole());
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha no cadastro - Nenhuma linha foi afetada.");
            }
            
            System.out.println("Usuário " + user.getNome() + " gravado com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("ERRO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar no banco: " + e.getMessage());
        }
    }
    
    public UserDTO logar(String email, String senha) {
        try {
            Connection conn = Conexao.conectar();
            if (conn == null) return null;
            
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM usuarios WHERE email = ? AND senha = ?"
            );
            
            stmt.setString(1, email);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setNome(rs.getString("nome"));
                user.setRole(rs.getString("role"));
                return user; 
            }
        } catch (SQLException e) {
            System.err.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}