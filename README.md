# Favorite Manager

O `Favorite Manager` foi desenvolvido para atender às necessidades da **Magalu** no gerenciamento dos produtos favoritos de seus clientes. Esta aplicação permite criar, listar, atualizar e remover clientes, além de gerenciar a lista de produtos favoritos. Regras de negócio garantem a correta validação das operações, como evitar duplicidade de e-mails e listas únicas de favoritos por cliente.

---

## Guia de Uso

### Pré-requisitos para ambiente local

Antes de começar, certifique-se de que o seu ambiente possui as seguintes ferramentas configuradas:

- **Java 17**: Certifique-se de ter o JDK (Java Development Kit) 17 ou uma versão superior instalado no sistema.
- **Gradle**: Necessário para o gerenciamento de dependências e compilação do projeto. [Guia de instalação do Gradle](https://gradle.org/install).
- **Docker**: Para o ambiente local e de produção, o projeto utiliza um banco de dados **PostgreSQL** e o **WireMock** configurados via Docker. [Guia de instalação do Docker](https://docs.docker.com/get-docker/).

---

### Executar a API

#### Ambiente local
Para execução local, o projeto requer o banco de **H2** (banco em memória) no perfil **local** e um serviço do **WireMock** em containers Docker.

1. No diretório raiz do projeto, inicie os containers necessários:
   ```bash
   docker-compose -f docker-compose.local.yaml up
   ```

   Esse comando levantará o ambiente com **WireMock** configurado.

2. Execute a aplicação:
   ```bash
   ./gradlew bootRun --args='--spring.profiles.active=local'
   ```

Este comando iniciará a aplicação localmente com o banco **H2** e o endpoint simulado pelo **WireMock**.

---

#### Ambiente de produção
Para execução em ambiente de produção, o projeto utiliza o banco de dados **PostgreSQL**. Siga os passos abaixo:

1. Construa o projeto:
   ```bash
   ./gradlew build
   ```

2. Inicie os containers Docker:
   - Certifique-se de que o Docker está instalado e em execução no sistema.
   - No diretório raiz do projeto, execute:
     ```bash
     docker-compose up
     ```

---

### Documentação dos Controladores

Com o projeto em execução, acesse a documentação da API no **Swagger** através do seguinte link:

- **Ambiente local**:
   - [Swagger UI - Local](http://localhost:8080/swagger-ui/index.html)

---

### Observações

- Certifique-se de que o `JAVA_HOME` esteja configurado corretamente no sistema para que o Gradle funcione sem problemas.
- Para o ambiente local, o arquivo `docker-compose.local.yaml` contém a configuração necessária para o serviço do **WireMock**, que é usado para simular endpoints externos.
- Caso tenha dúvidas ou dificuldades, consulte a [documentação do Gradle](https://docs.gradle.org/current/userguide/userguide.html) ou [documentação do Docker](https://docs.docker.com/).

---
