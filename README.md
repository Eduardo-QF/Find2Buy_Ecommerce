# E-commerce

## 👥 Integrantes do Grupo
- Eduardo Quintino Filho - 41775317
- Felipe Veiga da Silva - 41779801
- Giovanna de Vasconcelos Borges - 42975212
- Niccolas Lupetti dos Santos - 42727430
- Victor Candile Monteiro Barbosa - 41713397

---

## 📋 Tema Escolhido
E-commerce

---

## 🎯 Objetivo do Sistema

Sistema de e-commerce desenvolvido em Java com execução via terminal, que simula o funcionamento de uma loja virtual completa. O sistema permite que clientes naveguem por produtos, montem seu carrinho de compras e finalizem pedidos, enquanto administradores gerenciam o catálogo da loja.

O projeto é dividido em dois perfis de acesso: o **cliente**, que pode se cadastrar, visualizar produtos, adicionar itens ao carrinho e concluir compras; e o **administrador**, que possui acesso a um menu exclusivo para cadastrar, editar e remover produtos do sistema.

A aplicação foi desenvolvida aplicando os princípios de Orientação a Objetos, com validações de dados nos construtores e setters, controle de estoque em tempo real e geração de pedidos com resumo detalhado da compra.

---

## 📦 Funcionalidades Principais

1. **Cadastro e autenticação de clientes** – O cliente informa nome, idade (mínimo 18 anos), e-mail e senha para se registrar. Os dados são validados antes do cadastro ser concluído.
2. **Gerenciamento de produtos (Admin)** – O administrador pode cadastrar, listar, alterar e remover produtos do catálogo, acessando um menu exclusivo protegido por login.
3. **Carrinho de compras** – O cliente pode adicionar produtos ao carrinho com verificação de estoque disponível, visualizar os itens e calcular o total da compra.
4. **Finalização de pedido** – Ao finalizar a compra, um `Pedido` é gerado com o resumo dos itens, quantidades, subtotais e valor total, e o carrinho é limpo automaticamente.
5. **Controle de estoque** – O estoque de cada produto é reduzido automaticamente ao ser adicionado ao carrinho, impedindo vendas além da quantidade disponível.

---

## 🏗️ Estrutura de Classes

### Classes do Sistema Base (CP1)

- **Classe 1:** `Cliente` – Representa o usuário comprador, armazenando seus dados pessoais (id, nome, idade, e-mail e senha) com validações em todos os setters.
- **Classe 2:** `Produto` – Representa um item à venda, com controle de preço e estoque, e métodos para aumentar ou reduzir a quantidade disponível.
- **Classe 3:** `ItemCarrinho` – Representa a associação entre um `Produto` e a quantidade escolhida pelo cliente, calculando o subtotal de cada item.
- **Classe 4:** `Carrinho` – Agrega os `ItemCarrinho`, gerencia a adição de produtos com verificação de estoque, listagem dos itens e cálculo do total geral.
- **Classe 5:** `Pedido` – Registra uma compra finalizada, vinculando o `Cliente` à lista de itens adquiridos e armazenando o total pago e o status do pedido.
- **Classe 6:** `SistemaEcommerce` – Classe principal de controle que gerencia o fluxo do sistema, os menus (cliente e admin), e coordena todas as operações entre as demais classes.

### Implementações de Hierarquia e Polimorfismo (CP2)

#### Hierarquia de Usuários


- **Superclasse:** `Usuario` – Classe base abstrata que contém os atributos e métodos comuns a todos os usuários do sistema (id, nome, email, senha). Implementa métodos de autenticação e exibição de dados que serão sobrescritos pelas subclasses.

- **Subclasse 1:** `Cliente` – Herda de `Usuario` e adiciona o atributo `idade` com validação específica (mínimo 18 anos). Sobrescreve os métodos `exibirTipoUsuario()` e `exibirDados()` para comportamentos específicos do cliente. Implementa sobrecarga no método `isMaiorIdade()`.

