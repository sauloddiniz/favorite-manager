## Favorite Manager

#### O 'Favorite Manager' foi desenvolvido com foco em atender as necessidades da Magalu para gerenciar os produtos favoritos de seus clientes. Esta aplicação permite criar, listar, atualizar e remover clientes, além de gerenciar a lista de produtos favoritos dos mesmos. Há regras de negócio que garantem a validação das operações, como evitar duplicidades de e-mails e listas de favoritos únicas por cliente.

## <a name="guia-de-uso"></a>Guia de uso
### <a name="pre-requisitos-local"></a>Pré-requisitos ambiente local
Antes de começar, certifique-se de ter os seguintes requisitos instalados no seu sistema:

- Java 17: Certifique-se de ter o JDK (Java Development Kit) 17 ou uma versão superior instalada no seu sistema.
- Gradle: O projeto requer o Gradle para gerenciamento de dependências e compilação. Certifique-se de ter o Gradle instalado no seu sistema.
- Docker: O projeto requer o Docker para executar o banco de dados PostgreSQL em modo de produção. Certifique-se de ter o Docker instalado no seu sistema.

### <a name="executar-api"></a>Executar
### <a name="ambiente-local"></a>Ambiente local
**No diretório raiz do projeto, execute os comandos na ordem descrita abaixo. Este profile utiliza o banco de dados H2, facilitando o início rápido (starter) do projeto.**
````
gradle wrapper
````
````
gradle bootRun --args='--spring.profiles.active=local'
````

### <a name="ambiente-local"></a>Ambiente producao
**No diretório raiz do projeto, execute os comandos na ordem descrita abaixo. Este profile utiliza o banco de postgres.**
````
gradle wrapper
````
````
gradle build
````
````
docker-compose up
````

### Documentacao dos controladores
Com o projeto em execução
* [Swagger-local](http://localhost:8080/swagger-ui/index.html)