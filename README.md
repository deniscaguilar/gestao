### Sobre o projeto ###

Aplicação Java Web. Além de código fonte e configurações, segue também a documentação descrevendo como o projeto foi
desenvolvido, e o que foi adotado para implementar o teste solicitado.

Essa aplicação foi desenvolvida seguindo os modelos de arquitetura MVC e REST. Na solução também tem um banco de dados
MySQL que sobe via docker.

### Tecnologias Utilizadas ###

* Java versão 17.
* Lombok: Utilizada para gerar os getters e setter das classes de domínio
* JPA / Hibernate: mapeamento de entidades persistentes em pojos de domínio.
* Bean Validations: framework para definição de regras de validação em entidades JPA via anotações.
* Logback: geração de logs.
* Spring Data JPA: Tecnologia utilizada gerar parte do código relacionado a camada de persistência. Na aplicação foi
  escrito os contratos de persistência, que realizam a criação dos comandos de manipulação.
* Spring Web MVC: framework web usado como solução MVC para a definição de componentes seguindo o modelo de arquitetura
  REST
* Estrutura de pacotes e classes baseada no modelo Clean
  Architecture (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Camadas e pacotes ###

* br.com.dca.gestao.configurations: Pacote com as configurações necessárias para o projeto.
* br.com.dca.gestao.entities: Pacote contendo as entidades persistentes, mapeadas com anotações JPA.
* br.com.dca.gestao.exceptions: Pacote contendo as exceções customizadas.
* br.com.dca.gestao.repositories: Pacote contendo as interfaces de persistência.
* br.com.dca.gestao.gateways.impl: Pacote contendo as implementações para orquestrar os componentes de
  acesso a dados.
* br.com.dca.gestao.controllers: Pacote contendo os componentes Controller e serviços REST.
* br.com.dca.gestao.response: Pacote contendo os POJOS de saída que usamos nos serviços REST
* br.com.dca.gestao.request: Pacote contendo os POJOS de entrada que que usamos nos serviços REST
* br.com.dca.gestao.usecases: Pacote contendo os componentes de negócio, que são responsáveis por realizar
  todas as validações de negócio, orquestrar os componentes de acesso a dados, transação com banco de dados e eventuais
  validações.
* br.com.dca.gestao.converters: Pacote contendo os objetos utilizados para realizar as conversões.

### Tecnologias Adicionais ###

* Banco de dados: MySQL,o banco de dados escolhido foi o MySQL e ele é criado no start da aplicação e destruido quando a
  aplicação for derrubada. No start é criada a tabela pessoa.
* Testes: para os testes unitários do use case utilizamos Junit5 com Mockito. Já os testes dos serviços REST contam com
  Spring Web MVC para mock da infra-estrutura web. Os testes foram disponibilizados na estrutura
  src/test/java.
* Spring Boot: tecnologia utilizada para criar um ambiente embutido de execução, utilizei essa tecnologia para
  simplificar o uso do Spring e para controlar o escopo do banco. No arquivo src/main/resources/application.yml constam
  algumas propriedades do Spring Boot para o projeto.
* Undertown embutido: disponibilizado pelo Spring Boot escolhido por ter um melhor gerenciamento de memória.
* Maven: gestão de ciclo de vida e build do projeto.
* Swagger: documentação e execução de apis

### Pré-requisitos ###

* JDK - versão 17 do Java;
* Qualquer IDE Java com suporte ao Maven;
* Maven - para build e dependências.
* Docker

Após baixar os fontes, para executar a aplicação seguimos os seguintes passos:

$ entrar na raiz-do-projeto/infra-as-code (cd gestao/infra-as-code/)

* Para Iniciar Infraestura:              
  $ docker-compose up -d -> Para subir a infraestrutura em background              
  $ docker-compose up -> Para subir a infraestrutura sem ser em background            
  O docker-compose irá subir o banco de dados MySQL na porta 3306.              
  Sugiro subir sem ser em background para acompanhar o start de toda a infra para após a infra estiver UP subir a
  aplicação.

* Para acessar o banco MySQL é necessário algum cliente do banco de dados, a string de conexão é:
  host: localhost          
  porta: 3306              
  database: dbGestao
  Usuário: user_dca     
  Senha: pwd_dca

* Para Iniciar Aplicação: Esse step deve ser feito após a infraestrutura já toda disponivel pois dependemos do MySQL
  $ bash start-application.sh -> esse é o processo para rodar no mac.
  $ sh start-application.sh -> esse é o processo para rodar no linux.
  Esse arquivo irá rodar o maven clean install e rodar os testes e após finalizar irá roda o java -jar para subir a
  aplicação

Para visualizar a apis abra o browser de sua preferência e digite:

http://localhost:8080/swagger-ui/index.html

No swagger vai conter:

* api POST utilizada para cadastrar uma pessoa. No Swagger vai ser possível ver toda a documentação da api.