- **Subclasse 2:** `Administrador` – Herda de `Usuario` e adiciona os atributos `cargo` e `setor`. Sobrescreve os métodos de exibição e adiciona métodos específicos de gerenciamento com sobrecarga (`gerenciarSistema()` com diferentes parâmetros).

---

## 🔄 Regra de Negócio Complexa

**Fluxo de finalização de compra com cadastro condicional e controle de estoque integrado**

Ao tentar finalizar uma compra, o sistema executa uma cadeia de verificações:

1. Verifica se o carrinho possui itens — caso esteja vazio, a operação é cancelada imediatamente.
2. Verifica se há um cliente logado — caso não haja, o sistema inicia automaticamente o fluxo de cadastro antes de prosseguir.
3. Se o cadastro falhar (dados inválidos), a compra é interrompida sem gerar pedido.
4. Com cliente válido e carrinho não vazio, um `Pedido` é criado com os itens atuais, o total é calculado dinamicamente somando os subtotais de cada `ItemCarrinho`, o resumo é exibido e o carrinho é limpo.

Além disso, o estoque é decrementado no momento em que o produto é adicionado ao carrinho (e não apenas na finalização), garantindo que dois clientes não possam reservar o mesmo item simultaneamente além do limite disponível.

---

## 🎯 Implementações de Programação Orientada a Objetos (CP2)

### ✅ Hierarquia de Classes
- Criada hierarquia com 3 níveis: `Usuario` (superclasse), `Cliente` e `Administrador` (subclasses)
- Utilização de herança para reaproveitamento de código
- Atributos protegidos (`protected`) permitindo acesso pelas subclasses

### ✅ Sobrescrita de Métodos (@Override)
- `exibirTipoUsuario()` sobrescrito em `Cliente` e `Administrador`
- `exibirDados()` sobrescrito em ambas as subclasses utilizando `super` para chamar o método da superclasse
- Cada subclasse implementa comportamento específico mantendo a mesma assinatura

### ✅ Uso de `super`
- Construtores das subclasses chamam `super()` para inicializar atributos da superclasse
- Métodos sobrescritos utilizam `super.exibirDados()` para reaproveitar a implementação base

### ✅ Sobrecarga de Métodos
- **`Produto.atualizarPreco()`**: 3 versões
  - `atualizarPreco(double novoPreco)`
  - `atualizarPreco(double novoPreco, double desconto)`
  - `atualizarPreco(String promocao, double desconto)`
- **`Pedido.exibirResumo()`**: 2 versões
  - `exibirResumo()` sem parâmetros
  - `exibirResumo(double descontoPercentual)` com desconto
- **`Administrador.gerenciarSistema()`**: 3 versões
  - Sem parâmetros, com um parâmetro String, e com String + int
- **`Cliente.isMaiorIdade()`**: 2 versões
  - Sem parâmetros e com idade mínima personalizada
- **`Usuario.autenticar()`**: 2 versões
  - Com email e senha, e apenas com senha

### ✅ ArrayList Polimórfico
- `List<Usuario> usuarios` no `SistemaEcommerce` armazena objetos de diferentes subclasses
- `Pedido` agora aceita qualquer `Usuario` (polimorfismo no parâmetro)
- Iteração polimórfica: `for (Usuario u : usuarios)` chama métodos específicos de cada subclasse

### ✅ Polimorfismo em Ação
- Tratamento genérico de objetos através da superclasse `Usuario`
- Método `exibirDados()` chama automaticamente a versão correta para cada tipo de usuário
- `instanceof` utilizado para verificar permissões de administrador

---

## 🔧 Melhorias Técnicas Implementadas

1. **Refatoração da classe Cliente** para herdar de Usuario, eliminando duplicação de código
2. **Criação da classe Administrador** com funcionalidades específicas de gerenciamento
3. **Modificação da classe Pedido** para aceitar qualquer tipo de Usuario (não apenas Cliente)
4. **Adição de ArrayList polimórfico** para gerenciar diferentes tipos de usuários
5. **Implementação de sobrecarga** em múltiplas classes para flexibilidade de uso
6. **Uso consistente de @Override** para garantir sobrescrita correta de métodos