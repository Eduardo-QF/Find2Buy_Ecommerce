// TesteCrud.java
package br.com.ecommerce.test;

import br.com.ecommerce.dao.ClienteDao;
import br.com.ecommerce.model.Cliente;
import br.com.ecommerce.model.Usuario;

public class TesteCrud {
    public static void main(String[] args) {
        ClienteDao clienteDao = new ClienteDao();

        System.out.println("=== TESTE CRUD CLIENTE ===\n");

        System.out.println("1. Criando cliente...");
        Cliente novoCliente = new Cliente(1, "Joao Silva", 25, "joao@email.com", "123456", "12345-678");
        clienteDao.create(novoCliente);

        System.out.println("\n2. Buscando cliente ID 1...");
        Usuario cliente = clienteDao.read(1);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            cliente.exibirDados();
        } else {
            System.out.println("Cliente nao encontrado!");
        }

        System.out.println("\n3. Atualizando cliente...");
        Cliente clienteAtualizado = new Cliente(1, "Joao Santos", 26, "joao.santos@email.com", "123456", "98765-432");
        clienteDao.update(clienteAtualizado);

        System.out.println("\n4. Listando todos clientes...");
        var clientes = clienteDao.readAll();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Usuario u : clientes) {
                u.exibirDados();
                System.out.println("---");
            }
        }

        System.out.println("\n5. Deletando cliente ID 1...");
        clienteDao.delete(1);

        System.out.println("\n=== TESTE CRUD FINALIZADO ===");

        br.com.ecommerce.database.DatabaseConnection.closeConnection();
    }
}