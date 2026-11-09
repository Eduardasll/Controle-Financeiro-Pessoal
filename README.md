# Controle Financeiro Pessoal

Sistema de controle financeiro pessoal desenvolvido em **Java**, com execução via **terminal**. Permite gerenciar receitas e despesas, consultar saldo, filtrar movimentações por categoria e visualizar resumos financeiros.

Projeto focado na prática de orientação a objetos, coleções e boas práticas de programação.

## Problema que o projeto resolve

Uma pessoa quer registrar o dinheiro que recebe e gasta para saber quanto ainda tem disponível. O programa permite cadastrar **receitas** (entradas) e **despesas** (saídas), visualizar as movimentações e consultar o saldo.

> **Exemplo:** você registra uma receita de R$ 2.500,00, uma despesa de R$ 300,00 e outra de R$ 50,00. O sistema mostra saldo de **R$ 2.150,00**.

## Funcionalidades

| Opção | Funcionalidade | O que faz |
|---|---|---|
| 1 | Cadastrar receita | Registra uma entrada de dinheiro (descrição, valor e categoria). |
| 2 | Cadastrar despesa | Registra uma saída de dinheiro (descrição, valor e categoria). |
| 3 | Listar movimentações | Mostra ID, tipo, descrição, categoria e valor de cada registro. |
| 4 | Ver saldo | Soma as receitas e subtrai as despesas. |
| 5 | Filtrar por categoria | Exibe apenas as movimentações de uma categoria. |
| 6 | Excluir movimentação | Remove um registro pelo ID. |
| 7 | Ver resumo | Mostra total de receitas, total de despesas e saldo. |
| 0 | Sair | Encerra o programa. |

## Pré-requisitos

- **JDK 17 ou 21** instalado ([Adoptium](https://adoptium.net/) ou outro).
- Terminal (Prompt de Comando, PowerShell, bash etc.).
- Opcional: IntelliJ IDEA Community ou VS Code para abrir o projeto.

Verifique sua versão do Java com:

```bash
java -version
```

## Como executar

A partir da raiz do projeto:

```bash
# 1. Compilar o código para a pasta out/
javac -d out $(find src/main/java -name "*.java")

# 2. Executar a aplicação
java -cp out br.com.portfolio.financeiro.Main
```

> No Windows (PowerShell), substitua o passo 1 por:
> ```powershell
> javac -d out (Get-ChildItem -Recurse src/main/java -Filter *.java).FullName
> ```

## Exemplo de uso no terminal

```text
===== CONTROLE FINANCEIRO =====
1 - Cadastrar receita
2 - Cadastrar despesa
3 - Listar movimentacoes
4 - Ver saldo
5 - Filtrar por categoria
6 - Excluir movimentacao
7 - Ver resumo
0 - Sair
Escolha: 1
Descricao: Salario
Valor: 2500
Categoria: Trabalho
Cadastrada com sucesso: ID 1 | RECEITA  | Salario         | Trabalho        | R$ 2500

Escolha: 2
Descricao: Supermercado
Valor: 300
Categoria: Alimentacao
Cadastrada com sucesso: ID 2 | DESPESA  | Supermercado    | Alimentacao     | R$ 300

Escolha: 4
Saldo atual: R$ 2200
```

## Estrutura do projeto

```text
Controle-Financeiro-Pessoal/
├── README.md
├── .gitignore
└── src/
    └── main/
        └── java/
            └── br/com/portfolio/financeiro/
                ├── Main.java                 # Menu e interação com o terminal
                ├── model/
                │   ├── Movimentacao.java     # Registro de entrada ou saída
                │   └── TipoMovimentacao.java # Enum: RECEITA ou DESPESA
                ├── repository/
                │   └── MovimentacaoRepository.java # Armazena os dados em memória
                └── service/
                    └── FinanceiroService.java      # Regras de negócio e cálculos
```

### Responsabilidade de cada classe

- **`Main`** — exibe o menu, lê entradas e imprime resultados. Não calcula saldo.
- **`Movimentacao`** — representa um registro de entrada ou saída.
- **`TipoMovimentacao`** — enum com os valores `RECEITA` e `DESPESA`.
- **`MovimentacaoRepository`** — guarda as movimentações numa lista e as busca/remove por ID.
- **`FinanceiroService`** — aplica as regras: cadastro, validação, exclusão, filtro e cálculo de saldo.

## Regras de negócio

1. Toda movimentação tem **ID único**, descrição não vazia, valor maior que zero, tipo e categoria.
2. Os tipos são `RECEITA` e `DESPESA`.
3. O valor é sempre **positivo**; o tipo define se entra ou sai do saldo.
4. O saldo é `receitas - despesas` e **pode ser negativo**.
5. Não é possível excluir um ID inexistente (mensagem amigável).
6. Uma opção inválida do menu **não** encerra o programa.
7. Os dados existem **somente enquanto o programa está aberto** (sem banco de dados nesta versão).

## Decisões técnicas

- **`BigDecimal`** para valores monetários, evitando os erros de arredondamento do `double`.
- **`ArrayList`** para armazenamento simples em memória, sem banco de dados.
- Separação em camadas (`model`, `repository`, `service` e interface) para manter o código organizado e fácil de evoluir.

## Possíveis melhorias futuras

- Salvar e carregar movimentações em arquivo CSV.
- Registrar a data de cada movimentação com `LocalDate`.
- Categorias predefinidas com `enum`.
- Testes automatizados com JUnit para o cálculo do saldo.

## Licença

Distribuído sob a licença MIT. Veja o arquivo [LICENSE](LICENSE).
