// model/Cliente.java
package br.com.ecommerce.model;

public class Cliente extends Usuario {
    //ATRIBUTOS
    private int idade;
    private String cep;

    public Cliente(int id, String nome, int idade, String email, String senha, String cep) {
        super(id, nome, email, senha);
        setIdade(idade);
        this.cep = cep;

    }

    //Getters Setters
    public int getIdade() { return idade; }

    public void setIdade(int idade) {
        if (idade < 18) {
            throw new IllegalArgumentException("Cliente precisa ter 18 anos ou mais");
        }
        this.idade = idade;
    }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

//    METODOS SOBRESCRITOS
    @Override
    public void exibirTipoUsuario() {
        System.out.println("Tipo: Cliente");
    }

    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Idade: " + idade);
    }

    //METODO
    public boolean isMaiorIdade() {
        return idade >= 18; }
}