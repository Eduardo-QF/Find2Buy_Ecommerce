# Find2Buy - Ecommerce

[Java 17+] [PostgreSQL 14+] 

## Integrantes do Projeto

| Nome | RGM
|------|--------
| Eduardo Quintino Filho | 41775317
| Felipe Veiga da Silva | 41779801
| Giovanna de Vasconcelos Borges | 42975212
| Niccolas Lupetti dos Santos | 42727430
| Victor Candile Monteiro Barbosa | 41713397


---

## Objetivo do Projeto

O Find2Buy e um sistema de e-commerce desenvolvido em Java com conexao a banco de dados PostgreSQL. O sistema permite cadastro e autenticacao de clientes, gerenciamento de produtos (CRUD completo com banco de dados), carrinho de compras com calculo de frete (Strategy Pattern), finalizacao de pedidos com historico, area administrativa com login especifico e persistencia de dados relacional.

---

## Video Demonstrativo

https://youtu.be/UqojbkExQJI?si=NICWdVEhs-LMg_4d

---

## Tecnologias Utilizadas

| Tecnologia | Versao | Finalidade |
|------------|--------|------------|
| Java | 17+ | Linguagem principal |
| PostgreSQL | 14+ | Banco de dados relacional |
| JDBC | - | Driver de conexao Java/PostgreSQL |
| PreparedStatement | - | Execucao de consultas SQL seguras |
| Maven | - | Gerenciador de dependencias |

---

## Arquitetura do Projeto

### Camadas
| Camada | Pacote | Descricao |
|--------|--------|-----------|
| View | view/ | Interface com o usuario (terminal) |
| Model | model/ | Entidades do sistema |
| DAO | dao/ | Acesso ao banco de dados (PostgreSQL) |
| Strategy | strategy/ | Algoritmos de calculo de frete |
| Database | database/ | Conexao com PostgreSQL |

### Conexao com Banco de Dados
- Driver: PostgreSQL JDBC Driver
- Connection: Gerenciamento de conexoes
- PreparedStatement: Execucao de SQL com parametros
- ResultSet: Leitura de resultados do banco

---

## Estrutura do Projeto

Find2Buy/
├── src/
│ └── br/com/ecommerce/
│ ├── dao/
│ │ ├── ClienteDao.java # CRUD clientes (PostgreSQL)
│ │ ├── ProdutoDao.java # CRUD produtos (PostgreSQL)
│ │ ├── Dao.java # Interface generica
│ │ └── UsuarioDao.java # Interface especifica
│ ├── database/
│ │ ├── DatabaseConnection.java # Conexao com PostgreSQL
│ │ └── TesteConexao.java # Teste de conexao
│ ├── model/
│ │ ├── Usuario.java # Classe abstrata base
│ │ ├── Cliente.java # Cliente (comprador)
│ │ ├── Administrador.java # Administrador
│ │ ├── Produto.java # Produto
│ │ ├── Carrinho.java # Carrinho de compras
│ │ ├── ItemCarrinho.java # Item dentro do carrinho
│ │ └── Pedido.java # Pedido finalizado
│ ├── strategy/
│ │ ├── CalculadoraFrete.java # Interface
│ │ ├── FreteNormal.java # Frete padrao
│ │ └── FreteExpresso.java # Frete rapido
│ ├── view/
│ │ ├── Main.java # Classe principal
│ │ └── SistemaEcommerce.java # Sistema completo
│ └── test/
│ └── TesteCrud.java # Testes unitarios
├── target/ # Arquivos compilados
├── .gitignore
├── pom.xml # Configuracao Maven
├── E-COMMERCE.iml
└── README.md

---

## Configuracao do Banco de Dados

### Script SQL para PostgreSQL

sql
-- Criar banco de dados
CREATE DATABASE ecommerce_db;

-- Conectar ao banco
\c ecommerce_db;

-- Tabela de clientes
CREATE TABLE clientes (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL,
    idade INTEGER NOT NULL,
    cep VARCHAR(10) NOT NULL,
    CONSTRAINT idade_minima CHECK (idade >= 18)
);

