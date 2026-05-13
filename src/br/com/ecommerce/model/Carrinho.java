package br.com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

// Sacola temporaria de compras, armazena uma lista de itens individuais
public class Carrinho {
    //ATRIBUTO
    List<ItemCarrinho> itens = new ArrayList<>();

    //METODOS
    public List<ItemCarrinho> getItens() {
        return itens;
    } //Devolve a lista de itens para quem pedir

    public void adicionarProduto (Produto produto, int quantidade){
        if (produto.getEstoque() < quantidade){
            System.out.println("Estoque insuficiente");
            return;
        }
        itens.add(new ItemCarrinho(produto, quantidade));//cria um novo objeto ItemCarrinho com produto e quantidade
        produto.reduzirEstoque(quantidade);
        System.out.println("Produto adicionado no carrinho");
    }

    public void listarCarrinho (){
        if (itens.isEmpty()){
            System.out.println("Carrinho vazio");
            return;
        }

        for (ItemCarrinho item : itens) { //Para cada ItemCarrinho dentro da lista itens...
            //Na primeira volta item é o primeiro, na segunda volta item é o segundo
            System.out.println(item.getProduto().getNome() +
                    " | Quantidade: " + item.getQuantidade() +
                    " | Subtotal: R$" + item.calcularSubtotal());
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void limparCarrinho() {
        itens.clear();
    }
}