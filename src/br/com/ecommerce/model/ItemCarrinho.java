package br.com.ecommerce.model;

// Representa um produto dentro do carrinho
public class ItemCarrinho {
    //ATRIBUTOS
    private Produto produto;
    private int quantidade;

    //CLASSE CONSTRUTORA
    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    //GETTERS SETTERS
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    //METODO
    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }

}