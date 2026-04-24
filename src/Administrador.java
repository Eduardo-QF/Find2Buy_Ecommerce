public class Administrador extends Usuario {
    private String cargo;
    private String setor;

    public Administrador(int id, String nome, String email, String senha, String cargo, String setor) {
        super(id, nome, email, senha);
        this.cargo = cargo;
        this.setor = setor;
    }

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

    public void gerenciarSistema() {
        System.out.println("Administrador " + nome + " está gerenciando o sistema");
    }

    public void gerenciarSistema(String funcionalidade) {
        System.out.println("Administrador " + nome + " gerenciando: " + funcionalidade);
    }

    public void gerenciarSistema(String funcionalidade, int prioridade) {
        System.out.println("Administrador " + nome + " gerenciando: " + funcionalidade + " (Prioridade: " + prioridade + ")");
    }
}