package br.com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    List<ItemCarrinho> itens = new ArrayList<>();

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void adicionarProduto (Produto produto, int quantidade){
        if (produto.getEstoque() < quantidade){
            System.out.println("Estoque insuficiente");
            return;
        }
        itens.add(new ItemCarrinho(produto, quantidade));
        produto.reduzirEstoque(quantidade);
        System.out.println("Produto adicionado no carrinho");
    }

    public void listarCarrinho (){
        if (itens.isEmpty()){
            System.out.println("Carrinho vazio");
            return;
        }

        for (ItemCarrinho item : itens) {
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