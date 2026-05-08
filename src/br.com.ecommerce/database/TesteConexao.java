package br.com.ecommerce.database;

import br.com.ecommerce.database.DatabaseConnection;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("=== TESTE DE CONEXÃO COM POSTGRESQL ===\n");

        // Testar conexão
        if (DatabaseConnection.testConnection()) {
            System.out.println("✅ Conexão estabelecida com sucesso!");
            System.out.println("📊 Banco: ecommerce_db");
            System.out.println("👤 Usuário: postgres");
        } else {
            System.out.println("❌ Falha na conexão! Verifique:");
            System.out.println("  1. Se o PostgreSQL está rodando");
            System.out.println("  2. Se a senha está correta");
            System.out.println("  3. Se o banco 'ecommerce_db' existe");
        }

        // Fechar conexão
        DatabaseConnection.closeConnection();
    }
}