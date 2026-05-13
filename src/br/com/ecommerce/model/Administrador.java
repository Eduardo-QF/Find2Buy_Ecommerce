package br.com.ecommerce.model;

public class Administrador extends Usuario {
    //ATRIBUTOS
    private String cargo;
    private String setor;

    //METODOS CONSTRUTOR
    public Administrador(int id, String nome, String email, String senha, String cargo, String setor) {
        super(id, nome, email, senha);
        this.cargo = cargo;
        this.setor = setor;
    }

    // GETTERS E SETTERS
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    //METODOS SOBRESCRITOS
    @Override
    public void exibirTipoUsuario() {
        System.out.println("Tipo: Administrador");
    }

    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Cargo: " + cargo);
        System.out.println("Setor: " + setor);
    }

    //METODOS
    public void gerenciarSistema() {
        System.out.println( nome + " está gerenciando o sistema");
    }
}