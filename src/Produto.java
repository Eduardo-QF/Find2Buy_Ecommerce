public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        setNome(nome);
        setPreco(preco);
        setEstoque(estoque);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
        } else {
            System.out.println("Preço deve ser maior que 0");
        }
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        if (estoque >= 0) {
            this.estoque = estoque;
        } else {
            System.out.println("Estoque inválido");
        }
    }

    public void exibirDetalhes() {
        System.out.println(id + " | " + nome + " | " + "R$ " + preco + " | " + "Estoque " + estoque);
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade <= 0){
            System.out.println("Quantidade inválida");
        }
        if (estoque >= quantidade) {
            estoque -= quantidade;
        }
        else {
            System.out.println("Estoque insuficiente");
        }
    }

    public void aumentarEstoque(int quantidade) {
        estoque += quantidade;
    }

    public void atualizarPreco(double novoPreco) {
        setPreco(novoPreco);
        System.out.println("Preço atualizado para R$" + novoPreco);
    }

    public void atualizarPreco(double novoPreco, double desconto) {
        double precoComDesconto = novoPreco - (novoPreco * desconto / 100);
        setPreco(precoComDesconto);
        System.out.println("Preço atualizado com " + desconto + "% de desconto: R$" + precoComDesconto);
    }

    public void atualizarPreco(String promocao, double desconto) {
        if (promocao.equalsIgnoreCase("BLACKFRIDAY")) {
            double precoPromocional = preco - (preco * desconto / 100);
            setPreco(precoPromocional);
            System.out.println("Preço promocional Black Friday: R$" + precoPromocional);
        }
    }
}