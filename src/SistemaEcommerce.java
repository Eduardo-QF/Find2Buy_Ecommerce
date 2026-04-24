import java.util.*;

public class SistemaEcommerce {

    private List<Produto> produtos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private Carrinho carrinho = new Carrinho();
    private Usuario usuarioLogado = null;
    private List<Usuario> usuarios = new ArrayList<>();
    private int contadorPedido = 1;
    private int contadorUsuario = 1;

    Scanner sc = new Scanner(System.in);

    public void loginAdmin() {
        sc.nextLine();

        System.out.print("Usuário admin: ");
        String user = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        if (user.equals("admin") && senha.equals("123")) {
            
            Administrador admin = new Administrador(contadorUsuario++, "Administrador", "admin@sistema.com", senha, "Gerente", "TI");
            usuarioLogado = admin;
            System.out.println("Admin logado com sucesso!");
            admin.gerenciarSistema();
            menuAdmin();
        } else {
            System.out.println("Credenciais inválidas");
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
        if (!(usuarioLogado instanceof Administrador)) {
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
        if (!(usuarioLogado instanceof Administrador)) {
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
        if (usuarioLogado != null) {
            System.out.println("Você já está logado");
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
            usuarioLogado = cliente;
            usuarios.add(cliente);

            System.out.println("Cadastro realizado com sucesso!");
            cliente.exibirTipoUsuario();

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void exibirDadosCliente() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuário logado");
            return;
        }

        System.out.println("===== SUA CONTA =====");
        usuarioLogado.exibirDados();
    }

    public void sairConta() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuário logado");
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
            System.out.println("Você precisa se cadastrar primeiro");
            cadastrarCliente();

            if (usuarioLogado == null) {
                System.out.println("Cadastro não realizado");
                return;
            }
        }

        Pedido pedido = new Pedido(contadorPedido++, usuarioLogado, carrinho.getItens());
        pedidos.add(pedido);

        pedido.exibirResumo();
        
        carrinho.limparCarrinho();
    }

    public void listarTodosUsuarios() {
        System.out.println("===== TODOS OS USUÁRIOS CADASTRADOS =====");
        for (Usuario u : usuarios) {
            System.out.println("---");
            u.exibirDados();
            u.exibirTipoUsuario();
        }
    }

    public void menuAdmin() {
        int opcao;

        do {
            System.out.println("\n===== MENU ADMIN =====");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Cadastrar produto");
            System.out.println("3 - Alterar produto");
            System.out.println("4 - Remover produto");
            System.out.println("5 - Listar usuários (demo polimorfismo)");
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
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 6);
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