-- Tabela de produtos
CREATE TABLE produtos (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT preco_positivo CHECK (preco > 0),
    CONSTRAINT estoque_nao_negativo CHECK (estoque >= 0)
);

-- Dados iniciais (produtos)
INSERT INTO produtos (id, nome, preco, estoque) VALUES
(1, 'Notebook Dell', 3500.00, 10),
(2, 'Mouse Logitech', 150.00, 50),
(3, 'Teclado Mecanico', 250.00, 30),
(4, 'Monitor Samsung', 1200.00, 15),
(5, 'Headset HyperX', 350.00, 20);
Configuracao da Conexao
No arquivo DatabaseConnection.java:

java
private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce_db";
private static final String USER = "postgres";
private static final String PASSWORD = "sua_senha";

---

## Cliente (Comprador)

Funcionalidades


Funcionalidade
Cadastro	Criar conta com nome, email, senha, idade e CEP
Login	Autenticar com email e senha
Listar produtos	Visualizar todos os produtos disponiveis
Adicionar ao carrinho	Selecionar produtos e quantidades
Ver carrinho	Listar itens, calcular subtotal e frete
Escolher frete	Normal (mais barato) ou Expresso (mais rapido)
Finalizar compra	Confirmar pedido e gerar historico
Ver conta	Exibir dados do perfil

## Administrador

Funcionalidade
	
Login	Credenciais fixas: admin / 123456
Listar produtos	Visualizar todos os produtos
Cadastrar produto	Adicionar novos produtos ao sistema
Alterar produto	Modificar nome, preco ou estoque
Remover produto	Excluir produto permanentemente
Listar usuarios	Ver todos os clientes cadastrados

---

## Regras de Negocio

### RN01 - Idade Minima
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Cliente deve ter 18 anos ou mais para realizar cadastro |
| Validacao | Sistema verifica idade informada no momento do cadastro |
| Mensagem | "Cliente precisa ter 18 anos ou mais" |

### RN02 - Email Unico
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Email do cliente deve ser unico no sistema |
| Validacao | Banco de dados possui constraint UNIQUE na coluna email |
| Mensagem | "Email ja cadastrado no sistema" |

### RN03 - Senha Segura
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Senha deve ter no minimo 5 caracteres |
| Validacao | Validado no setter da classe Usuario |
| Mensagem | "Senha invalida - minimo 5 caracteres" |

### RN04 - Nome do Produto
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Nome do produto nao pode ser vazio ou nulo |
| Validacao | Validado no setter da classe Produto |
| Mensagem | "Nome nao pode ser vazio" |

### RN05 - Preco do Produto
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Preco do produto deve ser maior que zero |
| Validacao | CONSTRAINT CHECK no banco de dados |
| Mensagem | "Preco deve ser maior que 0" |

### RN06 - Estoque do Produto
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Estoque do produto nao pode ser negativo |
| Validacao | CONSTRAINT CHECK no banco de dados |
| Mensagem | "Estoque nao pode ser negativo" |

### RN07 - Estoque Insuficiente
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Nao e possivel adicionar quantidade maior que o estoque disponivel |
| Validacao | Verificado no metodo adicionarProduto() da classe Carrinho |
| Mensagem | "Estoque insuficiente" |

### RN08 - Frete Normal
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Calculo do frete baseado no CEP do cliente |
| Sao Paulo (CEP: 01, 02, 03) | R$ 10,00 |
| Regiao Sul (CEP: 8, 9) | R$ 25,00 |
| Demais regioes | R$ 15,00 |
| CEP nao informado | R$ 20,00 |

### RN09 - Frete Expresso
| Propriedade | Descricao |
|-------------|-----------|
| Regra | Calculo do frete expresso baseado no CEP do cliente |
| Sao Paulo (CEP: 01, 02, 03) | R$ 25,00 |
| Regiao Sul (CEP: 8, 9) | R$ 60,00 |
| Demais regioes | R$ 35,00 |
| CEP nao informado | R$ 45,00 |

---

