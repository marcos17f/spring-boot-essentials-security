# Gym Management API 🏋️‍♂️💻

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

Uma API RESTful completa desenvolvida para simular o backend de uma plataforma de gestão de academia. O foco principal deste projeto é a aplicação rigorosa de segurança, boas práticas de arquitetura de software e resolução de desafios estruturais no ecossistema Java.

## 🚀 O Projeto

Este sistema gerencia dados essenciais do dia a dia de uma academia, como cadastro de alunos, treinos de musculação, exercícios e avaliações físicas (peso, altura, percentual de gordura corporal, etc.). 

O diferencial do projeto é a sua base arquitetural, construída para ser escalável, resiliente e segura, indo muito além de um CRUD básico.

## 🛠️ Tecnologias Utilizadas

*   **Java**
*   **Spring Boot** (Web, Data JPA, Security)
*   **Banco de Dados:** MySQL
*   **Autenticação:** JSON Web Tokens (JWT)
*   **Mapeamento Objeto-Relacional:** Hibernate
*   **Testes de API:** Postman / Insomnia

## 🏗️ Arquitetura e Boas Práticas Implementadas

*   **Autenticação Stateless com JWT:** Implementação de segurança de ponta a ponta. O `JwtAuthenticationFilter` intercepta as requisições, validando o token Bearer e garantindo que rotas sensíveis sejam acessadas apenas por usuários autenticados e com as permissões corretas (Roles). Senhas são armazenadas com hash utilizando o algoritmo BCrypt.
*   **Padrão DTO (Data Transfer Object):** Resolução definitiva para problemas de carregamento preguiçoso do Hibernate, como a `LazyInitializationException`. A separação entre as entidades de banco de dados e os objetos de transferência garante que a API exponha apenas os dados necessários para o frontend, aumentando a segurança e o encapsulamento.
*   **Tratamento Global de Exceções:** Utilização do `@ControllerAdvice` para capturar exceções globalmente. Isso garante que a API retorne payloads de erro consistentes e padronizados, melhorando significativamente a experiência de integração do cliente (frontend/mobile).

## ⚙️ Como Executar na sua Máquina

### Pré-requisitos
*   Java JDK 17 ou superior
*   MySQL Server rodando localmente
*   Maven


