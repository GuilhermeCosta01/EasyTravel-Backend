# 🧳 EasyTravel Backend

Este é o repositório do **Backend da aplicação EasyTravel**, uma plataforma de gerenciamento de pacotes de viagem com autenticação, persistência de dados e integração com banco de dados na nuvem.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
  - Spring Security
  - Spring Data JPA
  - Swagger (OpenAPI)
  - MapStruct
  - **SQL Server** (Azure SQL Database)
  - **Azure App Service** (Linux)
  - **GitHub Actions** (CI/CD automatizado)

---

## 🧪 Como rodar localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/GuilhermeCosta01/EasyTravel-Backend.git
   cd EasyTravel-Backend

2. Configure o arquivo application.properties com suas credenciais do banco de dados:

    spring.datasource.url=jdbc:sqlserver://<seu-servidor>.database.windows.net:1433;database=<seu-banco>
    spring.datasource.username=<usuario>
    spring.datasource.password=<senha>

3. Execute a aplicação:
  
    ./mvnw spring-boot:run

4. Acesse a documentação da API:

    http://localhost:8080/swagger-ui.html

---

## ☁️ **Deploy na Azure:**

    O backend está hospedado no Azure App Service com Java 21 em ambiente Linux.
    O banco de dados está no Azure SQL Database.
    O deploy é feito automaticamente via GitHub Actions na branch main.

## 🔗 **Links úteis:**

  🔗 Repositório: EasyTravel-Backend
  🔗 API pública: https://back-et.azurewebsites.net
  🔗 Swagger: https://back-et.azurewebsites.net/swagger-ui.html

  

## 📬 **Contato**
Em caso de dúvidas ou sugestões, sinta-se à vontade para abrir uma issue ou entrar em contato!