## Tabela Resumo das Regras

| Regra | Descricao | Local da Validacao |
|-------|-----------|-------------------|
| RN01 | Idade minima 18 anos | Cliente.setIdade() |
| RN02 | Email unico | Banco de Dados (UNIQUE) |
| RN03 | Senha minimo 5 caracteres | Usuario.setSenha() |
| RN04 | Nome do produto nao vazio | Produto.setNome() |
| RN05 | Preco maior que zero | Banco de Dados (CHECK) |
| RN06 | Estoque nao negativo | Banco de Dados (CHECK) |
| RN07 | Validacao de estoque | Carrinho.adicionarProduto() |
| RN08 | Frete Normal | FreteNormal.calcular() |
| RN09 | Frete Expresso | FreteExpresso.calcular() |

---

##Design Patterns Utilizados

Pattern	Local	Descricao
Strategy	strategy/	Calculo de frete com diferentes estrategias
DAO	dao/	Isolamento da logica de persistencia
Template Method	Usuario	Classe abstrata com metodos concretos e abstratos

---

## Como Executar o Projeto

Pre-requisitos
Java JDK 11 ou superior

PostgreSQL 14 ou superior

Maven (opcional)

Passo a passo
Clone o repositorio

bash
git clone https://github.com/seu-usuario/find2buy.git
cd find2buy
Configure o banco de dados (scripts acima)

Compile o projeto

bash
javac -cp ".;postgresql-42.7.1.jar" -d . src/br/com/ecommerce/**/*.java
Execute o sistema

bash
java -cp ".;postgresql-42.7.1.jar" br.com.ecommerce.view.Main
Alternativa (IDE)
Abra o projeto em sua IDE preferida (IntelliJ, Eclipse, VS Code)

Certifique-se de adicionar a biblioteca do PostgreSQL JDBC

Localize a classe Main.java no pacote view

Clique em "Run"

---

## Guia Rapido de Uso

### Fluxo de Compra (Cliente)

1. Opcao 5 - Cadastrar cliente
2. Opcao 6 - Login com email e senha
3. Opcao 1 - Listar produtos
4. Opcao 2 - Adicionar produto ao carrinho (informar ID e quantidade)
5. Opcao 3 - Ver carrinho e calcular frete
6. Opcao 4 - Finalizar compra


### Fluxo Admin

1. Opcao 10 - Login Admin (usuario: admin / senha: 123456)
2. Gerenciar produtos pelo menu administrativo
3. Opcao 6 - Logout

---

##Diagrama de Classes

Usuario (abstract)
    ├── Cliente (idade, cep)
    └── Administrador (cargo, setor)

Produto (id, nome, preco, estoque)

Carrinho (1) ---- < (N) ItemCarrinho (N) > ---- (1) Produto

Pedido (1) ---> (1) Usuario
Pedido (1) ---> (N) ItemCarrinho

CalculadoraFrete (interface)
    ├── FreteNormal
    └── FreteExpresso

Dao (interface)
    ├── ClienteDao (implementa)
    └── ProdutoDao (implementa)

---

## Testes

O projeto possui testes unitarios na classe TesteCrud.java localizada no pacote test, validando as operacoes de:

Insercao de clientes

Insercao de produtos

Busca por ID

Listagem completa

Atualizacao de dados

Remocao de registros

---

## Consideracoes Finais

O Find2Buy foi desenvolvido como projeto academico para demonstrar a aplicacao de conceitos fundamentais de Programacao Orientada a Objetos, incluindo:

Heranca e polimorfismo

Encapsulamento e abstracao

Sobrecarga de metodos

Design Patterns (Strategy, DAO, Template Method)

Integracao Java com PostgreSQL (JDBC)

Estruturas de dados (ArrayList)

O sistema atende todos os requisitos propostos, oferecendo uma experiencia completa de compras online, desde o cadastro de usuarios, finalizacao de pedidos com calculo de frete personalizado ate conexao com banco de dados relacional.

---

## Agradecimentos

Agradecemos ao professor pelo suporte durante o desenvolvimento do projeto.
