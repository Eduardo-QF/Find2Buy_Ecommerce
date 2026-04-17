import java.util.*;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<ItemCarrinho> itens;
    private double total;
    private String status;

    public Pedido(int id, Cliente cliente, List<ItemCarrinho> itens) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens);
        this.total = calcularTotal();
        this.status = "PAGO";
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void exibirResumo() {
        System.out.println("===== PEDIDO #" + id + " =====");
        System.out.println("Cliente: " + cliente.getNome());

        for (ItemCarrinho item : itens) {
            System.out.println(item.getProduto().getNome() +
                    " x" + item.getQuantidade() +
                    " = R$" + item.calcularSubtotal());
        }

        System.out.println("TOTAL: R$" + total);
        System.out.println("Status: " + status);
    }
}
