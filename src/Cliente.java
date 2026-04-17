public class Cliente {
    private int id;
    private String nome;
    private int idade;
    private String email;
    private String senha;

    // construtor
    public Cliente(int id, String nome, int idade, String email, String senha) {
        this.id = id;
        setNome(nome);
        setIdade(idade);
        setEmail(email);
        setSenha(senha);
    }

    // getters e setters
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 18) {
            throw new IllegalArgumentException("Você precisa ter mais de 18 anos de idade");
        }
        this.idade = idade;
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

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 5) {
            throw new IllegalArgumentException("Senha inválida");
        }
        this.senha = senha;
    }
}

