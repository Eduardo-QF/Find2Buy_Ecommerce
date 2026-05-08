package br.com.ecommerce.view;

import br.com.ecommerce.dao.ClienteDao;
import br.com.ecommerce.dao.ProdutoDao;
import br.com.ecommerce.model.*;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaEcommerce {

    private List<Produto> produtos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private Carrinho carrinho = new Carrinho();
    private Usuario usuarioLogado = null;
    private List<Usuario> usuarios = new ArrayList<>();
    private int contadorPedido = 1;
    private int contadorUsuario = 1;
    private ClienteDao clienteDao = new ClienteDao();
    private ProdutoDao produtoDao = new ProdutoDao(); // Um atributo que cria o objeto ProdutoDao e declara

    Scanner sc = new Scanner(System.in);

    public void loginCliente() {
        if (usuarioLogado != null) {
            System.out.println("Voce ja esta logado");
            return;
        }

        sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Usuario usuario = clienteDao.autenticar(email, senha);

        if (usuario != null) {
            usuarioLogado = usuario;
            System.out.println("Login realizado com sucesso!");
            usuario.exibirDados();
        } else {
            System.out.println("Email ou senha invalidos!");
        }
    }

    public void loginAdmin() {
        sc.nextLine();

        System.out.print("Usuario admin: ");
        String user = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (user.equals("admin") && senha.equals("123456")) {

            Administrador admin = new Administrador(contadorUsuario++,
                    "Administrador",
                    "admin@sistema.com",
                    senha,
                    "Gerente Geral",
                    "Tecnologia");
            usuarioLogado = admin;
            usuarios.add(admin);
            System.out.println("Admin logado com sucesso!");
            admin.exibirDados();
            admin.gerenciarSistema();
            menuAdmin();
        } else {
            System.out.println("Credenciais invalidas");
        }
    }

    public void logoutAdmin() {
        usuarioLogado = null;
        System.out.println("Admin deslogado");
    }

    public void cadastrarProduto() {
        if (!(usuarioLogado instanceof Administrador)) {
            System.out.println("Acesso negado!");
            return;
        }

        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Preco: ");
        double preco = sc.nextDouble();

        System.out.print("Estoque: ");
        int estoque = sc.nextInt();

        int id = produtoDao.readAll().size() + 1;
        Produto p = new Produto(id, nome, preco, estoque);
        produtoDao.create(p);  // Salva no banco

        System.out.println("Produto cadastrado no banco de dados!");
    }

    public void alterarProduto() {
        if (!(usuarioLogado instanceof Administrador)) {
            System.out.println("Acesso negado!");
            return;
        }

        listarProdutos();
        System.out.print("ID do produto: ");
        int id = sc.nextInt();

        Produto p = buscarProduto(id);
        if (p == null) {
            System.out.println("Produto nao encontrado");
            return;
        }

        sc.nextLine();
        System.out.print("Novo nome: ");
        p.setNome(sc.nextLine());

        System.out.print("Novo preco: ");
        p.setPreco(sc.nextDouble());

        System.out.print("Novo estoque: ");
        p.setEstoque(sc.nextInt());

        produtoDao.update(p);
        System.out.println("Produto atualizado!");
    }

    public void removerProduto() {
        if (!(usuarioLogado instanceof Administrador)) {
            System.out.println("Acesso negado!");
            return;
        }

        listarProdutos();
        System.out.print("ID do produto: ");
        int id = sc.nextInt();

        Produto p = buscarProduto(id);
        if (p == null) {
            System.out.println("Produto nao encontrado");
            return;
        }

        produtoDao.delete(id);  //  remove do banco
        produtos.remove(p);     // remove da lista em memoria
        System.out.println("Produto removido!");
    }

    public void listarProdutos() {
        System.out.println("===== PRODUTOS =====");
        List<Produto> produtosDoBanco = produtoDao.readAll();

        if (produtosDoBanco.isEmpty()) {
            System.out.println("Nenhum produto cadastrado!");
        } else {
            for (Produto p : produtosDoBanco) {
                System.out.println("ID: " + p.getId());
                System.out.println("Nome: " + p.getNome());
                System.out.println("Preco: R$ " + p.getPreco());
                System.out.println("Estoque: " + p.getEstoque());
                System.out.println("---");
            }
        }
    }

    public Produto buscarProduto(int id) {
        return produtoDao.read(id); //busca no banco
    }

    public void adicionarAoCarrinho() {
        listarProdutos();
        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt();

        Produto p = buscarProduto(id);
        if (p == null) {
            System.out.println("Produto nao encontrado!");
            return;
        }

        System.out.print("Quantidade: ");
        int quantidade = sc.nextInt();

        if (quantidade <= 0) {
            System.out.println("Quantidade invalida");
            return;
        }

        carrinho.adicionarProduto(p, quantidade);
    }

    public void cadastrarCliente() {
        if (usuarioLogado != null) {
            System.out.println("Voce ja esta logado");
            return;
        }

        System.out.println("==== CADASTRO ====");

        try {
            int id = contadorUsuario++;
            sc.nextLine();

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Idade: ");
            int idade = sc.nextInt();
            sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            Cliente cliente = new Cliente(id, nome, idade, email, senha);
            clienteDao.create(cliente);
            usuarios.add(cliente);

            System.out.println("Cadastro realizado com sucesso!");
            cliente.exibirTipoUsuario();

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void exibirDadosCliente() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuario logado");
            return;
        }

        System.out.println("===== SUA CONTA =====");
        usuarioLogado.exibirDados();
    }

    public void sairConta() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuario logado");
            return;
        }

        usuarioLogado = null;
        carrinho.limparCarrinho();
        System.out.println("Logout realizado com sucesso");
    }

    public void finalizarCompra() {
        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        if (usuarioLogado == null) {
            System.out.println("Voce precisa se cadastrar primeiro");
            cadastrarCliente();

            if (usuarioLogado == null) {
                System.out.println("Cadastro nao realizado");
                return;
            }
        }

        // Atualizar estoque de cada item no carrinho
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto p = item.getProduto();
            int quantidade = item.getQuantidade();
            produtoDao.atualizarEstoque(p.getId(), quantidade);
        }

        Pedido pedido = new Pedido(contadorPedido++, usuarioLogado, carrinho.getItens());
        pedidos.add(pedido);

        pedido.exibirResumo();

        carrinho.limparCarrinho();
    }

    public void listarTodosUsuarios() {
        System.out.println("===== TODOS OS USUARIOS DO BANCO =====");

        List<Usuario> clientesDoBanco = clienteDao.readAll();

        if (clientesDoBanco.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado no banco!");
        } else {
            for (Usuario u : clientesDoBanco) {
                System.out.println("---");
                u.exibirDados();
                u.exibirTipoUsuario();
            }
        }

        System.out.println("\n===== USUARIOS NA LISTA DE MEMORIA =====");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario na lista de memoria!");
        } else {
            for (Usuario u : usuarios) {
                System.out.println("---");
                u.exibirDados();
                u.exibirTipoUsuario();
            }
        }
    }

    public void menuAdmin() {
        int opcao = 0;

        do {
            try {
                System.out.println("\n===== MENU ADMIN =====");
                System.out.println("1 - Listar produtos");
                System.out.println("2 - Cadastrar produto");
                System.out.println("3 - Alterar produto");
                System.out.println("4 - Remover produto");
                System.out.println("5 - Listar usuarios");
                System.out.println("6 - Logout admin");
                System.out.print("Escolha: ");

                opcao = sc.nextInt();

                switch (opcao) {
                    case 1:
                        listarProdutos();
                        break;
                    case 2:
                        cadastrarProduto();
                        break;
                    case 3:
                        alterarProduto();
                        break;
                    case 4:
                        removerProduto();
                        break;
                    case 5:
                        listarTodosUsuarios();
                        break;
                    case 6:
                        logoutAdmin();
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Opcao invalida! Digite apenas numeros.");
                sc.nextLine();
                opcao = 0;
            }
        } while (opcao != 6);
    }

    public void menu() {
        int opcao = 0;

        do {
            try {
                System.out.println("\n===== E-COMMERCE =====");
                System.out.println("1 - Listar produtos");
                System.out.println("2 - Adicionar ao carrinho");
                System.out.println("3 - Ver carrinho");
                System.out.println("4 - Finalizar compra");
                System.out.println("5 - Cadastrar");
                System.out.println("6 - Login");
                System.out.println("7 - Sair da conta");
                System.out.println("8 - Ver conta");
                System.out.println("9 - Sair");
                System.out.println("10 - Login Admin");
                System.out.print("Escolha: ");

                opcao = sc.nextInt();

                switch (opcao) {
                    case 1:
                        listarProdutos();
                        break;
                    case 2:
                        adicionarAoCarrinho();
                        break;
                    case 3:
                        carrinho.listarCarrinho();
                        System.out.println("Total: R$ " + carrinho.calcularTotal());
                        break;
                    case 4:
                        finalizarCompra();
                        break;
                    case 5:
                        cadastrarCliente();
                        break;
                    case 6:
                        loginCliente();
                        break;
                    case 7:
                        sairConta();
                        break;
                    case 8:
                        exibirDadosCliente();
                        break;
                    case 9:
                        System.out.println("Saindo...");
                        break;
                    case 10:
                        loginAdmin();
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Opcao invalida! Digite apenas numeros.");
                sc.nextLine();
                opcao = 0;
            }
        } while (opcao != 9);
    }
}