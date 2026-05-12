package br.com.ecommerce.model;
//Classe Mãe
public abstract class  Usuario {
    //ATRIBUTOS
    protected int id;
    protected String nome;
    protected String email;
    protected String senha;

    //METODO CONSTRUTOR
    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        //Foi utilizado o set para passar pela validação ao ser acessado
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }

    //GETTERS SETTERS
    //Validações usando == null(verifica se não aponta para nenhuma string), trim()(Remove espaços do começo e fim)
    //isEmpty() (Verifica se está vazio), contains() (verifica se contém determinado texto)

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 5) {
            throw new IllegalArgumentException("Senha inválida");
        }
        this.senha = senha;
    }

    //METODO ABSTRATO, a classe cliente e administrador faz o override desse metodo
    public abstract void exibirTipoUsuario(); //As classes filhas são obrigadas a implementarem.

    //METODOS CONCRETOS, prontos para serem usados ou herdados, ja sabem exatamente oque fazer.
    public void exibirDados() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
    }

    //Verifica se o email guardado(this.email) é igual ao email que o usuario digitou, o mesmo acontece para a senha
    public boolean autenticar(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
}


