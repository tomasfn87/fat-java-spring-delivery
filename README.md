# 📦 DeliveryTech API

API RESTful desenvolvida com Spring Boot 3 e Java 21 para gerenciar um sistema de delivery completo. Este projeto simula as funcionalidades principais de plataformas como iFood e Uber Eats, incluindo autenticação JWT, cache, monitoramento, CI/CD e muito mais.

---

## 🚀 Funcionalidades

- Cadastro e login de usuários com JWT
- Controle de acesso por perfis (CLIENTE, RESTAURANTE, ADMIN, ENTREGADOR)
- Cadastro de clientes, restaurantes, produtos e pedidos
- Listagem de produtos por restaurante
- Criação de pedidos com itens e cálculo do total
- Atualização de status de pedido
- Cache com Spring Cache
- Testes automatizados com JUnit e Mockito
- Documentação com Swagger/OpenAPI
- Banco de dados em memória com H2
- Containerização com Docker e orquestração com Docker Compose
- Pipeline CI/CD com GitHub Actions (sugestão)

---

## 🧪 Tecnologias e Dependências

- **Java 21 & Spring Boot 3.2.5**: Core do projeto e framework principal.
- **Spring Data JPA**: Abstração de persistência e integração com banco de dados.
- **Spring Security & JJWT**: Segurança, controle de acesso e autenticação via tokens JWT.
- **Spring Validation**: Validação de dados de entrada com Bean Validation.
- **H2 Database**: Banco de dados SQL em memória para desenvolvimento e testes.
- **Redis & Spring Cache**: Gerenciamento de cache para otimização de performance.
- **Lombok**: Redução de código boilerplate (Getters, Setters, Constructors).
- **SpringDoc OpenAPI**: Geração automática de documentação Swagger.
- **Spring Actuator & Micrometer**: Monitoramento, métricas e rastreamento (Brave/Zipkin).
- **JUnit 5 & Mockito**: Ferramentas para testes unitários e mocks.

---

## 📄 Documentação da API

Acesse via Swagger:

```
http://localhost:8080/swagger-ui.html
```

---

## ⚙️ Como Rodar o Projeto - Comandos Principais do Maven (Spring Boot)

### 🚀 Ciclo de Vida e Build

> Estes comandos lidam com a compilação e empacotamento do seu arquivo .jar.

- `./mvnw clean`: Limpa a pasta target, removendo builds antigos e arquivos temporários.
- `./mvnw compile`: Compila o código-fonte do projeto.
- `./mvnw package`: Compila o código e gera o arquivo executável (geralmente um .jar) na pasta target.
- `./mvnw install`: Faz o package e instala o seu .jar no repositório local do Maven (~/.m2). Útil se outros projetos locais dependem deste.
- `./mvnw clean package -DskipTests`: Gera o .jar pulando a execução dos testes unitários (comum em deploys rápidos).

### 🏃 Execução (Spring Boot)

> Comandos específicos do plugin do Spring Boot.

- `./mvnw spring-boot:run`: Inicia a aplicação diretamente pelo Maven.
- `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`: Inicia a aplicação usando um profile específico (ex: application-dev.properties).

### 🧪 Testes e Qualidade

- `./mvnw test`: Executa todos os testes unitários do projeto.
- `./mvnw test -Dtest=NomeDaClasseTest`: Executa apenas uma classe de teste específica.
- `./mvnw verify`: Executa testes de integração e verifica a integridade do pacote gerado.

### 📦 Gerenciamento de Dependências

> Como você está lidando com várias bibliotecas, estes ajudam a resolver conflitos:

- `./mvnw dependency:tree`: Exibe a árvore hierárquica de todas as dependências. Essencial para encontrar conflitos de versão (o famoso "Jar Hell").
- `./mvnw dependency:purge-local-repository`: Remove dependências corrompidas do seu cache local e as baixa novamente.

---

## 🧪 Endpoints de Teste

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/clientes`
- `POST /api/pedidos`

---

## 🐳 Docker

```shell
docker run --rm -it -p 8080:8080 -v $(pwd)/data.mv.db:/app/data.mv.db fat-java-spring-delivery
```

> Alterar o banco de dados H2 de Embed para Server e usar o valor `jdbc:h2:file:/app/data` no campo JDBC URL.

---

## 📬 Contato

[tomasfnalle@protonmail.com] - [https://www.linkedin.com/in/tomas-foch-nalle]
