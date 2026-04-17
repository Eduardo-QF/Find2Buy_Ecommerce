
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

- **Classe 1:** `Cliente` – Representa o usuário comprador, armazenando seus dados pessoais (id, nome, idade, e-mail e senha) com validações em todos os setters.
- **Classe 2:** `Produto` – Representa um item à venda, com controle de preço e estoque, e métodos para aumentar ou reduzir a quantidade disponível.
- **Classe 3:** `ItemCarrinho` – Representa a associação entre um `Produto` e a quantidade escolhida pelo cliente, calculando o subtotal de cada item.
- **Classe 4:** `Carrinho` – Agrega os `ItemCarrinho`, gerencia a adição de produtos com verificação de estoque, listagem dos itens e cálculo do total geral.
- **Classe 5:** `Pedido` – Registra uma compra finalizada, vinculando o `Cliente` à lista de itens adquiridos e armazenando o total pago e o status do pedido.
- **Classe 6:** `SistemaEcommerce` – Classe principal de controle que gerencia o fluxo do sistema, os menus (cliente e admin), e coordena todas as operações entre as demais classes.

---

## 🔄 Regra de Negócio Complexa

**Fluxo de finalização de compra com cadastro condicional e controle de estoque integrado**

Ao tentar finalizar uma compra, o sistema executa uma cadeia de verificações:

1. Verifica se o carrinho possui itens — caso esteja vazio, a operação é cancelada imediatamente.
2. Verifica se há um cliente logado — caso não haja, o sistema inicia automaticamente o fluxo de cadastro antes de prosseguir.
3. Se o cadastro falhar (dados inválidos), a compra é interrompida sem gerar pedido.
4. Com cliente válido e carrinho não vazio, um `Pedido` é criado com os itens atuais, o total é calculado dinamicamente somando os subtotais de cada `ItemCarrinho`, o resumo é exibido e o carrinho é limpo.

Além disso, o estoque é decrementado no momento em que o produto é adicionado ao carrinho (e não apenas na finalização), garantindo que dois clientes não possam reservar o mesmo item simultaneamente além do limite disponível.