package br.com.ecommerce.model;

import java.util.*;
//Compra finalizada
public class Pedido {
    //ATRIBUTOS
    private int id;
    private Usuario usuario;
    private List<ItemCarrinho> itens; //copia da lista do carrinho
    private double total;
    private String status;

    //CLASSE CONSTRUTORA
    public Pedido(int id, Usuario usuario, List<ItemCarrinho> itens) {
        this.id = id;
        this.usuario = usuario;
        this.itens = new ArrayList<>(itens);
        this.total = calcularTotal();
        this.status = "PAGO";
    }

    //É importante ter ele aqui pois o pedido não pode mudar, ele guarda o valor que foi pago
    public double calcularTotal() { //calcula sobre a lista copia do carrinho
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void exibirResumo() {
        System.out.println("===== PEDIDO #" + id + " =====");
        System.out.print("Cliente: " + usuario.getNome() + " - ");
        usuario.exibirTipoUsuario();

        for (ItemCarrinho item : itens) {
            System.out.println(item.getProduto().getNome() +
                    " x" + item.getQuantidade() +
                    " = R$" + item.calcularSubtotal());
        }

        System.out.println("TOTAL: R$" + total);
        System.out.println("Status: " + status);
    }
}