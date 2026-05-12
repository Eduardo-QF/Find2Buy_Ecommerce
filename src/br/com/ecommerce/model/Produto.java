package br.com.ecommerce.model;

public class Produto {
    //ATRIBUTOS
    private int id;
    private String nome;
    private double preco;
    private int estoque;

    //CLASSE CONSTRUTORA
    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        setNome(nome);
        setPreco(preco);
        setEstoque(estoque);
    }

    //GETTERS SETTERS
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

    //METODOS
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
}