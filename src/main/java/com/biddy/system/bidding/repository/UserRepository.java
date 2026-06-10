package com.biddy.system.bidding.repository;

import com.biddy.system.bidding.model.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Usuario
 */
@Repository
public class UserRepository {

    public void register(UserDTO user) {
        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement("Insert into usuarios (nome, email, senha, role)  values(?,?,?,?)");

            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Falha na atualização - Nenhuma linha foi afetada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDTO logar(String email, String senha) {
        UserDTO user = new UserDTO();

        try {
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            stmt = conn.prepareStatement("select * from usuarios where email = ? and senha = ?");

            stmt.setString(1, email);
            stmt.setString(2, senha);

            rs = stmt.executeQuery();

            if (rs.next()) {
                user.setEmail(rs.getString("email"));
                user.setId(rs.getLong("id"));
                user.setNome(rs.getString("nome"));
                user.setRole(rs.getString("role"));

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return user;

    }
    
}