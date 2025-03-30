# PIA3A Backend

Este é o repositório do backend da aplicação **API3A**, uma API REST simples desenvolvida com Java e Spring Boot.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.3
- Maven
  - Spring Security
  - JWT (Json Web Token)
- JPA / Hibernate
- PostgreSQL (ou banco em memória H2 para testes)
- Lombok

---

## Requisitos

Para rodar este projeto em qualquer sistema operacional (Windows, macOS ou Linux), você precisa ter instalado:

- [Java 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/) (se for usar banco real)
- (Opcional) [Docker](https://www.docker.com/) — para rodar tudo de forma padronizada
- (Opcional) [Postman](https://www.postman.com/) — para testar os endpoints

---

##  Como rodar o projeto

###  1. Clone o repositório

```bash
git clone https://github.com/Batsy13/pia3a-backend.git
cd pia3a-backend
```
### 2. Configure o banco de dados

No arquivo ```src/main/resources/application-dev.properties```, ajuste os dados de conexão:

Postgresql
```properties
# PostgreSQL Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/pia3a
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
# Driver JDBC
spring.datasource.driver-class-name=org.postgresql.Driver
# Dialect with SQL
hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
# DDL
spring.jpa.hibernate.ddl-auto=update
# Show SQL
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.format-sql=true
# Secrec key
api.security.token.secret=${JWT_SECRET:12345}
```

No arquivo ```src/main/resources/application-test.properties```, ajuste os dados de conexão:

H2
```properties
# H2 Connection
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
# H2 Client
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
# Show SQL
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
# Never show stacktrace
server.error.include-stacktrace=never
# Secret key
api.security.token.secret=${JWT_SECRET:12345}
````

E no arquivo ```src/main/resources/application.properties```, ajuste os dados de conexão, aqui você pode alternar entre dev e test:

```properties
# dev está para PostgreSQL e test está para H2
spring.profiles.active=${APP_PROFILE:dev}
# Disable open in view
spring.jpa.open-in-view=false
# Define which *origins* can make requests to an API
cors.origins=${CORS_ORIGINS:http://localhost:5173,http://localhost:3000,http://localhost:8080,http://127.0.0.1:5500}
```

## 3. Compile o projeto

```bash
./mvnw clean install     # Linux ou macOS
mvnw.cmd clean install   # Windows
```

## 4. Rode a aplicação

```bash
./mvnw spring-boot:run     # Linux/macOS
mvnw.cmd spring-boot:run   # Windows
```
