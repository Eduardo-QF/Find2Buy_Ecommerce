package br.com.ecommerce.model;

public abstract class  Usuario {
    protected int id;
    protected String nome;
    protected String email;
    protected String senha;

    //METODO CONSTRUTOR
    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        setNome(nome);
        setEmail(email);
        setSenha(senha);
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

    //METODO ABSTRATO, a classe cliente faz o override desse metodo
    public abstract void exibirTipoUsuario();

    //METODOS CONCRETOS, prontos para serem usados ou herdados
    public void exibirDados() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
    }

    public boolean autenticar(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}


