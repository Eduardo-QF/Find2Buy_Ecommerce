package br.com.ecommerce.dao;

import br.com.ecommerce.database.DatabaseConnection;
import br.com.ecommerce.model.Cliente;
import br.com.ecommerce.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements Dao<Usuario> {

    @Override
    public void create(Usuario usuario) {
        Cliente cliente = (Cliente) usuario;
        String sql = "INSERT INTO clientes (id, nome, email, senha, idade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cliente.getId());
            pstmt.setString(2, cliente.getNome());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getSenha());
            pstmt.setInt(5, cliente.getIdade());

            pstmt.executeUpdate();
            System.out.println("Cliente cadastrado no banco de dados!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @Override
    public Usuario read(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cep")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> readAll() {
        List<Usuario> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cep")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public void update(Usuario usuario) {
        Cliente cliente = (Cliente) usuario;
        String sql = "UPDATE clientes SET nome = ?, email = ?, senha = ?, idade = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getSenha());
            pstmt.setInt(4, cliente.getIdade());
            pstmt.setInt(5, cliente.getId());

            pstmt.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Cliente deletado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM clientes WHERE email = ? AND senha = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("cep")

                );
            }

        } catch (SQLException e) {
            System.out.println("Erro na autenticacao: " + e.getMessage());
        }
        return null;
    }
}