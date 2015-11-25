# Fase 3 TODOS
## Part 1 - Trabalho
### Use cases propostos (Fase 1)
- Login no sistema
- Requisitar encurtamento de um link
    - Link personalizado
    - Link privado
    - Link temporario
- Requisitar estatísticas de um link
- Acessar API
- Acessar um link encurtado
- Suporte Técnico

### Use cases implementados
- Login no sistema
- Requisitar encurtamento de um link
    - Link personalizado
    - Link privado
    - Link temporario
    - Link encurtado logado
    - Link encurtado não logado
- Acessar um link encurtado

### Statecharts propostos (Fase 2)
- Login no sistema
- Acessar um link encurtado
- Requesitar link temporario
- Requesitar link privado

### Outros use cases que não estão no statecharts 
- Requisitar link personalizado
- Requisitar link encurtado logado
- Requisitar link encurtado não logado

##Parte 2 - Documentação
- Dados gerados pelo JUnit
- Sequência percorrida pelos testes citatos acima
- Relatório de erros
- Relatorio de análise
- Relatório de problemas encontrados na documentação

# Divisão do trabalho

##Time Dev (2 pessoas)
- Criar os testes no JUnit segundo a linha dos use cases e statecharts
- Fazer o teste de classe aplicado a classe **Tunnel.java**
- Colher os dados gerados pelo JUnit e documentar
- Relatório de erros encontrados nos testes acima
- Relatório dos problemas encontrados na documentação do design e na implementação fornecidas

##Time Doc (3 pessoas)
- Fazer os testes a partir dos Statecharts
- Fazer os testes a dos outros use cases
- Fazer o teste de classe aplicado a classe **Tunnel.java**
- Sequencia percorrida nos testes de use cases e statecharts
- Relatório de erros encontrados nos testes acima
- Relatório de analise comparativa (vide enunciado da fase 3)


# Anotações gerais

O Sommervile fala um pouco sobre testes estruturais na pagina **585** no ultimo parágrafo. O conjunto de testes estrutural visa todos caminhos lógicos possiveis que o programa pode resultar. Quando estiver escrevendo casos de teste para um dado método escreva um método de teste para cada caminho individual que aquele médoto possa ser, a grosso modo temos :
```
         |---->Caminho 1 ----->TesteMetodo1Caminho1
         |---->Caminho 2 ----->TesteMetodo1Caminho2
Método 1 |---->Caminho 3 ----->TesteMetodo1Caminho3
```