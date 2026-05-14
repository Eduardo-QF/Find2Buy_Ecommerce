package br.com.ecommerce.view;

import br.com.ecommerce.dao.ClienteDao;
import br.com.ecommerce.dao.ProdutoDao;
import br.com.ecommerce.model.*;
import br.com.ecommerce.strategy.CalculadoraFrete;
import br.com.ecommerce.strategy.FreteExpresso;
import br.com.ecommerce.strategy.FreteNormal;

import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaEcommerce {
    //ATRIBUTOS
    //private List<Produto> produtos = new ArrayList<>(); Lista local "removida" devido a conexão com o banco de dados, isso impede lista local e DAO ficarem diferentes
    private List<Pedido> pedidos = new ArrayList<>(); //Historico em memória
    private Carrinho carrinho = new Carrinho(); //Carrinho atual, é uma sessão atual, oque não faria sentido salvar no banco
    private Usuario usuarioLogado = null;
    //private List<Usuario> usuarios = new ArrayList<>(); Lista local "removida" devido a conexão com banco de dados
    private int contadorPedido = 1;
    private int contadorUsuario = 1;
    private ClienteDao clienteDao = new ClienteDao();
    private ProdutoDao produtoDao = new ProdutoDao(); // Um atributo que cria o objeto ProdutoDao e declara
    private CalculadoraFrete freteNormal; //frete padrão
    private CalculadoraFrete freteExpresso; //frete rápido
    private CalculadoraFrete freteSelecionado; // O que foi escolhido pelo cliente

    Scanner sc = new Scanner(System.in);

    //Construtor
    public SistemaEcommerce() {
        // Cria os objetos das classes que implementam a interface
        freteNormal = new FreteNormal();      // instancia a classe
        freteExpresso = new FreteExpresso();  // instancia a classe
        freteSelecionado = freteNormal;       // começa com frete normal
    }



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
            // usuarios.add(admin);
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

        // ID = 0 (temporário) - o banco vai gerar o ID real
        Produto p = new Produto(0, nome, preco, estoque);
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
        //produtos.remove(p);     // remove da lista em memoria
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
        sc.nextLine();

        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Idade: ");
            int idade = sc.nextInt();
            sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Senha: ");
            String senha = sc.nextLine();

            System.out.print("CEP: ");
            String cep = sc.nextLine();

            Cliente cliente = new Cliente(0, nome, idade, email, senha, cep);
            clienteDao.create(cliente);
            //usuarios.add(cliente);

            System.out.println("Cadastro realizado com sucesso!");
            cliente.exibirTipoUsuario();

            System.out.println("\nRealizando login automatico...");

            Usuario usuarioLogadoAposCadastro = clienteDao.autenticar(email, senha);

            if (usuarioLogadoAposCadastro != null) {
                usuarioLogado = usuarioLogadoAposCadastro;
                System.out.println("Login automatico realizado com sucesso!");
                usuarioLogado.exibirDados();
            } else {
                System.out.println("Erro no login automatico. Faca login manualmente.");
            }
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
            System.out.println("Você precisa se cadastrar ou estar logado primeiro");
            System.out.println("Você já possui cadastro 1 - (Sim) / 2 - (Não)");

            int opcao = sc.nextInt();

            if (opcao == 1) {
                loginCliente();
            } else if (opcao == 2) {
                cadastrarCliente();
            } else {
                System.out.println("Opção inválida");
            }


            if (usuarioLogado == null) {
                System.out.println("Cadastro ou login nao realizado");
                return;
            }
        }

        // PERGUNTAR O TIPO DE FRETE ANTES DE FINALIZAR
        System.out.println("\nAntes de finalizar, escolha o tipo de frete:");
        escolherFrete();

        // Calcular valores com o frete selecionado
        double subtotal = carrinho.calcularTotal();
        double frete = calcularFrete();
        double total = subtotal + frete;

        // Mostrar resumo da compra
        System.out.println("\n===== RESUMO DA COMPRA =====");
        carrinho.listarCarrinho();
        System.out.println("--------------------------------");
        System.out.println("Subtotal: R$ " + String.format("%.2f", subtotal));
        System.out.println("Frete: R$ " + String.format("%.2f", frete));
        System.out.println("--------------------------------");
        System.out.println("TOTAL A PAGAR: R$ " + String.format("%.2f", total));

        System.out.print("\nConfirmar compra? (1-Sim / 2-Nao): ");
        int confirmar = sc.nextInt();

        if (confirmar != 1) {
            System.out.println("Compra cancelada!");
            return;
        }

        // Atualizar estoque de cada item no carrinho
        for (ItemCarrinho item : carrinho.getItens()) {
            Produto p = item.getProduto();
            int quantidade = item.getQuantidade();
            produtoDao.atualizarEstoque(p.getId(), quantidade);
        }

        Pedido pedido = new Pedido(contadorPedido++, usuarioLogado, carrinho.getItens());
        pedidos.add(pedido);

        System.out.println("\nCOMPRA FINALIZADA COM SUCESSO!");
        pedido.exibirResumo();
        System.out.println("Frete utilizado: " + (freteSelecionado instanceof FreteExpresso ? "Expresso" : "Normal"));
        System.out.println("Total pago: R$ " + String.format("%.2f", total));

        carrinho.limparCarrinho();
    }

    public void listarTodosUsuarios() {
        System.out.println("===== TODOS OS USUARIOS =====");

        List<Usuario> todosUsuarios = clienteDao.readAll();

        if (todosUsuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado!");
        } else {
            for (Usuario u : todosUsuarios) {
                System.out.println("---");
                u.exibirDados();
                u.exibirTipoUsuario();
            }
        }
    }

    //FRETE
    //Método para escolher frete
    public void escolherFrete() {
        System.out.println("\n===== ESCOLHA O TIPO DE FRETE =====");
        System.out.println("1 - Frete Normal");
        System.out.println("2 - Frete Expresso");
        System.out.print("Escolha: ");

        int opcao = sc.nextInt();

        if (opcao == 2) {
            freteSelecionado = freteExpresso;
            System.out.println("Frete Expresso selecionado!");
        } else {
            freteSelecionado = freteNormal;
            System.out.println("Frete Normal selecionado!");
        }
    }

    private String obterCepCliente() {
        if (usuarioLogado instanceof Cliente) {
            Cliente cliente = (Cliente) usuarioLogado;
            String cep = cliente.getCep();

            if (cep != null && !cep.isEmpty()) {
                return cep;
            }
        }

        // Se não tiver CEP, pergunta
        System.out.print("Digite seu CEP para calcular o frete: ");
        String cepDigitado = sc.nextLine();

        // Validação básica - foi usado o trim para remover espaços no começo e no fim da string
        while (cepDigitado == null || cepDigitado.trim().isEmpty()) {
            System.out.print("CEP não pode ficar vazio! Digite novamente: ");
            cepDigitado = sc.nextLine();
        }

        return cepDigitado;
    }

    public double calcularFrete() {
        String cep = obterCepCliente();
        return freteSelecionado.calcular(cep);  // Chama o método da classe escolhida
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
                System.out.println("\n===== Find2Buy - ECOMMERCE =====");
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
                        double subtotalCarrinho = carrinho.calcularTotal();
                        System.out.println("Subtotal: R$ " + String.format("%.2f", subtotalCarrinho));

                        // Perguntar se quer ver opções de frete
                        if (!carrinho.getItens().isEmpty()) {
                            System.out.print("\nDeseja calcular frete? (1-Sim / 2-Nao): ");
                            int calcFrete = sc.nextInt();

                            if (calcFrete == 1) {
                                escolherFrete();
                                double freteCarrinho = calcularFrete();
                                double totalCarrinho = subtotalCarrinho + freteCarrinho;
                                System.out.println("Frete: R$ " + String.format("%.2f", freteCarrinho));
                                System.out.println("TOTAL COM FRETE: R$ " + String.format("%.2f", totalCarrinho));
                            }
                        }

                        else {
                            System.out.println("Não é possível calcular frete");
                        }
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