import java.util.*;

public class Pedido {
    private int id;
    private Usuario usuario;
    private List<ItemCarrinho> itens;
    private double total;
    private String status;

    public Pedido(int id, Usuario usuario, List<ItemCarrinho> itens) {
        this.id = id;
        this.usuario = usuario;
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

    public void exibirResumo(double descontoPercentual) {
        double valorComDesconto = total - (total * descontoPercentual / 100);
        exibirResumo();
        System.out.println("DESCONTO: " + descontoPercentual + "%");
        System.out.println("TOTAL COM DESCONTO: R$" + valorComDesconto);
    }
}