import java.util.*;

public class SistemaEcommerce {

    private List<Produto> produtos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private Carrinho carrinho = new Carrinho();
    private Cliente clienteLogado = null;
    private int contadorPedido = 1;
    private int contadorCliente = 1;
    private boolean adminLogado = false;

    Scanner sc = new Scanner(System.in);

    public void loginAdmin() {
        sc.nextLine();

        System.out.print("Usuário admin: ");
        String user = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (user.equals("admin") && senha.equals("123")) {
            adminLogado = true;
            System.out.println("Admin logado com sucesso!");

            menuAdmin(); // entra no menu admin

        } else {
            System.out.println("Credenciais inválidas");
        }
    }

    public void logoutAdmin() {
        adminLogado = false;
        System.out.println("Admin deslogado");
    }

    public void cadastrarProduto() {

        if (!adminLogado) {
            System.out.println("Acesso negado!");
            return;
        }

        sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Preço: ");
        double preco = sc.nextDouble();

        System.out.print("Estoque: ");
        int estoque = sc.nextInt();

        int id = produtos.size() + 1;

        Produto p = new Produto(id, nome, preco, estoque);
        produtos.add(p);

        System.out.println("Produto cadastrado!");
    }

    public void alterarProduto() {

        if (!adminLogado) {
            System.out.println("Acesso negado!");
            return;
        }

        listarProdutos();

        System.out.print("ID do produto: ");
        int id = sc.nextInt();

        Produto p = buscarProduto(id);

        if (p == null) {
            System.out.println("Produto não encontrado");
            return;
        }

        sc.nextLine();

        System.out.print("Novo nome: ");
        p.setNome(sc.nextLine());

        System.out.print("Novo preço: ");
        p.setPreco(sc.nextDouble());

        System.out.print("Novo estoque: ");
        p.setEstoque(sc.nextInt());

        System.out.println("Produto atualizado!");
    }

    public void removerProduto() {

        if (!adminLogado) {
            System.out.println("Acesso negado!");
            return;
        }

        listarProdutos();

        System.out.print("ID do produto: ");
        int id = sc.nextInt();

        Produto p = buscarProduto(id);

        if (p == null) {
            System.out.println("Produto não encontrado");
            return;
        }

        produtos.remove(p);
        System.out.println("Produto removido!");
    }

    public void listarProdutos() {
        System.out.println("===== PRODUTOS =====");
        for (Produto p : produtos) {
            p.exibirDetalhes();
        }
    }

    public Produto buscarProduto(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void adicionarAoCarrinho() {
        listarProdutos();
        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt();

        Produto p = buscarProduto(id);
        if (p == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        System.out.print("Quantidade: ");
        int quantidade = sc.nextInt();

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida");
            return;
        }

        carrinho.adicionarProduto(p, quantidade);
    }

    public void cadastrarCliente() {

        if (clienteLogado != null) {
            System.out.println("Você já está logado");
            return;
        }

        System.out.println("==== CADASTRO ====");

        try {
            int id = contadorCliente++;

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

            clienteLogado = cliente;
            System.out.println("Cadastro realizado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void exibirDadosCliente() {
        if (clienteLogado == null) {
            System.out.println("Nenhum cliente logado");
            return;
        }

        System.out.println("===== SUA CONTA =====");
        System.out.println("ID: " + clienteLogado.getId());
        System.out.println("Nome: " + clienteLogado.getNome());
        System.out.println("Idade: " + clienteLogado.getIdade());
        System.out.println("Email: " + clienteLogado.getEmail());
    }

    public void sairConta() {
        if (clienteLogado == null) {
            System.out.println("Nenhum cliente logado");
            return;
        }

        clienteLogado = null;
        carrinho.limparCarrinho();
        System.out.println("Logout realizado com sucesso");
    }

    public void finalizarCompra() {

        if (carrinho.getItens().isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        if (clienteLogado == null) {
            System.out.println("Você precisa se cadastrar primeiro");
            cadastrarCliente();

            if (clienteLogado == null) {
                System.out.println("Cadastro não realizado");
                return;
            }
        }

        Pedido pedido = new Pedido(contadorPedido++, clienteLogado, carrinho.getItens());
        pedidos.add(pedido);

        pedido.exibirResumo();
        carrinho.limparCarrinho();
    }

    public void menuAdmin() {
        int opcao;

        do {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Cadastrar produto");
            System.out.println("3 - Alterar produto");
            System.out.println("4 - Remover produto");
            System.out.println("5 - Logout admin");
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
                    logoutAdmin();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 5);
    }

    public void menu() {
        int opcao;

        do {
            System.out.println("\n===== E-COMMERCE =====");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Adicionar ao carrinho");
            System.out.println("3 - Ver carrinho");
            System.out.println("4 - Finalizar compra");
            System.out.println("5 - Cadastrar");
            System.out.println("6 - Sair da conta");
            System.out.println("7 - Ver conta");
            System.out.println("8 - Sair");
            System.out.println("9 - Login Admin");
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
                    sairConta();
                    break;
                case 7:
                    exibirDadosCliente();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                case 9:
                    loginAdmin();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 8);
    }
}