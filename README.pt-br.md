# Sistema de Gestão de Pedidos Online
🌍 Idiomas: [English](README.md) | [Português](README.pt-br.md)

  
![Design do Sistema](/Order%20Manger%20System%20Design.png)

## Descrição:
Desenvolver um sistema de gestão de pedidos para restaurantes que permita aos clientes realizar pedidos de produtos. O sistema contará com uma API RESTful utilizando microserviços, sendo que cada microserviço será responsável por funcionalidades específicas.

### Instalação
Clone o repositório:

```bash
$ git clone https://github.com/Guilherme-Beckman/order-manager.git
````

## Tecnologias Utilizadas:

* Java
* Spring Boot
* Spring Security
* Postman para documentação da API
* MongoDB para armazenamento de dados
* Docker para conteinerização das aplicações
* RabbitMQ para comunicação entre microserviços

## Funcionalidades do Sistema:

### Autenticação e Autorização:

* Implementar autenticação usando Spring Security com tokens JWT.
* Definir diferentes papéis de usuário (cliente, restaurante, administrador) e controlar o acesso à API com base nesses papéis.

### Gestão de Usuários:

* Permitir que usuários se registrem e façam login no sistema.
* Clientes podem visualizar seu perfil, atualizar informações e consultar histórico de pedidos.
* Restaurantes podem gerenciar seus cardápios, disponibilidade de produtos e status dos pedidos.

### Gestão de Pedidos:

* Clientes podem navegar pelos cardápios, adicionar itens ao carrinho e realizar pedidos.
* Restaurantes recebem novos pedidos e podem aceitar, recusar ou atualizar o status.
* Implementar um sistema de fila com RabbitMQ para processar pedidos de forma assíncrona e escalável.

### Documentação da API:

* Gerar documentação automática da API usando Postman para facilitar a integração com o frontend e fornecer uma referência clara para desenvolvedores.

### Banco de Dados

O projeto utiliza MongoDB como banco de dados. MongoDB é um banco NoSQL que oferece flexibilidade e escalabilidade para armazenar e gerenciar dados.

## Considerações Finais:

Este projeto pode ser expandido e personalizado de acordo com suas necessidades e interesses. É possível adicionar funcionalidades adicionais, como suporte a pagamentos online, integração com sistemas de entrega, análise de dados de vendas, entre outros. O uso de tecnologias modernas como microserviços, containers Docker e comunicação assíncrona com RabbitMQ torna o sistema robusto, escalável e fácil de manter.

